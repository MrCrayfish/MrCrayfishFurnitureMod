package com.mrcrayfish.furniture.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockRangeHood extends BlockFurniture
{
    public static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
    public static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
    public static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
    public static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5, 0.0, 0.2, 1.0, 1.0, 0.8);
    public static final AxisAlignedBB[] COLLISION_BOX = {COLLISION_BOX_SOUTH, COLLISION_BOX_WEST, COLLISION_BOX_NORTH, COLLISION_BOX_EAST};
    public static final AxisAlignedBB COLLISION_BOTTOM = new AxisAlignedBB(0, 0, 0, 1, 4 * 0.0625, 1);

    public BlockRangeHood(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.METAL);
        this.setLightLevel(0.5F);
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

    protected List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        EnumFacing facing = state.getValue(FACING);

        boxes.add(COLLISION_BOTTOM);
        boxes.add(COLLISION_BOX[facing.getHorizontalIndex()]);

        return boxes;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        List<AxisAlignedBB> list = getCollisionBoxList(this.getActualState(state, world, pos), world, pos);
        for(AxisAlignedBB box : list)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for(AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, world, pos), world, pos))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for(RayTraceResult raytraceresult : list)
        {
            if(raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if(d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }
}
