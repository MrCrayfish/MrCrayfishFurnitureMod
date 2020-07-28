package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.tileentity.KitchenDrawerTileEntity;
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
import java.util.*;

/**
 * Author: MrCrayfish
 */
public class KitchenDrawerBlock extends FurnitureHorizontalBlock implements ISidedInventoryProvider
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public KitchenDrawerBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(OPEN, false).with(DIRECTION, Direction.NORTH));
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 ->
        {
            final VoxelShape TOP = Block.makeCuboidShape(0, 13, 0, 16, 16, 16);
            final VoxelShape[] DEFAULT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 16, 13, 15), Direction.SOUTH));
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP);
            shapes.add(DEFAULT_BASE[state.get(DIRECTION).getHorizontalIndex()]);
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(OPEN);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(state.get(DIRECTION).getOpposite() == result.getFace())
        {
            if(!world.isRemote())
            {
                TileEntity tileEntity = world.getTileEntity(pos);
                if(tileEntity instanceof KitchenDrawerTileEntity)
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
        if(tileEntity instanceof KitchenDrawerTileEntity)
        {
            ((KitchenDrawerTileEntity) tileEntity).onScheduledTick();
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
        return new KitchenDrawerTileEntity();
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
