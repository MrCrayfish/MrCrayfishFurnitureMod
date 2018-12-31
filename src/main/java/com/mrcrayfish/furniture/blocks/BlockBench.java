package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SeatUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class BlockBench extends BlockFurniture
{
    public static final PropertyBool LEFT = PropertyBool.create("left");
    public static final PropertyBool RIGHT = PropertyBool.create("right");

    private static final AxisAlignedBB[] BACK_LEFT_LEG = new Bounds(1.2, 0, 2.2, 2.7, 8, 3.7).getRotatedBounds();
    private static final AxisAlignedBB[] BACK_RIGHT_LEG = new Bounds(13.3, 0, 2.2, 14.8, 8, 3.7).getRotatedBounds();
    private static final AxisAlignedBB[] FRONT_RIGHT_LEG = new Bounds(13.3, 0, 12.3, 14.8, 8, 13.8).getRotatedBounds();
    private static final AxisAlignedBB[] FRONT_LEFT_LEG = new Bounds(1.2, 0, 12.3, 2.7, 8, 13.8).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_LEFT_BEAM = new Bounds(1, 8, 2, 3, 9, 14).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_RIGHT_BEAM = new Bounds(13, 8, 2, 15, 9, 14).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_BACK_BEAM = new Bounds(3, 8, 2, 13, 9, 4).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_FRONT_BEAM = new Bounds(3, 8, 12, 13, 9, 14).getRotatedBounds();
    private static final AxisAlignedBB[] TOP = new Bounds(3, 8.3, 4, 13, 8.7, 12).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(BACK_LEFT_LEG, BACK_RIGHT_LEG, FRONT_RIGHT_LEG, FRONT_LEFT_LEG, TOP_LEFT_BEAM, TOP_RIGHT_BEAM, TOP_BACK_BEAM, TOP_FRONT_BEAM, TOP);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    private static final AxisAlignedBB[] TOP_BACK_BEAM_END = new Bounds(3, 8, 2, 16, 9, 4).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_FRONT_BEAM_END = new Bounds(3, 8, 12, 16, 9, 14).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_END = new Bounds(3, 8.3, 4, 16, 8.7, 12).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_END = Bounds.getRotatedBoundLists(BACK_LEFT_LEG, FRONT_LEFT_LEG, TOP_LEFT_BEAM, TOP_BACK_BEAM_END, TOP_FRONT_BEAM_END, TOP_END);
    private static final AxisAlignedBB[] BOUNDING_BOX_END = Bounds.getBoundingBoxes(COLLISION_BOXES_END);

    private static final AxisAlignedBB[] TOP_FRONT_BEAM_CENTER = new Bounds(0, 8, 12, 16, 9, 14).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_BACK_BEAM_CENTER = new Bounds(0, 8, 2, 16, 9, 4).getRotatedBounds();
    private static final AxisAlignedBB[] TOP_CENTER = new Bounds(0, 8.3, 4, 16, 8.7, 12).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_CENTER = Bounds.getRotatedBoundLists(TOP_FRONT_BEAM_CENTER, TOP_BACK_BEAM_CENTER, TOP_CENTER);
    private static final AxisAlignedBB[] BOUNDING_BOX_CENTER = Bounds.getBoundingBoxes(COLLISION_BOXES_CENTER);

    public BlockBench(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, false).withProperty(RIGHT, false));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return getBoxes(BOUNDING_BOX, BOUNDING_BOX_CENTER, BOUNDING_BOX_END, state, source, pos, false);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return getBoxes(COLLISION_BOXES, COLLISION_BOXES_CENTER, COLLISION_BOXES_END, state, world, pos, isActualState);
    }

    protected <T> T getBoxes(T[] solitary, T[] center, T[] end, IBlockState state, IBlockAccess source, BlockPos pos, boolean isActualState)
    {
        if (!isActualState)
            state = state.getActualState(source, pos);

        EnumFacing facing = state.getValue(FACING);
        int i = facing.getHorizontalIndex();
        boolean left = state.getValue(LEFT);
        boolean right = state.getValue(RIGHT);
        if (left == right)
            return left && right ? center[i] : solitary[i];

        if (facing.getAxisDirection() == AxisDirection.POSITIVE ? right : left)
            i = facing.getOpposite().getHorizontalIndex();

        return end[i];
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        if(facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
        {
            IBlockState right = worldIn.getBlockState(pos.east());
            IBlockState left = worldIn.getBlockState(pos.west());

            if(worldIn.getBlockState(pos.east()).getBlock() == this && (right.getValue(FACING) == EnumFacing.NORTH | right.getValue(FACING) == EnumFacing.SOUTH))
            {
                state = state.withProperty(RIGHT, true);
            }
            if(worldIn.getBlockState(pos.west()).getBlock() == this && (left.getValue(FACING) == EnumFacing.NORTH | left.getValue(FACING) == EnumFacing.SOUTH))
            {
                state = state.withProperty(LEFT, true);
            }

            return state;
        }

        if(facing == EnumFacing.EAST || facing == EnumFacing.WEST)
        {
            IBlockState right = worldIn.getBlockState(pos.north());
            IBlockState left = worldIn.getBlockState(pos.south());

            if(right.getBlock() == this && (right.getValue(FACING) == EnumFacing.EAST | right.getValue(FACING) == EnumFacing.WEST))
            {
                state = state.withProperty(RIGHT, true);
            }
            if(left.getBlock() == this && (left.getValue(FACING) == EnumFacing.EAST | left.getValue(FACING) == EnumFacing.WEST))
            {
                state = state.withProperty(LEFT, true);
            }

            return state;
        }

        return state;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0.4);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LEFT, RIGHT);
    }
}
