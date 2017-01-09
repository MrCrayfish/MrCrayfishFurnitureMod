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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemEnvelope extends Item implements IMail
{
	public ItemEnvelope()
	{
		setMaxStackSize(1);
		setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		TileEntity tile_entity = worldIn.getTileEntity(pos);
		if (!worldIn.isRemote)
		{
			if (tile_entity instanceof TileEntityMailBox)
			{
				if (player.isSneaking())
				{
					player.sendMessage(new TextComponentString("You must sign the envelope before depositing it."));
				}
			}
			else
			{
				player.sendMessage(new TextComponentString("To open the envelope, make sure you are not highlighting any block."));
			}
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) 
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		if (!worldIn.isRemote)
		{
			playerIn.openGui(MrCrayfishFurnitureMod.instance, 5, worldIn, 0, 0, 0);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	public static IInventory getInv(EntityPlayer par1EntityPlayer)
	{
		ItemStack mail = par1EntityPlayer.inventory.getCurrentItem();
		InventoryEnvelope invMail = null;
		if (mail != null && mail.getItem() instanceof ItemEnvelope)
		{
			invMail = new InventoryEnvelope(par1EntityPlayer, mail);
		}
		return invMail;
	}
}