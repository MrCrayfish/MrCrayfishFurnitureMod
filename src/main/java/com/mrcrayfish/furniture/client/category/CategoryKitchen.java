package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryKitchen extends AbstractCategory
{
    public CategoryKitchen()
    {
        super("Kitchen", new ItemStack(FurnitureBlocks.FREEZER));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.BIN);
    }
}
