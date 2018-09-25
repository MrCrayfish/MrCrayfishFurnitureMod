package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryOutdoor extends AbstractCategory
{
    public CategoryOutdoor()
    {
        super("Outdoor", new ItemStack(FurnitureBlocks.FIRE_PIT_ON));
    }

    @Override
    public void init()
    {

    }
}
