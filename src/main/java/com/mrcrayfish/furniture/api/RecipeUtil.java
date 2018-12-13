package com.mrcrayfish.furniture.api;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import java.util.ArrayList;

public class RecipeUtil
{
    public static void printRequired(String type, String desc, String modName)
    {
        MrCrayfishFurnitureMod.logger().warn("## RecipeAPI Error Report ##");
        MrCrayfishFurnitureMod.logger().warn("From Mod: " + modName);
        MrCrayfishFurnitureMod.logger().warn("Type: " + type);
        MrCrayfishFurnitureMod.logger().warn("Description: " + desc);
        MrCrayfishFurnitureMod.logger().warn("Required Variables:");
        for(String var : getRequiredVariablesList(type))
        {
            MrCrayfishFurnitureMod.logger().warn("- " + var);
        }
    }

    public static void printUnknownType(Parser parser, int num, String desc)
    {
        MrCrayfishFurnitureMod.logger().warn("## RecipeAPI Error Report ##");
        MrCrayfishFurnitureMod.logger().warn("Recipe Number: " + num);
        MrCrayfishFurnitureMod.logger().warn("Unknown Type: " + parser.getValue("type", null));
        MrCrayfishFurnitureMod.logger().warn("Description: " + desc);
        MrCrayfishFurnitureMod.logger().warn("Variables: ");
        printVariables(parser, "");
    }

    public static void printMissing(Parser parser, int num, String missingKey, String desc)
    {
        MrCrayfishFurnitureMod.logger().warn("## RecipeAPI Error Report ##");
        MrCrayfishFurnitureMod.logger().warn("Recipe Number: " + num);
        MrCrayfishFurnitureMod.logger().warn("Type: " + parser.getValue("type", null));
        MrCrayfishFurnitureMod.logger().warn("Description: " + desc);
        MrCrayfishFurnitureMod.logger().warn("Variables: ");
        MrCrayfishFurnitureMod.logger().warn("- " + missingKey + ": (Missing)");
        printVariables(parser, "");
    }

    public static void printReport(Parser parser, int num, String erroredKey, String desc)
    {
        MrCrayfishFurnitureMod.logger().warn("## RecipeAPI Error Report ##");
        MrCrayfishFurnitureMod.logger().warn("Recipe Number: " + num);
        MrCrayfishFurnitureMod.logger().warn("Type: " + parser.getValue("type", null) + (erroredKey.equalsIgnoreCase("type") ? " (Error)" : ""));
        MrCrayfishFurnitureMod.logger().warn("Description: " + desc);
        MrCrayfishFurnitureMod.logger().warn("Variables: ");
        printVariables(parser, erroredKey);
    }

    private static void printVariables(Parser parser, String erroredKey)
    {
        for(String key : parser.getMap().keySet())
        {
            if(!key.equalsIgnoreCase("type"))
            {
                String pre = "- " + key + ": " + parser.getValue(key, null);
                if(key.equalsIgnoreCase(erroredKey))
                {
                    pre += " (Error)";
                }
                MrCrayfishFurnitureMod.logger().warn(pre);
            }
        }
    }

    private static ArrayList<String> getRequiredVariablesList(String type)
    {
        ArrayList<String> vars = new ArrayList<>();
        if(type.equalsIgnoreCase("minebay") | type.equalsIgnoreCase("oven") | type.equalsIgnoreCase("freezer") | type.equalsIgnoreCase("printer") | type.equalsIgnoreCase("choppingboard") | type.equalsIgnoreCase("microwave") | type.equalsIgnoreCase("washingmachine") | type.equalsIgnoreCase("toaster") | type.equalsIgnoreCase("dishwasher") | type.equalsIgnoreCase("grill"))
        {
            vars.add("input");
        }

        if(type.equalsIgnoreCase("oven") | type.equalsIgnoreCase("freezer") | type.equalsIgnoreCase("choppingboard") | type.equalsIgnoreCase("microwave") | type.equalsIgnoreCase("toaster") | type.equalsIgnoreCase("grill"))
        {
            vars.add("output");
        }

        if(type.equalsIgnoreCase("blender"))
        {
            vars.add("name");
            vars.add("heal");
            vars.add("ingredients");
            vars.add("colour");
        }
        return vars;
    }
}