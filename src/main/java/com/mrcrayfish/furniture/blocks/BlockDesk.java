package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.util.StateHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockDesk extends BlockFurniture implements IDesk, IRayTrace
{
	public static final PropertyEnum<DeskType> TYPE = PropertyEnum.create("type", DeskType.class);

	public BlockDesk(Material material, SoundType sound, String name)
	{
		super(material, name);
		this.setSoundType(sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, DeskType.NONE));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		if (GuiScreen.isShiftKeyDown())
		{
			String info = I18n.format("cfm.desk.info");
			tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
		} else
		{
			tooltip.add(TextFormatting.YELLOW + I18n.format("cfm.info"));
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		if (StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) instanceof IDesk)
		{
			if (StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.RIGHT)
			{
				return state.withProperty(TYPE, DeskType.CORNER_RIGHT);
			} else if (StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.LEFT)
			{
				return state.withProperty(TYPE, DeskType.CORNER_LEFT);
			}
		}

		boolean left = false;
		boolean right = false;

		if (StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) instanceof IDesk)
		{
			left = true;
		}
		if (StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) instanceof IDesk)
		{
			right = true;
		}
		if (left && !right)
		{
			return state.withProperty(TYPE, DeskType.LEFT);
		} else if (!left && right)
		{
			return state.withProperty(TYPE, DeskType.RIGHT);
		} else if (!left && !right)
		{
			return state.withProperty(TYPE, DeskType.NONE);
		}
		return state.withProperty(TYPE, DeskType.BOTH);
	}

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		// TODO add the desk boxes
		// EnumFacing facing = state.getValue(FACING);
		//
		// DeskType type = state.getValue(TYPE);
		//
		// AxisAlignedBB top = new AxisAlignedBB(0, 14.5 * 0.0625, 0, 1, 1, 1);
		// AxisAlignedBB[] backs = new AxisAlignedBB[] {
		// CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 14 * 0.0625, 2 * 0.0625, 2 *
		// 0.0625, 14.5 * 0.0625, 14.5 * 0.0625, 14 * 0.0625),
		// CollisionHelper.getBlockBounds(EnumFacing.WEST, 14 * 0.0625, 2 * 0.0625, 2 *
		// 0.0625, 14.5 * 0.0625, 14.5 * 0.0625, 14 * 0.0625),
		// CollisionHelper.getBlockBounds(EnumFacing.NORTH, 14 * 0.0625, 2 * 0.0625, 2 *
		// 0.0625, 14.5 * 0.0625, 14.5 * 0.0625, 14 * 0.0625),
		// CollisionHelper.getBlockBounds(EnumFacing.EAST, 14 * 0.0625, 2 * 0.0625, 2 *
		// 0.0625, 14.5 * 0.0625, 14.5 * 0.0625, 14 * 0.0625) };
		// AxisAlignedBB[] lsides = new AxisAlignedBB[] {
		// CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 1 * 0.0625, 0, 0.5 * 0.0625,
		// 15 * 0.0625, 14.5 * 0.0625, 2 * 0.0625),
		// CollisionHelper.getBlockBounds(EnumFacing.WEST, 1 * 0.0625, 0, 0.5 * 0.0625,
		// 15 * 0.0625, 14.5 * 0.0625, 2 * 0.0625),
		// CollisionHelper.getBlockBounds(EnumFacing.NORTH, 1 * 0.0625, 0, 0.5 * 0.0625,
		// 15 * 0.0625, 14.5 * 0.0625, 2 * 0.0625),
		// CollisionHelper.getBlockBounds(EnumFacing.EAST, 1 * 0.0625, 0, 0.5 * 0.0625,
		// 15 * 0.0625, 14.5 * 0.0625, 2 * 0.0625) };
		// AxisAlignedBB[] rsides = new AxisAlignedBB[] { lsides[0].offset(-13.5 *
		// 0.0625, 0, 0), lsides[1].offset(0, 0, -13.5 * 0.0625), lsides[2].offset(13.5
		// * 0.0625, 0, 0), lsides[3].offset(0, 0, 13.5 * 0.0625) };
		//
		// boxes.add(top);
		// if (type == DeskType.NONE)
		// {
		// boxes.add(backs[facing.getHorizontalIndex()]);
		// boxes.add(lsides[facing.getHorizontalIndex()]);
		// boxes.add(rsides[facing.getHorizontalIndex()]);
		// }
		boxes.add(this.getBoundingBox(state, world, pos));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, TYPE);
	}

	public enum DeskType implements IStringSerializable
	{
		NONE, LEFT, RIGHT, BOTH, CORNER_LEFT, CORNER_RIGHT;

		@Override
		public String getName()
		{
			return this.toString().toLowerCase(Locale.US);
		}
	}
}
