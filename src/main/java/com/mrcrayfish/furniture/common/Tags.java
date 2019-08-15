package com.mrcrayfish.furniture.common;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class Tags
{
    public static class Blocks
    {
        public static final Tag<Block> UPGRADED_FENCES = tag("upgraded_fences");
        public static final Tag<Block> PICKET_FENCES = tag("picket_fences");

        private static Tag<Block> tag(String name)
        {
            return new BlockTags.Wrapper(new ResourceLocation("cfm", name));
        }
    }
}
