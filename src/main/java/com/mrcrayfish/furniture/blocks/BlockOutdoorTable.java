package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import com.mrcrayfish.furniture.util.Bounds;

public class BlockOutdoorTable extends BlockTable
{
    public static final AxisAlignedBB LEGS[] = new Bounds(0.25, 0, 13.75, 2.25, 14, 15.75).getRotatedBounds();
    public static final AxisAlignedBB TOP = new Bounds(0, 14, 0, 16, 16, 16).toAABB();

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
            if(!west) boxes.add(LEGS[1]);
            if(!east) boxes.add(LEGS[0]);
        }
        if(!south)
        {
            if(!west) boxes.add(LEGS[2]);
            if(!east) boxes.add(LEGS[3]);
        }
        boxes.add(TOP);

        return boxes;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
