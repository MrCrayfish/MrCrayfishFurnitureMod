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
package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMailBox extends BlockFurnitureTile
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.1875, 0.0F, 0.1875, 0.8125, 1.1625, 0.8125);
	
	public BlockMailBox(Material material)
	{
		super(material);
		this.setSoundType(SoundType.WOOD);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (placer instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) placer;
			player.addStat(FurnitureAchievements.mailBox);
			if (!world.isRemote)
			{
				player.sendMessage(new TextComponentString("Now right click the mailbox to claim ownership"));
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tile_entity = worldIn.getTileEntity(pos);
		if (!worldIn.isRemote)
		{
			if (tile_entity instanceof TileEntityMailBox)
			{
				TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tile_entity;
				if (tileEntityMailBox.ownerName.isEmpty())
				{
					tileEntityMailBox.setOwner(playerIn);
					playerIn.sendMessage(new TextComponentString("Successfully set the owner of the mail box to " + TextFormatting.YELLOW + playerIn.getName()));
					TileEntityUtil.markBlockForUpdate(worldIn, pos);
					return true;
				}

				tileEntityMailBox.tryAndUpdateName(playerIn);
				
				if (tileEntityMailBox.canOpen(playerIn))
				{
					playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
				else
				{
					playerIn.sendMessage(new TextComponentString("This mail box belongs to " + TextFormatting.YELLOW + tileEntityMailBox.ownerName));
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
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) world.getTileEntity(pos);
		if (tileEntityMailBox != null)
		{
			if(tileEntityMailBox.canOpen(player) || !tileEntityMailBox.isClaimed() || isAuthorized(player))
			{
				this.breakBlock(world, pos, world.getBlockState(pos));
				world.setBlockToAir(pos);
			}
			else
			{
				world.setBlockState(pos, world.getBlockState(pos));
				if (!world.isRemote)
				{
					player.sendMessage(new TextComponentString(TextFormatting.RED + "You need to be the owner of the mailbox to destroy it."));
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isAuthorized(EntityPlayer player)
	{
		return player.capabilities.isCreativeMode && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == FurnitureItems.itemHammer;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) 
	{
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMailBox();
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		TileEntityMailBox mailbox = (TileEntityMailBox) world.getTileEntity(pos);
		return mailbox.getMailCount() > 0 ? 1 : 0;
	}
}
