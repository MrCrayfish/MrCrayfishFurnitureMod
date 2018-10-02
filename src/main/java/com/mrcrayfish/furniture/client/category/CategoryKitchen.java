package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryKitchen extends AbstractCategory
{
    public CategoryKitchen()
    {
        super("cfm.filter.kitchen", new ItemStack(FurnitureBlocks.FREEZER));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.FREEZER);
        add(FurnitureBlocks.OVEN);
        add(FurnitureBlocks.RANGE_HOOD);
        add(FurnitureBlocks.FIRE_ALARM_OFF);
        add(FurnitureBlocks.BIN);
        add(FurnitureBlocks.TOASTER);
        add(FurnitureBlocks.MICROWAVE);
        add(FurnitureBlocks.WASHING_MACHINE);
        add(FurnitureBlocks.DISHWASHER);
        add(FurnitureBlocks.COOKIE_JAR);
        add(FurnitureBlocks.BLENDER);
        add(FurnitureBlocks.PLATE);
        add(FurnitureBlocks.CHOPPING_BOARD);
        add(FurnitureBlocks.COUNTER);
        add(FurnitureBlocks.COUNTER_DRAWER);
        add(FurnitureBlocks.COUNTER_SINK);
        add(FurnitureBlocks.KITCHEN_CABINET);
        add(FurnitureBlocks.BAR_STOOL);
        add(FurnitureItems.COOL_PACK);
        add(FurnitureItems.BREAD_SLICE);
        add(FurnitureItems.TOAST);
        add(FurnitureItems.SAUSAGE);
        add(FurnitureItems.SAUSAGE_COOKED);
        add(FurnitureItems.KEBAB);
        add(FurnitureItems.KEBAB_COOKED);
        add(FurnitureItems.KNIFE);
        add(FurnitureItems.CUP);
        add(FurnitureItems.SOAP);
        add(FurnitureItems.SOAPY_WATER);
        add(FurnitureItems.SUPER_SOAPY_WATER);
        add(FurnitureBlocks.MODERN_WINDOW);
        add(FurnitureBlocks.MODERN_TABLE);
        add(FurnitureBlocks.MODERN_CHAIR);
    }
}
