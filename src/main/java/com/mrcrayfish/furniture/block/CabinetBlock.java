package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.tileentity.CabinetTileEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class CabinetBlock extends FurnitureHorizontalWaterloggedBlock implements ISidedInventoryProvider
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public CabinetBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(OPEN, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] BASE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 16, 16, 15), Direction.SOUTH));
        final VoxelShape[] HANDLE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14, 5, 15, 15, 11, 16), Direction.SOUTH));
        final VoxelShape[] BASE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 16, 16, 13), Direction.SOUTH));
        final VoxelShape[] HANDLE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(-1, 5, 27, 0, 11, 28), Direction.SOUTH));
        final VoxelShape[] DOOR_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 13, 2, 16, 29), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            if(state.get(OPEN))
            {
                shapes.add(BASE_OPEN[direction.getHorizontalIndex()]);
                shapes.add(HANDLE_OPEN[direction.getHorizontalIndex()]);
                shapes.add(DOOR_OPEN[direction.getHorizontalIndex()]);
            }
            else
            {
                shapes.add(BASE_CLOSED[direction.getHorizontalIndex()]);
                shapes.add(HANDLE_CLOSED[direction.getHorizontalIndex()]);
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
                if(tileEntity instanceof CabinetTileEntity)
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
        if(tileEntity instanceof CabinetTileEntity)
        {
            ((CabinetTileEntity) tileEntity).onScheduledTick();
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
        return new CabinetTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(OPEN);
    }

    @Override
    public ISidedInventory createInventory(BlockState state, IWorld world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof ISidedInventory)
        {
            return (ISidedInventory) tileEntity;
        }
        return null;
    }
}