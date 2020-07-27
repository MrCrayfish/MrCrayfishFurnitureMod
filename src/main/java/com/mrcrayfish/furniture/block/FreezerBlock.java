package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.tileentity.FreezerTileEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Author: MrCrayfish
 */
public class FreezerBlock extends FurnitureHorizontalBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();
    private final Block fridge;

    public FreezerBlock(Properties properties, Block fridge)
    {
        super(properties);
        this.fridge = fridge;
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(OPEN, false));
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 -> {
            Direction direction = state1.get(DIRECTION);
            boolean open = state1.get(OPEN);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(Block.makeCuboidShape(0, 16, 0, 16, 32, 16));
            if(open)
            {
                final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 1, 0, 16, 16, 13), Direction.SOUTH));
                final VoxelShape[] DOOR = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 1, 29, 16, 16, 13), Direction.SOUTH));
                shapes.add(BASE[direction.getHorizontalIndex()]);
                shapes.add(DOOR[direction.getHorizontalIndex()]);
            }
            else
            {
                shapes.add(Block.makeCuboidShape(0, 1, 0, 16, 16, 16));
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
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(state.get(DIRECTION).getOpposite() == result.getFace())
        {
            if(!world.isRemote())
            {
                TileEntity tileEntity = world.getTileEntity(pos);
                if(tileEntity instanceof INamedContainerProvider)
                {
                    NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, pos);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof FreezerTileEntity)
        {
            ((FreezerTileEntity) tileEntity).onScheduledTick();
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
        return new FreezerTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(OPEN);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader reader, BlockPos pos)
    {
        return reader.getBlockState(pos.up()).isAir();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), this.fridge.getDefaultState().with(DIRECTION, placer.getHorizontalFacing()));
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        BlockState upState = worldIn.getBlockState(pos.up());
        if(upState.getBlock() instanceof FridgeBlock)
        {
            worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, pos.up(), Block.getStateId(upState));
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
