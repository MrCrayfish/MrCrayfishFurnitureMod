package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockTV extends BlockAbstractTV
{
    private static final AxisAlignedBB BOUNDING_BOX = new Bounds(1, 0, 1, 15, 14, 15).toAABB();

    public BlockTV(Material material)
    {
        super(material, 10, 10, 1.5, -6.1);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
}
