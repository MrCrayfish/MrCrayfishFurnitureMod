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

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityBlender;
import com.mrcrayfish.furniture.util.InventoryUtil;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBlender extends BlockFurnitureTile
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(4 * 0.0625, 0.0, 4 * 0.0625, 12 * 0.0625, 1.0, 12 * 0.0625);

	public BlockBlender(Material material) {
		super(material);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityBlender) {
			TileEntityBlender tileEntityBlender = (TileEntityBlender) tileEntity;
			if (tileEntityBlender.drinkCount == 0) {
				if (!heldItem.isEmpty() && !tileEntityBlender.isFull() && !tileEntityBlender.isBlending()) {
					tileEntityBlender.addIngredient(heldItem.copy());
					TileEntityUtil.markBlockForUpdate(worldIn, pos);
					heldItem.setCount(0);
					return true;
				} else {
					if (playerIn.isSneaking()) {
						if (!tileEntityBlender.isBlending()) {
							if (tileEntityBlender.hasValidIngredients()) {
								tileEntityBlender.startBlending();
								TileEntityUtil.markBlockForUpdate(worldIn, pos);
								worldIn.updateComparatorOutputLevel(pos, this);
							}
						}
					} else if (!tileEntityBlender.isBlending()) {
						tileEntityBlender.removeIngredient();
					}
				}
			} else {
				if (!heldItem.isEmpty() && tileEntityBlender.hasDrink()) {
					if (heldItem.getItem() == FurnitureItems.itemCup) {
						if (heldItem.getCount() == 0 | heldItem.getCount() == 1) {
							playerIn.setHeldItem(hand, tileEntityBlender.getDrink());
						} else {
							playerIn.inventory.addItemStackToInventory(tileEntityBlender.getDrink());
							heldItem.shrink(1);
						}
						tileEntityBlender.drinkCount--;
						TileEntityUtil.markBlockForUpdate(worldIn, pos);
					}
					return true;
				}
			}
		}
		return true;
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
		return new TileEntityBlender();
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		if (world.getTileEntity(pos) instanceof TileEntityBlender) {
			TileEntityBlender blender = (TileEntityBlender) world.getTileEntity(pos);
			return blender.isBlending() ? 1 : 0;
		}
		return 0;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		if (world.getTileEntity(pos) instanceof TileEntityBlender) {
			TileEntityBlender blender = (TileEntityBlender) world.getTileEntity(pos);
			InventoryUtil.dropInventoryItems(world, pos, blender);
		}
	}
}
