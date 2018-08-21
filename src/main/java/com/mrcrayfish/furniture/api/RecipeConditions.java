package com.mrcrayfish.furniture.api;

import net.minecraft.item.ItemStack;

import java.util.Map;

public class RecipeConditions
{
    public static boolean hasMineBayArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasOvenArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        if(variables.get("output") == null) return false;
        if(!(variables.get("output") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasFreezerArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        if(variables.get("output") == null) return false;
        if(!(variables.get("output") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasPrinterArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasChoppingBoardArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        if(variables.get("output") == null) return false;
        if(!(variables.get("output") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasBlenderArgs(Map<String, Object> variables)
    {
        if(variables.get("name") == null) return false;
        if(!(variables.get("name") instanceof String)) return false;
        if(variables.get("heal") == null) return false;
        if(!(variables.get("heal") instanceof Integer)) return false;
        if(variables.get("ingredients") == null) return false;
        if(!(variables.get("ingredients") instanceof ItemStack[])) return false;
        if(((ItemStack[]) variables.get("ingredients")).length == 0 | ((ItemStack[]) variables.get("ingredients")).length > 4)
            return false;
        if(variables.get("colour") == null) return false;
        if(!(variables.get("colour") instanceof int[])) return false;
        if(((int[]) variables.get("colour")).length != 3) return false;
        return true;
    }

    public static boolean hasMicrowaveArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        if(variables.get("output") == null) return false;
        if(!(variables.get("output") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasWashingMachineArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasToasterArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        if(variables.get("output") == null) return false;
        if(!(variables.get("output") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasDishwasherArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        return true;
    }

    public static boolean hasGrillArgs(Map<String, Object> variables)
    {
        if(variables.get("input") == null) return false;
        if(!(variables.get("input") instanceof ItemStack)) return false;
        if(variables.get("output") == null) return false;
        if(!(variables.get("output") instanceof ItemStack)) return false;
        return true;
    }
}
