package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class DeskBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    private MaterialType materialType;
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public DeskBlock(Properties properties, MaterialType materialType)
    {
        super(properties);
        this.materialType = materialType;
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(TYPE, Type.SINGLE));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    protected ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] DESK_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 14, 0, 16, 16, 16), Direction.SOUTH));
        final VoxelShape[] DESK_BACK = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 2, 1, 16, 14, 3), Direction.SOUTH));
        final VoxelShape[] DESK_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0, 0, 0, 2, 14, 15), Direction.SOUTH));
        final VoxelShape[] DESK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(14, 0, 0, 16, 14, 15), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            Type type = state.get(TYPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(DESK_TOP[direction.getHorizontalIndex()]);
            shapes.add(DESK_BACK[direction.getHorizontalIndex()]);
            switch(type)
            {
                case SINGLE:
                    shapes.add(DESK_LEFT[direction.getHorizontalIndex()]);
                    shapes.add(DESK_RIGHT[direction.getHorizontalIndex()]);
                    break;
                case LEFT:
                    shapes.add(DESK_LEFT[direction.getHorizontalIndex()]);
                    break;
                case RIGHT:
                    shapes.add(DESK_RIGHT[direction.getHorizontalIndex()]);
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
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        Direction dir = state.get(DIRECTION);
        boolean left = isDesk(world, pos, dir.rotateYCCW(), dir);
        boolean right = isDesk(world, pos, dir.rotateY(), dir);
        if(left && right)
        {
            return state.with(TYPE, Type.MIDDLE);
        }
        if(left)
        {
            return state.with(TYPE, Type.RIGHT);
        }
        if(right)
        {
            return state.with(TYPE, Type.LEFT);
        }
        return state.with(TYPE, Type.SINGLE);
    }

    private boolean isDesk(IWorld world, BlockPos source, Direction checkDirection, Direction tableDirection)
    {
        BlockState state = world.getBlockState(source.offset(checkDirection));
        return state.getBlock() instanceof DeskBlock && ((DeskBlock) state.getBlock()).materialType == materialType && state.get(DIRECTION) == tableDirection;
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

    public enum MaterialType
    {
        OAK,
        BIRCH,
        SPRUCE,
        JUNGLE,
        ACACIA,
        DARK_OAK,
        CRIMSON,
        WARPED,
        STONE,
        GRANITE,
        DIORITE,
        ANDESITE,
        STRIPPED_OAK,
        STRIPPED_BIRCH,
        STRIPPED_SPRUCE,
        STRIPPED_JUNGLE,
        STRIPPED_ACACIA,
        STRIPPED_DARK_OAK,
        STRIPPED_CRIMSON,
        STRIPPED_WARPED,
        WHITE_CONCRETE,
        ORANGE_CONCRETE,
        MAGENTA_CONCRETE,
        LIGHT_BLUE_CONCRETE,
        YELLOW_CONCRETE,
        LIME_CONCRETE,
        PINK_CONCRETE,
        GRAY_CONCRETE,
        LIGHT_GRAY_CONCRETE,
        CYAN_CONCRETE,
        PURPLE_CONCRETE,
        BLUE_CONCRETE,
        BROWN_CONCRETE,
        GREEN_CONCRETE,
        RED_CONCRETE,
        BLACK_CONCRETE
    }
}