package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.tileentity.TileEntityMirror;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMirror extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] FRAME_TOP = new Bounds(0, 15, 0, 16, 16, 1.25).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_BOTTOM = new Bounds(0, 0, 0, 16, 1, 1.25).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_LEFT = new Bounds(0, 1, 0, 1, 15, 1.25).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_RIGHT = new Bounds(15, 1, 0, 16, 15, 1.25).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_BACKING = new Bounds(1, 1, 0, 15, 15, 1).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(FRAME_TOP, FRAME_BOTTOM, FRAME_LEFT, FRAME_RIGHT, FRAME_BACKING);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    public BlockMirror(Material material)
    {
        super(material);
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
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMirror();
    }
}
