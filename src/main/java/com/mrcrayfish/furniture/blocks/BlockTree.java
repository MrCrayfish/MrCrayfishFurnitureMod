package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityTree;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public class BlockTree extends BlockFurnitureTile implements IRayTrace
{
	private static final AxisAlignedBB BOUNDING_BOX_BOTTOM = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 2.0, 0.9375);
	private static final AxisAlignedBB BOUNDING_BOX_TOP = new AxisAlignedBB(0.0625, -1.0, 0.0625, 0.9375, 1.0, 0.9375);

	public static final AxisAlignedBB BASE = new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 12 * 0.0625, 5.5 * 0.0625, 12 * 0.0625);
	public static final AxisAlignedBB TRUNK = new AxisAlignedBB(7 * 0.0625, 5 * 0.0625, 7 * 0.0625, 9 * 0.0625, 7 * 0.0625, 9 * 0.0625);
	public static final AxisAlignedBB LAYER0 = new AxisAlignedBB(1 * 0.0625, 7 * 0.0625, 1 * 0.0625, 15 * 0.0625, 10 * 0.0625, 15 * 0.0625);
	public static final AxisAlignedBB LAYER1 = new AxisAlignedBB(2 * 0.0625, 10 * 0.0625, 2 * 0.0625, 14 * 0.0625, 13 * 0.0625, 14 * 0.0625);
	public static final AxisAlignedBB LAYER2 = new AxisAlignedBB(3 * 0.0625, 13 * 0.0625, 3 * 0.0625, 13 * 0.0625, 16 * 0.0625, 13 * 0.0625);
	public static final AxisAlignedBB LAYER3 = new AxisAlignedBB(4 * 0.0625, 16 * 0.0625, 4 * 0.0625, 12 * 0.0625, 19 * 0.0625, 12 * 0.0625);
	public static final AxisAlignedBB LAYER4 = new AxisAlignedBB(5 * 0.0625, 19 * 0.0625, 5 * 0.0625, 11 * 0.0625, 22 * 0.0625, 11 * 0.0625);
	public static final AxisAlignedBB LAYER5 = new AxisAlignedBB(6 * 0.0625, 22 * 0.0625, 6 * 0.0625, 10 * 0.0625, 25 * 0.0625, 10 * 0.0625);
	public static final AxisAlignedBB LAYER6 = new AxisAlignedBB(7 * 0.0625, 25 * 0.0625, 7 * 0.0625, 9 * 0.0625, 28 * 0.0625, 9 * 0.0625);;

	public BlockTree(String name, boolean top)
	{
		super(Material.WOOD, name);
		this.setSoundType(SoundType.WOOD);
		this.setLightLevel(0.3F);
		this.setHardness(0.5F);
		if (top)
			this.setCreativeTab(null);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if (this == FurnitureBlocks.TREE_BOTTOM)
			world.setBlockState(pos.up(), FurnitureBlocks.TREE_TOP.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()));
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityTree)
		{
			TileEntityTree tileEntityTree = (TileEntityTree) tileEntity;
			tileEntityTree.addOrnament(playerIn.getHorizontalFacing(), heldItem);
			if (!heldItem.isEmpty())
				heldItem.shrink(1);
			TileEntityUtil.markBlockForUpdate(worldIn, pos);
		}
		return true;
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (this == FurnitureBlocks.TREE_TOP)
		{
			if (player.getHeldItemMainhand() != null)
			{
				if (player.getHeldItemMainhand().getItem() != Items.SHEARS)
				{
					worldIn.destroyBlock(pos.down(), false);
				} else
				{
					player.getHeldItemMainhand().damageItem(1, player);
				}
			} else
			{
				worldIn.destroyBlock(pos.down(), false);
			}
		} else
		{
			worldIn.destroyBlock(pos.up(), false);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return this == FurnitureBlocks.TREE_BOTTOM ? BOUNDING_BOX_BOTTOM : BOUNDING_BOX_TOP;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return new ItemStack(FurnitureBlocks.TREE_BOTTOM).getItem();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(FurnitureBlocks.TREE_BOTTOM);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityTree();
	}

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		EnumFacing facing = state.getValue(FACING);

		if (this == FurnitureBlocks.TREE_BOTTOM)
		{
			boxes.add(BASE);
			boxes.add(TRUNK);
			boxes.add(LAYER0);
			boxes.add(LAYER1);
			boxes.add(LAYER2);
			boxes.add(LAYER3);
			boxes.add(LAYER4);
			boxes.add(LAYER5);
			boxes.add(LAYER6);
		} else
		{
			boxes.add(BASE.offset(0, -1, 0));
			boxes.add(TRUNK.offset(0, -1, 0));
			boxes.add(LAYER0.offset(0, -1, 0));
			boxes.add(LAYER1.offset(0, -1, 0));
			boxes.add(LAYER2.offset(0, -1, 0));
			boxes.add(LAYER3.offset(0, -1, 0));
			boxes.add(LAYER4.offset(0, -1, 0));
			boxes.add(LAYER5.offset(0, -1, 0));
			boxes.add(LAYER6.offset(0, -1, 0));
		}
	}
}
