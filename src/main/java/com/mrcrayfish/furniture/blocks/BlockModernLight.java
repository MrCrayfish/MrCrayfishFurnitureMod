package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockModernLight extends BlockCollisionRaytrace implements IPowered
{
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

    private static final Bounds BASE_BOUNDS = new Bounds(4, 0, 4, 12, 1, 12);
    private static final Bounds GLASS_BOUNDS = new Bounds(5, 1, 5, 11, 1.5, 11);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_SIDES = Bounds.getRotatedBoundLists(BASE_BOUNDS.rotateX(Rotation.COUNTERCLOCKWISE_90).getRotatedBounds(), GLASS_BOUNDS.rotateX(Rotation.COUNTERCLOCKWISE_90).getRotatedBounds());
    private static final AxisAlignedBB[] BOUNDING_BOX_SIDES = Bounds.getBoundingBoxes(COLLISION_BOXES_SIDES);

    private static final List<AxisAlignedBB> COLLISION_BOXES_BOTTOM = Lists.newArrayList(BASE_BOUNDS.toAABB(), GLASS_BOUNDS.toAABB());
    private static final AxisAlignedBB BOUNDING_BOX_BOTTOM = Bounds.getBoundingBox(COLLISION_BOXES_BOTTOM);

    private static final List<AxisAlignedBB> COLLISION_BOXES_TOP = Lists.newArrayList(BASE_BOUNDS.rotateX(Rotation.CLOCKWISE_180).toAABB(), GLASS_BOUNDS.rotateX(Rotation.CLOCKWISE_180).toAABB());
    private static final AxisAlignedBB BOUNDING_BOX_TOP = Bounds.getBoundingBox(COLLISION_BOXES_TOP);

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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return facing == EnumFacing.UP ? BOUNDING_BOX_BOTTOM : (facing == EnumFacing.DOWN ? BOUNDING_BOX_TOP : BOUNDING_BOX_SIDES[facing.getHorizontalIndex()]);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        EnumFacing facing = state.getValue(FACING);
        return facing == EnumFacing.UP ? COLLISION_BOXES_BOTTOM : (facing == EnumFacing.DOWN ? COLLISION_BOXES_TOP : COLLISION_BOXES_SIDES[facing.getHorizontalIndex()]);
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
