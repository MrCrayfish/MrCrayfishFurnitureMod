package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryLighting extends AbstractCategory
{
    public CategoryLighting()
    {
        super("cfm.filter.lighting", new ItemStack(FurnitureBlocks.LAMP_OFF, 1, 14));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.LAMP_OFF);
        add(FurnitureBlocks.CEILING_LIGHT_OFF);
        add(FurnitureBlocks.MODERN_LIGHT_OFF);
        add(FurnitureBlocks.LIGHT_SWITCH_OFF);
        add(FurnitureBlocks.TREE_BOTTOM);
        add(FurnitureBlocks.FAIRY_LIGHT);
        add(FurnitureBlocks.FIRE_PIT_OFF);
    }
}
