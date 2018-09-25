package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryGeneral extends AbstractCategory
{
    public CategoryGeneral()
    {
        super("General", new ItemStack(FurnitureBlocks.TABLE_OAK));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.TABLE_OAK);
        add(FurnitureBlocks.TABLE_SPRUCE);
        add(FurnitureBlocks.TABLE_BIRCH);
        add(FurnitureBlocks.TABLE_JUNGLE);
        add(FurnitureBlocks.TABLE_ACACIA);
        add(FurnitureBlocks.TABLE_DARK_OAK);
        add(FurnitureBlocks.TABLE_STONE);
        add(FurnitureBlocks.TABLE_GRANITE);
        add(FurnitureBlocks.TABLE_DIORITE);
        add(FurnitureBlocks.TABLE_ANDESITE);
    }
}
