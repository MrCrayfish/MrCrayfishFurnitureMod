package com.mrcrayfish.furniture.common;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class ModTags
{
    public static class Items
    {
        public static final ITag.INamedTag<Item> UPGRADED_FENCES = tag("fences/upgraded");
        public static final ITag.INamedTag<Item> PICKET_FENCES = tag("fences/picket");
        public static final ITag.INamedTag<Item> UPGRADED_FENCE_GATES = tag("fence_gates/upgraded");
        public static final ITag.INamedTag<Item> PICKET_FENCE_GATES = tag("fence_gates/picket");
        public static final ITag.INamedTag<Item> HEDGES = tag("fences/hedge");
        public static final ITag.INamedTag<Item> BEDROOM = tag("bedroom");
        public static final ITag.INamedTag<Item> BATHROOM = tag("bathroom");
        public static final ITag.INamedTag<Item> GENERAL = tag("general");
        public static final ITag.INamedTag<Item> ITEMS = tag("items");
        public static final ITag.INamedTag<Item> KITCHEN = tag("kitchen");
        public static final ITag.INamedTag<Item> OUTDOORS = tag("outdoors");
        public static final ITag.INamedTag<Item> STORAGE = tag("storage");
        public static final ITag.INamedTag<Item> TRAMPOLINE = tag("trampoline");

        private static ITag.INamedTag<Item> tag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation(Reference.MOD_ID, name));
        }
    }

    public static class Blocks
    {
        public static final ITag.INamedTag<Block> UPGRADED_FENCES = tag("fences/upgraded");
        public static final ITag.INamedTag<Block> PICKET_FENCES = tag("fences/picket");
        public static final ITag.INamedTag<Block> UPGRADED_FENCE_GATES = tag("fence_gates/upgraded");
        public static final ITag.INamedTag<Block> PICKET_FENCE_GATES = tag("fence_gates/picket");
        public static final ITag.INamedTag<Block> HEDGES = tag("fences/hedge");

        private static ITag.INamedTag<Block> tag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(Reference.MOD_ID, name));
        }
    }
}
