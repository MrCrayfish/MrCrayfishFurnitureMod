package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
//TODO update forge blockstate once
public class CoffeeTableBlock extends FurnitureWaterloggedBlock
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty TALL = BooleanProperty.create("tall");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public CoffeeTableBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(TALL, false));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape TABLE_TOP_SHORT = Block.box(0.0, 6.0, 0.0, 16.0, 8.0, 16.0);
        final VoxelShape TABLE_TOP_TALL = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        final VoxelShape LEG_SOUTH_EAST_TALL = Block.box(13.5, 0, 13.5, 15.5, 14, 15.5);
        final VoxelShape LEG_SOUTH_WEST_TALL = Block.box(0.5, 0, 13.5, 2.5, 14, 15.5);
        final VoxelShape LEG_NORTH_WEST_TALL = Block.box(0.5, 0, 0.5, 2.5, 14, 2.5);
        final VoxelShape LEG_NORTH_EAST_TALL = Block.box(13.5, 0, 0.5, 15.5, 14, 2.5);
        final VoxelShape LEG_SOUTH_EAST_SHORT = Block.box(13.5, 0, 13.5, 15.5, 6, 15.5);
        final VoxelShape LEG_SOUTH_WEST_SHORT = Block.box(0.5, 0, 13.5, 2.5, 6, 15.5);
        final VoxelShape LEG_NORTH_WEST_SHORT = Block.box(0.5, 0, 0.5, 2.5, 6, 2.5);
        final VoxelShape LEG_NORTH_EAST_SHORT = Block.box(13.5, 0, 0.5, 15.5, 6, 2.5);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean tall = state.getValue(TALL);
            boolean north = state.getValue(NORTH);
            boolean east = state.getValue(EAST);
            boolean south = state.getValue(SOUTH);
            boolean west = state.getValue(WEST);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(tall ? TABLE_TOP_TALL : TABLE_TOP_SHORT);
            if(!north && !west)
            {
                shapes.add(tall ? LEG_NORTH_WEST_TALL : LEG_NORTH_WEST_SHORT);
            }
            if(!north && !east)
            {
                shapes.add(tall ? LEG_NORTH_EAST_TALL : LEG_NORTH_EAST_SHORT);
            }
            if(!south && !west)
            {
                shapes.add(tall ? LEG_SOUTH_WEST_TALL : LEG_SOUTH_WEST_SHORT);
            }
            if(!south && !east)
            {
                shapes.add(tall ? LEG_SOUTH_EAST_TALL : LEG_SOUTH_EAST_SHORT);
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
    public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        boolean tall = state.getValue(TALL);
        boolean north = this.isCoffeeTable(level, pos, Direction.NORTH, tall);
        boolean east = this.isCoffeeTable(level, pos, Direction.EAST, tall);
        boolean south = this.isCoffeeTable(level, pos, Direction.SOUTH, tall);
        boolean west = this.isCoffeeTable(level, pos, Direction.WEST, tall);
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west);
    }

    private boolean isCoffeeTable(LevelAccessor level, BlockPos source, Direction direction, boolean tall)
    {
        BlockState state = level.getBlockState(source.relative(direction));
        return state.getBlock() == this && state.getValue(TALL) == tall;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        if(state.getBlock() == this)
        {
            return state.setValue(TALL, true);
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context)
    {
        ItemStack stack = context.getItemInHand();
        return !state.getValue(TALL) && stack.getItem() == this.asItem();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
        builder.add(TALL);
    }
}