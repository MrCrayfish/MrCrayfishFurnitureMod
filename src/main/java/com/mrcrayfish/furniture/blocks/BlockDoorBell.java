package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockDoorBell extends BlockFurniture
{
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.85, 0.3, 0.4, 1.0, 0.7, 0.6);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    public BlockDoorBell(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.TRUE));
        this.setTickRandomly(true);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public int tickRate(World worldIn)
    {
        return 30;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return !(side == EnumFacing.UP || side == EnumFacing.DOWN) && world.isSideSolid(pos.offset(side.getOpposite()), side, true);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite()).withProperty(POWERED, Boolean.FALSE);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing facing = state.getValue(FACING);
        if(!this.canPlaceBlockOnSide(worldIn, pos, facing.getOpposite()))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(state.getValue(POWERED) || worldIn.isRemote)
        {
            return true;
        }
        else
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, true), 3);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.playSound(null, pos, FurnitureSounds.door_bell, SoundCategory.BLOCKS, 1.0F, 1.0F);
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            Triggers.trigger(Triggers.CLICKED_DOORBELL, playerIn);
            return true;
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if(!worldIn.isRemote)
        {
            if(state.getValue(POWERED))
            {
                this.handleArrow(worldIn, pos, state);
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if(!worldIn.isRemote)
        {
            if(!state.getValue(POWERED))
            {
                this.handleArrow(worldIn, pos, state);
            }
        }
    }

    private void handleArrow(World worldIn, BlockPos pos, IBlockState state)
    {
        List<? extends Entity> list = worldIn.<Entity>getEntitiesWithinAABB(EntityArrow.class, state.getBoundingBox(worldIn, pos).offset(pos));
        boolean flag = !list.isEmpty();
        boolean flag1 = state.getValue(POWERED);

        if(flag && !flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, true));
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }

        if(!flag && flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, false));
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5F);
        }

        if(flag)
        {
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(POWERED, meta > 3);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if(state.getValue(POWERED))
        {
            meta += 4;
        }
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, POWERED);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if(!blockState.getValue(POWERED))
        {
            return 0;
        }
        else
        {
            return blockState.getValue(FACING) == side ? 15 : 0;
        }
    }
}
