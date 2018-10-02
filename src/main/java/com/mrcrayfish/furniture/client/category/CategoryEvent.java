package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryEvent extends AbstractCategory
{
    public CategoryEvent()
    {
        super("cfm.filter.event", new ItemStack(FurnitureBlocks.TREE_BOTTOM));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.PRESENT);
        add(FurnitureBlocks.TREE_BOTTOM);
        add(FurnitureBlocks.MANTEL_PIECE);
        add(FurnitureBlocks.GRAND_CHAIR_BOTTOM);
        add(FurnitureBlocks.CANDLE);
        add(FurnitureBlocks.CHIMNEY);
        add(FurnitureBlocks.WREATH);
        add(FurnitureBlocks.FAIRY_LIGHT);
        add(FurnitureBlocks.FIRE_PIT_OFF);
    }
}
