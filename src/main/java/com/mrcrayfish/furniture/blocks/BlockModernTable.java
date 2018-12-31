package com.mrcrayfish.furniture.blocks;

import java.util.ArrayList;
import java.util.List;

import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockModernTable extends BlockOutdoorTable
{
    public BlockModernTable(String id)
    {
        super(Material.WOOD, SoundType.WOOD);
        this.setHardness(1.0F);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        legs = new Bounds(15, 0, 13.5, 13.5, 14, 15).getRotatedBounds();
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
