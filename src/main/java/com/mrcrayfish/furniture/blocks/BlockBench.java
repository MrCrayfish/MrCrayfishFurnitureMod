package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SittableUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBench extends BlockFurniture
{
	public static final PropertyBool LEFT = PropertyBool.create("left");
	public static final PropertyBool RIGHT = PropertyBool.create("right");

	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 1 * 0.0625, 0.0, 0.0, 15 * 0.0625, 10 * 0.0625, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 1 * 0.0625, 0.0, 0.0, 15 * 0.0625, 10 * 0.0625, 1.0);

	private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 2 * 0.0625F, 0.0F, 1 * 0.0625F, 14 * 0.0625F, 9 * 0.0625F, 15 * 0.0625F);
	private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 2 * 0.0625F, 0.0F, 1 * 0.0625F, 14 * 0.0625F, 9 * 0.0625F, 15 * 0.0625F);

	public BlockBench(Material material) {
		super(material);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, false).withProperty(RIGHT, false));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if (getMetaFromState(state) % 2 == 1) {
			return BOUNDING_BOX_NORTH;
		}
		return BOUNDING_BOX_EAST;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
	{
		if (getMetaFromState(state) % 2 == 1) {
			super.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_NORTH);
			return;
		}
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_EAST);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing facing = state.getValue(FACING);
		if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
			IBlockState right = worldIn.getBlockState(pos.east());
			IBlockState left = worldIn.getBlockState(pos.west());

			if (worldIn.getBlockState(pos.east()).getBlock() == this && (right.getValue(FACING) == EnumFacing.NORTH | right.getValue(FACING) == EnumFacing.SOUTH)) {
				state = state.withProperty(RIGHT, true);
			}
			if (worldIn.getBlockState(pos.west()).getBlock() == this && (left.getValue(FACING) == EnumFacing.NORTH | left.getValue(FACING) == EnumFacing.SOUTH)) {
				state = state.withProperty(LEFT, true);
			}

			return state;
		}

		if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
			IBlockState right = worldIn.getBlockState(pos.north());
			IBlockState left = worldIn.getBlockState(pos.south());

			if (right.getBlock() == this && (right.getValue(FACING) == EnumFacing.EAST | right.getValue(FACING) == EnumFacing.WEST)) {
				state = state.withProperty(RIGHT, true);
			}
			if (left.getBlock() == this && (left.getValue(FACING) == EnumFacing.EAST | left.getValue(FACING) == EnumFacing.WEST)) {
				state = state.withProperty(LEFT, true);
			}

			return state;
		}

		return state;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0.4);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, LEFT, RIGHT });
	}
}
