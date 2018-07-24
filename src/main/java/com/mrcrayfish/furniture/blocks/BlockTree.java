/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityTree;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockTree extends BlockFurnitureTile
{
    private static final AxisAlignedBB BOUNDING_BOX_BOTTOM = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 2.0, 0.9375);
    private static final AxisAlignedBB BOUNDING_BOX_BOTTOM_ALT = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375);
    private static final AxisAlignedBB BOUNDING_BOX_TOP = new AxisAlignedBB(0.0625, -1.0, 0.0625, 0.9375, 1.0, 0.9375);

    public BlockTree(Material material, boolean top)
    {
        super(material);
        this.setSoundType(SoundType.WOOD);
        this.setLightLevel(0.3F);
        this.setHardness(0.5F);
        if(top) this.setCreativeTab(null);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if(this == FurnitureBlocks.TREE_BOTTOM)
            world.setBlockState(pos.up(), FurnitureBlocks.TREE_TOP.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()));
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityTree)
        {
            TileEntityTree tileEntityTree = (TileEntityTree) tileEntity;
            tileEntityTree.addOrnament(playerIn.getHorizontalFacing(), heldItem);
            if(!heldItem.isEmpty()) heldItem.shrink(1);
            TileEntityUtil.markBlockForUpdate(worldIn, pos);
        }
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(this == FurnitureBlocks.TREE_TOP)
        {
            if(player.getHeldItemMainhand() != null)
            {
                if(player.getHeldItemMainhand().getItem() != Items.SHEARS)
                {
                    worldIn.destroyBlock(pos.down(), false);
                }
                else
                {
                    player.getHeldItemMainhand().damageItem(1, player);
                }
            }
            else
            {
                worldIn.destroyBlock(pos.down(), false);
            }
        }
        else
        {
            worldIn.destroyBlock(pos.up(), false);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if(this == FurnitureBlocks.TREE_BOTTOM)
        {
            if(source.getBlockState(pos.up()).getBlock() == FurnitureBlocks.TREE_TOP)
            {
                return BOUNDING_BOX_BOTTOM;
            }
            else
            {
                return BOUNDING_BOX_BOTTOM_ALT;
            }
        }
        else
        {
            return BOUNDING_BOX_TOP;
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.TREE_BOTTOM).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.TREE_BOTTOM);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTree();
    }
}
