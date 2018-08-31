package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCandle extends Block
{
    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.2625, 0.9375);

    public static final AxisAlignedBB BOTTOM_LIP = new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 12 * 0.0625, 1 * 0.0625, 12 * 0.0625);
    public static final AxisAlignedBB BOTTOM = new AxisAlignedBB(5 * 0.0625, 1 * 0.0625, 5 * 0.0625, 11 * 0.0625, 2 * 0.0625, 11 * 0.0625);
    public static final AxisAlignedBB CENTER_PILLAR = new AxisAlignedBB(7 * 0.0625, 2 * 0.0625, 7 * 0.0625, 9 * 0.0625, 7 * 0.0625, 9 * 0.0625);
    public static final AxisAlignedBB CROSS_1 = new AxisAlignedBB(2 * 0.0625, 7 * 0.0625, 7 * 0.0625, 14 * 0.0625, 9 * 0.0625, 9 * 0.0625);
    public static final AxisAlignedBB CROSS_2 = new AxisAlignedBB(7 * 0.0625, 7 * 0.0625, 2 * 0.0625, 9 * 0.0625, 9 * 0.0625, 14 * 0.0625);
    public static final AxisAlignedBB[] CANDLES = {new AxisAlignedBB(1 * 0.0625, 9 * 0.0625, 6 * 0.0625, 5 * 0.0625, 17 * 0.0625, 10 * 0.0625), new AxisAlignedBB(6 * 0.0625, 9 * 0.0625, 1 * 0.0625, 10 * 0.0625, 17 * 0.0625, 5 * 0.0625), new AxisAlignedBB(11 * 0.0625, 9 * 0.0625, 6 * 0.0625, 15 * 0.0625, 17 * 0.0625, 10 * 0.0625), new AxisAlignedBB(6 * 0.0625, 9 * 0.0625, 11 * 0.0625, 10 * 0.0625, 17 * 0.0625, 15 * 0.0625), new AxisAlignedBB(6 * 0.0625, 9 * 0.0625, 6 * 0.0625, 10 * 0.0625, 20 * 0.0625, 10 * 0.0625)};

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

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.45, pos.getZ() + (7.95 * 0.0625), 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (12.95 * 0.0625), 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (2.95 * 0.0625), 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (12.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (7.95 * 0.0625), 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + (2.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (7.95 * 0.0625), 0.0D, 0.0D, 0.0D);

        if(rand.nextBoolean())
        {
            double xRand = 0.001D * (rand.nextInt(4) - 2);
            double zRand = 0.001D * (rand.nextInt(4) - 2);
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.45, pos.getZ() + (7.95 * 0.0625), xRand + (rand.nextInt(3) - 1) * 0.01, 0.01D, zRand + (rand.nextInt(3) - 1) * 0.01);
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (12.95 * 0.0625), xRand + (rand.nextInt(3) - 1) * 0.01, 0.01D, zRand + (rand.nextInt(3) - 1) * 0.01);
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + (7.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (2.95 * 0.0625), xRand + (rand.nextInt(3) - 1) * 0.01, 0.01D, zRand + (rand.nextInt(3) - 1) * 0.01);
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + (12.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (7.95 * 0.0625), xRand + (rand.nextInt(3) - 1) * 0.01, 0.01D, zRand + (rand.nextInt(3) - 1) * 0.01);
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + (2.95 * 0.0625), pos.getY() + 1.2, pos.getZ() + (7.95 * 0.0625), xRand + (rand.nextInt(3) - 1) * 0.01, 0.01D, zRand + (rand.nextInt(3) - 1) * 0.01);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOTTOM_LIP);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOTTOM);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, CENTER_PILLAR);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, CROSS_1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, CROSS_2);
        for(AxisAlignedBB CANDLE : CANDLES)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, CANDLE);
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
