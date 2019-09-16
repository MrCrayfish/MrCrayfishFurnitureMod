package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.inventory.container.PostBoxContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class PostBoxBlock extends FurnitureHorizontalWaterloggedBlock
{
    private static final TranslationTextComponent TITLE = new TranslationTextComponent("container.cfm.post_box");

    public PostBoxBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        playerEntity.openContainer(state.getContainer(world, pos));
        return true;
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
