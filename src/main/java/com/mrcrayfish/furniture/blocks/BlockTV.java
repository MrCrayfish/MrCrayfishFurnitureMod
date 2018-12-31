package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTV extends BlockAbstractTV
{
    private static final AxisAlignedBB[] BORDER_1 = new Bounds(1.5, 0, 1.5, 9.5, 1.5, 14.5).getRotatedBounds();
    private static final AxisAlignedBB[] BORDER_2 = new Bounds(1.5, 11.5, 1.5, 9.5, 13, 14.5).getRotatedBounds();
    private static final AxisAlignedBB[] BORDER_3 = new Bounds(1.5, 1.5, 1.5, 9.5, 11.5, 3).getRotatedBounds();
    private static final AxisAlignedBB[] BORDER_4 = new Bounds(1.5, 1.5, 13, 9.5, 11.5, 14.5).getRotatedBounds();
    private static final AxisAlignedBB[] SCREEN = new Bounds(2, 1.6, 3, 6.8, 11.6, 13).getRotatedBounds();
    private static final AxisAlignedBB[] BACK = new Bounds(8, 0.5, 2.5, 14.4, 12.2, 13.5).getRotatedBounds();
    private static final AxisAlignedBB[] ANTENNA_BASE = new Bounds(3.5, 13, 6, 5.5, 13.5, 10).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_1 = new Bounds(1.1, 0.4, 12, 1.5, 1.2, 12.8).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_2 = new Bounds(1.1, 0.4, 10.8, 1.5, 1.2, 11.6).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BORDER_1, BORDER_2, BORDER_3, BORDER_4, SCREEN, BACK);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_PROTRUSIONS = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, ANTENNA_BASE, BUTTON_1, BUTTON_2);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_PROTRUSIONS);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES_BODY);

    public BlockTV(Material material)
    {
        super(material, 10, 10, 1.5, -6.1);
        this.setSoundType(SoundType.METAL);
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
}
