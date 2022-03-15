package com.mrcrayfish.furniture.common;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Author: MrCrayfish
 */
public class ModTags
{
    public static class Items
    {
        public static final TagKey<Item> UPGRADED_FENCES = tag("fences/upgraded");
        public static final TagKey<Item> PICKET_FENCES = tag("fences/picket");
        public static final TagKey<Item> UPGRADED_FENCE_GATES = tag("fence_gates/upgraded");
        public static final TagKey<Item> PICKET_FENCE_GATES = tag("fence_gates/picket");
        public static final TagKey<Item> HEDGES = tag("fences/hedge");
        public static final TagKey<Item> BEDROOM = tag("bedroom");
        public static final TagKey<Item> GENERAL = tag("general");
        public static final TagKey<Item> ITEMS = tag("items");
        public static final TagKey<Item> KITCHEN = tag("kitchen");
        public static final TagKey<Item> OUTDOORS = tag("outdoors");
        public static final TagKey<Item> STORAGE = tag("storage");
        public static final TagKey<Item> TRAMPOLINE = tag("trampoline");

        private static TagKey<Item> tag(String name)
        {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Reference.MOD_ID, name));
        }
    }

    public static class Blocks
    {
        public static final TagKey<Block> UPGRADED_FENCES = tag("fences/upgraded");
        public static final TagKey<Block> PICKET_FENCES = tag("fences/picket");
        public static final TagKey<Block> UPGRADED_FENCE_GATES = tag("fence_gates/upgraded");
        public static final TagKey<Block> PICKET_FENCE_GATES = tag("fence_gates/picket");
        public static final TagKey<Block> HEDGES = tag("fences/hedge");

        private static TagKey<Block> tag(String name)
        {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Reference.MOD_ID, name));
        }
    }
}
