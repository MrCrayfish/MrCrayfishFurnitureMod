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
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//Starting in a minute!

public class ItemCup extends Item implements IItemColor
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
	public ItemStack onItemUseFinish(ItemStack cup, World world, EntityLivingBase entity)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			if (hasLiquid)
			{
				int heal = cup.getTagCompound().getInteger("HealAmount");
				player.getFoodStats().addStats(heal, 0.5F);
				return new ItemStack(FurnitureItems.itemCup);
			}
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack cup, World worldIn, EntityPlayer playerIn, EnumHand hand) 
	{
		if (hasLiquid)
		{
			if (playerIn.getFoodStats().needFood())
			{
				playerIn.setActiveHand(hand);
			}
		}
		return new ActionResult(EnumActionResult.PASS, cup);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (playerIn.isSneaking())
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (block == Blocks.SNOW_LAYER && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1)
			{
				side = EnumFacing.UP;
			}
			else if (!block.isReplaceable(worldIn, pos))
			{
				pos = pos.offset(side);
			}

			if (!playerIn.canPlayerEdit(pos, side, stack))
			{
				return EnumActionResult.FAIL;
			}
			else if (stack.stackSize == 0)
			{
				return EnumActionResult.FAIL;
			}
			else
			{
				if (worldIn.canBlockBePlaced(this.cupBlock, pos, false, side, (Entity) null, stack))
				{
					IBlockState iblockstate1 = this.cupBlock.getDefaultState();
					if (worldIn.setBlockState(pos, iblockstate1, 3))
					{
						iblockstate1 = worldIn.getBlockState(pos);

						if (iblockstate1.getBlock() == this.cupBlock)
						{
							ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack);
							iblockstate1.getBlock().onBlockPlacedBy(worldIn, pos, iblockstate1, playerIn, stack);
						}
						
						worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, this.cupBlock.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, this.cupBlock.getSoundType().getPitch() * 0.8F, false);
						--stack.stackSize;
					}
				}

				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
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
	public int getColorFromItemstack(ItemStack stack, int tintIndex) 
	{
		return this.getColorFromCompound(stack);
	}
}
