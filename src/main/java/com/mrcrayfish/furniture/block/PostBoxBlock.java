package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.inventory.container.PostBoxContainer;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class PostBoxBlock extends FurnitureHorizontalWaterloggedBlock
{
    private static final TranslationTextComponent TITLE = new TranslationTextComponent("container.cfm.post_box");

    public final VoxelShape SHAPE;

    public PostBoxBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
        SHAPE = this.generateShape();
    }

    private VoxelShape generateShape()
    {
        List<VoxelShape> shapes = new ArrayList<>();
        shapes.add(Block.makeCuboidShape(1, 0, 1, 3, 6, 3));
        shapes.add(Block.makeCuboidShape(13, 0, 1, 15, 6, 3));
        shapes.add(Block.makeCuboidShape(13, 0, 13, 15, 6, 15));
        shapes.add(Block.makeCuboidShape(1, 0, 13, 3, 6, 15));
        shapes.add(Block.makeCuboidShape(1, 6, 1, 15, 23, 15));
        return VoxelShapeHelper.combineAll(shapes);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        playerEntity.openContainer(state.getContainer(world, pos));
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos)
    {
        return new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> {
            return new PostBoxContainer(windowId, playerInventory, IWorldPosCallable.of(world, pos));
        }, TITLE);
    }
}
