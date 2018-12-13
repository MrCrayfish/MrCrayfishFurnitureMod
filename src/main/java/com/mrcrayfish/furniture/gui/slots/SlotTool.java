package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.api.RecipeAPI;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;

public class SlotTool extends Slot
{
    public static final String[] SLOT_NAMES = {Reference.MOD_ID + ":items/outline_pickaxe", Reference.MOD_ID + ":items/outline_shovel", Reference.MOD_ID + ":items/outline_sword", Reference.MOD_ID + ":items/outline_axe", Reference.MOD_ID + ":items/outline_hoe", "minecraft:items/empty_armor_slot_shield"};

    private int toolType;

    public SlotTool(IInventory dishwasher, int id, int x, int y, int toolType)
    {
        super(dishwasher, id, x, y);
        this.toolType = toolType;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        if(stack == null)
        {
            return false;
        }

        Item item = stack.getItem();
        switch(toolType)
        {
            case 0:
                if(item instanceof ItemPickaxe)
                {
                    return true;
                }
                break;
            case 1:
                if(item instanceof ItemSpade)
                {
                    return true;
                }
                break;
            case 2:
                if(item instanceof ItemSword)
                {
                    return true;
                }
                break;
            case 3:
                if(item instanceof ItemAxe)
                {
                    return true;
                }
                break;
            case 4:
                if(item instanceof ItemHoe)
                {
                    return true;
                }
                break;
            case 5:
                return !(item instanceof ItemPickaxe || item instanceof ItemSpade || item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemHoe) && RecipeAPI.getDishwasherRecipeFromInput(stack) != null;
        }

        return false;
    }

    @Override
    public String getSlotTexture()
    {
        return toolType < SLOT_NAMES.length && toolType >= 0 ? SLOT_NAMES[toolType] : super.getSlotTexture();
    }
}