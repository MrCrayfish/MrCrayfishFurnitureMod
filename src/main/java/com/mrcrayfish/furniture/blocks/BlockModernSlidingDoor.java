package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityModernSlidingDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class BlockModernSlidingDoor extends BlockFurniture
{
    public static final PropertyBool TOP = PropertyBool.create("top");

    public BlockModernSlidingDoor()
    {
        super(Material.WOOD);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TOP, false));
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(!state.getValue(TOP))
        {
            worldIn.setBlockState(pos.up(), FurnitureBlocks.MODERN_SLIDING_DOOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(TOP, true));
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(state.getValue(TOP))
        {
            worldIn.destroyBlock(pos.down(), false);
        }
        else
        {
            worldIn.destroyBlock(pos.up(), false);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityModernSlidingDoor)
        {
            TileEntityModernSlidingDoor slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
            boolean powered = slidingDoor.isPowered();
            slidingDoor.setPowered(!powered);
            slidingDoor.sync();

            if(state.getValue(TOP))
            {
                pos = pos.offset(EnumFacing.DOWN);
            }
            else
            {
                pos = pos.offset(EnumFacing.UP);
            }

            tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityModernSlidingDoor)
            {
                slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
                slidingDoor.setPowered(!powered);
                slidingDoor.sync();
            }
        }
        return true;
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
        return new TileEntityModernSlidingDoor();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(TOP, meta / 4 > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(TOP) ? 4 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TOP);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
