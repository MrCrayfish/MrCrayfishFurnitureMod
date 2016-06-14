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

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.util.InventoryUtil;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPresent extends Block implements ITileEntityProvider
{
	public static final PropertyEnum COLOUR = PropertyEnum.create("colour", EnumDyeColor.class);
	
	public BlockPresent(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundTypeCloth);
		this.isBlockContainer = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, EnumDyeColor.WHITE));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		TileEntityPresent present = (TileEntityPresent) world.getTileEntity(pos);
		if (present != null)
		{
			world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.levelup", 0.75F, 1.0F);
			player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Merry Christmas" + EnumChatFormatting.RESET + " from " + EnumChatFormatting.RED + present.ownerName));
			player.triggerAchievement(FurnitureAchievements.unwrapPresent);
		}
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof IInventory)
		{
			IInventory inv = (IInventory) tileEntity;
			InventoryHelper.dropInventoryItems(world, pos, inv);
		}
		if (tileEntity instanceof ISimpleInventory)
		{
			ISimpleInventory inv = (ISimpleInventory) tileEntity;
			InventoryUtil.dropInventoryItems(world, pos, inv);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, BlockPos pos)
	{
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.35F, 0.75F);
	}

	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.35F, 0.75F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPresent();
	}

	public int getDaysInbetween(Date date, Date date2)
	{
		return (int) (date2.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
	}
	
	public MapColor getMapColor(IBlockState state)
    {
        return ((EnumDyeColor)state.getValue(COLOUR)).getMapColor();
    }
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return ((EnumDyeColor)state.getValue(COLOUR)).getMetadata();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumDyeColor)state.getValue(COLOUR)).getMetadata();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(COLOUR, EnumDyeColor.byMetadata(meta));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { COLOUR });
	}
	
	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
	{
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}
	
	@Override
	@SideOnly(Side.CLIENT) 
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (EnumDyeColor dye : EnumDyeColor.values())
		{
			list.add(new ItemStack(itemIn, 1, dye.getMetadata()));
		}
	}
}
