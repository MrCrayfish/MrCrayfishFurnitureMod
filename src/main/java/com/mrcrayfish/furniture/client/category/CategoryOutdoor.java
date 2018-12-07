package com.mrcrayfish.furniture.client.category;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CategoryOutdoor extends AbstractCategory
{
    public CategoryOutdoor()
    {
        super("cfm.filter.outdoor", new ItemStack(FurnitureBlocks.FIRE_PIT_ON));
    }

    @Override
    public void init()
    {
        add(FurnitureBlocks.HEDGE_OAK);
        add(FurnitureBlocks.HEDGE_SPRUCE);
        add(FurnitureBlocks.HEDGE_BIRCH);
        add(FurnitureBlocks.HEDGE_JUNGLE);
        add(FurnitureBlocks.HEDGE_ACACIA);
        add(FurnitureBlocks.HEDGE_DARK_OAK);
        add(FurnitureBlocks.BIRD_BATH);
        add(FurnitureBlocks.STONE_PATH);
        add(FurnitureBlocks.WHITE_FENCE);
        add(FurnitureBlocks.ELECTRIC_FENCE);
        add(FurnitureBlocks.TAP);
        add(FurnitureBlocks.MAIL_BOX_OAK);
        add(FurnitureBlocks.MAIL_BOX_SPRUCE);
        add(FurnitureBlocks.MAIL_BOX_BIRCH);
        add(FurnitureBlocks.MAIL_BOX_JUNGLE);
        add(FurnitureBlocks.MAIL_BOX_ACACIA);
        add(FurnitureBlocks.MAIL_BOX_DARK_OAK);
        add(FurnitureBlocks.DOOR_BELL);
        add(FurnitureBlocks.DOOR_BELL_SPRUCE);
        add(FurnitureBlocks.DOOR_BELL_BIRCH);
        add(FurnitureBlocks.DOOR_BELL_JUNGLE);
        add(FurnitureBlocks.DOOR_BELL_ACACIA);
        add(FurnitureBlocks.DOOR_BELL_DARK_OAK);
        add(FurnitureItems.LOG);
        add(FurnitureBlocks.FIRE_PIT_OFF);
        add(FurnitureBlocks.TRAMPOLINE);
        add(FurnitureBlocks.CRATE);
        add(FurnitureBlocks.CRATE_SPRUCE);
        add(FurnitureBlocks.CRATE_BIRCH);
        add(FurnitureBlocks.CRATE_JUNGLE);
        add(FurnitureBlocks.CRATE_ACACIA);
        add(FurnitureBlocks.CRATE_DARK_OAK);
        add(FurnitureBlocks.TABLE_OUTDOOR);
        add(FurnitureBlocks.BENCH);
        add(FurnitureBlocks.GRILL);
        add(FurnitureBlocks.DIVING_BOARD_BASE);
        add(FurnitureBlocks.DOOR_MAT);
        add(FurnitureBlocks.COOLER);
        add(FurnitureBlocks.MODERN_SLIDING_DOOR);
        add(FurnitureBlocks.MODERN_TABLE_OUTDOOR);
        add(FurnitureBlocks.OAK_FENCE_UPGRADED);
        add(FurnitureBlocks.SPRUCE_FENCE_UPGRADED);
        add(FurnitureBlocks.BIRCH_FENCE_UPGRADED);
        add(FurnitureBlocks.JUNGLE_FENCE_UPGRADED);
        add(FurnitureBlocks.ACACIA_FENCE_UPGRADED);
        add(FurnitureBlocks.DARK_OAK_FENCE_UPGRADED);
        add(FurnitureBlocks.NETHER_BRICK_FENCE_UPGRADED);
        add(FurnitureBlocks.OAK_GATE_UPGRADED);
        add(FurnitureBlocks.SPRUCE_GATE_UPGRADED);
        add(FurnitureBlocks.BIRCH_GATE_UPGRADED);
        add(FurnitureBlocks.JUNGLE_GATE_UPGRADED);
        add(FurnitureBlocks.ACACIA_GATE_UPGRADED);
        add(FurnitureBlocks.DARK_OAK_GATE_UPGRADED);
        add(FurnitureItems.SPATULA);
        add(FurnitureItems.SAUSAGE);
        add(FurnitureItems.SAUSAGE_COOKED);
        add(FurnitureItems.KEBAB);
        add(FurnitureItems.KEBAB_COOKED);
    }
}
