package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockModernStair extends BlockFurniture
{
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    private static final AxisAlignedBB[] BOTTOM_COLLISION_BOXES = new Bounds(0, 0, 0, 16, 8, 16).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_COLLISION_BOXES = new Bounds(8, 0, 0, 16, 16, 16).getRotatedBounds();

    public BlockModernStair()
    {
        super(Material.IRON);
        this.setHardness(1.0F);
        this.setUnlocalizedName("modern_stair");
        this.setRegistryName("modern_stair");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, Type.NONE));
        this.setHarvestLevel("pickaxe", 1); //stone pickaxe, remove this if you want
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        EnumFacing facing = state.getValue(FACING);
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOTTOM_COLLISION_BOXES[facing.getHorizontalIndex()]);
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, TOP_COLLISION_BOXES[facing.getHorizontalIndex()]);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        boolean up = false;
        boolean down = false;

        EnumFacing facing = state.getValue(FACING);
        IBlockState upState = worldIn.getBlockState(pos.up().offset(facing));
        if(upState.getBlock() == this && upState.getValue(FACING) == facing)
        {
            up = true;
        }
        IBlockState downState = worldIn.getBlockState(pos.down().offset(facing.getOpposite()));
        if(downState.getBlock() == this && downState.getValue(FACING) == facing)
        {
            down = true;
        }

        if(up && down)
        {
            state = state.withProperty(TYPE, Type.BOTH);
        }
        else if(up)
        {
            state = state.withProperty(TYPE, Type.DOWN);
        }
        else if(down)
        {
            state = state.withProperty(TYPE, Type.UP);
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

    protected List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        EnumFacing facing = state.getValue(FACING);
        boxes.add(BOTTOM_COLLISION_BOXES[facing.getHorizontalIndex()]);
        boxes.add(TOP_COLLISION_BOXES[facing.getHorizontalIndex()]);
        return boxes;
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for(AxisAlignedBB axisalignedbb : getCollisionBoxList(blockState, world, pos))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult result = null;
        double d1 = 0.0D;

        for(RayTraceResult raytraceresult : list)
        {
            if(raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);
                if(d0 > d1)
                {
                    result = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return result;
    }

    public enum Type implements IStringSerializable
    {
        NONE, UP, DOWN, BOTH;

        @Override
        public String getName()
        {
            return name().toLowerCase(Locale.US);
        }
    }
}

