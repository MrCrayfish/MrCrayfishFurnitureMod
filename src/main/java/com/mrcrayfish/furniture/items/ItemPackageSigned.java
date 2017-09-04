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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

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
				par3List.add(TextFormatting.GRAY + "from " + nbttagstring.getString());
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
					player.sendMessage(new TextComponentString("You cannot use this in creative."));
				}
				else if (tile_entity instanceof TileEntityMailBox)
				{
					TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tile_entity;
					if (tileEntityMailBox.isMailBoxFull() == false && player.isSneaking() && !world.isRemote)
					{
						ItemStack itemStack = stack.copy();
						tileEntityMailBox.addMail(itemStack);
						player.sendMessage(new TextComponentString("Thank you! - " + TextFormatting.YELLOW + tileEntityMailBox.getOwner()));
						stack.shrink(1);
					}
					else if (tileEntityMailBox.isMailBoxFull() == true && player.isSneaking())
					{
						player.sendMessage(new TextComponentString(TextFormatting.YELLOW + tileEntityMailBox.getOwner() + "'s" + TextFormatting.WHITE + " mail box seems to be full. Try again later."));
					}
				}
			}
			else
			{
				player.sendMessage(new TextComponentString("You cannot insert a used package."));
			}
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) 
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		if (!worldIn.isRemote)
		{
			if (this == FurnitureItems.itemPackageSigned)
			{
				playerIn.openGui(MrCrayfishFurnitureMod.instance, 8, worldIn, 0, 0, 0);
				playerIn.addStat(FurnitureAchievements.firstMail);
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	public static IInventory getInv(EntityPlayer player)
	{
		ItemStack mail = player.inventory.getCurrentItem();
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