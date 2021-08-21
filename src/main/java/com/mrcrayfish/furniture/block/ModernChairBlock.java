package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ModernChairBlock extends FurnitureHorizontalWaterloggedBlock
{
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ModernChairBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] BACKREST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.1, 9.5, 11.5, 13.9, 19.5, 13.0), Direction.NORTH));
        final VoxelShape[] SEAT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.1, 8.0, 3.0, 13.9, 9.5, 14.0), Direction.SOUTH));
        final VoxelShape[] FRONT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 7.0, 2.0, 14.0, 8.0, 3.5), Direction.NORTH));
        final VoxelShape[] LEFT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.0, 6.5, 12.5, 14.0, 8.0, 14.0), Direction.WEST));
        final VoxelShape[] RIGHT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 6.5, 12.5, 13.0, 8.0, 14.0), Direction.EAST));
        final VoxelShape[] BACK_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 6.5, 11.5, 14.0, 8.0, 13.0), Direction.NORTH));
        final VoxelShape[] FRONT_LEFT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 12.5, 3.5, 8.0, 14.0), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12.5, 0.0, 12.5, 14.0, 8.0, 14.0), Direction.SOUTH));
        final VoxelShape[] LEFT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 12.5, 14.0, 1.5, 14.0), Direction.WEST));
        final VoxelShape[] RIGHT_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 12.5, 14.0, 1.5, 14.0), Direction.EAST));
        final VoxelShape[] BACK_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 12.5, 14.0, 1.5, 14.0), Direction.NORTH));


        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BACKREST[direction.getHorizontalIndex()]);
            shapes.add(SEAT[direction.getHorizontalIndex()]);
            shapes.add(FRONT_BASE[direction.getHorizontalIndex()]);
            shapes.add(BACK_BASE[direction.getHorizontalIndex()]);
            shapes.add(LEFT_BASE[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_BASE[direction.getHorizontalIndex()]);
            shapes.add(FRONT_LEFT_LEG[direction.getHorizontalIndex()]);
            shapes.add(FRONT_RIGHT_LEG[direction.getHorizontalIndex()]);
            shapes.add(LEFT_SUPPORT[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_SUPPORT[direction.getHorizontalIndex()]);
            shapes.add(BACK_SUPPORT[direction.getHorizontalIndex()]);
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        return SeatEntity.create(world, pos, 0.4, playerEntity);
    }

}
