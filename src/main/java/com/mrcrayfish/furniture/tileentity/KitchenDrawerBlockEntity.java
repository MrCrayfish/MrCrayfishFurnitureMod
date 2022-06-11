package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.DeskCabinetBlock;
import com.mrcrayfish.furniture.block.KitchenDrawerBlock;
import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class KitchenDrawerBlockEntity extends BasicLootBlockEntity
{
    protected KitchenDrawerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public KitchenDrawerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.KITCHEN_DRAWER.get(), pos, state);
    }

    @Override
    public int getContainerSize()
    {
        return 9;
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.translatable("container.cfm.kitchen_drawer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new ChestMenu(MenuType.GENERIC_9x1, windowId, playerInventory, this, 1);
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        this.playDrawerSound(state, ModSounds.BLOCK_BEDSIDE_CABINET_OPEN.get());
        this.setDrawerState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.playDrawerSound(state, ModSounds.BLOCK_BEDSIDE_CABINET_CLOSE.get());
        this.setDrawerState(state, false);
    }

    private void playDrawerSound(BlockState state, SoundEvent event)
    {
        Vec3i directionVec = state.getValue(DeskCabinetBlock.DIRECTION).getNormal();
        double x = this.worldPosition.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.worldPosition.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.worldPosition.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        Level level = this.getLevel();
        if(level != null)
        {
            level.playSound(null, x, y, z, event, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }
    }

    private void setDrawerState(BlockState blockState, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), blockState.setValue(KitchenDrawerBlock.OPEN, open), 3);
        }
    }
}
