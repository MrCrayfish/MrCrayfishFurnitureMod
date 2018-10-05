package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityDeskCabinet;
import com.mrcrayfish.furniture.tileentity.TileEntityTVStand;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockTVStand extends BlockFurnitureTile
{
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockTVStand()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("tv_stand");
        this.setRegistryName("tv_stand");
        this.setHardness(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, Type.NONE));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityTVStand)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
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
            state = state.withProperty(TYPE, Type.BOTH);
        }
        else if(left)
        {
            state = state.withProperty(TYPE, Type.RIGHT);
        }
        else if(right)
        {
            state = state.withProperty(TYPE, Type.LEFT);
        }
        else
        {
            state = state.withProperty(TYPE, Type.NONE);
        }
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTVStand();
    }

    public enum Type implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH;

        @Override
        public String getName()
        {
            return name().toLowerCase(Locale.US);
        }
    }
}
