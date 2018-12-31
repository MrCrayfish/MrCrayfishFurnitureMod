package com.mrcrayfish.furniture.blocks;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.util.Bounds;

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

public class BlockCandle extends BlockCollisionRaytrace
{
    private static final AxisAlignedBB[] ARM = new Bounds(7, 7, 2, 9, 9, 7).getRotatedBounds();
    private static final AxisAlignedBB[] PLATE_SIDE = new Bounds(6, 9, 1, 10, 10, 5).getRotatedBounds();
    private static final AxisAlignedBB[] CANDLE_SIDE = new Bounds(7.2, 10, 2.2, 8.7, 17, 3.7).getRotatedBounds();

    private static final AxisAlignedBB BASE_BOTTOM = new Bounds(4, 0, 4, 12, 1, 12).toAABB();
    private static final AxisAlignedBB BASE_TOP = new Bounds(5, 1, 5, 11, 2, 11).toAABB();
    private static final AxisAlignedBB BODY = new Bounds(7, 2, 7, 9, 13, 9).toAABB();
    private static final AxisAlignedBB PLATE_CENTER = new Bounds(6, 13, 6, 10, 14, 10).toAABB();
    private static final AxisAlignedBB CANDLE_CENTER = new Bounds(7.2, 14, 7.2, 8.7, 21, 8.7).toAABB();

    private static final List<AxisAlignedBB> SIDE_CANDLES = Arrays.stream(Bounds.getRotatedBoundLists(ARM, PLATE_SIDE, CANDLE_SIDE)).flatMap(List::stream).collect(Collectors.toList());
    private static final List<AxisAlignedBB> MAIN_CANDLE = Lists.newArrayList(BASE_BOTTOM, BASE_TOP, BODY, PLATE_CENTER, CANDLE_CENTER);
    private static final List<AxisAlignedBB> COLLISION_BOXES = Stream.of(SIDE_CANDLES, MAIN_CANDLE).flatMap(Collection::stream).collect(Collectors.toList());
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES;
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
