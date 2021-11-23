package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class OvenRangeHoodBlock extends FurnitureHorizontalWaterloggedBlock {

    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public OvenRangeHoodBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(DOWN, false).with(LIT, true));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] MAIN_HOOD = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 0.4, 0.0, 16.0, 3.2, 16.0), Direction.EAST));
        final VoxelShape[] CEILING_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(8.0, 4.0, 3.2, 16.0, 16.0, 12.8), Direction.EAST));
        final VoxelShape[] CEILING_VENT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(8.0, 0.0, 3.2, 16.0, 16.0, 12.8), Direction.EAST));
        final VoxelShape[] MAIN_HOOD_OVERLAY = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 3.2, 0.0, 16.0, 4.0, 16.0), Direction.EAST));
        final VoxelShape[] FAR_LEFT_LIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 0.0, 2.0, 7.0, 0.8, 3.0), Direction.EAST));
        final VoxelShape[] LEFT_LIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 0.0, 4.0, 7.0, 0.8, 5.0), Direction.EAST));
        final VoxelShape[] CENTER_LEFT_LIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 0.0, 6.0, 7.0, 0.8, 7.0), Direction.EAST));
        final VoxelShape[] CENTER_RIGHT_LIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 0.0, 9.0, 7.0, 0.8, 10.0), Direction.EAST));
        final VoxelShape[] RIGHT_LIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 0.0, 11.0, 7.0, 0.8, 13.0), Direction.EAST));
        final VoxelShape[] FAR_RIGHT_LIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 0.0, 13.0, 7.0, 0.8, 14.0), Direction.EAST));
        final VoxelShape[] BACK_LIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(9.0, 0.0, 1.6, 14.4, 0.8, 14.4), Direction.EAST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states) {
            Direction direction = state.get(DIRECTION);
            boolean down = state.get(DOWN);
            List<VoxelShape> shapes = new ArrayList<>();

            if(!down) {

                shapes.add(MAIN_HOOD[direction.getHorizontalIndex()]);
                shapes.add(CEILING_SUPPORT[direction.getHorizontalIndex()]);
                shapes.add(MAIN_HOOD_OVERLAY[direction.getHorizontalIndex()]);
                shapes.add(FAR_LEFT_LIGHT[direction.getHorizontalIndex()]);
                shapes.add(LEFT_LIGHT[direction.getHorizontalIndex()]);
                shapes.add(CENTER_LEFT_LIGHT[direction.getHorizontalIndex()]);
                shapes.add(CENTER_RIGHT_LIGHT[direction.getHorizontalIndex()]);
                shapes.add(RIGHT_LIGHT[direction.getHorizontalIndex()]);
                shapes.add(FAR_RIGHT_LIGHT[direction.getHorizontalIndex()]);
                shapes.add(BACK_LIGHT[direction.getHorizontalIndex()]);

            } else if (down) {

                shapes.add(CEILING_VENT[direction.getHorizontalIndex()]);

            }

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
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        boolean down = world.getBlockState(pos.down()).getBlock() == this;
        return state.with(DOWN, down);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(DOWN);
        builder.add(LIT);
    }

    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, Boolean.valueOf(false)), 2);
        } else {
            world.setBlockState(pos, state.with(LIT, Boolean.valueOf(true)), 2);
        }
        return ActionResultType.SUCCESS;
    }

}
