/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.gui.slots;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.api.RecipeAPI;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

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
