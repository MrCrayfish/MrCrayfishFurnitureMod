package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.BlockModelFields;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class OfficeChairBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final net.minecraft.state.BooleanProperty SEATED = BooleanProperty.create("seated");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public OfficeChairBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.SOUTH).with(SEATED, true));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {

        // final VoxelShape[] BACK_RIGHT_CONNECTION = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.6, 2.5, 7.3, 2.0, 4.3, 8.7), Direction.SOUTH));
        final VoxelShape[] BACK_RIGHT_EXTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 2.0, 3.1, 2.5, 4.5), Direction.SOUTH));
        final VoxelShape[] BACK_RIGHT_AXLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.1, 0.2, 2.2, 3.4, 2.3, 4.3), Direction.SOUTH));
        final VoxelShape[] BACK_RIGHT_INTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.4, 0.0, 2.0, 4.5, 2.5, 4.5), Direction.SOUTH));
        // final VoxelShape[] BACK_LEFT_CONNECTION = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 2.5, 7.3, 15.4, 4.3, 8.7), Direction.SOUTH));
        final VoxelShape[] BACK_LEFT_INTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(11.5, 0.0, 2.0, 12.6, 2.5, 4.5), Direction.SOUTH));
        final VoxelShape[] BACK_LEFT_AXLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12.6, 0.2, 2.2, 12.9, 2.3, 4.3), Direction.SOUTH));
        final VoxelShape[] BACK_LEFT_EXTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12.9, 0.0, 2.0, 14.0, 2.5, 4.5), Direction.SOUTH));
        // final VoxelShape[] FRONT_RIGHT_CONNECTION = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.6, 2.5, 7.3, 2.0, 4.3, 8.7), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_EXTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 0.0, 11.5, 3.1, 2.5, 13.0), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_AXLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12.6, 0.2, 11.7, 12.9, 2.3, 13.8), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_INTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.4, 0.0, 11.5, 4.5, 2.5, 13.0), Direction.SOUTH));
        // final VoxelShape[] FRONT_LEFT_CONNECTION = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 2.5, 7.3, 15.4, 4.3, 8.7), Direction.SOUTH));
        final VoxelShape[] FRONT_LEFT_INTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(11.5, 0.0, 11.5, 12.6, 2.5, 13.0), Direction.SOUTH));
        final VoxelShape[] FRONT_LEFT_AXLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.1, 0.2, 11.7, 3.4, 2.3, 13.8), Direction.SOUTH));
        final VoxelShape[] FRONT_LEFT_EXTERNAL_WHEEL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12.9, 0.0, 11.5, 14.0, 2.5, 13.0), Direction.SOUTH));
        // final VoxelShape[] BACK_RIGHT_FRONT_LEFT_SPINDLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 2.5, 7.0, 14.0, 4.5, 9.0), Direction.SOUTH));
        // final VoxelShape[] BACK_LEFT_FRONT_RIGHT_SPINDLE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 2.5, 7.0, 14.0, 4.5, 9.0), Direction.SOUTH));
        final VoxelShape[] CENTRAL_SUPPORT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(7.0, 4.5, 7.0, 9.0, 9.5, 9.0), Direction.SOUTH));
        final VoxelShape[] CENTRAL_SUPPORT_CENTER = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(6.0, 9.0, 6.0, 10.0, 9.5, 10.0), Direction.SOUTH));
        final VoxelShape[] CHAIR_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.5, 9.5, 5.0, 13.5, 11.0, 15.0), Direction.SOUTH));
        // final VoxelShape[] CHAIR_BOTTOM_TO_BACK = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.5, 9.5, 1.6, 13.5, 11.0, 5.0), Direction.SOUTH));
        final VoxelShape[] CHAIR_BACK = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.5, 12.0, 2.6, 13.5, 26.0, 4.1), Direction.SOUTH));
        final VoxelShape[] CHAIR_BOTTOM_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.5, 10.0, 5.4, 2.5, 11.5, 13.0), Direction.SOUTH));
        // final VoxelShape[] CHAIR_BOTTOM_TO_BACK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.5, 10.0, 2.1, 2.5, 11.5, 5.4), Direction.SOUTH));
        final VoxelShape[] CHAIR_BACK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.5, 12.3, 3.1, 2.5, 26.0, 4.6), Direction.SOUTH));
        final VoxelShape[] CHAIR_ARM_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.5, 10.0, 13.0, 2.5, 15.0, 14.5), Direction.SOUTH));
        final VoxelShape[] CHAIR_ARMREST_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.2, 15.0, 2.8, 2.8, 15.5, 15.0), Direction.SOUTH));
        final VoxelShape[] CHAIR_BOTTOM_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.5, 12.3, 3.1, 14.5, 26.0, 4.6), Direction.SOUTH));
        // final VoxelShape[] CHAIR_BOTTOM_TO_BACK_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.5, 10.0, 2.1, 14.5, 11.5, 5.4), Direction.SOUTH));
        final VoxelShape[] CHAIR_BACK_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.5, 10.0, 5.4, 14.5, 11.5, 13.0), Direction.SOUTH));
        final VoxelShape[] CHAIR_ARM_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.5, 10.0, 13.0, 14.5, 15.0, 14.5), Direction.SOUTH));
        final VoxelShape[] CHAIR_ARMREST_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.2, 15.0, 2.8, 14.8, 15.5, 15.0), Direction.SOUTH));

        final VoxelShape[] PILLOW = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(5.0, 22.0, 4.0, 11.0, 25.4, 4.5), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            // shapes.add(BACK_RIGHT_CONNECTION[direction.getHorizontalIndex()]);
            shapes.add(BACK_RIGHT_EXTERNAL_WHEEL[direction.getHorizontalIndex()]);
            shapes.add(BACK_RIGHT_AXLE[direction.getHorizontalIndex()]);
            shapes.add(BACK_RIGHT_INTERNAL_WHEEL[direction.getHorizontalIndex()]);
            // shapes.add(BACK_LEFT_CONNECTION[direction.getHorizontalIndex()]);
            shapes.add(BACK_LEFT_EXTERNAL_WHEEL[direction.getHorizontalIndex()]);
            shapes.add(BACK_LEFT_AXLE[direction.getHorizontalIndex()]);
            shapes.add(BACK_LEFT_INTERNAL_WHEEL[direction.getHorizontalIndex()]);
            // shapes.add(FRONT_RIGHT_CONNECTION[direction.getHorizontalIndex()]);
            shapes.add(FRONT_RIGHT_EXTERNAL_WHEEL[direction.getHorizontalIndex()]);
            shapes.add(FRONT_RIGHT_AXLE[direction.getHorizontalIndex()]);
            shapes.add(FRONT_RIGHT_INTERNAL_WHEEL[direction.getHorizontalIndex()]);
            // shapes.add(FRONT_LEFT_CONNECTION[direction.getHorizontalIndex()]);
            shapes.add(FRONT_LEFT_EXTERNAL_WHEEL[direction.getHorizontalIndex()]);
            shapes.add(FRONT_LEFT_AXLE[direction.getHorizontalIndex()]);
            shapes.add(FRONT_LEFT_INTERNAL_WHEEL[direction.getHorizontalIndex()]);
            // shapes.add(BACK_RIGHT_FRONT_LEFT_SPINDLE[direction.getHorizontalIndex()]);
            // shapes.add(BACK_LEFT_FRONT_RIGHT_SPINDLE[direction.getHorizontalIndex()]);

            shapes.add(CENTRAL_SUPPORT[direction.getHorizontalIndex()]);
            shapes.add(CENTRAL_SUPPORT_CENTER[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_BOTTOM[direction.getHorizontalIndex()]);
            // shapes.add(CHAIR_BOTTOM_TO_BACK[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_BACK[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_BOTTOM_RIGHT[direction.getHorizontalIndex()]);
            // shapes.add(CHAIR_BOTTOM_TO_BACK_RIGHT[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_BACK_RIGHT[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_ARM_RIGHT[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_ARMREST_RIGHT[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_BOTTOM_LEFT[direction.getHorizontalIndex()]);
            // shapes.add(CHAIR_BOTTOM_TO_BACK_LEFT[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_BACK_LEFT[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_ARM_LEFT[direction.getHorizontalIndex()]);
            shapes.add(CHAIR_ARMREST_LEFT[direction.getHorizontalIndex()]);
            shapes.add(PILLOW[direction.getHorizontalIndex()]);

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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(SEATED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        if(state.get(SEATED)) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.OFFICE_CHAIR.get().create();
    }

    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {
        SeatEntity.create(world, pos, 0.4, playerEntity);
        if(state.get(SEATED)) {
            world.setBlockState(pos, state.with(SEATED, Boolean.valueOf(false)), 2);
        } else {
            world.setBlockState(pos, state.with(SEATED, Boolean.valueOf(true)), 2);
        }
        return ActionResultType.SUCCESS;
    }

}
