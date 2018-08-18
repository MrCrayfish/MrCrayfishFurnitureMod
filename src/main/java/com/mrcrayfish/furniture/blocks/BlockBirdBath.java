package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockBirdBath extends ModBlock implements IRayTrace
{
    public static final AxisAlignedBB CENTER = new AxisAlignedBB(5 * 0.0625, 1.75 * 0.0625, 5 * 0.0625, 11 * 0.0625, 12 * 0.0625, 11 * 0.0625);
    public static final AxisAlignedBB BOTTOM_LIP = new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 12 * 0.0625, 1.5 * 0.0625, 12 * 0.0625);
    public static final AxisAlignedBB TOP = new AxisAlignedBB(1 * 0.0625, 11.25 * 0.0625, 1 * 0.0625, 15 * 0.0625, 12.75 * 0.0625, 15 * 0.0625);
    public static final AxisAlignedBB BOWL = new AxisAlignedBB(0, 12.75 * 0.0625, 0, 1, 14.5 * 0.0625, 1);

    public BlockBirdBath()
    {
        super(Material.ROCK, "bird_bath");
        this.setHardness(1.5F);
        this.setHardness(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_OUTDOOR_FURNITURE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
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
    public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
    {
        boxes.add(CENTER);
        boxes.add(BOTTOM_LIP);
        boxes.add(TOP);
        boxes.add(BOWL);
    }
    
    @Override
    public boolean applyCollisions(IBlockState state, World world, BlockPos pos)
    {
    	return true;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
