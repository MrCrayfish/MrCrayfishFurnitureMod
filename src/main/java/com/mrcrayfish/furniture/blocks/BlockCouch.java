package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityCouch;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SittableUtil;
import com.mrcrayfish.furniture.util.StateHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public abstract class BlockCouch extends BlockFurnitureTile implements IRayTrace
{
	public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
	public static final PropertyEnum<CouchType> TYPE = PropertyEnum.create("type", CouchType.class);

	private static final AxisAlignedBB COUCH_BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.6, 1.0);

	private static final AxisAlignedBB COUCH_BACKREST_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
	private static final AxisAlignedBB COUCH_BACKREST_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
	private static final AxisAlignedBB COUCH_BACKREST_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
	private static final AxisAlignedBB COUCH_BACKREST_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
	private static final AxisAlignedBB[] COUCH_BACKREST = { COUCH_BACKREST_SOUTH, COUCH_BACKREST_WEST, COUCH_BACKREST_NORTH, COUCH_BACKREST_EAST };

	private static final AxisAlignedBB COUCH_ARMREST_LEFT_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
	private static final AxisAlignedBB COUCH_ARMREST_LEFT_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
	private static final AxisAlignedBB COUCH_ARMREST_LEFT_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
	private static final AxisAlignedBB COUCH_ARMREST_LEFT_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
	private static final AxisAlignedBB[] COUCH_ARMREST_LEFT = { COUCH_ARMREST_LEFT_SOUTH, COUCH_ARMREST_LEFT_WEST, COUCH_ARMREST_LEFT_NORTH, COUCH_ARMREST_LEFT_EAST };

	private static final AxisAlignedBB COUCH_ARMREST_RIGHT_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
	private static final AxisAlignedBB COUCH_ARMREST_RIGHT_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
	private static final AxisAlignedBB COUCH_ARMREST_RIGHT_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
	private static final AxisAlignedBB COUCH_ARMREST_RIGHT_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
	private static final AxisAlignedBB[] COUCH_ARMREST_RIGHT = { COUCH_ARMREST_RIGHT_SOUTH, COUCH_ARMREST_RIGHT_WEST, COUCH_ARMREST_RIGHT_NORTH, COUCH_ARMREST_RIGHT_EAST };

	public BlockCouch(String name)
	{
		super(Material.CLOTH, name);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.CLOTH);

		IBlockState baseState = this.blockState.getBaseState();
		if (isSpecial())
		{
			this.setDefaultState(baseState.withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, CouchType.BOTH));
		} else
		{
			this.setDefaultState(baseState.withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, CouchType.BOTH).withProperty(COLOUR, 0));
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return FULL_BLOCK_AABB;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		if (!isSpecial())
		{
			int colour = ((TileEntityCouch) world.getTileEntity(pos)).getColour();
			state = state.withProperty(COLOUR, colour);
		}

		if (StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) instanceof BlockCouch)
		{
			if (StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.RIGHT)
			{
				return state.withProperty(TYPE, CouchType.CORNER_RIGHT);
			} else if (StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.LEFT)
			{
				return state.withProperty(TYPE, CouchType.CORNER_LEFT);
			}
		}

		boolean left = false;
		boolean right = false;

		if (!StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT))
		{
			left = true;
		}
		if (!StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT))
		{
			right = true;
		}
		if (left && !right)
		{
			return state.withProperty(TYPE, CouchType.LEFT);
		}
		if (!left && right)
		{
			return state.withProperty(TYPE, CouchType.RIGHT);
		}
		if (!left && !right)
		{
			return state.withProperty(TYPE, CouchType.BOTH);
		}
		return state.withProperty(TYPE, CouchType.NONE);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		if (!isSpecial())
		{
			state = state.withProperty(COLOUR, 0);
		}
		return state;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (!isSpecial())
		{
			if (!heldItem.isEmpty() && heldItem.getItem() == Items.NAME_TAG)
			{
				if (heldItem.hasDisplayName())
				{
					if (heldItem.getDisplayName().equals("jeb_"))
					{
						worldIn.setBlockState(pos, FurnitureBlocks.COUCH_JEB.getDefaultState().withProperty(FACING, state.getValue(FACING)));
						if (!playerIn.isCreative())
						{
							heldItem.shrink(1);
						}
						Triggers.trigger(Triggers.CREATE_COUCH_JEB, playerIn);
						return true;
					}
				}
			}

			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof TileEntityCouch)
			{
				TileEntityCouch tileEntityCouch = (TileEntityCouch) tileEntity;
				if (!heldItem.isEmpty())
				{
					if (heldItem.getItem() instanceof ItemDye)
					{
						if (tileEntityCouch.getColour() != (15 - heldItem.getItemDamage()))
						{
							tileEntityCouch.setColour(heldItem.getItemDamage());
							if (!playerIn.isCreative())
							{
								heldItem.shrink(1);
							}
							TileEntityUtil.markBlockForUpdate(worldIn, pos);
						}
						return true;
					}
				}
			}
		}
		return SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0.45);
	}
	
	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		EnumFacing facing = state.getValue(FACING);

		boxes.add(COUCH_BACKREST[facing.getHorizontalIndex()]);
		boxes.add(COUCH_BASE);

		if (state.getValue(TYPE) == CouchType.CORNER_LEFT)
		{
			if (facing.getAxis() == Axis.X)
			{
				boxes.add(COUCH_BACKREST[(facing.getHorizontalIndex() - 1) < 0 ? 3 : facing.getHorizontalIndex() - 1]);
			} else
			{
				boxes.add(COUCH_BACKREST[(facing.getHorizontalIndex() + 1) % 4]);
			}
		} else if (state.getValue(TYPE) == CouchType.CORNER_RIGHT)
		{
			if (facing.getAxis() == Axis.X)
			{
				boxes.add(COUCH_BACKREST[(facing.getHorizontalIndex() + 1) % 4]);
			} else
			{
				boxes.add(COUCH_BACKREST[(facing.getHorizontalIndex() - 1) < 0 ? 3 : facing.getHorizontalIndex() - 1]);
			}
		} else
		{
			if (StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT))
			{
				boxes.add(COUCH_ARMREST_LEFT[facing.getHorizontalIndex()]);
			}
			if (StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT))
			{
				boxes.add(COUCH_ARMREST_RIGHT[facing.getHorizontalIndex()]);
			}
		}
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState)
	{
		if (!(entity instanceof EntitySittableBlock))
		{
			super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCouch();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return isSpecial() ? new BlockStateContainer(this, FACING, TYPE) : new BlockStateContainer(this, FACING, COLOUR, TYPE);
	}

	public abstract boolean isSpecial();

	public enum CouchType implements IStringSerializable
	{
		NONE, LEFT, RIGHT, BOTH, CORNER_LEFT, CORNER_RIGHT;

		@Override
		public String getName()
		{
			return this.toString().toLowerCase(Locale.US);
		}
	}
}
