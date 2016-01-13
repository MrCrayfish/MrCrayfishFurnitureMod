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

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.gui.inventory.InventoryPackage;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import com.mrcrayfish.furniture.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPackageSigned extends Item implements IMail
{
	public ItemPackageSigned()
	{
		setMaxStackSize(1);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack.hasTagCompound())
		{
			NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
			NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

			if (nbttagstring != null)
			{
				par3List.add(EnumChatFormatting.GRAY + "from " + nbttagstring.getString());
			}
		}
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile_entity = world.getTileEntity(pos);
		if (!world.isRemote)
		{
			NBTTagList var2 = (NBTTagList) NBTHelper.getCompoundTag(stack, "Package").getTag("Items");
			if (var2.tagCount() > 0)
			{
				if (player.capabilities.isCreativeMode && player.isSneaking() && tile_entity instanceof TileEntityMailBox)
				{
					player.addChatMessage(new ChatComponentText("You cannot use this in creative."));
				}
				else if (tile_entity instanceof TileEntityMailBox)
				{
					TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tile_entity;
					if (tileEntityMailBox.isMailBoxFull() == false && player.isSneaking() && !world.isRemote)
					{
						ItemStack itemStack = stack.copy();
						tileEntityMailBox.addMail(itemStack);
						player.addChatComponentMessage(new ChatComponentText("Thank you! - " + EnumChatFormatting.YELLOW + tileEntityMailBox.ownerName));
						stack.stackSize--;
					}
					else if (tileEntityMailBox.isMailBoxFull() == true && player.isSneaking())
					{
						player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + tileEntityMailBox.ownerName + "'s" + EnumChatFormatting.WHITE + " mail box seems to be full. Try again later."));
					}
				}
			}
			else
			{
				player.addChatMessage(new ChatComponentText("You cannot insert a used package."));
			}
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			if (this == FurnitureItems.itemPackageSigned)
			{
				par3EntityPlayer.openGui(MrCrayfishFurnitureMod.instance, 8, par2World, 0, 0, 0);
				par3EntityPlayer.triggerAchievement(FurnitureAchievements.firstMail);
			}
		}

		return par1ItemStack;
	}

	public static IInventory getInv(EntityPlayer player)
	{
		ItemStack mail = player.getCurrentEquippedItem();
		InventoryPackage invMail = null;
		if (mail != null && mail.getItem() instanceof ItemPackageSigned)
		{
			invMail = new InventoryPackage(player, mail);
		}
		return invMail;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}