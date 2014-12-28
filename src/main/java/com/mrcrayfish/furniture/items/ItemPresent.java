/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
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

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.gui.inventory.InventoryPresent;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessagePresent;
import com.mrcrayfish.furniture.util.NBTHelper;

public class ItemPresent extends Item implements IMail
{
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();
			NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

			if (nbttagstring != null)
			{
				list.add(EnumChatFormatting.GRAY + "from " + nbttagstring.getString());
			}
			else
			{
				list.add(EnumChatFormatting.GRAY + "Unsigned");
			}
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isSideSolid(pos, EnumFacing.UP))
		{
			int metadata = 0;
			if (this == FurnitureItems.itemPresentGreen)
			{
				metadata = 1;
			}

			if (stack.hasTagCompound())
			{
				NBTTagCompound nbttagcompound = stack.getTagCompound();
				NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

				if (nbttagstring != null)
				{
					NBTTagList itemList = (NBTTagList) NBTHelper.getCompoundTag(stack, "Present").getTag("Items");
					if (itemList.tagCount() > 0)
					{
						IBlockState state = null;
						if (this == FurnitureItems.itemPresentRed)
						{
							state = FurnitureBlocks.present_red.getDefaultState();
						}
						else
						{
							state = FurnitureBlocks.present_green.getDefaultState();
						}
						world.setBlockState(pos.up(), state, 2);
						world.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), state.getBlock().stepSound.getPlaceSound(), (state.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, state.getBlock().stepSound.getFrequency() * 0.8F);

						if (world.isRemote)
						{
							PacketHandler.INSTANCE.sendToServer(new MessagePresent(stack, pos.getX(), pos.getY() + 1, pos.getZ()));
						}

						--stack.stackSize;
					}
					else
					{
						if (world.isRemote)
						{
							player.addChatMessage(new ChatComponentText("You some how have no items in the present. You cannot use this present."));
						}
					}
				}
				else
				{
					if (world.isRemote)
					{
						player.addChatMessage(new ChatComponentText("You need to sign it before you can place it"));
					}
				}
			}
			else
			{
				if (world.isRemote)
				{
					player.addChatMessage(new ChatComponentText("You need to sign it before you can place it"));
				}
			}
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			if (stack.hasTagCompound())
			{
				NBTTagCompound nbttagcompound = stack.getTagCompound();
				NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

				if (nbttagstring == null)
				{
					player.openGui(MrCrayfishFurnitureMod.instance, 9, world, 0, 0, 0);
				}
				else if (nbttagstring.getString().equals(""))
				{
					player.addChatMessage(new ChatComponentText("You cannot unwrap the present once signed"));
				}
			}
			else
			{
				player.openGui(MrCrayfishFurnitureMod.instance, 9, world, 0, 0, 0);
			}
		}
		return stack;
	}

	public static IInventory getInv(EntityPlayer player)
	{
		ItemStack present = player.getCurrentEquippedItem();
		InventoryPresent invPresent = null;
		if (present != null && present.getItem() instanceof ItemPresent)
		{
			invPresent = new InventoryPresent(player, present);
		}
		return invPresent;
	}
}
