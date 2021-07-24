package com.mrcrayfish.furniture.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class BasicContainerOpenersCounter extends ContainerOpenersCounter
{
    private BasicLootBlockEntity blockEntity;

    public BasicContainerOpenersCounter(BasicLootBlockEntity blockEntity)
    {
        this.blockEntity = blockEntity;
    }

    @Override
    protected void onOpen(Level level, BlockPos pos, BlockState state)
    {
        this.blockEntity.onOpen(level, pos, state);
    }

    @Override
    protected void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.blockEntity.onClose(level, pos, state);
    }

    @Override
    protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int oldCount, int count)
    {
        level.blockEvent(pos, state.getBlock(), 1, count);
    }

    @Override
    protected boolean isOwnContainer(Player player)
    {
        return this.blockEntity.isMatchingContainerMenu(player.containerMenu);
    }
}
