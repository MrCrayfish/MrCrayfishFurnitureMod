package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.tileentity.BasicLootBlockEntity;
import com.mrcrayfish.furniture.tileentity.KitchenDrawerBlockEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class KitchenDrawerBlock extends FurnitureHorizontalBlock implements EntityBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public KitchenDrawerBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(OPEN, false).setValue(DIRECTION, Direction.NORTH));
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 ->
        {
            final VoxelShape TOP = Block.box(0, 13, 0, 16, 16, 16);
            final VoxelShape[] DEFAULT_BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 0, 0, 16, 13, 15), Direction.SOUTH));
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP);
            shapes.add(DEFAULT_BASE[state.getValue(DIRECTION).get2DDataValue()]);
            return VoxelShapeHelper.combineAll(shapes);
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(DIRECTION).getOpposite() == result.getDirection())
        {
            if(!level.isClientSide())
            {
                if(level.getBlockEntity(pos) instanceof KitchenDrawerBlockEntity blockEntity)
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new KitchenDrawerBlockEntity(pos, state);
    }
}
