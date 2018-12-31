package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockModernTV extends BlockAbstractTV
{
    public static final PropertyBool MOUNTED = PropertyBool.create("mounted");

    private static final AxisAlignedBB[] BASE = new Bounds(2, 0, 5, 14, 1, 11).getRotatedBounds();
    private static final AxisAlignedBB[] STAND = new Bounds(6, 1, 6.8, 10, 4, 8.3).getRotatedBounds();

    private static final AxisAlignedBB[] MOUNT_LEFT = new Bounds(0, 5, 0, 1, 15, 1.5).getRotatedBounds();
    private static final AxisAlignedBB[] MOUNT_RIGHT = new Bounds(15, 5, 0, 16, 15, 1.5).getRotatedBounds();
    private static final AxisAlignedBB[] MOUNT_CENTER = new Bounds(1, 9.5, 0.3, 15, 10.5, 1.3).getRotatedBounds();

    private static final AxisAlignedBB[] FRAME_1 = new Bounds(-4, 3, 7.5, 20, 4, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_3 = new Bounds(-4, 16, 7.5, 20, 17, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] CONNECTION = new Bounds(5.5, 5, 6.7, 10.5, 7, 7.3).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_5 = new Bounds(-4, 4, 7.5, -3, 16, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_6 = new Bounds(19, 4, 7.5, 20, 16, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] BACK_COVER_1 = new Bounds(-3, 4, 6.5, 19, 16, 8.3).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_TV = Bounds.getRotatedBoundLists(FRAME_1, FRAME_3, CONNECTION, FRAME_5, FRAME_6, BACK_COVER_1);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_TV, Bounds.getRotatedBoundLists(BASE, STAND));
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES_TV);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_TV_MOUNTED = Bounds.transformBoxListsHorizontal(EnumFacing.Axis.X, 5, COLLISION_BOXES_TV);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_MOUNTED = Bounds.combineBoxLists(COLLISION_BOXES_TV_MOUNTED, Bounds.getRotatedBoundLists(MOUNT_LEFT, MOUNT_RIGHT, MOUNT_CENTER));
    private static final AxisAlignedBB[] BOUNDING_BOX_MOUNTED = Bounds.getBoundingBoxes(COLLISION_BOXES_TV_MOUNTED);

    public BlockModernTV()
    {
        super(Material.ANVIL, 22, 12, 4, -0.35);
        this.setUnlocalizedName("modern_tv");
        this.setRegistryName("modern_tv");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MOUNTED, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(MOUNTED) ? BOUNDING_BOX_MOUNTED[i] : BOUNDING_BOX[i];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(MOUNTED) ? COLLISION_BOXES_MOUNTED[i] : COLLISION_BOXES[i];
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        if(facing.getHorizontalIndex() != -1)
        {
            state = state.withProperty(MOUNTED, true);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(MOUNTED) ? 4 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(MOUNTED, meta / 4 > 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, MOUNTED);
    }

    @Override
    public double getScreenZOffset(IBlockState state)
    {
        if(state.getValue(MOUNTED))
        {
            return 4.65;
        }
        return super.getScreenZOffset(state);
    }
}
