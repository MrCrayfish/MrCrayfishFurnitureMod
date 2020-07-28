package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlindsBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty EXTENSION = BooleanProperty.create("extension");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public BlindsBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(OPEN, true).with(EXTENSION, false).with(WATERLOGGED, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] BOX = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 16, 16, 3), Direction.SOUTH));
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BOX[direction.getHorizontalIndex()]);
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public boolean isVariableOpacity()
    {
        return true;
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
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return VoxelShapeHelper.rotate(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 1, 0, 16, 16, 3), Direction.SOUTH), state.get(DIRECTION));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return this.getBlindState(state, context.getWorld(), context.getPos());
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return this.getBlindState(stateIn, worldIn, currentPos);
    }

    private BlockState getBlindState(BlockState state, IWorld world, BlockPos pos)
    {
        BlockState aboveState = world.getBlockState(pos.up());
        boolean isExtension = aboveState.getBlock() == this && aboveState.get(DIRECTION) == state.get(DIRECTION);
        return state.with(EXTENSION, isExtension);
    }

    @Override
    public boolean isTransparent(BlockState state)
    {
        return !state.get(OPEN);
    }

    /*@Override
    public boolean isSolid(BlockState state) //Now uses Block Properties to set solid
    {
        return !state.get(OPEN);
    }*/

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        this.toggleBlinds(world, pos, !state.get(OPEN), state.get(DIRECTION), 5);
        if(!world.isRemote)
        {
            if(state.get(OPEN))
            {
                world.playSound(null, pos, ModSounds.BLOCK_BLINDS_CLOSE, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.8F);
            }
            else
            {
                world.playSound(null, pos, ModSounds.BLOCK_BLINDS_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }
        return ActionResultType.SUCCESS;
    }

    private void toggleBlinds(World world, BlockPos pos, boolean targetOpen, Direction targetDirection, int depth)
    {
        if(depth <= 0)
            return;

        BlockState state = world.getBlockState(pos);
        if(state.getBlock() == this)
        {
            boolean open = state.get(OPEN);
            Direction direction = state.get(DIRECTION);
            if(open != targetOpen && direction.equals(targetDirection))
            {
                world.setBlockState(pos, state.with(OPEN, targetOpen), 3);
                this.toggleBlinds(world, pos.offset(targetDirection.rotateY()), targetOpen, targetDirection, depth - 1);
                this.toggleBlinds(world, pos.offset(targetDirection.rotateYCCW()), targetOpen, targetDirection, depth - 1);
                this.toggleBlinds(world, pos.offset(Direction.UP), targetOpen, targetDirection, depth - 1);
                this.toggleBlinds(world, pos.offset(Direction.DOWN), targetOpen, targetDirection, depth - 1);
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(OPEN);
        builder.add(EXTENSION);
    }
}
