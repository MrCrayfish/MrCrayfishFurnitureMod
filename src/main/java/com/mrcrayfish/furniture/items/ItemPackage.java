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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.gui.inventory.InventoryPackage;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;

public class ItemPackage extends Item implements IMail
{
	public ItemPackage()
	{
		setMaxStackSize(1);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile_entity = world.getTileEntity(pos);
		if (!world.isRemote)
		{
			if (tile_entity instanceof TileEntityMailBox)
			{
				if (player.isSneaking() && !world.isRemote)
				{
					player.addChatMessage(new ChatComponentText("You must sign the package before depositing it."));
				}
			}
		}
		return true;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			par3EntityPlayer.openGui(MrCrayfishFurnitureMod.instance, 7, par2World, 0, 0, 0);
		}
		return par1ItemStack;
	}

	public static IInventory getInv(EntityPlayer par1EntityPlayer)
	{
		ItemStack mail = par1EntityPlayer.getCurrentEquippedItem();
		InventoryPackage invMail = null;
		if (mail != null && mail.getItem() instanceof ItemPackage)
		{
			invMail = new InventoryPackage(par1EntityPlayer, mail);
		}
		return invMail;
	}
}