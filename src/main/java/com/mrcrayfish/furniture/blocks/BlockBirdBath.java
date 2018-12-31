package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.util.Bounds;

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

import java.util.List;

import javax.annotation.Nullable;

public class BlockBirdBath extends BlockCollisionRaytrace
{
    private static final AxisAlignedBB BASE = new Bounds(4, 0, 4, 12, 1.6, 12).toAABB();
    private static final AxisAlignedBB COLUMN = new Bounds(5, 1.6, 5, 11, 11.2, 11).toAABB();
    private static final AxisAlignedBB BOWL_BASE = new Bounds(1, 11.2, 1, 15, 12.8, 15).toAABB();
    private static final AxisAlignedBB BOWL_LEFT = new Bounds(0, 12.8, 0, 1.6, 14.4, 16).toAABB();
    private static final AxisAlignedBB BOWL_BACK = new Bounds(1.6, 12.8, 0, 14.4, 14.4, 1.6).toAABB();
    private static final AxisAlignedBB BOWL_FRONT = new Bounds(1.6, 12.8, 14.4, 14.4, 14.4, 16).toAABB();
    private static final AxisAlignedBB BOWL_RIGHT = new Bounds(14.4, 12.8, 0, 16, 14.4, 16).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(BASE, COLUMN, BOWL_BASE, BOWL_LEFT, BOWL_BACK, BOWL_FRONT, BOWL_RIGHT);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

    public BlockBirdBath(Material material)
    {
        super(material);
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
