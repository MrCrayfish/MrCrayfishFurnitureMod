package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ChairBlock extends FurnitureHorizontalWaterloggedBlock
{
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;
    
    public ChairBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateDefinition().getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] FRONT_LEFT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(2, 0, 12, 4, 8, 14), Direction.SOUTH));
        final VoxelShape[] BACKREST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1.5, 10, 1.5, 14.5, 20, 3.5), Direction.SOUTH));
        final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(1.5, 8, 1.5, 14.5, 10, 14.5), Direction.SOUTH));
        final VoxelShape[] FRONT_RIGHT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(12, 0, 12, 14, 8, 14), Direction.SOUTH));
        final VoxelShape[] BACK_RIGHT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(12, 0, 2, 14, 8, 4), Direction.SOUTH));
        final VoxelShape[] BACK_LEFT_LEG = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(2, 0, 2, 4, 8, 4), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(FRONT_LEFT_LEG[direction.get2DDataValue()]);
            shapes.add(BACKREST[direction.get2DDataValue()]);
            shapes.add(BASE[direction.get2DDataValue()]);
            shapes.add(FRONT_RIGHT_LEG[direction.get2DDataValue()]);
            shapes.add(BACK_RIGHT_LEG[direction.get2DDataValue()]);
            shapes.add(BACK_LEFT_LEG[direction.get2DDataValue()]);
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
        return SeatEntity.create(level, pos, 0.4, player, state.getValue(DIRECTION));
    }
}