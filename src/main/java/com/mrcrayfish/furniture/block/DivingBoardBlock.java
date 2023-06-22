package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class DivingBoardBlock extends FurnitureHorizontalBlock
{
    public static final EnumProperty<DivingBoardPart> PART = EnumProperty.create("part", DivingBoardPart.class);

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public DivingBoardBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, DivingBoardPart.BASE).setValue(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] BOARD = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 4, 0, 15, 6, 16), Direction.SOUTH));
        final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(3, 0, 2, 13, 4, 15), Direction.SOUTH));
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BOARD[state.getValue(DIRECTION).get2DDataValue()]);
            if(state.getValue(PART) == DivingBoardPart.BASE)
            {
                shapes.add(BASE[state.getValue(DIRECTION).get2DDataValue()]);
            }
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        Direction direction = state.getValue(DIRECTION);
        DivingBoardPart part = state.getValue(PART);
        BlockPos otherPos = part == DivingBoardPart.BASE ? pos.relative(direction) : pos.relative(direction.getOpposite());
        BlockState otherBlockState = level.getBlockState(otherPos);
        if(otherBlockState.getBlock() == this && otherBlockState.getValue(PART) != part)
        {
            level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(player, 2001, otherPos, Block.getId(otherBlockState));
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        level.setBlock(pos.relative(placer.getDirection()), this.defaultBlockState().setValue(PART, DivingBoardPart.BOARD).setValue(DIRECTION, placer.getDirection()), 3);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.relative(state.getValue(DIRECTION))).isAir();
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(level.getBlockState(pos).getValue(PART) != DivingBoardPart.BOARD)
        {
            return;
        }

        if(entityIn instanceof LivingEntity)
        {
            float strength = 5.0F;
            float maxHeight = 8F;
            float height = entityIn.fallDistance * strength;
            if(height > 0 && !entityIn.isShiftKeyDown())
            {
                if(height > maxHeight - 0.25F) height = maxHeight - 0.25F;
                entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                entityIn.push(0, Math.sqrt(0.22 * (height + 0.25F)), 0);
                if(level.isClientSide)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        level.addParticle(ParticleTypes.ENTITY_EFFECT, entityIn.xo, entityIn.yo, entityIn.zo, 1.0, 1.0, 1.0);
                    }
                }
                else
                {
                    level.playSound(null, pos, ModSounds.BLOCK_DIVING_BOARD_BOUNCE.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 1.0F);
                }
            }
            entityIn.fallDistance = 0;
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn)
    {
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles)
    {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    public enum DivingBoardPart implements StringRepresentable
    {
        BASE,
        BOARD;

        @Override
        public String toString()
        {
            return this.getSerializedName();
        }

        @Override
        public String getSerializedName()
        {
            return this == BASE ? "base" : "board";
        }
    }
}
