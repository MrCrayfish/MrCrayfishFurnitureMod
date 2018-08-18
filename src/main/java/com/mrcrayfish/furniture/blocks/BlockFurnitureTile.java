package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.util.InventoryUtil;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public abstract class BlockFurnitureTile extends BlockFurniture implements ITileEntityProvider
{
	public BlockFurnitureTile(Material material, String name)
	{
		this(material, material.getMaterialMapColor(), name, name);
	}

	public BlockFurnitureTile(Material material, String registryName, String unlocalizedName)
	{
		this(material, material.getMaterialMapColor(), registryName, unlocalizedName);
	}

	public BlockFurnitureTile(Material material, MapColor color, String name)
	{
		this(material, color, name, name);
	}

	public BlockFurnitureTile(Material material, MapColor color, String registryName, String unlocalizedName)
	{
		super(material, color, registryName, unlocalizedName);
		this.hasTileEntity = true;
	}

	@Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        return Container.calcRedstone(tileEntity);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof IInventory)
        {
            IInventory inv = (IInventory) tileEntity;
            InventoryHelper.dropInventoryItems(world, pos, inv);
        }
        if(tileEntity instanceof ISimpleInventory)
        {
            ISimpleInventory inv = (ISimpleInventory) tileEntity;
            InventoryUtil.dropInventoryItems(world, pos, inv);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }
}
