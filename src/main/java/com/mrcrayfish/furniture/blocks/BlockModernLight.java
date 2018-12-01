package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockModernLight extends Block implements IPowered
{
    private static final AxisAlignedBB[] COLLISION_BOUNDS = new Bounds(14, 4, 4, 16, 12, 12).getRotatedBounds();
    private static final AxisAlignedBB[] SELECTION_BOUNDS = new Bounds(13, 3, 3, 16, 13, 13).getRotatedBounds();

    private static final AxisAlignedBB SELECTION_BOUND_DOWN = new Bounds(3, 0, 3, 13, 3, 13).toAABB();
    private static final AxisAlignedBB SELECTION_BOUND_UP = new Bounds(3, 13, 3, 13, 16, 13).toAABB();
    private static final AxisAlignedBB COLLISION_BOUND_DOWN = new Bounds(4, 0, 4, 14, 2, 14).toAABB();
    private static final AxisAlignedBB COLLISION_BOUND_UP = new Bounds(4, 14, 4, 14, 16, 14).toAABB();

    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

    public BlockModernLight(String id, boolean powered)
    {
        super(Material.GLASS);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setHardness(0.5F);
        if(!powered)
        {
            this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        }
        else
        {
            this.setLightLevel(1.0F);
        }
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
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
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        EnumFacing facing = state.getValue(FACING);
        if(facing.getHorizontalIndex() != -1)
        {
            Block.addCollisionBoxToList(pos, entityBox, collidingBoxes,SELECTION_BOUNDS[facing.getOpposite().getHorizontalIndex()] );
        }
        else if(facing == EnumFacing.UP)
        {
            Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOUND_DOWN);
        }
        else if(facing == EnumFacing.DOWN)
        {
            Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOUND_UP);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        if(facing.getHorizontalIndex() != -1)
        {
            return SELECTION_BOUNDS[facing.getOpposite().getHorizontalIndex()];
        }
        else if(facing == EnumFacing.UP)
        {
            return SELECTION_BOUND_DOWN;
        }
        else if(facing == EnumFacing.DOWN)
        {
            return SELECTION_BOUND_UP;
        }
        return FULL_BLOCK_AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void setPowered(World world, BlockPos pos, boolean powered)
    {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);
        if(powered)
        {
            world.setBlockState(pos, FurnitureBlocks.MODERN_LIGHT_ON.getDefaultState().withProperty(FACING, facing));
        }
        else
        {
            world.setBlockState(pos, FurnitureBlocks.MODERN_LIGHT_OFF.getDefaultState().withProperty(FACING, facing));
        }
    }
}
