package com.mrcrayfish.furniture.blocks;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRangeHood extends BlockFurniture
{
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public static final AxisAlignedBB[] COLLISION_VENTILATION = new Bounds(0.5, 0.0, 0.2, 1.0, 1.0, 0.8).getRotatedBounds();
    public static final AxisAlignedBB COLLISION_BASE = new AxisAlignedBB(0, 0, 0, 1, 4 * 0.0625, 1);

    public BlockRangeHood(Material material, boolean powered)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.METAL);
        if(powered) this.setLightLevel(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DOWN, false));
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
        EnumFacing facing = state.getValue(FACING);
        List<AxisAlignedBB> boxes = new ArrayList<>();
        boxes.add(COLLISION_VENTILATION[facing.getHorizontalIndex()]);
        if(!state.getValue(DOWN))
        {
            boxes.add(COLLISION_BASE);
        }
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
