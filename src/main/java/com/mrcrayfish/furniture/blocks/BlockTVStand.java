package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityTVStand;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockTVStand extends BlockFurnitureTile
{
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    private static final AxisAlignedBB[] TOP = new Bounds(-0, 14.5, 1, 16, 16, 15).getRotatedBounds();

    private static final AxisAlignedBB[] SHELF_BOTH = new Bounds(0.5, 9, 3.5, 15.5, 10.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BACK_BOTH = new Bounds(0.5, 5, 2, 15.5, 14.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_BOTH = new Bounds(0, 3.5, 2, 16, 5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] LEFT_BOTH = new Bounds(0, 5, 2, 0.5, 14.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] RIGHT_BOTH = new Bounds(15.5, 5, 2, 16, 14.5, 14).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTH = Bounds.getRotatedBoundLists(TOP, SHELF_BOTH, BACK_BOTH, BASE_BOTH, LEFT_BOTH, RIGHT_BOTH);
    private static final AxisAlignedBB[] BOUNDING_BOX_BOTH = Bounds.getBoundingBoxes(COLLISION_BOXES_BOTH);

    private static final AxisAlignedBB[] LEG_1_NONE = new Bounds(1, 0, 2, 2.5, 3.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_2_NONE = new Bounds(13.5, 0, 2, 15, 3.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_3_NONE = new Bounds(13.5, 0, 12.5, 15, 3.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_4_NONE = new Bounds(1, 0, 12.5, 2.5, 3.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] LEFT_NONE = new Bounds(1, 5, 2, 2.5, 14.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] RIGHT_NONE = new Bounds(13.5, 5, 2, 15, 14.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] SHELF_NONE = new Bounds(2.5, 9, 3.5, 13.5, 10.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BACK_NONE = new Bounds(2.5, 5, 2, 13.5, 14.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_NONE = new Bounds(1, 3.5, 2, 15, 5, 14).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_NONE = Bounds.getRotatedBoundLists(TOP, LEG_1_NONE, LEG_2_NONE, LEG_3_NONE, LEG_4_NONE, LEFT_NONE, RIGHT_NONE, SHELF_NONE, BACK_NONE, BASE_NONE);
    private static final AxisAlignedBB[] BOUNDING_BOX_NONE = Bounds.getBoundingBoxes(COLLISION_BOXES_NONE);
 
    private static final AxisAlignedBB[] LEG_1_LEFT = new Bounds(1, 0, 2, 2.5, 3.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_4_LEFT = new Bounds(1, 0, 12.5, 2.5, 3.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BODY_LEFT = new Bounds(1, 3.5, 2, 16, 14.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_LEFT = new Bounds(2.5, 5, 13.5, 15, 14, 14.5).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_1_LEFT = new Bounds(13, 7, 14.5, 14, 8, 15).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_2_LEFT = new Bounds(13, 11, 14.5, 14, 12, 15).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_3_LEFT = new Bounds(13, 7, 15, 14, 12, 15.5).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_LEFT = Bounds.getRotatedBoundLists(TOP, LEG_1_LEFT, LEG_4_LEFT, BODY_LEFT, DOOR_LEFT, HANDLE_1_LEFT, HANDLE_2_LEFT, HANDLE_3_LEFT);

    private static final AxisAlignedBB[] LEG_2_RIGHT = new Bounds(13.5, 0, 2, 15, 3.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_3_RIGHT = new Bounds(13.5, 0, 12.5, 15, 3.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BODY_RIGHT = new Bounds(0, 3.5, 2, 15, 14.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_RIGHT = new Bounds(1, 5, 13.5, 13.5, 14, 14.5).getRotatedBounds();
    private static final AxisAlignedBB[] _HANDLE_1_RIGHT = new Bounds(2, 7, 14.5, 3, 8, 15).getRotatedBounds();
    private static final AxisAlignedBB[] _HANDLE_2_RIGHT = new Bounds(2, 11, 14.5, 3, 12, 15).getRotatedBounds();
    private static final AxisAlignedBB[] _HANDLE_3_RIGHT = new Bounds(2, 7, 15, 3, 12, 15.5).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_RIGHT = Bounds.getRotatedBoundLists(TOP, LEG_2_RIGHT, LEG_3_RIGHT, BODY_RIGHT, DOOR_RIGHT, _HANDLE_1_RIGHT, _HANDLE_2_RIGHT, _HANDLE_3_RIGHT);

    public BlockTVStand()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("tv_stand");
        this.setRegistryName("tv_stand");
        this.setHardness(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, Type.NONE));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(TYPE) == Type.BOTH ? BOUNDING_BOX_BOTH[i] : BOUNDING_BOX_NONE[i];
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
            case BOTH: return COLLISION_BOXES_BOTH[i];
            default: return COLLISION_BOXES_NONE[i];
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityTVStand)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        boolean left = false;
        boolean right = false;

        IBlockState rightState = worldIn.getBlockState(pos.offset(facing.rotateY()));
        if(rightState.getBlock() == this && rightState.getValue(FACING) == facing)
        {
            right = true;
        }
        IBlockState leftState = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
        if(leftState.getBlock() == this && leftState.getValue(FACING) == facing)
        {
            left = true;
        }

        if(left && right)
        {
            state = state.withProperty(TYPE, Type.BOTH);
        }
        else if(left)
        {
            state = state.withProperty(TYPE, Type.RIGHT);
        }
        else if(right)
        {
            state = state.withProperty(TYPE, Type.LEFT);
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

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTVStand();
    }

    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return true;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    public enum Type implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH;

        @Override
        public String getName()
        {
            return name().toLowerCase(Locale.US);
        }
    }
}
