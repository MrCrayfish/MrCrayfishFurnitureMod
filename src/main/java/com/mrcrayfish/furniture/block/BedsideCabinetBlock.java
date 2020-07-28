package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.tileentity.BedsideCabinetTileEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class BedsideCabinetBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public BedsideCabinetBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(OPEN, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] LEG_BACK_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 0, 1, 3, 2, 3), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 0, 11, 3, 2, 13), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 0, 11, 15, 2, 13), Direction.SOUTH));
        final VoxelShape[] LEG_BACK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 0, 1, 15, 2, 3), Direction.SOUTH));
        final VoxelShape[] TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 14, 0, 16, 16, 16), Direction.SOUTH));
        final VoxelShape[] HANDLE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(5, 5, 15, 11, 6, 16), Direction.SOUTH));
        final VoxelShape[] BASE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 2, 0, 16, 14, 15), Direction.SOUTH));
        final VoxelShape[] HANDLE_TOP_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(5, 11, 15, 11, 12, 16), Direction.SOUTH));
        final VoxelShape[] BASE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 2, 0, 16, 14, 13), Direction.SOUTH));
        final VoxelShape[] DRAW_TOP_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 8, 20, 16, 14, 22), Direction.SOUTH));
        final VoxelShape[] DRAW_BOTTOM_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 2, 13, 16, 8, 15), Direction.SOUTH));
        final VoxelShape[] HANDLE_TOP_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(5, 11, 22, 11, 12, 23), Direction.SOUTH));
        final VoxelShape[] DRAW_INSIDE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 8, 13, 15, 10, 20), Direction.SOUTH));
        final VoxelShape[] DRAW_INSIDE_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1, 10, 13, 3, 14, 20), Direction.SOUTH));
        final VoxelShape[] DRAW_INSIDE_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 10, 13, 15, 14, 20), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(LEG_BACK_LEG[direction.getHorizontalIndex()]);
            shapes.add(LEG_FRONT_LEFT[direction.getHorizontalIndex()]);
            shapes.add(LEG_FRONT_RIGHT[direction.getHorizontalIndex()]);
            shapes.add(LEG_BACK_RIGHT[direction.getHorizontalIndex()]);
            shapes.add(TOP[direction.getHorizontalIndex()]);
            shapes.add(HANDLE_BOTTOM[direction.getHorizontalIndex()]);
            if(state.get(OPEN))
            {
                shapes.add(BASE_OPEN[direction.getHorizontalIndex()]);
                shapes.add(DRAW_TOP_OPEN[direction.getHorizontalIndex()]);
                shapes.add(DRAW_BOTTOM_OPEN[direction.getHorizontalIndex()]);
                shapes.add(HANDLE_TOP_OPEN[direction.getHorizontalIndex()]);
                shapes.add(DRAW_INSIDE_BOTTOM[direction.getHorizontalIndex()]);
                shapes.add(DRAW_INSIDE_LEFT[direction.getHorizontalIndex()]);
                shapes.add(DRAW_INSIDE_RIGHT[direction.getHorizontalIndex()]);
            }
            else
            {
                shapes.add(BASE_CLOSED[direction.getHorizontalIndex()]);
                shapes.add(HANDLE_TOP_CLOSED[direction.getHorizontalIndex()]);
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
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(state.get(DIRECTION).getOpposite() == result.getFace())
        {
            if(!world.isRemote())
            {
                TileEntity tileEntity = world.getTileEntity(pos);
                if(tileEntity instanceof BedsideCabinetTileEntity)
                {
                    playerEntity.openContainer((INamedContainerProvider) tileEntity);
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof BedsideCabinetTileEntity)
        {
            ((BedsideCabinetTileEntity) tileEntity).onScheduledTick();
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new BedsideCabinetTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(OPEN);
    }
}
