package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityColoured;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockModernBed extends BlockFurnitureTile
{
    public static final PropertyEnum<Type> BED_TYPE = PropertyEnum.create("type", Type.class);
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);

    public BlockModernBed(String id)
    {
        super(Material.WOOD);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setHardness(0.5F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityColoured();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            int colour = ((TileEntityColoured) tileEntity).getColour();
            state = state.withProperty(COLOUR, colour);
        }

        EnumFacing facing = state.getValue(FACING);
        boolean left = false;
        boolean right = false;

        IBlockState rightState = worldIn.getBlockState(pos.offset(facing.rotateY()));
        if(rightState.getBlock() == this && rightState.getValue(FACING) == facing)
        {
            right = true;
        }
        IBlockState leftState = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
        if(leftState.getBlock() == this && leftState.getValue(FACING) == facing)
        {
            left = true;
        }

        if(left && right)
        {
            state = state.withProperty(BED_TYPE, Type.BOTH);
        }
        else if(left)
        {
            state = state.withProperty(BED_TYPE, Type.RIGHT);
        }
        else if(right)
        {
            state = state.withProperty(BED_TYPE, Type.LEFT);
        }
        else
        {
            state = state.withProperty(BED_TYPE, Type.NONE);
        }
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, BED_TYPE, COLOUR);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            ((TileEntityColoured) tileEntity).setColour(15 - stack.getMetadata());
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        EnumFacing facing = state.getValue(FACING);
        if(this == FurnitureBlocks.MODERN_BED_TOP)
        {
            if(worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() == FurnitureBlocks.MODERN_BED_BOTTOM)
            {
                worldIn.destroyBlock(pos.offset(facing.getOpposite()), false);
            }
        }
        else if(this == FurnitureBlocks.MODERN_BED_BOTTOM)
        {
            if(worldIn.getBlockState(pos.offset(facing)).getBlock() == FurnitureBlocks.MODERN_BED_TOP)
            {
                worldIn.destroyBlock(pos.offset(facing), false);
            }
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if (tileEntity instanceof TileEntityColoured)
        {
            TileEntityColoured couch = (TileEntityColoured) tileEntity;
            ItemStack itemstack = new ItemStack(FurnitureBlocks.MODERN_BED_BOTTOM, 1, couch.getColour());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, tileEntity, stack);
        }
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
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            metadata = ((TileEntityColoured) tileEntity).getColour();
        }
        return new ItemStack(this, 1, metadata);
    }

    public enum Type implements IStringSerializable
    {
        LEFT, RIGHT, BOTH, NONE;

        @Override
        public String getName()
        {
            return name().toLowerCase(Locale.US);
        }
    }
}
