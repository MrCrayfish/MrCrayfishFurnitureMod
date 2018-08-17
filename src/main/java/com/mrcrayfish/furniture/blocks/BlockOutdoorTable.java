package com.mrcrayfish.furniture.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOutdoorTable extends BlockTable
{
    public static final AxisAlignedBB SOUTH_EAST_LEG = new AxisAlignedBB(0.25 * 0.0625, 0, 13.75 * 0.0625, 2.25 * 0.0625, 14 * 0.0625, 15.75 * 0.0625);
    public static final AxisAlignedBB SOUTH_WEST_LEG = new AxisAlignedBB(13.75 * 0.0625, 0, 13.75 * 0.0625, 15.75 * 0.0625, 14 * 0.0625, 15.75 * 0.0625);
    public static final AxisAlignedBB NORTH_EAST_LEG = new AxisAlignedBB(0.25 * 0.0625, 0, 0.25 * 0.0625, 2.25 * 0.0625, 14 * 0.0625, 2.25 * 0.0625);
    public static final AxisAlignedBB NORTH_WEST_LEG = new AxisAlignedBB(13.75 * 0.0625, 0, 0.25 * 0.0625, 15.75 * 0.0625, 14 * 0.0625, 2.25 * 0.0625);
    public static final AxisAlignedBB TOP = new AxisAlignedBB(0, 14 * 0.0625, 0, 1, 1, 1);

    public BlockOutdoorTable(Material material, SoundType sound)
    {
        super(material, sound);
        this.setHardness(1.0F);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        boolean north = state.getValue(FORWARD);
        boolean south = state.getValue(BACK);
        boolean east = state.getValue(LEFT);
        boolean west = state.getValue(RIGHT);

        if(!north)
        {
            if(!west) boxes.add(NORTH_WEST_LEG);
            if(!east) boxes.add(NORTH_EAST_LEG);
        }
        if(!south)
        {
            if(!west) boxes.add(SOUTH_WEST_LEG);
            if(!east) boxes.add(SOUTH_EAST_LEG);
        }
        boxes.add(TOP);

        return boxes;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
