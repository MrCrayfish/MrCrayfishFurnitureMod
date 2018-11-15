package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityModernSlidingDoor;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockModernSlidingDoor extends BlockFurniture implements IPowered
{
    public static final PropertyBool TOP = PropertyBool.create("top");

    private static final AxisAlignedBB[] COLLISION_BOX_BOTTOM = new Bounds(6, 0, 0, 10, 32, 16).getRotatedBounds();
    private static final AxisAlignedBB[] COLLISION_BOX_BOTTOM_OPENED = new Bounds(6 * 0.0625, 0, 0, 10 * 0.0625, 2F, 2.96 * 0.0625).getRotatedBounds();
    private static final AxisAlignedBB[] COLLISION_BOX_TOP = new Bounds(6, -16, 0, 10, 16, 16).getRotatedBounds();
    private static final AxisAlignedBB[] COLLISION_BOX_TOP_OPENED = new Bounds(6 * 0.0625, -1.0, 0, 10 * 0.0625, 1.0, 2.96 * 0.0625).getRotatedBounds();


    public BlockModernSlidingDoor()
    {
        super(Material.WOOD);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TOP, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        boolean open = false;
        TileEntity tileEntity = source.getTileEntity(pos);
        if(tileEntity instanceof TileEntityModernSlidingDoor)
        {
            open = ((TileEntityModernSlidingDoor) tileEntity).isPowered();
        }

        EnumFacing facing = state.getValue(FACING);
        if(state.getValue(TOP))
        {
            if(open)
            {
                return COLLISION_BOX_TOP_OPENED[facing.getHorizontalIndex()];
            }
            else
            {
                return COLLISION_BOX_TOP[facing.getHorizontalIndex()];
            }
        }
        else
        {
            if(open)
            {
                return COLLISION_BOX_BOTTOM_OPENED[facing.getHorizontalIndex()];
            }
            else
            {
                return COLLISION_BOX_BOTTOM[facing.getHorizontalIndex()];
            }
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        boolean open = false;
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityModernSlidingDoor)
        {
            open = ((TileEntityModernSlidingDoor) tileEntity).isPowered();
        }

        EnumFacing facing = state.getValue(FACING);
        if(state.getValue(TOP))
        {
            if(open)
            {
                Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_TOP_OPENED[facing.getHorizontalIndex()]);
            }
            else
            {
                Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_TOP[facing.getHorizontalIndex()]);
            }
        }
        else
        {
            if(open)
            {
                Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_BOTTOM_OPENED[facing.getHorizontalIndex()]);
            }
            else
            {
                Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_BOTTOM[facing.getHorizontalIndex()]);
            }
        }
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityModernSlidingDoor)
        {
            TileEntityModernSlidingDoor slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
            boolean powered = slidingDoor.isPowered();
            setPowered(world, pos, state, !powered);
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!worldIn.isRemote && blockIn instanceof BlockModernSlidingDoor)
        {
            System.out.println(worldIn.isBlockPowered(pos));
            boolean hasPower = worldIn.isBlockPowered(pos);
            if(state.getValue(TOP))
            {
                hasPower |= worldIn.isBlockPowered(pos.down());
            }
            else
            {
                hasPower |= worldIn.isBlockPowered(pos.up());
            }
            setPowered(worldIn, pos, state, hasPower);
        }
    }

    private void setPowered(World world, BlockPos pos, IBlockState state, boolean powered)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityModernSlidingDoor)
        {
            TileEntityModernSlidingDoor slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
            if(slidingDoor.isPowered() == powered)
                return;

            slidingDoor.setPowered(powered);
            slidingDoor.sync();

            if(powered)
            {
                world.playSound(null, pos, FurnitureSounds.sliding_door_open, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            else
            {
                world.playSound(null, pos, FurnitureSounds.sliding_door_close, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            if(state.getValue(TOP))
            {
                pos = pos.offset(EnumFacing.DOWN);
            }
            else
            {
                pos = pos.offset(EnumFacing.UP);
            }

            tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof TileEntityModernSlidingDoor)
            {
                slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
                slidingDoor.setPowered(powered);
                slidingDoor.sync();
            }

            EnumFacing slidingFacing = state.getValue(FACING).rotateYCCW();
            BlockPos otherDoor = pos.offset(slidingFacing.getOpposite());
            IBlockState state1 = world.getBlockState(otherDoor);
            if(state1.getBlock() instanceof BlockModernSlidingDoor)
            {
                ((BlockModernSlidingDoor) state1.getBlock()).setPowered(world, otherDoor, state1, powered);
            }
        }
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

    @Override
    public void setPowered(World world, BlockPos pos, boolean powered)
    {
        setPowered(world, pos, world.getBlockState(pos), powered);
    }
}
