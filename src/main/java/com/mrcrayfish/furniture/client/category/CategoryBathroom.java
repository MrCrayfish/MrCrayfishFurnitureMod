package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryBathroom extends AbstractCategory
{
    public CategoryBathroom()
    {
        super("cfm.filter.bathroom", new ItemStack(FurnitureBlocks.TOILET));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.TOILET);
        add(FurnitureBlocks.BASIN);
        add(FurnitureBlocks.WALL_CABINET);
        add(FurnitureBlocks.BATH_2);
        add(FurnitureBlocks.SHOWER_BOTTOM);
        add(FurnitureBlocks.SHOWER_HEAD_OFF);
        add(FurnitureBlocks.BIN);
        add(FurnitureBlocks.MIRROR);
        add(FurnitureBlocks.MODERN_WINDOW);
    }
}
