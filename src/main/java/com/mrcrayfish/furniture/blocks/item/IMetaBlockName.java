package com.mrcrayfish.furniture.blocks.item;

import net.minecraft.item.ItemStack;

/**
 * <em><b>Copyright (c) 2018 The Starcraft Minecraft (SCMC) Mod Team.</b></em>
 */
public interface IMetaBlockName {

	/**
	 * Should return the name of the variant i.e. when we have the block breaker we
	 * have two types, Basic and Advanced, I just used "basic" and "advanced" for
	 * the correct item damage Refer to {@link ItemStack#getItemDamage()} to get the
	 * correct damage
	 * 
	 * @param stack
	 *            The stack
	 * @return The name of the variant that specific item is
	 */
	String getSpecialName(ItemStack stack);
}