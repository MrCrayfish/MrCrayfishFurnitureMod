package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public class BlockWashingMachine extends BlockFurnitureTile implements IRayTrace
{
	public static final AxisAlignedBB CENTER_BOX_AND_PANEL = new AxisAlignedBB(1 * 0.0625, 0, 1 * 0.0625, 15 * 0.0625, 1, 15 * 0.0625);
	public static final AxisAlignedBB[] CENTER_BOXES = new AxisAlignedBB[] { new AxisAlignedBB(1 * 0.0625, 0, 2 * 0.0625, 15 * 0.0625, 1, 15 * 0.0625), new AxisAlignedBB(1 * 0.0625, 0, 1 * 0.0625, 14 * 0.0625, 1, 15 * 0.0625), new AxisAlignedBB(1 * 0.0625, 0, 1 * 0.0625, 15 * 0.0625, 1, 14 * 0.0625), new AxisAlignedBB(2 * 0.0625, 0, 1 * 0.0625, 15 * 0.0625, 1, 15 * 0.0625) };

	public BlockWashingMachine()
	{
		super(Material.ANVIL, "washing_machine");
		this.setHardness(1.0F);
		this.setSoundType(SoundType.ANVIL);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (placer instanceof EntityPlayer)
		{
			Triggers.trigger(Triggers.PLACE_APPLIANCE, (EntityPlayer) placer);
		}
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tile_entity = worldIn.getTileEntity(pos);

			if (tile_entity instanceof TileEntityWashingMachine)
			{
				playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityWashingMachine();
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		TileEntityWashingMachine washing_machine = (TileEntityWashingMachine) world.getTileEntity(pos);
		return washing_machine.isWashing() ? 1 : 0;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState)
	{
		addCollisionBoxToList(pos, entityBox, collidingBoxes, CENTER_BOX_AND_PANEL);
	}

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		EnumFacing facing = state.getValue(FACING);
		boxes.add(CENTER_BOXES[facing.getHorizontalIndex()]);
	}
	
	@Override
	public void addWireframeBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
		boxes.add(this.getBoundingBox(this.getActualState(state, world, pos), world, pos));
	}
}
