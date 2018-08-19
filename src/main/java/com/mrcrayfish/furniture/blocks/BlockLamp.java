package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockLamp extends ModBlock implements IRayTrace
{
	public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 1, 0.9375);
	public static final AxisAlignedBB UP_DOWN_BOX = new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 12 * 0.0625, 1, 12 * 0.0625);
	public static final AxisAlignedBB UP_BOX = new AxisAlignedBB(2 * 0.0625, 0, 2 * 0.0625, 14 * 0.0625, 1, 14 * 0.0625);

	public static final AxisAlignedBB SMALL_BASE_BOX = new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 12 * 0.0625, 5 * 0.0625, 12 * 0.0625);
	public static final AxisAlignedBB BOTTOM_BASE_BOX = new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 12 * 0.0625, 2 * 0.0625, 12 * 0.0625);

	public static final AxisAlignedBB CENTER_POLE_BOX = new AxisAlignedBB(6.125 * 0.0625, 0, 6.125 * 0.0625, 9.875 * 0.0625, 1, 9.875 * 0.0625);
	public static final AxisAlignedBB LAMP_SHADE_BOX = new AxisAlignedBB(1.5 * 0.0625, 8 * 0.0625, 14.5 * 0.0625, 14.5 * 0.0625, 15 * 0.0625, 1.5 * 0.0625);
	public static final AxisAlignedBB TOP_CENTER_BOX = new AxisAlignedBB(6.125 * 0.0625, 0, 6.125 * 0.0625, 9.875 * 0.0625, 9 * 0.0625, 9.875 * 0.0625);

	public BlockLamp(Material material, String name, boolean on)
	{
		super(material, name);
		this.setHardness(0.75F);
		this.setSoundType(SoundType.CLOTH);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, 0));
		if (!on)
		{
			this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		}
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		IBlockState actualState = this.getActualState(state, source, pos);
		return actualState.getValue(UP) && actualState.getValue(DOWN) ? UP_DOWN_BOX : actualState.getValue(UP) ? UP_BOX : BOUNDING_BOX;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!heldItem.isEmpty())
		{
			if (heldItem.getItem() instanceof ItemDye)
			{
				world.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()));
				if (!playerIn.isCreative())
					heldItem.shrink(1);
				return true;
			}
		}

		if (!(world.getBlockState(pos.up()).getBlock() instanceof BlockLamp))
		{
			world.setBlockState(pos, FurnitureBlocks.LAMP_ON.getDefaultState().withProperty(BlockLampOn.COLOUR, state.getValue(COLOUR)), 3);
		} else
		{
			int yOffset = 1;
			while (world.getBlockState(pos.up(yOffset)).getBlock() instanceof BlockLamp)
				yOffset++;

			int colour = world.getBlockState(pos.up(yOffset).down()).getValue(COLOUR);

			if (world.getBlockState(pos.up(yOffset).down()).getBlock() instanceof BlockLampOn)
			{
				world.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.LAMP_OFF.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
			} else
			{
				world.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.LAMP_ON.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
			}
		}
		return true;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		boolean up = worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLamp;
		boolean down = worldIn.getBlockState(pos.down()).getBlock() instanceof BlockLamp;
		return state.withProperty(UP, up).withProperty(DOWN, down);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(COLOUR, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(COLOUR);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, COLOUR, UP, DOWN);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(COLOUR, Math.min(meta, 15));
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		drops.add(new ItemStack(FurnitureBlocks.LAMP_OFF, 1, state.getValue(COLOUR)));
	}

	@Override
	public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
	{
		for (int i = 0; i < EnumDyeColor.values().length; i++)
		{
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(FurnitureBlocks.LAMP_OFF, 1, state.getValue(COLOUR));
	}
	
	@Override
	public void addWireframeBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
boxes.add(this.getBoundingBox(state, world, pos));
	}

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		boolean up = state.getValue(UP);
		boolean down = state.getValue(DOWN);

		if (up && down)
		{
			boxes.add(CENTER_POLE_BOX);
		} else if (up)
		{
			boxes.add(BOTTOM_BASE_BOX);
			boxes.add(CENTER_POLE_BOX);
		} else if (down)
		{
			boxes.add(TOP_CENTER_BOX);
			boxes.add(LAMP_SHADE_BOX);
		} else
		{
			boxes.add(SMALL_BASE_BOX);
			boxes.add(TOP_CENTER_BOX);
			boxes.add(LAMP_SHADE_BOX);
		}
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}
