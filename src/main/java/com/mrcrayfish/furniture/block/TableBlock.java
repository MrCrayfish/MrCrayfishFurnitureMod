package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
//TODO update forge blockstate once
public class TableBlock extends FurnitureWaterloggedBlock
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public TableBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape TABLE_TOP = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
        final VoxelShape MIDDLE_POST = Block.box(6.0, 0.0, 6.0, 10.0, 14.0, 10.0);
        final VoxelShape END_POST = Block.box(3.0, 0.0, 6.0, 7.0, 14.0, 10.0);
        final VoxelShape CORNER_POST = Block.box(3.0, 0.0, 9.0, 7.0, 14.0, 13.0);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            boolean north = state.getValue(NORTH);
            boolean east = state.getValue(EAST);
            boolean south = state.getValue(SOUTH);
            boolean west = state.getValue(WEST);

            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TABLE_TOP);

            if(!north & !east && !south && !west)
            {
                shapes.add(MIDDLE_POST);
            }
            else if(north & !east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.NORTH));
            }
            else if(!north & east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.EAST));
            }
            else if(!north & !east && south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.SOUTH));
            }
            else if(!north & !east && !south && west)
            {
                shapes.add(VoxelShapeHelper.rotate(END_POST, Direction.WEST));
            }
            else if(north && east && !south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.EAST));
            }
            else if(!north && east && south && !west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.SOUTH));
            }
            else if(!north && !east && south && west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.WEST));
            }
            else if(north && !east && !south && west)
            {
                shapes.add(VoxelShapeHelper.rotate(CORNER_POST, Direction.NORTH));
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
        boolean north = level.getBlockState(pos.north()).getBlock() == this;
        boolean east = level.getBlockState(pos.east()).getBlock() == this;
        boolean south = level.getBlockState(pos.south()).getBlock() == this;
        boolean west = level.getBlockState(pos.west()).getBlock() == this;
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
    }
}