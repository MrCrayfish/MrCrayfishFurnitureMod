package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.tileentity.BasicLootBlockEntity;
import com.mrcrayfish.furniture.tileentity.BedsideCabinetBlockEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class BedsideCabinetBlock extends FurnitureHorizontalBlock implements EntityBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public BedsideCabinetBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(OPEN, false));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] LEG_BACK_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 0, 1, 3, 2, 3), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 0, 11, 3, 2, 13), Direction.SOUTH));
        final VoxelShape[] LEG_FRONT_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 0, 11, 15, 2, 13), Direction.SOUTH));
        final VoxelShape[] LEG_BACK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 0, 1, 15, 2, 3), Direction.SOUTH));
        final VoxelShape[] TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 14, 0, 16, 16, 16), Direction.SOUTH));
        final VoxelShape[] HANDLE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(5, 5, 15, 11, 6, 16), Direction.SOUTH));
        final VoxelShape[] BASE_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 2, 0, 16, 14, 15), Direction.SOUTH));
        final VoxelShape[] HANDLE_TOP_CLOSED = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(5, 11, 15, 11, 12, 16), Direction.SOUTH));
        final VoxelShape[] BASE_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 2, 0, 16, 14, 13), Direction.SOUTH));
        final VoxelShape[] DRAW_TOP_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 8, 20, 16, 14, 22), Direction.SOUTH));
        final VoxelShape[] DRAW_BOTTOM_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 2, 13, 16, 8, 15), Direction.SOUTH));
        final VoxelShape[] HANDLE_TOP_OPEN = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(5, 11, 22, 11, 12, 23), Direction.SOUTH));
        final VoxelShape[] DRAW_INSIDE_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 8, 13, 15, 10, 20), Direction.SOUTH));
        final VoxelShape[] DRAW_INSIDE_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1, 10, 13, 3, 14, 20), Direction.SOUTH));
        final VoxelShape[] DRAW_INSIDE_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 10, 13, 15, 14, 20), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(LEG_BACK_LEG[direction.get2DDataValue()]);
            shapes.add(LEG_FRONT_LEFT[direction.get2DDataValue()]);
            shapes.add(LEG_FRONT_RIGHT[direction.get2DDataValue()]);
            shapes.add(LEG_BACK_RIGHT[direction.get2DDataValue()]);
            shapes.add(TOP[direction.get2DDataValue()]);
            shapes.add(HANDLE_BOTTOM[direction.get2DDataValue()]);
            if(state.getValue(OPEN))
            {
                shapes.add(BASE_OPEN[direction.get2DDataValue()]);
                shapes.add(DRAW_TOP_OPEN[direction.get2DDataValue()]);
                shapes.add(DRAW_BOTTOM_OPEN[direction.get2DDataValue()]);
                shapes.add(HANDLE_TOP_OPEN[direction.get2DDataValue()]);
                shapes.add(DRAW_INSIDE_BOTTOM[direction.get2DDataValue()]);
                shapes.add(DRAW_INSIDE_LEFT[direction.get2DDataValue()]);
                shapes.add(DRAW_INSIDE_RIGHT[direction.get2DDataValue()]);
            }
            else
            {
                shapes.add(BASE_CLOSED[direction.get2DDataValue()]);
                shapes.add(HANDLE_TOP_CLOSED[direction.get2DDataValue()]);
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(DIRECTION).getOpposite() == result.getDirection())
        {
            if(!level.isClientSide())
            {
                if(level.getBlockEntity(pos) instanceof BedsideCabinetBlockEntity cabinet)
                {
                    player.openMenu(cabinet);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new BedsideCabinetBlockEntity(pos, state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if(level.getBlockEntity(pos) instanceof BasicLootBlockEntity blockEntity)
        {
            blockEntity.updateOpenerCount();
        }
    }
}
