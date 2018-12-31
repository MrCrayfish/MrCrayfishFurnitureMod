package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class BlockRangeHood extends BlockFurniture
{
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private static final AxisAlignedBB[] SHROUD = new Bounds(0, 0.4, 0, 16, 4, 16).getRotatedBounds();
    private static final AxisAlignedBB[] VENT = new Bounds(8, 4, 3.2, 16, 16, 12.8).getRotatedBounds();
    private static final AxisAlignedBB[] INTAKE_1 = new Bounds(1.6, 0, 2, 7, 0.8, 3).getRotatedBounds();
    private static final AxisAlignedBB[] INTAKE_2 = new Bounds(1.6, 0, 4, 7, 0.8, 5).getRotatedBounds();
    private static final AxisAlignedBB[] INTAKE_3 = new Bounds(1.6, 0, 6, 7, 0.8, 7).getRotatedBounds();
    private static final AxisAlignedBB[] INTAKE_4 = new Bounds(1.6, 0, 9, 7, 0.8, 10).getRotatedBounds();
    private static final AxisAlignedBB[] INTAKE_5 = new Bounds(1.6, 0, 11, 7, 0.8, 12).getRotatedBounds();
    private static final AxisAlignedBB[] INTAKE_6 = new Bounds(1.6, 0, 13, 7, 0.8, 14).getRotatedBounds();
    private static final AxisAlignedBB[] LIGHT = new Bounds(9, 0, 1.6, 14.4, 0.8, 14.4).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, SHROUD, VENT, INTAKE_1, INTAKE_2, INTAKE_3, INTAKE_4, INTAKE_5, INTAKE_6, LIGHT);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    private static final AxisAlignedBB[] VENT_TOP = new Bounds(8, 0, 3.2, 16, 16, 12.8).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_VENT = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, VENT_TOP);
    private static final AxisAlignedBB[] BOUNDING_BOX_VENT = Bounds.getBoundingBoxes(COLLISION_BOXES_VENT);

    public BlockRangeHood(Material material, boolean powered)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.METAL);
        if(powered) this.setLightLevel(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DOWN, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(DOWN) ? BOUNDING_BOX_VENT[i] : BOUNDING_BOX[i];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = state.getActualState(world, pos);

        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(DOWN) ? COLLISION_BOXES_VENT[i] : COLLISION_BOXES[i];
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(this == FurnitureBlocks.RANGE_HOOD)
        {
            worldIn.setBlockState(pos, FurnitureBlocks.RANGE_HOOD_POWERED.getDefaultState().withProperty(FACING, state.getValue(FACING)));
        }
        else if(this == FurnitureBlocks.RANGE_HOOD_POWERED)
        {
            worldIn.setBlockState(pos, FurnitureBlocks.RANGE_HOOD.getDefaultState().withProperty(FACING, state.getValue(FACING)));
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        IBlockState downState = worldIn.getBlockState(pos.down());
        if(downState.getBlock() instanceof BlockRangeHood && downState.getValue(FACING) == state.getValue(FACING))
        {
            state = state.withProperty(DOWN, true);
        }
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, DOWN);
    }
}
