package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockCoffeeTable extends ModBlock implements IRayTrace
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
    public static final AxisAlignedBB TABLE_TOP = new AxisAlignedBB(0, 6.5 * 0.0625, 0, 1, 8 * 0.0625, 1);
    public static final AxisAlignedBB SOUTH_WEST_LEG = new AxisAlignedBB(0, 0, 14.4 * 0.0625, 1.6 * 0.0625, 6.5 * 0.0625, 1);
    public static final AxisAlignedBB NORTH_WEST_LEG = new AxisAlignedBB(0, 0, 0, 1.6 * 0.0625, 6.5 * 0.0625, 1.6 * 0.0625);
    public static final AxisAlignedBB SOUTH_EAST_LEG = new AxisAlignedBB(14.4 * 0.0625, 0, 14.4 * 0.0625, 1, 6.5 * 0.0625, 1);
    public static final AxisAlignedBB NORTH_EAST_LEG = new AxisAlignedBB(14.4 * 0.0625, 0, 0, 1, 6.5 * 0.0625, 1.6 * 0.0625);

    public BlockCoffeeTable(Material material, SoundType sound, String name)
    {
        super(material, name);
        this.setSoundType(sound);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(NORTH, isCoffeeTable(world, pos.north())).withProperty(EAST, isCoffeeTable(world, pos.east())).withProperty(SOUTH, isCoffeeTable(world, pos.south())).withProperty(WEST, isCoffeeTable(world, pos.west()));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
    }

    public boolean isCoffeeTable(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == this;
    }

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
        boolean north = state.getValue(NORTH);
        boolean south = state.getValue(SOUTH);
        boolean east = state.getValue(EAST);
        boolean west = state.getValue(WEST);
        boxes.add(TABLE_TOP);
        if(!north)
        {
            if(!west) boxes.add(NORTH_WEST_LEG);
            if(!east) boxes.add(NORTH_EAST_LEG);
        }
        if(!south)
        {
            if(!west) boxes.add(SOUTH_WEST_LEG);
            if(!east) boxes.add(SOUTH_EAST_LEG);
        }
	}
	
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
