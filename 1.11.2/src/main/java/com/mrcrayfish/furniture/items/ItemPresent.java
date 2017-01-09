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
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPresent extends ItemBlock implements IMail, IItemColor
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
				list.add(TextFormatting.GRAY + "from " + nbttagstring.getString());
			}
			else
			{
				list.add(TextFormatting.GRAY + "Unsigned");
			}
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		ItemStack stack = player.getHeldItem(hand);
		if (worldIn.isSideSolid(pos, EnumFacing.UP))
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
						
						worldIn.setBlockState(pos.up(), state, 2);
						worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (state.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, state.getBlock().getSoundType().getPitch() * 0.8F, false);
						
						
						TileEntityPresent tep = new TileEntityPresent();
						tep.setOwner(player.getName());

						for (int i = 0; i < itemList.tagCount(); i++)
						{
							tep.setInventorySlotContents(i, new ItemStack(itemList.getCompoundTagAt(i)));
						}

						worldIn.setTileEntity(pos.up(), tep);

						stack.shrink(1);
						return EnumActionResult.SUCCESS;
					}
					else
					{
						if (worldIn.isRemote)
						{
							player.sendMessage(new TextComponentString("You some how have no items in the present. You cannot use this present."));
						}
					}
				}
				else
				{
					if (worldIn.isRemote)
					{
						player.sendMessage(new TextComponentString("You need to sign it before you can place it"));
					}
				}
			}
			else
			{
				if (worldIn.isRemote)
				{
					player.sendMessage(new TextComponentString("You need to sign it before you can place it"));
				}
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) 
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		if (!worldIn.isRemote)
		{
			if (stack.hasTagCompound())
			{
				NBTTagCompound nbttagcompound = stack.getTagCompound();
				NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

				if (nbttagstring == null)
				{
					playerIn.openGui(MrCrayfishFurnitureMod.instance, 9, worldIn, 0, 0, 0);
				}
				else if (nbttagstring.getString().equals(""))
				{
					playerIn.sendMessage(new TextComponentString("You cannot unwrap the present once signed"));
				}
			}
			else
			{
				playerIn.openGui(MrCrayfishFurnitureMod.instance, 9, worldIn, 0, 0, 0);
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	public static IInventory getInv(EntityPlayer player, EnumHand activeHand)
	{
		ItemStack present = player.getHeldItemMainhand();
		InventoryPresent invPresent = null;
		if (present != null && present.getItem() instanceof ItemPresent)
		{
			invPresent = new InventoryPresent(player, present);
		}
		return invPresent;
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

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) 
	{
		return ItemDye.DYE_COLORS[stack.getMetadata()];
	}
}
