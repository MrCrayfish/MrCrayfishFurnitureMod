package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.util.SittableUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockBarStool extends ModBlock implements IRayTrace
{
	public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(3 * 0.0625, 0, 3 * 0.0625, 13 * 0.0625, 13 * 0.0625, 13 * 0.0625);

	public static final AxisAlignedBB TOP_RIM = new AxisAlignedBB(3 * 0.0625, 9 * 0.0625, 3 * 0.0625, 13 * 0.0625, 12 * 0.0625, 13 * 0.0625);
	public static final AxisAlignedBB TOP_SEAT = new AxisAlignedBB(3.5 * 0.0625, 12 * 0.0625, 3.5 * 0.0625, 12.5 * 0.0625, 12.5 * 0.0625, 12.5 * 0.0625);

	public static final AxisAlignedBB[] LEGS = new AxisAlignedBB[] { new AxisAlignedBB(10 * 0.0625, 0, 10 * 0.0625, 12 * 0.0625, 9 * 0.0625, 12 * 0.0625), new AxisAlignedBB(4 * 0.0625, 0, 10 * 0.0625, 6 * 0.0625, 9 * 0.0625, 12 * 0.0625), new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 6 * 0.0625, 9 * 0.0625, 6 * 0.0625), new AxisAlignedBB(12 * 0.0625, 0, 4 * 0.0625, 10 * 0.0625, 9 * 0.0625, 6 * 0.0625) };
	public static final AxisAlignedBB[] CONNECTORS = new AxisAlignedBB[] { new AxisAlignedBB(6 * 0.0625, 4 * 0.0625, 10.5 * 0.0625, 10 * 0.0625, 5 * 0.0625, 11.5 * 0.0625), new AxisAlignedBB(5.5 * 0.0625, 4 * 0.0625, 6 * 0.0625, 4.5 * 0.0625, 5 * 0.0625, 10 * 0.0625), new AxisAlignedBB(6 * 0.0625, 4 * 0.0625, 4.5 * 0.0625, 10 * 0.0625, 5 * 0.0625, 5.5 * 0.0625), new AxisAlignedBB(10.5 * 0.0625, 4 * 0.0625, 6 * 0.0625, 11.5 * 0.0625, 5 * 0.0625, 10 * 0.0625) };

	public BlockBarStool()
	{
		super(Material.WOOD, "bar_stool");
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, 0));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!heldItem.isEmpty())
		{
			if (heldItem.getItem() instanceof ItemDye)
			{
				if (state.getValue(COLOUR) != 15 - heldItem.getItemDamage())
				{
					worldIn.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()));
					if (!playerIn.isCreative())
					{
						heldItem.shrink(1);
					}
					return true;
				}
			}
		}
		return !playerIn.isSneaking() && SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 9 * 0.0625);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(COLOUR, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(COLOUR);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, COLOUR);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(COLOUR, Math.min(meta, 15));
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		drops.add(new ItemStack(this, 1, Math.min(state.getValue(COLOUR), 15)));
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
		return new ItemStack(this, 1, state.getValue(COLOUR));
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		boxes.add(TOP_RIM);
		boxes.add(TOP_SEAT);

		for (int i = 0; i < LEGS.length; i++)
		{
			boxes.add(LEGS[i]);
		}

		for (int i = 0; i < CONNECTORS.length; i++)
		{
			boxes.add(CONNECTORS[i]);
		}
	}
}
