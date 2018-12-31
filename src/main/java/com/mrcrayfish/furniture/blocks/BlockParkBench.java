package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.blocks.BlockModernCouch.CouchType;
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

/**
 * Author: MrCrayfish
 */
public class BlockParkBench extends BlockFurniture
{
    public static final PropertyEnum<BlockModernCouch.CouchType> TYPE = PropertyEnum.create("type", BlockModernCouch.CouchType.class);

    private static final AxisAlignedBB[] SEAT_FRONT = new Bounds(0, 8.3, 11, 16, 9.3, 15).getRotatedBounds();
    private static final AxisAlignedBB[] SEAT_BACK = new Bounds(0, 8.3, 6, 16, 9.3, 10).getRotatedBounds();
    private static final AxisAlignedBB[] BACKREST_BOTTOM = new Bounds(0, 9.5, 3.3, 16, 13.5, 5.7).getRotatedBounds();
    private static final AxisAlignedBB[] BACKREST_TOP = new Bounds(0, 14.1, 1.4, 16, 18.1, 3.8).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(SEAT_FRONT, SEAT_BACK, BACKREST_BOTTOM, BACKREST_TOP);

    private static final AxisAlignedBB[] LEG_BACK_ = new Bounds(1, 0, 3, 3, 9.5, 5).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_FRONT = new Bounds(1, 0, 12, 3, 6.3, 14).getRotatedBounds();
    private static final AxisAlignedBB[] SUPPORT_TOP = new Bounds(1, 6.3, 5, 3, 8.3, 15).getRotatedBounds();
    private static final AxisAlignedBB[] NUT_FRONT = new Bounds(1.5, 9.3, 12.5, 2.5, 9.6, 13.5).getRotatedBounds();
    private static final AxisAlignedBB[] NUT_BACK = new Bounds(1.5, 9.3, 7.5, 2.5, 9.6, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] SUPPORT_BACK_BOTTOM = new Bounds(1.01, 9, 1.4, 2.99, 14.1, 3.4).getRotatedBounds();
    private static final AxisAlignedBB[] SUPPORT_BACK_TOP = new Bounds(1.01, 12.1, -0.3, 2.99, 17.2, 1.7).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_LEG_LEFT = Bounds.getRotatedBoundLists(LEG_BACK_, LEG_FRONT, SUPPORT_TOP, NUT_FRONT, NUT_BACK, SUPPORT_BACK_BOTTOM, SUPPORT_BACK_TOP);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_LEG_RIGHT = Bounds.transformBoxListsHorizontal(EnumFacing.Axis.Z, 12, COLLISION_BOXES_LEG_LEFT);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTH = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_LEG_LEFT, COLLISION_BOXES_LEG_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_RIGHT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_LEG_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_LEFT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_LEG_LEFT);

    private static final AxisAlignedBB[] BOUNDING_BOX_NONE = Bounds.getBoundingBoxes(COLLISION_BOXES_BODY);
    private static final AxisAlignedBB[] BOUNDING_BOX_BOTH = Bounds.getBoundingBoxes(COLLISION_BOXES_RIGHT);

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
        state = getActualState(state, source, pos);
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(TYPE) == CouchType.NONE ? BOUNDING_BOX_NONE[i] : BOUNDING_BOX_BOTH[i];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = getActualState(state, world, pos);

        int i = state.getValue(FACING).getHorizontalIndex();
        switch (state.getValue(TYPE))
        {
            case LEFT: return COLLISION_BOXES_LEFT[i];
            case RIGHT: return COLLISION_BOXES_RIGHT[i];
            case NONE: return COLLISION_BOXES_BODY[i];
            default: return COLLISION_BOXES_BOTH[i];
        }
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
