package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.tileentity.TileEntityCouch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockCouchNormal extends BlockCouch
{
    public BlockCouchNormal()
	{
		super("couch");
	}

	@Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        TileEntity te = super.createTileEntity(world, state);
        if(te instanceof TileEntityCouch)
        {
            ((TileEntityCouch) te).setColour(state.getValue(COLOUR));
        }
        return te;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(new ItemStack(this, 1, 15 - Math.max(0, state.getValue(COLOUR))));
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
    {
        for(int i = 0; i < EnumDyeColor.values().length; i++)
        {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        int metadata = 0;
        if(world.getTileEntity(pos) instanceof TileEntityCouch)
        {
            metadata = ((TileEntityCouch) world.getTileEntity(pos)).getColour();
        }
        return new ItemStack(this, 1, metadata);
    }

    @Override
    public boolean isSpecial()
    {
        return false;
    }
}
