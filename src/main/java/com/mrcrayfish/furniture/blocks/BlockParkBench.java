package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.SeatUtil;
import com.mrcrayfish.furniture.util.StateHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockParkBench extends BlockFurniture
{
    public static final PropertyEnum<BlockModernCouch.CouchType> TYPE = PropertyEnum.create("type", BlockModernCouch.CouchType.class);

    public static final AxisAlignedBB BOUNDING_BOX = new Bounds(0, 0, 0, 16, 18, 16).toAABB();
    public static final AxisAlignedBB CHAIR_SEAT = new Bounds(0, 0, 0, 16, 9, 16).toAABB();
    public static final AxisAlignedBB[] CHAIR_BACKREST = new Bounds(11, 9, 0, 16, 18, 16).getRotatedBounds();

    public BlockParkBench(String id)
    {
        super(Material.WOOD);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, BlockModernCouch.CouchType.BOTH));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, CHAIR_SEAT);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, CHAIR_BACKREST[state.getValue(FACING).getHorizontalIndex()]);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        boolean left = false;
        boolean right = false;

        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) instanceof BlockParkBench)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) == StateHelper.Direction.DOWN)
            {
                left = true;
            }
        }
        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) instanceof BlockParkBench)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) == StateHelper.Direction.DOWN)
            {
                right = true;
            }
        }
        if(left && !right)
        {
            return state.withProperty(TYPE, BlockModernCouch.CouchType.LEFT);
        }
        else if(!left && right)
        {
            return state.withProperty(TYPE, BlockModernCouch.CouchType.RIGHT);
        }
        else if(!left && !right)
        {
            return state.withProperty(TYPE, BlockModernCouch.CouchType.BOTH);
        }
        return state.withProperty(TYPE, BlockModernCouch.CouchType.NONE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 6 * 0.0625F);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }
}
