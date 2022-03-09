package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class SofaBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public SofaBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(TYPE, Type.SINGLE));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape BASE = Block.box(0, 3, 0, 16, 10, 16);
        final VoxelShape[] LEG_BACK_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 0, 3, 3, 3), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 13, 3, 3, 16), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 0, 13, 16, 3, 16), Direction.SOUTH));
        final VoxelShape[] LEG_BACK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 0, 0, 16, 3, 3), Direction.SOUTH));
        final VoxelShape[] BACK_REST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 10, 0, 16, 20, 4), Direction.SOUTH));
        final VoxelShape[] BACK_REST_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 10, 4, 4, 20, 16), Direction.SOUTH));
        final VoxelShape[] BACK_REST_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(12, 10, 4, 16, 20, 16), Direction.SOUTH));
        final VoxelShape[] LEFT_ARM_REST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(-2, 9, 1, 2, 14, 16), Direction.SOUTH));
        final VoxelShape[] RIGHT_ARM_REST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(14, 9, 1, 18, 14, 16), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            Type type = state.getValue(TYPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BASE);
            shapes.add(BACK_REST[direction.get2DDataValue()]);
            switch(type)
            {
                case SINGLE:
                    shapes.add(LEG_BACK_LEFT[direction.get2DDataValue()]);
                    shapes.add(LEG_FRONT_LEFT[direction.get2DDataValue()]);
                    shapes.add(LEG_FRONT_RIGHT[direction.get2DDataValue()]);
                    shapes.add(LEG_BACK_RIGHT[direction.get2DDataValue()]);
                    shapes.add(LEFT_ARM_REST[direction.get2DDataValue()]);
                    shapes.add(RIGHT_ARM_REST[direction.get2DDataValue()]);
                    break;
                case LEFT:
                    shapes.add(LEG_BACK_LEFT[direction.get2DDataValue()]);
                    shapes.add(LEG_FRONT_LEFT[direction.get2DDataValue()]);
                    shapes.add(LEFT_ARM_REST[direction.get2DDataValue()]);
                    break;
                case RIGHT:
                    shapes.add(LEG_FRONT_RIGHT[direction.get2DDataValue()]);
                    shapes.add(LEG_BACK_RIGHT[direction.get2DDataValue()]);
                    shapes.add(RIGHT_ARM_REST[direction.get2DDataValue()]);
                    break;
                case CORNER_LEFT:
                    shapes.add(LEG_BACK_LEFT[direction.get2DDataValue()]);
                    shapes.add(BACK_REST_LEFT[direction.get2DDataValue()]);
                    break;
                case CORNER_RIGHT:
                    shapes.add(LEG_BACK_RIGHT[direction.get2DDataValue()]);
                    shapes.add(BACK_REST_RIGHT[direction.get2DDataValue()]);
                    break;
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
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return this.getSofaState(state, context.getLevel(), context.getClickedPos(), state.getValue(DIRECTION));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide())
        {
            ItemStack stack = playerEntity.getItemInHand(hand);
            if(stack.getItem() == Items.NAME_TAG && this != ModBlocks.SOFA_RAINBOW.get())
            {
                if(stack.getHoverName().getString().equals("jeb_"))
                {
                    BlockState rainbowSofaState = ModBlocks.SOFA_RAINBOW.get().defaultBlockState().setValue(DIRECTION, state.getValue(DIRECTION)).setValue(TYPE, state.getValue(TYPE)).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
                    level.setBlock(pos, rainbowSofaState, 3);
                    return InteractionResult.SUCCESS;
                }
            }
            return SeatEntity.create(level, pos, 0.4, playerEntity, state.getValue(DIRECTION));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getSofaState(state, level, pos, state.getValue(DIRECTION));
    }

    private BlockState getSofaState(BlockState state, LevelAccessor level, BlockPos pos, Direction dir)
    {
        boolean left = this.isSofa(level, pos, dir.getCounterClockWise(), dir) || this.isSofa(level, pos, dir.getCounterClockWise(), dir.getCounterClockWise());
        boolean right = this.isSofa(level, pos, dir.getClockWise(), dir) || this.isSofa(level, pos, dir.getClockWise(), dir.getClockWise());
        boolean cornerLeft = this.isSofa(level, pos, dir.getOpposite(), dir.getCounterClockWise());
        boolean cornerRight = this.isSofa(level, pos, dir.getOpposite(), dir.getClockWise());

        if(cornerLeft)
        {
            return state.setValue(TYPE, Type.CORNER_LEFT);
        }
        else if(cornerRight)
        {
            return state.setValue(TYPE, Type.CORNER_RIGHT);
        }
        else if(left && right)
        {
            return state.setValue(TYPE, Type.MIDDLE);
        }
        else if(left)
        {
            return state.setValue(TYPE, Type.RIGHT);
        }
        else if(right)
        {
            return state.setValue(TYPE, Type.LEFT);
        }
        return state.setValue(TYPE, Type.SINGLE);
    }

    private boolean isSofa(LevelAccessor level, BlockPos source, Direction direction, Direction targetDirection)
    {
        BlockState state = level.getBlockState(source.relative(direction));
        if(state.getBlock() == this)
        {
            Direction sofaDirection = state.getValue(DIRECTION);
            return sofaDirection.equals(targetDirection);
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    public enum Type implements StringRepresentable
    {
        SINGLE("single"),
        LEFT("left"),
        RIGHT("right"),
        MIDDLE("middle"),
        CORNER_LEFT("corner_left"),
        CORNER_RIGHT("corner_right");

        private final String id;

        Type(String id)
        {
            this.id = id;
        }

        @Override
        public String getSerializedName()
        {
            return id;
        }

        @Override
        public String toString()
        {
            return id;
        }
    }
}
