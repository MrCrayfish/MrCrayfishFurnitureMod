package com.mrcrayfish.furniture.blocks.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * <em><b>Copyright (c) 2018 The Starcraft Minecraft (SCMC) Mod Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Creates an ItemBlock for the blocks that have meta data. Must be specified by the block in registry.
 * 
 * @see ItemBlock
 */
public class ItemBlockMeta extends ItemBlock {

	/**
	 * Default {@link ItemBlock} constructor
	 * 
	 * @param block
	 *            The original block
	 */
	public ItemBlockMeta(Block block) {
		super(block);

		if (!(block instanceof IMetaBlockName)) {
			throw new IllegalArgumentException(String.format("The given Block %s is not an instance of IMetaBlockName!", block.getUnlocalizedName()));
		}

		setHasSubtypes(true);
		setMaxDamage(0);
	}

	/**
	 * Fixes a bug with not placing the correct variant of the block THIS IS NEEDED
	 */
	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	/**
	 * Gets and modifies the unlocalized name
	 */
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + ((IMetaBlockName) block).getSpecialName(stack);
	}
}