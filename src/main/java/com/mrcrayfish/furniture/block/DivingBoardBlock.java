package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class DivingBoardBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<DivingBoardPart> PART = EnumProperty.create("part", DivingBoardPart.class);

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public DivingBoardBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(PART, DivingBoardPart.BASE).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 -> {
            final VoxelShape[] BOARD = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 4, 0, 15, 6, 16), Direction.SOUTH));
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BOARD[state.get(DIRECTION).getHorizontalIndex()]);
            if(state1.get(PART) == DivingBoardPart.BASE)
            {
                final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3, 0, 2, 13, 4, 15), Direction.SOUTH));
                shapes.add(BASE[state.get(DIRECTION).getHorizontalIndex()]);
            }
            return VoxelShapeHelper.combineAll(shapes);
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        Direction direction = state.get(DIRECTION);
        DivingBoardPart part = state.get(PART);
        BlockPos otherPos = part == DivingBoardPart.BASE ? pos.offset(direction) : pos.offset(direction.getOpposite());
        BlockState otherBlockState = worldIn.getBlockState(otherPos);
        if(otherBlockState.getBlock() == this && otherBlockState.get(PART) != part)
        {
            worldIn.setBlockState(otherPos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, otherPos, Block.getStateId(otherBlockState));
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.offset(placer.getHorizontalFacing()), this.getDefaultState().with(PART, DivingBoardPart.BOARD).with(DIRECTION, placer.getHorizontalFacing()), 3);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.offset(state.get(DIRECTION))).isAir();
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(worldIn.getBlockState(pos).get(PART) != DivingBoardPart.BOARD)
        {
            return;
        }

        if(entityIn instanceof LivingEntity)
        {
            float strength = 5.0F;
            float maxHeight = 8F;
            float height = entityIn.fallDistance * strength;
            if(height > 0 && !entityIn.isSneaking())
            {
                if(height > maxHeight - 0.25F) height = maxHeight - 0.25F;
                entityIn.setMotion(entityIn.getMotion().mul(1.0, 0.0, 1.0));
                entityIn.addVelocity(0, Math.sqrt(0.22 * (height + 0.25F)), 0);
                if(worldIn.isRemote)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, entityIn.prevPosX, entityIn.prevPosY, entityIn.prevPosZ, 1.0, 1.0, 1.0);
                    }
                }
                else
                {
                    worldIn.playSound(null, pos, ModSounds.BLOCK_DIVING_BOARD_BOUNCE.get(), SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.1F + 1.0F);
                }
            }
            entityIn.fallDistance = 0;
        }
    }

    @Override
    public void onLanded(IBlockReader worldIn, Entity entityIn)
    {
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerWorld worldserver, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles)
    {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(PART);
    }

    public enum DivingBoardPart implements IStringSerializable
    {
        BASE,
        BOARD;

        @Override
        public String toString()
        {
            return this.getString();
        }

        @Override
        public String getString()
        {
            return this == BASE ? "base" : "board";
        }
    }
}
