package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityKitchenCounterDrawer;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
public class BlockCounterDrawer extends BlockFurnitureTile implements IRayTrace
{
	public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);

	public BlockCounterDrawer()
	{
		super(Material.ROCK, "counter_drawer");
		this.setHardness(0.5F);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityKitchenCounterDrawer)
		{
			TileEntityKitchenCounterDrawer tileEntityCouch = (TileEntityKitchenCounterDrawer) tileEntity;
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
			if (!worldIn.isRemote)
			{
				playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState)
	{
		List<AxisAlignedBB> list = getCollisionBoxList(this.getActualState(state, worldIn, pos));
		for (AxisAlignedBB box : list)
		{
			Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
		}
	}

	private List<AxisAlignedBB> getCollisionBoxList(IBlockState state)
	{
		List<AxisAlignedBB> list = Lists.newArrayList();
		EnumFacing facing = state.getValue(FACING);
		list.add(BlockCounter.COUNTER_TOP);
		list.add(BlockCounter.FORWARD_BOXES[facing.getHorizontalIndex()]);
		return list;
	}

	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
	{
		List<RayTraceResult> list = Lists.newArrayList();

		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
		{
			list.add(this.rayTrace(pos, start, end, axisalignedbb));
		}

		RayTraceResult result = null;
		double d1 = 0.0D;

		for (RayTraceResult raytraceresult : list)
		{
			if (raytraceresult != null)
			{
				double d0 = raytraceresult.hitVec.squareDistanceTo(end);

				if (d0 > d1)
				{
					result = raytraceresult;
					d1 = d0;
				}
			}
		}

		return result;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, COLOUR);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityKitchenCounterDrawer)
		{
			int colour = ((TileEntityKitchenCounterDrawer) tileEntity).getColour();
			state = state.withProperty(COLOUR, colour);
		}
		return state;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(COLOUR, 15 - Math.max(0, meta));
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		TileEntity tileEntity = super.createTileEntity(world, state);
		if (tileEntity instanceof TileEntityKitchenCounterDrawer)
		{
			((TileEntityKitchenCounterDrawer) tileEntity).setColour(state.getValue(COLOUR));
		}
		return tileEntity;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		drops.add(new ItemStack(this, 1, 15 - Math.max(0, state.getValue(COLOUR))));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		int metadata = 0;
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity instanceof TileEntityKitchenCounterDrawer)
		{
			metadata = ((TileEntityKitchenCounterDrawer) tileEntity).getColour();
		}
		return new ItemStack(this, 1, metadata);
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
    public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
    {
        EnumFacing facing = state.getValue(FACING);
        boxes.add(BlockCounter.COUNTER_TOP);
        boxes.add(BlockCounter.FORWARD_BOXES[facing.getHorizontalIndex()]);
    }

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityKitchenCounterDrawer();
	}
}
