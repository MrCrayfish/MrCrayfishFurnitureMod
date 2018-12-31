package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMantelPiece extends BlockFurniture
{
    private static final AxisAlignedBB[] LEFT = new Bounds(-6, 0, 0, -2, 19, 5).getRotatedBounds();
    private static final AxisAlignedBB[] RIGHT = new Bounds(18, 0, 0, 22, 19, 5).getRotatedBounds();
    private static final AxisAlignedBB[] TOP = new Bounds(-7, 19, 0, 23, 22, 6).getRotatedBounds();
    private static final AxisAlignedBB[] CENTER = new Bounds(-2, 0, 0, 18, 19, 4).getRotatedBounds();
    private static final AxisAlignedBB[] LEFT_INNER = new Bounds(-2, 0, 0, 1, 16, 4).getRotatedBounds();
    private static final AxisAlignedBB[] RIGHT_INNER = new Bounds(15, 0, 0, 18, 16, 4).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_INNER = new Bounds(-2, 16, 0, 18, 19, 4).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(LEFT, RIGHT, TOP, LEFT_INNER, RIGHT_INNER, TOP_INNER);
    private static final List<AxisAlignedBB>[] RAYTRACE_BOXES = Bounds.getRotatedBoundLists(LEFT, RIGHT, TOP, CENTER);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    public BlockMantelPiece(Material materialIn)
    {
        super(materialIn);
        this.setHardness(1.0F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getRaytraceBoxes(IBlockState state, World world, BlockPos pos)
    {
        return RAYTRACE_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }
}
