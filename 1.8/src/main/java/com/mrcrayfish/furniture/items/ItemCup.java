/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.items;

import java.awt.Color;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//Starting in a minute!

public class ItemCup extends Item
{
	private boolean hasLiquid = false;
	private Block cupBlock = FurnitureBlocks.cup;

	public ItemCup(boolean hasLiquid)
	{
		this.hasLiquid = hasLiquid;
		if (hasLiquid)
		{
			setMaxStackSize(1);
		}
		else
		{
			setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack cup, World world, EntityPlayer player)
	{
		if (hasLiquid)
		{
			int heal = cup.getTagCompound().getInteger("HealAmount");
			player.getFoodStats().addStats(heal, 0.5F);
			player.setCurrentItemOrArmor(0, new ItemStack(FurnitureItems.itemCup));
		}
		return cup;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack cup)
	{
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack cup, World world, EntityPlayer player)
	{
		if (hasLiquid)
		{
			if (player.getFoodStats().needFood())
			{
				player.setItemInUse(cup, this.getMaxItemUseDuration(cup));
			}
		}
		return cup;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking())
		{
			IBlockState iblockstate = world.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (block == Blocks.snow_layer && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1)
			{
				side = EnumFacing.UP;
			}
			else if (!block.isReplaceable(world, pos))
			{
				pos = pos.offset(side);
			}

			if (!player.canPlayerEdit(pos, side, stack))
			{
				return false;
			}
			else if (stack.stackSize == 0)
			{
				return false;
			}
			else
			{
				if (world.canBlockBePlaced(this.cupBlock, pos, false, side, (Entity) null, stack))
				{
					IBlockState iblockstate1 = this.cupBlock.getDefaultState();
					if (world.setBlockState(pos, iblockstate1, 3))
					{
						iblockstate1 = world.getBlockState(pos);

						if (iblockstate1.getBlock() == this.cupBlock)
						{
							ItemBlock.setTileEntityNBT(world, pos, stack, player);
							iblockstate1.getBlock().onBlockPlacedBy(world, pos, iblockstate1, player, stack);
						}

						world.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), this.cupBlock.stepSound.getPlaceSound(), (this.cupBlock.stepSound.getVolume() + 1.0F) / 2.0F, this.cupBlock.stepSound.getFrequency() * 0.8F);
						--stack.stackSize;
					}
				}

				return true;
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromCompound(ItemStack cup)
	{
		if (cup.hasTagCompound())
		{
			if (cup.getTagCompound().hasKey("Colour"))
			{
				int[] colour = cup.getTagCompound().getIntArray("Colour");
				Color color = new Color(colour[0], colour[1], colour[2]);
				return color.getRGB();
			}
		}
		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack cup, int renderPass)
	{
		return renderPass < 1 ? 16777215 : this.getColorFromCompound(cup);
	}

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
}
