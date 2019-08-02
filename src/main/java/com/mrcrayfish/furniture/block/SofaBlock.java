package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
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
public class SofaBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public SofaBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(TYPE, Type.SINGLE));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape BASE = Block.makeCuboidShape(0, 3, 0, 16, 10, 16);
        final VoxelShape[] LEG_BACK_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 3, 3, 3), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 13, 3, 3, 16), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 0, 13, 16, 3, 16), Direction.SOUTH));
        final VoxelShape[] LEG_BACK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13, 0, 0, 16, 3, 3), Direction.SOUTH));
        final VoxelShape[] BACK_REST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 10, 0, 16, 20, 4), Direction.SOUTH));
        final VoxelShape[] BACK_REST_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 10, 4, 4, 20, 16), Direction.SOUTH));
        final VoxelShape[] BACK_REST_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12, 10, 4, 16, 20, 16), Direction.SOUTH));
        final VoxelShape[] LEFT_ARM_REST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(-2, 9, 1, 2, 14, 16), Direction.SOUTH));
        final VoxelShape[] RIGHT_ARM_REST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14, 9, 1, 18, 14, 16), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            Type type = state.get(TYPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BASE);
            shapes.add(BACK_REST[direction.getHorizontalIndex()]);
            switch(type)
            {
                case SINGLE:
                    shapes.add(LEG_BACK_LEFT[direction.getHorizontalIndex()]);
                    shapes.add(LEG_FRONT_LEFT[direction.getHorizontalIndex()]);
                    shapes.add(LEG_FRONT_RIGHT[direction.getHorizontalIndex()]);
                    shapes.add(LEG_BACK_RIGHT[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_REST[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_REST[direction.getHorizontalIndex()]);
                    break;
                case LEFT:
                    shapes.add(LEG_BACK_LEFT[direction.getHorizontalIndex()]);
                    shapes.add(LEG_FRONT_LEFT[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_REST[direction.getHorizontalIndex()]);
                    break;
                case RIGHT:
                    shapes.add(LEG_FRONT_RIGHT[direction.getHorizontalIndex()]);
                    shapes.add(LEG_BACK_RIGHT[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_REST[direction.getHorizontalIndex()]);
                    break;
                case CORNER_LEFT:
                    shapes.add(LEG_BACK_LEFT[direction.getHorizontalIndex()]);
                    shapes.add(BACK_REST_LEFT[direction.getHorizontalIndex()]);
                    break;
                case CORNER_RIGHT:
                    shapes.add(LEG_BACK_RIGHT[direction.getHorizontalIndex()]);
                    shapes.add(BACK_REST_RIGHT[direction.getHorizontalIndex()]);
                    break;
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
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(stack.getItem() == Items.NAME_TAG)
        {
            if(stack.getDisplayName().getString().equals("jeb_"))
            {
                BlockState rainbowSofaState = ModBlocks.SOFA_RAINBOW.getDefaultState().with(DIRECTION, state.get(DIRECTION)).with(TYPE, state.get(TYPE)).with(WATERLOGGED, state.get(WATERLOGGED));
                world.setBlockState(pos,rainbowSofaState, 3);
                return true;
            }
        }
        return SeatEntity.create(world, pos, 0.4, player);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        Direction sofaDirection = state.get(DIRECTION);
        boolean left = isSofa(world, pos, sofaDirection.rotateYCCW(), sofaDirection) || isSofa(world, pos, sofaDirection.rotateYCCW(), sofaDirection.rotateYCCW());
        boolean right = isSofa(world, pos, sofaDirection.rotateY(), sofaDirection) || isSofa(world, pos, sofaDirection.rotateY(), sofaDirection.rotateY());
        boolean cornerLeft = isSofa(world, pos, sofaDirection.getOpposite(), sofaDirection.rotateYCCW());
        boolean cornerRight = isSofa(world, pos, sofaDirection.getOpposite(), sofaDirection.rotateY());

        if(cornerLeft)
        {
            return state.with(TYPE, Type.CORNER_LEFT);
        }
        else if(cornerRight)
        {
            return state.with(TYPE, Type.CORNER_RIGHT);
        }
        else if(left && right)
        {
            return state.with(TYPE, Type.MIDDLE);
        }
        else if(left)
        {
            return state.with(TYPE, Type.RIGHT);
        }
        else if(right)
        {
            return state.with(TYPE, Type.LEFT);
        }
        return state.with(TYPE, Type.SINGLE);
    }

    private boolean isSofa(IWorld world, BlockPos source, Direction direction, Direction targetDirection)
    {
        BlockState state = world.getBlockState(source.offset(direction));
        if(state.getBlock() == this)
        {
            Direction sofaDirection = state.get(DIRECTION);
            return sofaDirection.equals(targetDirection);
        }
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(TYPE);
    }

    public enum Type implements IStringSerializable
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
        public String getName()
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
