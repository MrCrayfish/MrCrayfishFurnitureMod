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
package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;

public class BlockMailBox extends BlockFurnitureTile
{
	public BlockMailBox(Material material)
	{
		super(material);
		setStepSound(Block.soundTypeWood);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (placer instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) placer;
			player.triggerAchievement(FurnitureAchievements.mailBox);
			if (!world.isRemote)
			{
				player.addChatComponentMessage(new ChatComponentText("Now right click the mailbox to claim ownership"));
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile_entity = world.getTileEntity(pos);
		if (!world.isRemote)
		{
			if (tile_entity instanceof TileEntityMailBox)
			{
				TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tile_entity;
				if (tileEntityMailBox.ownerName.isEmpty())
				{
					tileEntityMailBox.setOwner(player);
					player.addChatComponentMessage(new ChatComponentText("Successfully set the owner of the mail box to " + EnumChatFormatting.YELLOW + player.getName()));
					world.markBlockForUpdate(pos);
					return true;
				}

				tileEntityMailBox.tryAndUpdateName(player);

				if (!player.isSneaking())
				{
					if (tileEntityMailBox.canOpen(tileEntityMailBox, player) && tileEntityMailBox.locked)
					{
						player.openGui(MrCrayfishFurnitureMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
					}
					else if (!world.isRemote && tileEntityMailBox.locked)
					{
						player.addChatComponentMessage(new ChatComponentText("This mail box belongs to " + EnumChatFormatting.YELLOW + tileEntityMailBox.ownerName));
					}
					else if (!tileEntityMailBox.locked)
					{
						player.openGui(MrCrayfishFurnitureMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
					}
				}
				else
				{
					if (tileEntityMailBox.canOpen(tileEntityMailBox, player))
					{
						if (tileEntityMailBox.locked)
						{
							tileEntityMailBox.locked = false;
							player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Mailbox unlocked."));
						}
						else if (!tileEntityMailBox.locked)
						{
							tileEntityMailBox.locked = true;
							player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Mailbox locked."));
						}
					}
					else
					{
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "You don't have permission to unlock this mail box."));
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public float getExplosionResistance(Entity entity)
	{
		if (entity instanceof EntityCreeper)
		{
			return 1000;
		}
		return this.blockResistance / 5.0F;
	}
	
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) world.getTileEntity(pos);
		
		if (tileEntityMailBox == null)
		{
			return false;
		}

		int metadata = getMetaFromState(world.getBlockState(pos));
		if (tileEntityMailBox.locked)
		{
			world.setBlockState(pos, world.getBlockState(pos));
			if (!world.isRemote)
			{
				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "You need to unlock the mail box to destory it. Crouch and " + EnumChatFormatting.RED + "right click with your bare hand to unlock."));
				return false;
			}
		}
		else
		{
			world.setBlockToAir(pos);
			this.breakBlock(world, pos, world.getBlockState(pos));
		}
		return true;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.1625F, 0.8125F);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.1625F, 0.8125F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMailBox();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return FurnitureItems.itemMailBox;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
	{
		return new ItemStack(FurnitureItems.itemMailBox);
	}
}
