package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryElectronics extends AbstractCategory
{
    public CategoryElectronics()
    {
        super("cfm.filter.electronics", new ItemStack(FurnitureBlocks.COMPUTER));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.FIRE_ALARM_OFF);
        add(FurnitureBlocks.CEILING_LIGHT_OFF);
        add(FurnitureBlocks.MODERN_LIGHT_OFF);
        add(FurnitureBlocks.CEILING_FAN);
        add(FurnitureBlocks.LIGHT_SWITCH_OFF);
        add(FurnitureBlocks.TV);
        add(FurnitureBlocks.MODERN_TV);
        add(FurnitureItems.TV_REMOTE);
        add(FurnitureBlocks.COMPUTER);
        add(FurnitureBlocks.PRINTER);
        add(FurnitureBlocks.STEREO);
        add(FurnitureBlocks.DIGITAL_CLOCK);
        add(FurnitureBlocks.DOOR_BELL);
        add(FurnitureBlocks.DOOR_BELL_SPRUCE);
        add(FurnitureBlocks.DOOR_BELL_BIRCH);
        add(FurnitureBlocks.DOOR_BELL_JUNGLE);
        add(FurnitureBlocks.DOOR_BELL_ACACIA);
        add(FurnitureBlocks.DOOR_BELL_DARK_OAK);
        add(FurnitureBlocks.TOASTER);
        add(FurnitureBlocks.MICROWAVE);
        add(FurnitureBlocks.DISHWASHER);
        add(FurnitureBlocks.WASHING_MACHINE);
        add(FurnitureBlocks.BLENDER);
    }
}
