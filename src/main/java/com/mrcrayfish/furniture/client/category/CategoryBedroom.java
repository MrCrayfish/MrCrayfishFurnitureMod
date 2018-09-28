package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryBedroom extends AbstractCategory
{
    public CategoryBedroom()
    {
        super("Bedroom", new ItemStack(FurnitureBlocks.MODERN_BED_BOTTOM, 1, 14));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.MODERN_BED_BOTTOM);
        add(FurnitureBlocks.BEDSIDE_CABINET_OAK);
        add(FurnitureBlocks.BEDSIDE_CABINET_SPRUCE);
        add(FurnitureBlocks.BEDSIDE_CABINET_BIRCH);
        add(FurnitureBlocks.BEDSIDE_CABINET_JUNGLE);
        add(FurnitureBlocks.BEDSIDE_CABINET_ACACIA);
        add(FurnitureBlocks.BEDSIDE_CABINET_DARK_OAK);
        add(FurnitureBlocks.BEDSIDE_CABINET_STONE);
        add(FurnitureBlocks.BEDSIDE_CABINET_GRANITE);
        add(FurnitureBlocks.BEDSIDE_CABINET_DIORITE);
        add(FurnitureBlocks.BEDSIDE_CABINET_ANDESITE);
        add(FurnitureBlocks.LAMP_OFF);
        add(FurnitureBlocks.PHOTO_FRAME);
        add(FurnitureBlocks.DIGITAL_CLOCK);
        add(FurnitureBlocks.CEILING_LIGHT_OFF);
        add(FurnitureBlocks.CEILING_FAN);
        add(FurnitureBlocks.LIGHT_SWITCH_OFF);
        add(FurnitureBlocks.MIRROR);
        add(FurnitureBlocks.CURTAINS);
        add(FurnitureBlocks.BLINDS);
        add(FurnitureBlocks.MODERN_TV);
        add(FurnitureBlocks.TV);
        add(FurnitureItems.TV_REMOTE);
        add(FurnitureBlocks.STEREO);
        add(FurnitureBlocks.MODERN_WINDOW);
        add(FurnitureBlocks.MODERN_SLIDING_DOOR);
        add(FurnitureBlocks.DESK_SPRUCE);
        add(FurnitureBlocks.DESK_BIRCH);
        add(FurnitureBlocks.DESK_JUNGLE);
        add(FurnitureBlocks.DESK_ACACIA);
        add(FurnitureBlocks.DESK_DARK_OAK);
        add(FurnitureBlocks.DESK_STONE);
        add(FurnitureBlocks.DESK_GRANITE);
        add(FurnitureBlocks.DESK_DIORITE);
        add(FurnitureBlocks.DESK_ANDESITE);
        add(FurnitureBlocks.DESK_CABINET_OAK);
        add(FurnitureBlocks.DESK_CABINET_SPRUCE);
        add(FurnitureBlocks.DESK_CABINET_BIRCH);
        add(FurnitureBlocks.DESK_CABINET_JUNGLE);
        add(FurnitureBlocks.DESK_CABINET_ACACIA);
        add(FurnitureBlocks.DESK_CABINET_DARK_OAK);
        add(FurnitureBlocks.DESK_CABINET_STONE);
        add(FurnitureBlocks.DESK_CABINET_GRANITE);
        add(FurnitureBlocks.DESK_CABINET_DIORITE);
        add(FurnitureBlocks.DESK_CABINET_ANDESITE);
        add(FurnitureBlocks.CHAIR_OAK);
        add(FurnitureBlocks.CHAIR_SPRUCE);
        add(FurnitureBlocks.CHAIR_BIRCH);
        add(FurnitureBlocks.CHAIR_JUNGLE);
        add(FurnitureBlocks.CHAIR_ACACIA);
        add(FurnitureBlocks.CHAIR_DARK_OAK);
        add(FurnitureBlocks.CHAIR_STONE);
        add(FurnitureBlocks.CHAIR_GRANITE);
        add(FurnitureBlocks.CHAIR_DIORITE);
        add(FurnitureBlocks.CHAIR_ANDESITE);
    }
}
