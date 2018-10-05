package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryStorage extends AbstractCategory
{
    public CategoryStorage()
    {
        super("cfm.filter.storage", new ItemStack(FurnitureBlocks.CABINET_OAK));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.CABINET_OAK);
        add(FurnitureBlocks.CABINET_SPRUCE);
        add(FurnitureBlocks.CABINET_BIRCH);
        add(FurnitureBlocks.CABINET_JUNGLE);
        add(FurnitureBlocks.CABINET_ACACIA);
        add(FurnitureBlocks.CABINET_DARK_OAK);
        add(FurnitureBlocks.CABINET_STONE);
        add(FurnitureBlocks.CABINET_GRANITE);
        add(FurnitureBlocks.CABINET_DIORITE);
        add(FurnitureBlocks.CABINET_ANDESITE);
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
        add(FurnitureBlocks.WALL_CABINET);
        add(FurnitureBlocks.COUNTER_DRAWER);
        add(FurnitureBlocks.KITCHEN_CABINET);
        add(FurnitureBlocks.CRATE);
        add(FurnitureBlocks.CRATE_SPRUCE);
        add(FurnitureBlocks.CRATE_BIRCH);
        add(FurnitureBlocks.CRATE_JUNGLE);
        add(FurnitureBlocks.CRATE_ACACIA);
        add(FurnitureBlocks.CRATE_DARK_OAK);
        add(FurnitureBlocks.COOLER);
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
        add(FurnitureBlocks.MAIL_BOX_OAK);
        add(FurnitureBlocks.MAIL_BOX_SPRUCE);
        add(FurnitureBlocks.MAIL_BOX_BIRCH);
        add(FurnitureBlocks.MAIL_BOX_JUNGLE);
        add(FurnitureBlocks.MAIL_BOX_ACACIA);
        add(FurnitureBlocks.MAIL_BOX_DARK_OAK);
        add(FurnitureBlocks.TV_STAND);
        add(FurnitureItems.CROWBAR);
    }
}
