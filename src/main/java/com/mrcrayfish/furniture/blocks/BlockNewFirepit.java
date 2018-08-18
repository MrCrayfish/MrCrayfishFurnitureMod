package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNewFirepit extends ModBlock
{
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);
	public static final PropertyBool BURNING = PropertyBool.create("burning");

	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.1875, 1.0);

	public BlockNewFirepit(String name)
	{
		super(Material.WOOD, name);
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		this.setHardness(2.0F);
		this.setResistance(5.0f);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.getValue(BURNING) ? 15 : 0;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (state.getValue(BURNING))
		{
			world.setBlockState(pos, FurnitureBlocks.FIRE_PIT_OFF.getDefaultState().withProperty(STAGE, 3));
			return true;
		} else
		{
			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (!heldItem.isEmpty())
			{
				if (heldItem.getItem() instanceof ItemFlintAndSteel)
				{
					if (state.getValue(STAGE) == 3)
					{
						world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
						world.setBlockState(pos, state.withProperty(BURNING, true));
						heldItem.damageItem(1, playerIn);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
	{
		if (state.getValue(BURNING))
		{
			if (rand.nextInt(24) == 0)
			{
				worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			}

			for (int i = 0; i < 2; ++i)
			{
				double posX = (double) pos.getX() + rand.nextDouble() * 0.8 + 0.2;
				double posY = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
				double posZ = (double) pos.getZ() + rand.nextDouble() * 0.8 + 0.2;
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
			}
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
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.isSideSolid(pos.down(), EnumFacing.UP);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
	{
		addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(STAGE) + (state.getValue(BURNING) ? 4 : 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STAGE, meta % 4).withProperty(BURNING, meta / 4 > 0);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, STAGE, BURNING);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return this.getLog();
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		ItemStack stack = this.getLog();
		stack.grow(state.getValue(STAGE));
		drops.add(stack);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return placer.getHeldItem(hand).getItem() == Item.getItemFromBlock(this) ? this.getDefaultState().withProperty(STAGE, 3).withProperty(BURNING, true) : this.getStateFromMeta(meta);
	}

	private ItemStack getLog()
	{
		Item item = FurnitureItems.LOG;
		if (this == FurnitureBlocks.FIRE_PIT_SPRUCE)
			item = FurnitureItems.LOG_SPRUCE;
		if (this == FurnitureBlocks.FIRE_PIT_BIRCH)
			item = FurnitureItems.LOG_BIRCH;
		if (this == FurnitureBlocks.FIRE_PIT_JUNGLE)
			item = FurnitureItems.LOG_JUNGLE;
		if (this == FurnitureBlocks.FIRE_PIT_ACACIA)
			item = FurnitureItems.LOG_ACACIA;
		if (this == FurnitureBlocks.FIRE_PIT_DARK_OAK)
			item = FurnitureItems.LOG_DARK_OAK;
		return new ItemStack(item);
	}
}