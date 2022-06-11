package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlindsBlock extends FurnitureHorizontalBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty EXTENSION = BooleanProperty.create("extension");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public BlindsBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(OPEN, true).setValue(EXTENSION, false));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] BOX = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 0, 16, 16, 3), Direction.SOUTH));
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BOX[direction.get2DDataValue()]);
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public boolean hasDynamicShape()
    {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        return VoxelShapeHelper.rotate(VoxelShapeHelper.rotate(Block.box(0, 1, 0, 16, 16, 3), Direction.SOUTH), state.getValue(DIRECTION));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return this.getBlindState(state, context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return this.getBlindState(stateIn, level, currentPos);
    }

    private BlockState getBlindState(BlockState state, LevelAccessor level, BlockPos pos)
    {
        BlockState aboveState = level.getBlockState(pos.above());
        boolean isExtension = aboveState.getBlock() == this && aboveState.getValue(DIRECTION) == state.getValue(DIRECTION);
        return state.setValue(EXTENSION, isExtension);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state)
    {
        return !state.getValue(OPEN);
    }

    /*@Override
    public boolean isSolid(BlockState state) //Now uses Block Properties to set solid
    {
        return !state.get(OPEN);
    }*/

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        this.toggleBlinds(level, pos, !state.getValue(OPEN), state.getValue(DIRECTION), 5);
        if(!level.isClientSide())
        {
            if(state.getValue(OPEN))
            {
                level.playSound(null, pos, ModSounds.BLOCK_BLINDS_CLOSE.get(), SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.8F);
            }
            else
            {
                level.playSound(null, pos, ModSounds.BLOCK_BLINDS_OPEN.get(), SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void toggleBlinds(Level level, BlockPos pos, boolean targetOpen, Direction targetDirection, int depth)
    {
        if(depth <= 0)
            return;

        BlockState state = level.getBlockState(pos);
        if(state.getBlock() == this)
        {
            boolean open = state.getValue(OPEN);
            Direction direction = state.getValue(DIRECTION);
            if(open != targetOpen && direction.equals(targetDirection))
            {
                level.setBlock(pos, state.setValue(OPEN, targetOpen), 3);
                this.toggleBlinds(level, pos.relative(targetDirection.getClockWise()), targetOpen, targetDirection, depth - 1);
                this.toggleBlinds(level, pos.relative(targetDirection.getCounterClockWise()), targetOpen, targetDirection, depth - 1);
                this.toggleBlinds(level, pos.relative(Direction.UP), targetOpen, targetDirection, depth - 1);
                this.toggleBlinds(level, pos.relative(Direction.DOWN), targetOpen, targetDirection, depth - 1);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
        builder.add(EXTENSION);
    }
}
