package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.tileentity.TrampolineBlockEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class TrampolineBlock extends FurnitureBlock implements EntityBlock
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty CORNER_NORTH_WEST = BooleanProperty.create("corner_north_west");
    public static final BooleanProperty CORNER_NORTH_EAST = BooleanProperty.create("corner_north_east");
    public static final BooleanProperty CORNER_SOUTH_EAST = BooleanProperty.create("corner_south_east");
    public static final BooleanProperty CORNER_SOUTH_WEST = BooleanProperty.create("corner_south_west");

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public TrampolineBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(CORNER_NORTH_WEST, false).setValue(CORNER_NORTH_EAST, false).setValue(CORNER_SOUTH_EAST, false).setValue(CORNER_SOUTH_WEST, false));
    }

    private VoxelShape getShape(BlockState state)
    {
        if(SHAPES.containsKey(state))
        {
            return SHAPES.get(state);
        }
        final VoxelShape BOTTOM_LEFT_SUPPORT_SHORT = Block.box(1, 0, 1, 4, 3, 15);
        final VoxelShape BOTTOM_LEFT_SUPPORT_LONG = Block.box(1, 0, 0, 4, 3, 16);
        final VoxelShape BOTTOM_LEFT_SUPPORT_NORTH = Block.box(1, 0, 0, 4, 3, 15);
        final VoxelShape BOTTOM_LEFT_SUPPORT_SOUTH = Block.box(1, 0, 1, 4, 3, 16);
        final VoxelShape BACK_LEFT_LEG = Block.box(1, 3, 1, 4, 13, 4);
        final VoxelShape FRONT_LEFT_LEG = Block.box(1, 3, 12, 4, 13, 15);
        final VoxelShape TOP = Block.box(0, 13, 0, 16, 16, 16);
        final VoxelShape BOTTOM_RIGHT_SUPPORT_SHORT = Block.box(12, 0, 1, 15, 3, 15);
        final VoxelShape BOTTOM_RIGHT_SUPPORT_LONG = Block.box(12, 0, 0, 15, 3, 16);
        final VoxelShape BOTTOM_RIGHT_SUPPORT_NORTH = Block.box(12, 0, 0, 15, 3, 15);
        final VoxelShape BOTTOM_RIGHT_SUPPORT_SOUTH = Block.box(12, 0, 1, 15, 3, 16);
        final VoxelShape FRONT_RIGHT_LEG = Block.box(12, 3, 12, 15, 13, 15);
        final VoxelShape BACK_RIGHT_LEG = Block.box(12, 3, 1, 15, 13, 4);
        final VoxelShape NORTH_WEST_CORNER_SUPPORT = Block.box(1, 0, 0, 4, 3, 4);
        final VoxelShape NORTH_EAST_CORNER_SUPPORT = Block.box(12, 0, 0, 15, 3, 4);
        final VoxelShape SOUTH_EAST_CORNER_SUPPORT = Block.box(12, 0, 12, 15, 3, 16);
        final VoxelShape SOUTH_WEST_CORNER_SUPPORT = Block.box(1, 0, 12, 4, 3, 16);

        boolean north = state.getValue(NORTH);
        boolean east = state.getValue(EAST);
        boolean south = state.getValue(SOUTH);
        boolean west = state.getValue(WEST);
        boolean cornerNorthWest = state.getValue(CORNER_NORTH_WEST);
        boolean cornerNorthEast = state.getValue(CORNER_NORTH_EAST);
        boolean cornerSouthEast = state.getValue(CORNER_SOUTH_EAST);
        boolean cornerSouthWest = state.getValue(CORNER_SOUTH_WEST);

        List<VoxelShape> shapes = new ArrayList<>();
        shapes.add(TOP);

        int count = 0;
        count += north ? 1 : 0;
        count += east ? 1 : 0;
        count += south ? 1 : 0;
        count += west ? 1 : 0;

        if(count >= 2)
        {
            if(north && !east && south && !west)
            {
                shapes.add(BOTTOM_LEFT_SUPPORT_LONG);
                shapes.add(BOTTOM_RIGHT_SUPPORT_LONG);
                VoxelShape shape = VoxelShapeHelper.combineAll(shapes);
                SHAPES.put(state, shape);
                return shape;
            }
            if(!north && east && !south && west)
            {
                VoxelShape shape = VoxelShapeHelper.combineAll(shapes);
                SHAPES.put(state, shape);
                return shape;
            }
        }

        if(north && east && !south && !west)
        {
            shapes.add(FRONT_LEFT_LEG);
        }
        if(north && !east && !south && west)
        {
            shapes.add(FRONT_RIGHT_LEG);
        }
        if(!north && east && south && !west)
        {
            shapes.add(BACK_LEFT_LEG);
        }
        if(!north && !east && south && west)
        {
            shapes.add(BACK_RIGHT_LEG);
        }

        if(!west)
        {
            if(south)
            {
                if(!north) shapes.add(BACK_LEFT_LEG);
                shapes.add(BOTTOM_LEFT_SUPPORT_SOUTH);
            }
            else if(north)
            {
                shapes.add(FRONT_LEFT_LEG);
                shapes.add(BOTTOM_LEFT_SUPPORT_NORTH);
            }
            else
            {
                shapes.add(FRONT_LEFT_LEG);
                shapes.add(BACK_LEFT_LEG);
                shapes.add(BOTTOM_LEFT_SUPPORT_SHORT);
            }
        }

        if(!east)
        {
            if(south)
            {
                if(!north) shapes.add(BACK_RIGHT_LEG);
                shapes.add(BOTTOM_RIGHT_SUPPORT_SOUTH);
            }
            else if(north)
            {
                shapes.add(FRONT_RIGHT_LEG);
                shapes.add(BOTTOM_RIGHT_SUPPORT_NORTH);
            }
            else
            {
                shapes.add(FRONT_RIGHT_LEG);
                shapes.add(BACK_RIGHT_LEG);
                shapes.add(BOTTOM_RIGHT_SUPPORT_SHORT);
            }
        }

        if(cornerNorthWest)
        {
            shapes.add(NORTH_WEST_CORNER_SUPPORT);
            shapes.add(BACK_LEFT_LEG);
        }
        if(cornerNorthEast)
        {
            shapes.add(NORTH_EAST_CORNER_SUPPORT);
            shapes.add(BACK_RIGHT_LEG);
        }
        if(cornerSouthEast)
        {
            shapes.add(SOUTH_EAST_CORNER_SUPPORT);
            shapes.add(FRONT_RIGHT_LEG);
        }
        if(cornerSouthWest)
        {
            shapes.add(SOUTH_WEST_CORNER_SUPPORT);
            shapes.add(FRONT_LEFT_LEG);
        }

        VoxelShape shape = VoxelShapeHelper.combineAll(shapes);
        SHAPES.put(state, shape);
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
        if(entity instanceof LivingEntity)
        {
            float strength = 1.0F;
            float maxHeight = 0;
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if(tileEntity instanceof TrampolineBlockEntity)
            {
                TrampolineBlockEntity trampoline = (TrampolineBlockEntity) tileEntity;
                strength += trampoline.getCount() / 100F;
                maxHeight = trampoline.getCount() * 0.25F;
            }

            float height = entity.fallDistance * strength;
            if(height > 0 && !entity.isShiftKeyDown())
            {
                if(height > maxHeight - 0.25F) height = maxHeight - 0.25F;
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                entity.push(0, Math.sqrt(0.22 * (height + 0.25F)), 0);
                if(level.isClientSide)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        level.addParticle(ParticleTypes.ENTITY_EFFECT, entity.xo, entity.yo, entity.zo, 1.0, 1.0, 1.0);
                    }
                }
                else
                {
                    level.playSound(null, pos, ModSounds.BLOCK_TRAMPOLINE_BOUNCE.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.9F);
                }
            }
            entity.fallDistance = 0;
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
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(!level.isClientSide())
        {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if(tileEntity instanceof TrampolineBlockEntity)
            {
                ((TrampolineBlockEntity) tileEntity).updateCount();
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.getTrampolineState(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getTrampolineState(state, level, pos);
    }

    private BlockState getTrampolineState(BlockState state, LevelAccessor level, BlockPos pos)
    {
        boolean north = level.getBlockState(pos.north()).getBlock() == this;
        boolean east = level.getBlockState(pos.east()).getBlock() == this;
        boolean south = level.getBlockState(pos.south()).getBlock() == this;
        boolean west = level.getBlockState(pos.west()).getBlock() == this;
        boolean cornerNorthWest = north && west && level.getBlockState(pos.north().west()).getBlock() != this;
        boolean cornerNorthEast = north && east && level.getBlockState(pos.north().east()).getBlock() != this;
        boolean cornerSouthEast = south && east && level.getBlockState(pos.south().east()).getBlock() != this;
        boolean cornerSouthWest = south && west && level.getBlockState(pos.south().west()).getBlock() != this;
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west).setValue(CORNER_NORTH_WEST, cornerNorthWest).setValue(CORNER_NORTH_EAST, cornerNorthEast).setValue(CORNER_SOUTH_EAST, cornerSouthEast).setValue(CORNER_SOUTH_WEST, cornerSouthWest);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(!state.is(newState.getBlock()))
        {
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
        builder.add(CORNER_NORTH_WEST);
        builder.add(CORNER_NORTH_EAST);
        builder.add(CORNER_SOUTH_EAST);
        builder.add(CORNER_SOUTH_WEST);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new TrampolineBlockEntity(pos, state);
    }
}
