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
import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnvelopeSigned extends Item implements IMail
{
	public static boolean canUse;

	public ItemEnvelopeSigned()
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
				par3List.add(TextFormatting.GRAY + "from " + nbttagstring.getString());
			}
		}
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile_entity = world.getTileEntity(pos);
		if (!world.isRemote)
		{
			NBTTagList var2 = (NBTTagList) NBTHelper.getCompoundTag(stack, "Envelope").getTag("Items");
			if (var2.tagCount() > 0)
			{
				if (player.capabilities.isCreativeMode && player.isSneaking() && tile_entity instanceof TileEntityMailBox)
				{
					player.addChatMessage(new TextComponentString("You cannot use this in creative."));
				}
				else if (tile_entity instanceof TileEntityMailBox)
				{
					TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tile_entity;
					if (tileEntityMailBox.isMailBoxFull() == false && player.isSneaking() && !world.isRemote)
					{
						ItemStack itemStack = stack.copy();
						tileEntityMailBox.addMail(itemStack);
						player.addChatMessage(new TextComponentString("Thank you! - " + TextFormatting.YELLOW + tileEntityMailBox.ownerName));
						player.addStat(FurnitureAchievements.sendMail);
						stack.stackSize--;
					}
					else if (tileEntityMailBox.isMailBoxFull() == true && player.isSneaking())
					{
						player.addChatMessage(new TextComponentString(TextFormatting.YELLOW + tileEntityMailBox.ownerName + "'s" + TextFormatting.WHITE + " mail box seems to be full. Try again later."));
					}
				}
			}
			else
			{
				player.addChatMessage(new TextComponentString("You cannot insert a used envelope."));
			}
		}
		return true;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			if (this == FurnitureItems.itemEnvelopeSigned)
			{
				par3EntityPlayer.openGui(MrCrayfishFurnitureMod.instance, 6, par2World, 0, 0, 0);
			}
		}
		return par1ItemStack;
	}

	public static IInventory getInv(EntityPlayer par1EntityPlayer)
	{
		ItemStack mail = par1EntityPlayer.inventory.getCurrentItem();
		InventoryEnvelope invMail = null;
		if (mail != null && mail.getItem() instanceof ItemEnvelopeSigned)
		{
			invMail = new InventoryEnvelope(par1EntityPlayer, mail);
		}
		return invMail;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
}