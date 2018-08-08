package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.tileentity.TileEntityModernTV;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class BlockModernTV extends BlockFurnitureTile
{
    public BlockModernTV()
    {
        super(Material.ANVIL);
        this.setUnlocalizedName("modern_tv");
        this.setRegistryName("modern_tv");
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityModernTV(22, 12);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityModernTV(22, 12);
    }
}
