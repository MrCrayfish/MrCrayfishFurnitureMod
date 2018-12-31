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

    private static final AxisAlignedBB[] STAIR_BOTTOM = new Bounds(0, 6, 8, 16, 8, 16).getRotatedBounds();
    private static final AxisAlignedBB[] STAIR_TOP = new Bounds(0, 14, 0, 16, 16, 8).getRotatedBounds();
    private static final AxisAlignedBB[] SUPPORT_BOTTOM = new Bounds(6.5, 0, 2.5, 9.5, 8, 13.5).getRotatedBounds();
    private static final AxisAlignedBB[] SUPPORT_TOP = new Bounds(6.5, 8, 2.5, 9.5, 14, 5.5).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(STAIR_BOTTOM, STAIR_TOP, SUPPORT_BOTTOM, SUPPORT_TOP);

    private static final AxisAlignedBB[] SUPPORT_TOP_CONNECTED = new Bounds(6.5, 8, -5.5, 9.5, 16, 5.5).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_CONNECTED = Bounds.getRotatedBoundLists(STAIR_BOTTOM, STAIR_TOP, SUPPORT_BOTTOM, SUPPORT_TOP_CONNECTED);
   
    public BlockModernStair()
    {
        super(Material.WOOD);
        this.setHardness(1.0F);
        this.setUnlocalizedName("modern_stair");
        this.setRegistryName("modern_stair");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, Type.NONE));
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = state.getActualState(world, pos);

        int i = state.getValue(FACING).getHorizontalIndex();
        Type type = state.getValue(TYPE);
        return type == Type.DOWN || type == Type.BOTH ? COLLISION_BOXES_CONNECTED[i] : COLLISION_BOXES[i];
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

