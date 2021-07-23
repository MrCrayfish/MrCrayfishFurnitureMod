package com.mrcrayfish.furniture.common;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Author: MrCrayfish
 */
public class ModTags
{
    public static class Items
    {
        public static final Tag.Named<Item> UPGRADED_FENCES = tag("fences/upgraded");
        public static final Tag.Named<Item> PICKET_FENCES = tag("fences/picket");
        public static final Tag.Named<Item> UPGRADED_FENCE_GATES = tag("fence_gates/upgraded");
        public static final Tag.Named<Item> PICKET_FENCE_GATES = tag("fence_gates/picket");
        public static final Tag.Named<Item> HEDGES = tag("fences/hedge");
        public static final Tag.Named<Item> BEDROOM = tag("bedroom");
        public static final Tag.Named<Item> GENERAL = tag("general");
        public static final Tag.Named<Item> ITEMS = tag("items");
        public static final Tag.Named<Item> KITCHEN = tag("kitchen");
        public static final Tag.Named<Item> OUTDOORS = tag("outdoors");
        public static final Tag.Named<Item> STORAGE = tag("storage");
        public static final Tag.Named<Item> TRAMPOLINE = tag("trampoline");

        private static Tag.Named<Item> tag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation(Reference.MOD_ID, name));
        }
    }

    public static class Blocks
    {
        public static final Tag.Named<Block> UPGRADED_FENCES = tag("fences/upgraded");
        public static final Tag.Named<Block> PICKET_FENCES = tag("fences/picket");
        public static final Tag.Named<Block> UPGRADED_FENCE_GATES = tag("fence_gates/upgraded");
        public static final Tag.Named<Block> PICKET_FENCE_GATES = tag("fence_gates/picket");
        public static final Tag.Named<Block> HEDGES = tag("fences/hedge");

        private static Tag.Named<Block> tag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(Reference.MOD_ID, name));
        }
    }
}
