package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.tileentity.BasicLootBlockEntity;
import com.mrcrayfish.furniture.tileentity.DeskCabinetBlockEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class DeskCabinetBlock extends DeskBlock implements EntityBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public DeskCabinetBlock(Properties properties, MaterialType materialType)
    {
        super(properties, materialType);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(TYPE, Type.SINGLE).setValue(OPEN, false));
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] DESK_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 14, 0, 16, 16, 16), Direction.SOUTH));
        final VoxelShape[] DESK_BACK = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 2, 1, 16, 14, 3), Direction.SOUTH));
        final VoxelShape[] DESK_LEFT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 0, 2, 14, 15), Direction.SOUTH));
        final VoxelShape[] DESK_RIGHT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(14, 0, 0, 16, 14, 15), Direction.SOUTH));
        final VoxelShape[] DESK_DRAWS = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(2, 2, 3, 14, 14, 15), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.getValue(DIRECTION);
            Type type = state.getValue(TYPE);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(DESK_TOP[direction.get2DDataValue()]);
            shapes.add(DESK_BACK[direction.get2DDataValue()]);
            shapes.add(DESK_DRAWS[direction.get2DDataValue()]);
            switch(type)
            {
                case SINGLE:
                    shapes.add(DESK_LEFT[direction.get2DDataValue()]);
                    shapes.add(DESK_RIGHT[direction.get2DDataValue()]);
                    break;
                case LEFT:
                    shapes.add(DESK_LEFT[direction.get2DDataValue()]);
                    break;
                case RIGHT:
                    shapes.add(DESK_RIGHT[direction.get2DDataValue()]);
                    break;
            }
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(DIRECTION).getOpposite() == result.getDirection())
        {
            if(!level.isClientSide())
            {
                if(level.getBlockEntity(pos) instanceof DeskCabinetBlockEntity blockEntity)
                {
                    player.openMenu(blockEntity);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if(level.getBlockEntity(pos) instanceof BasicLootBlockEntity blockEntity)
        {
            blockEntity.updateOpenerCount();
        }
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
        return new DeskCabinetBlockEntity(pos, state);
    }
}
