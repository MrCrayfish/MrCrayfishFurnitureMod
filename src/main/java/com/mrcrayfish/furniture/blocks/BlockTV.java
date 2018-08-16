package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockTV extends BlockAbstractTV
{
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);

    public BlockTV(Material material)
    {
        super(material, 10, 10, 1.4, -5);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
}
