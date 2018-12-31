package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.SeatUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGrandChair extends BlockFurniture
{
	// Top
	private static final AxisAlignedBB[] SIDE_BOTTOM_LEFT_TOP = new Bounds(0, 0, 1, 2, 4, 5).getRotatedBounds();
	private static final AxisAlignedBB[] SIDE_TOP_LEFT = new Bounds(0, 4, 1, 2, 12, 6).getRotatedBounds();
	private static final AxisAlignedBB[] SIDE_TOP_RIGHT = new Bounds(14, 4, 1, 16, 12, 6).getRotatedBounds();
	private static final AxisAlignedBB[] SIDE_BOTTOM_RIGHT_TOP = new Bounds(14, 0, 1, 16, 4, 5).getRotatedBounds();
	private static final AxisAlignedBB[] BACK_REST_2 = new Bounds(2, 0, 1, 14, 12, 2).getRotatedBounds();
	private static final AxisAlignedBB[] BACK_REST_BACK_2 = new Bounds(2, 0, 0, 14, 12, 1).getRotatedBounds();
	private static final List<AxisAlignedBB>[] COLLISION_BOXES_TOP = Bounds.getRotatedBoundLists(SIDE_BOTTOM_LEFT_TOP, SIDE_TOP_LEFT, SIDE_TOP_RIGHT, SIDE_BOTTOM_RIGHT_TOP, BACK_REST_2, BACK_REST_BACK_2);

    //Bottom
	private static final AxisAlignedBB[] LEG_LEFT_BACK = new Bounds(2, 0, 2, 4, 5, 4).getRotatedBounds();
	private static final AxisAlignedBB[] LEG_RIGHT_BACK = new Bounds(12, 0, 2, 14, 5, 4).getRotatedBounds();
	private static final AxisAlignedBB[] LEG_RIGHT_FRONT = new Bounds(12, 0, 12, 14, 5, 14).getRotatedBounds();
	private static final AxisAlignedBB[] LEG_LEFT_FRONT = new Bounds(2, 0, 12, 4, 5, 14).getRotatedBounds();
	private static final AxisAlignedBB[] BUM_REST = new Bounds(2, 7, 1, 14, 8, 15).getRotatedBounds();
	private static final AxisAlignedBB[] BACK_REST = new Bounds(2, 7, 1, 14, 16, 2).getRotatedBounds();
	private static final AxisAlignedBB[] ARM_REST_LEFT = new Bounds(0, 6, 1, 2, 12, 15).getRotatedBounds();
	private static final AxisAlignedBB[] ARM_REST_RIGHT = new Bounds(14, 6, 1, 16, 12, 15).getRotatedBounds();
	private static final AxisAlignedBB[] SIDE_BOTTOM_LEFT_BOTTOM = new Bounds(0, 12, 1, 2, 16, 5).getRotatedBounds();
	private static final AxisAlignedBB[] SIDE_BOTTOM_RIGHT_BOTTOM = new Bounds(14, 12, 1, 16, 16, 5).getRotatedBounds();
	private static final AxisAlignedBB[] BUM_REST_SUPPORT = new Bounds(1, 5, 1, 15, 7, 15).getRotatedBounds();
	private static final AxisAlignedBB[] BACK_REST_BACK = new Bounds(2, 7, 0, 14, 16, 1).getRotatedBounds();
	private static final AxisAlignedBB[] FRONT_WOOL = new Bounds(2, 6, 14.5, 14, 7.5, 15.5).getRotatedBounds();

	private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTTOM_BODY = Bounds.getRotatedBoundLists(LEG_LEFT_BACK, LEG_RIGHT_BACK, LEG_RIGHT_FRONT, LEG_LEFT_FRONT, BUM_REST, BACK_REST, ARM_REST_LEFT, ARM_REST_RIGHT, SIDE_BOTTOM_LEFT_BOTTOM, SIDE_BOTTOM_RIGHT_BOTTOM, BUM_REST_SUPPORT, BACK_REST_BACK);
	private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTTOM = Bounds.combineBoxLists(Bounds.getRotatedBoundLists(FRONT_WOOL), COLLISION_BOXES_BOTTOM_BODY);
	private static final List<AxisAlignedBB>[] COLLISION_BOXES_TOP_TRANSLATED = Bounds.transformBoxListsVertical(1, COLLISION_BOXES_TOP);
	private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES_BOTTOM_BODY, COLLISION_BOXES_TOP_TRANSLATED);

    public BlockGrandChair(Material materialIn, boolean top)
    {
        super(materialIn);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
        if(top) this.setCreativeTab(null);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()].offset(0, this == FurnitureBlocks.GRAND_CHAIR_TOP ? -1 : 0, 0);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (entity instanceof EntitySeat)
            return EMPTY;

        int i = state.getValue(FACING).getHorizontalIndex();
        return this == FurnitureBlocks.GRAND_CHAIR_BOTTOM ? COLLISION_BOXES_BOTTOM[i] : COLLISION_BOXES_TOP[i];
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) placer;
            Triggers.trigger(Triggers.PLACE_CHAIR, player);
            Triggers.trigger(Triggers.PLACE_CHAIR_OR_TABLE, player);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(this == FurnitureBlocks.GRAND_CHAIR_BOTTOM)
        {
            if(worldIn.getBlockState(pos.up()).getBlock() == FurnitureBlocks.GRAND_CHAIR_TOP)
            {
                worldIn.destroyBlock(pos.up(), false);
            }
        }
        else
        {
            if(worldIn.getBlockState(pos.down()).getBlock() == FurnitureBlocks.GRAND_CHAIR_BOTTOM)
            {
                worldIn.destroyBlock(pos.down(), false);
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if(this == FurnitureBlocks.GRAND_CHAIR_BOTTOM)
        {
            world.setBlockState(pos.up(), FurnitureBlocks.GRAND_CHAIR_TOP.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()));
        }
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(this == FurnitureBlocks.GRAND_CHAIR_BOTTOM && !playerIn.isSneaking())
        {
            if(SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 6 * 0.0625))
            {
                worldIn.updateComparatorOutputLevel(pos, this);
                return true;
            }
        }
        else
        {
            worldIn.getBlockState(pos.down()).getBlock().onBlockActivated(worldIn, pos.down(), state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return SeatUtil.isSomeoneSitting(world, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.GRAND_CHAIR_BOTTOM).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.GRAND_CHAIR_BOTTOM);
    }
}
