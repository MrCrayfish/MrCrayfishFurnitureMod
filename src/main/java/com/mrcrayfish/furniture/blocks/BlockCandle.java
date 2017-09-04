package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCandle extends Block 
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.2, 0.9375);
	
	public BlockCandle(Material materialIn) 
	{
		super(materialIn);
		this.setTickRandomly(true);
		this.setLightLevel(0.8F);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.STONE);
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.45, pos.getZ() + (7.95 * 0.0625), 0.0D, 0.0D, 0.0D, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (12.95 * 0.0625), 0.0D, 0.0D, 0.0D, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (2.95 * 0.0625), 0.0D, 0.0D, 0.0D, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (12.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (7.95 * 0.0625), 0.0D, 0.0D, 0.0D, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (2.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (7.95 * 0.0625), 0.0D, 0.0D, 0.0D, new int[0]);
    }
}
