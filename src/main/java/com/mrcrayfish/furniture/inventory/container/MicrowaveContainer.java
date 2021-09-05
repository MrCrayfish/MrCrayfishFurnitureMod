package com.mrcrayfish.furniture.inventory.container;

import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.tileentity.MicrowaveTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IIntArray;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MicrowaveContainer extends Container
{
    private MicrowaveTileEntity microwaveTileEntity;
    private IIntArray trackedData;
    private World world;

    public MicrowaveContainer(int windowId, PlayerInventory playerInventory, MicrowaveTileEntity microwaveTileEntity)
    {
        super(ModContainers.MICROWAVE.get(), windowId);

        assertInventorySize(microwaveTileEntity, 1);
        assertInventorySize(microwaveTileEntity.getMicrowaveData(), 1);

        microwaveTileEntity.openInventory(playerInventory.player);

        this.microwaveTileEntity = microwaveTileEntity;
        this.trackedData = microwaveTileEntity.getMicrowaveData();
        this.world = playerInventory.player.world;

        this.addSlot(new Slot(microwaveTileEntity, 0, 56, 17));
    }

    protected MicrowaveContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }

}
