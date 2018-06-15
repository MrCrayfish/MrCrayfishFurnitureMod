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

import java.util.Random;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFireAlarm extends BlockFurniture
{
	private boolean on = false;

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(5 * 0.0625, 0.875, 5 * 0.0625, 11 * 0.0625, 1.0, 11 * 0.0625);

	public BlockFireAlarm(Material material, boolean on) {
		super(material);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.STONE);
		this.on = on;
		if (on) {
			this.setLightLevel(1.0F);
			this.setCreativeTab(null);
		} else {
			this.setLightLevel(0.2F);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (placer instanceof EntityPlayer) {
			Triggers.trigger(Triggers.PLACE_APPLIANCE, (EntityPlayer) placer);
		}
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (on) {
			worldIn.setBlockState(pos, FurnitureBlocks.fire_alarm_off.getDefaultState(), 2);
		}
		return true;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.DOWN && this == FurnitureBlocks.fire_alarm_on ? 15 : 0;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		world.getMinecraftServer().addScheduledTask(() -> {
			if (!on) {
				int radius = 9;
				scanner: {
					for (int x = 0; x < radius; x++) {
						for (int y = 0; y < radius; y++) {
							for (int z = 0; z < radius; z++) {
								if (world.getBlockState(pos.add(-4 + x, -4 + y, -4 + z)).getBlock() == Blocks.FIRE) {
									world.playSound(null, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, FurnitureSounds.fire_alarm, SoundCategory.BLOCKS, 5.0F, 1.0F);
									world.setBlockState(pos, FurnitureBlocks.fire_alarm_on.getDefaultState(), 2);
									break scanner;
								}
								if (x == 8 && y == 8 && z == 8) {
									world.scheduleUpdate(pos, this, this.tickRate(world));
									break scanner;
								}
							}
						}
					}
				}
			}
		});
		if (on) {
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, FurnitureSounds.fire_alarm, SoundCategory.BLOCKS, 5.0F, 1.0F);
			world.scheduleUpdate(pos, this, 34);
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if (on) {
			world.scheduleUpdate(pos, this, 34);
		}
		if (!on) {
			world.scheduleUpdate(pos, this, 1);
		}
		world.notifyNeighborsOfStateChange(pos, this, true);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.DOWN;
	}

	public boolean canBlockStay(World par1World, BlockPos pos)
	{
		return !par1World.isAirBlock(pos.up());
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return new ItemStack(FurnitureBlocks.fire_alarm_off).getItem();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(FurnitureBlocks.fire_alarm_off);
	}
}
