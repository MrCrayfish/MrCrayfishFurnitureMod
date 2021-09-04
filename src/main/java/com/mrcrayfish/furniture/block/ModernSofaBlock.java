package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
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

public class ModernSofaBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ModernSofaBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(TYPE, Type.SINGLE));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] BACKBASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 5.0, 1.0, 14.0, 19.0, 2.0), Direction.SOUTH));
        final VoxelShape[] LEFT_BACKBASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 5.0, 1.0, 16.0, 19.0, 2.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_BACKBASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 5.0, 1.0, 2.0, 19.0, 2.0), Direction.SOUTH));
        final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 5.0, 2.0, 14.0, 9.0, 15.0), Direction.SOUTH));
        final VoxelShape[] LEFT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 5.0, 2.0, 16.0, 9.0, 15.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 5.0, 2.0, 2.0, 9.0, 15.0), Direction.SOUTH));
        final VoxelShape[] UNDERBASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 3.0, 1.0, 14.0, 5.0, 15.0), Direction.SOUTH));
        final VoxelShape[] LEFT_UNDERBASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 3.0, 1.0, 16.0, 5.0, 15.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_UNDERBASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 3.0, 1.0, 2.0, 5.0, 15.0), Direction.SOUTH));
        final VoxelShape[] BACKREST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(2.0, 9.0, 2.0, 14.0, 19.0, 5.0), Direction.SOUTH));
        final VoxelShape[] LEFT_BACKREST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 9.0, 2.0, 16.0, 19.0, 5.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_BACKREST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 9.0, 2.0, 2.0, 19.0, 5.0), Direction.SOUTH));
        final VoxelShape[] LEFT_ARM_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 11.0, 1.0, 2.0, 13.0, 15.0), Direction.SOUTH));
        final VoxelShape[] LEFT_ARM_FRONT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 2.0, 13.0, 2.0, 11.0, 15.0), Direction.SOUTH));
        final VoxelShape[] LEFT_ARM_BACK = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 2.0, 1.0, 2.0, 11.0, 3.0), Direction.SOUTH));
        final VoxelShape[] LEFT_ARM_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 0.0, 1.0, 2.0, 2.0, 15.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_ARM_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 11.0, 1.0, 16.0, 13.0, 15.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_ARM_FRONT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 2.0, 13.0, 16.0, 11.0, 15.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_ARM_BACK = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 2.0, 1.0, 16.0, 11.0, 3.0), Direction.SOUTH));
        final VoxelShape[] RIGHT_ARM_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14.0, 0.0, 1.0, 16.0, 2.0, 15.0), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state: states)
        {
            Direction direction = state.get(DIRECTION);
            Type type = state.get(TYPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(BASE[direction.getHorizontalIndex()]);
            shapes.add(BACKREST[direction.getHorizontalIndex()]);
            shapes.add(BACKBASE[direction.getHorizontalIndex()]);
            shapes.add(UNDERBASE[direction.getHorizontalIndex()]);
            switch(type)
            {
                case SINGLE:
                    shapes.add(LEFT_ARM_TOP[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_FRONT[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_BACK[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_BOTTOM[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_TOP[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_FRONT[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_BACK[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_BOTTOM[direction.getHorizontalIndex()]);
                    break;
                case LEFT:
                    shapes.add(LEFT_ARM_TOP[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_FRONT[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_BACK[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_ARM_BOTTOM[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_BACKREST[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_BASE[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_BACKBASE[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_UNDERBASE[direction.getHorizontalIndex()]);
                    break;
                case RIGHT:
                    shapes.add(RIGHT_ARM_TOP[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_FRONT[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_BACK[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_ARM_BOTTOM[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_BACKREST[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_BASE[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_BACKBASE[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_UNDERBASE[direction.getHorizontalIndex()]);
                    break;
                case MIDDLE:
                    shapes.add(LEFT_BACKREST[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_BASE[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_BACKBASE[direction.getHorizontalIndex()]);
                    shapes.add(LEFT_UNDERBASE[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_BACKREST[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_BASE[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_BACKBASE[direction.getHorizontalIndex()]);
                    shapes.add(RIGHT_UNDERBASE[direction.getHorizontalIndex()]);
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
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        return this.getSofaState(state, context.getWorld(), context.getPos(), state.get(DIRECTION));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(!world.isRemote)
        {
            ItemStack stack = playerEntity.getHeldItem(hand);
            return SeatEntity.create(world, pos, 0.4, playerEntity);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        return this.getSofaState(state, world, pos, state.get(DIRECTION));
    }

    private BlockState getSofaState(BlockState state, IWorld world, BlockPos pos, Direction dir)
    {
        boolean left = isSofa(world, pos, dir.rotateYCCW(), dir) || isSofa(world, pos, dir.rotateYCCW(), dir.rotateYCCW());
        boolean right = isSofa(world, pos, dir.rotateY(), dir) || isSofa(world, pos, dir.rotateY(), dir.rotateY());

        if(left && right)
        {
            return state.with(TYPE, ModernSofaBlock.Type.MIDDLE);
        }
        else if(left)
        {
            return state.with(TYPE, ModernSofaBlock.Type.RIGHT);
        }
        else if(right)
        {
            return state.with(TYPE, ModernSofaBlock.Type.LEFT);
        }
        return state.with(TYPE, ModernSofaBlock.Type.SINGLE);
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
        MIDDLE("middle");

        private final String id;

        Type(String id)
        {
            this.id = id;
        }

        @Override
        public String getString()
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
