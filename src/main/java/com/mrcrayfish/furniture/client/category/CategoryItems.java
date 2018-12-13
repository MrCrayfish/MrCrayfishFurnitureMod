package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryItems extends AbstractCategory
{
    public CategoryItems()
    {
        super("cfm.filter.items", new ItemStack(FurnitureItems.TOAST));
    }

    @Override
    public void init()
    {
        add(FurnitureItems.KEBAB);
        add(FurnitureItems.KEBAB_COOKED);
        add(FurnitureItems.SAUSAGE);
        add(FurnitureItems.SAUSAGE_COOKED);
        add(FurnitureItems.BREAD_SLICE);
        add(FurnitureItems.TOAST);
        add(FurnitureItems.FLESH);
        add(FurnitureItems.COOKED_FLESH);
        add(FurnitureItems.SPATULA);
        add(FurnitureItems.KNIFE);
        add(FurnitureItems.CUP);
        add(FurnitureItems.COOL_PACK);
        add(FurnitureItems.SOAP);
        add(FurnitureItems.SOAPY_WATER);
        add(FurnitureItems.SUPER_SOAPY_WATER);
        add(FurnitureItems.ENVELOPE);
        add(FurnitureItems.PACKAGE);
        add(FurnitureItems.INK_CARTRIDGE);
        add(FurnitureItems.RECIPE_BOOK);
        add(FurnitureItems.HAMMER);
        add(FurnitureItems.TV_REMOTE);
        add(FurnitureItems.CROWBAR);
        add(FurnitureItems.LOG);
    }
}