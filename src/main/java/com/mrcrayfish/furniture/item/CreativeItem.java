package com.mrcrayfish.furniture.item;

import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public interface CreativeItem
{
    void fill(Consumer<ItemStack> output);
}
