package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.util.Bounds;

public class BlockOutdoorTable extends BlockTable
{
    protected AxisAlignedBB[] legs;

    public BlockOutdoorTable(Material material, SoundType sound)
    {
        super(material, sound);
        this.setHardness(1.0F);
        legs = new Bounds(0.25, 0, 13.75, 2.25, 14, 15.75).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
        top = new Bounds(0, 14, 0, 16, 16, 16).toAABB();
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = state.getActualState(world, pos);

        List<AxisAlignedBB> boxes = new ArrayList<>();
        boolean north = state.getValue(FORWARD);
        boolean south = state.getValue(BACK);
        boolean east = state.getValue(LEFT);
        boolean west = state.getValue(RIGHT);

        if(!north)
        {
            if(!west) boxes.add(legs[1]);
            if(!east) boxes.add(legs[0]);
        }
        if(!south)
        {
            if(!west) boxes.add(legs[2]);
            if(!east) boxes.add(legs[3]);
        }
        boxes.add(top);

        return boxes;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
