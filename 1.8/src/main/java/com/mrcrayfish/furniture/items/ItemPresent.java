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
import com.mrcrayfish.furniture.blocks.BlockPresent;
import com.mrcrayfish.furniture.gui.inventory.InventoryPresent;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.util.NBTHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPresent extends ItemBlock implements IMail
{
	public ItemPresent(Block block) 
	{
		super(block);
        this.setHasSubtypes(true);
	}
	
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
			if (stack.hasTagCompound())
			{
				NBTTagCompound nbttagcompound = stack.getTagCompound();
				NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

				if (nbttagstring != null)
				{
					NBTTagList itemList = (NBTTagList) NBTHelper.getCompoundTag(stack, "Present").getTag("Items");
					if (itemList.tagCount() > 0)
					{
						IBlockState state = FurnitureBlocks.present.getDefaultState().withProperty(BlockPresent.COLOUR, EnumDyeColor.byMetadata(stack.getItemDamage()));
						
						world.setBlockState(pos.up(), state, 2);
						world.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), state.getBlock().stepSound.getPlaceSound(), (state.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, state.getBlock().stepSound.getFrequency() * 0.8F);

						TileEntityPresent tep = new TileEntityPresent();
						tep.setOwner(player.getName());

						for (int i = 0; i < itemList.tagCount(); i++)
						{
							tep.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(itemList.getCompoundTagAt(i)));
						}

						world.setTileEntity(pos.up(), tep);

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
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack cup, int renderPass)
	{
		return renderPass < 1 ? 16777215 : ItemDye.dyeColors[cup.getMetadata()];
	}

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) 
	{
		return super.getUnlocalizedName(stack) + "_" + EnumDyeColor.values()[stack.getItemDamage()].getName();
	}
}
