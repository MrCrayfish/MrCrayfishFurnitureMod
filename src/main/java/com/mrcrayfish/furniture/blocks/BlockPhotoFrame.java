package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.IValueContainer;
import com.mrcrayfish.furniture.tileentity.TileEntityPhotoFrame;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class BlockPhotoFrame extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BOUNDING_BOX = new Bounds(15, 0, 0, 16, 16, 16).getRotatedBounds();

    public BlockPhotoFrame()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("photo_frame");
        this.setRegistryName("photo_frame");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return side.getHorizontalIndex() != -1;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = this.getStateFromMeta(meta);
        if(facing.getHorizontalIndex() != -1)
        {
            return state.withProperty(FACING, facing.getOpposite());
        }
        return state;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof IValueContainer && tileEntity instanceof TileEntityPhotoFrame)
            {
                if(!((TileEntityPhotoFrame) tileEntity).isDisabled())
                {
                    playerIn.openGui(MrCrayfishFurnitureMod.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPhotoFrame();
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
