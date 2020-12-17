package com.mrcrayfish.furniture.common;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class Tags
{
    public static class Blocks
    {
        public static final ITag.INamedTag<Block> UPGRADED_FENCES = tag("upgraded_fences");
        public static final ITag.INamedTag<Block> PICKET_FENCES = tag("picket_fences");

        private static ITag.INamedTag<Block> tag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(Reference.MOD_ID, name));
        }
    }
}
