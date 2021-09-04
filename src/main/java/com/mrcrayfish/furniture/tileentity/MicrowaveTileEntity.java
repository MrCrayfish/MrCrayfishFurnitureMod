package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MicrowaveTileEntity extends BasicLootTileEntity
{
    private int playerCount;

    protected MicrowaveTileEntity(TileEntityType<?> type) { super(type); }

    public MicrowaveTileEntity() { super(ModTileEntities.MICROWAVE.get()); }

    @Override
    public int getSizeInventory() { return 9; }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container.cfm.microwave");
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory)
    {
        return new ChestContainer(ContainerType.GENERIC_9X1, windowId, playerInventory, this, 1);
    }
}