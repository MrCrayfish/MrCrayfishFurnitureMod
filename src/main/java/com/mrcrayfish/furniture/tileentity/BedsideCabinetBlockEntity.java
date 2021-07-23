package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.BedsideCabinetBlock;
import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
public class BedsideCabinetBlockEntity extends BasicLootBlockEntity
{
    public BedsideCabinetBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.BEDSIDE_CABINET.get(), pos, state);
    }

    protected BedsideCabinetBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public int getContainerSize()
    {
        return 9;
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("container.cfm.bedside_cabinet");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new ChestMenu(MenuType.GENERIC_9x1, windowId, playerInventory, this, 1);
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        this.playDoorSound(state, ModSounds.BLOCK_BEDSIDE_CABINET_OPEN.get());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.playDoorSound(state, ModSounds.BLOCK_BEDSIDE_CABINET_CLOSE.get());
        this.setDoorState(state, false);
    }

    private void playDoorSound(BlockState blockState, SoundEvent soundEvent)
    {
        Vec3i directionVec = blockState.getValue(BedsideCabinetBlock.DIRECTION).getNormal();
        double x = this.worldPosition.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.worldPosition.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.worldPosition.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        Level level = this.getLevel();
        if(level != null)
        {
            level.playSound(null, x, y, z, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }
    }

    private void setDoorState(BlockState blockState, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), blockState.setValue(BedsideCabinetBlock.OPEN, open), 3);
        }
    }
}
