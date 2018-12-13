package com.mrcrayfish.furniture.gui.containers;

import com.mrcrayfish.furniture.gui.inventory.ItemInventory;
import net.minecraft.inventory.Container;

/**
 * Author: MrCrayfish
 */
public abstract class ContainerItemInventory extends Container
{
    public abstract ItemInventory getItemInventory();
}