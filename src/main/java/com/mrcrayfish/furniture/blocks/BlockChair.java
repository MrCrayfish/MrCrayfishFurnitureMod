package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.SeatUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChair extends BlockFurniture
{
    private static final AxisAlignedBB[] LEG_3 = new Bounds(1.6, 0, 1.6, 3.2, 8, 3.2).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_2 = new Bounds(12.8, 0, 12.8, 14.4, 8, 14.4).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_1 = new Bounds(12.8, 0, 1.6, 14.4, 8, 3.2).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_4 = new Bounds(1.6, 0, 12.8, 3.2, 8, 14.4).getRotatedBounds();
    private static final AxisAlignedBB[] BACKREST_REGULAR = new Bounds(12.8, 9.6, 1.6, 14.4, 19.2, 14.4).getRotatedBounds();
    private static final AxisAlignedBB[] SEAT = new Bounds(1.6, 8, 1.6, 14.4, 9.6, 14.4).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_REGULAR = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, LEG_3, LEG_2, LEG_1, LEG_4, BACKREST_REGULAR, SEAT);
    private static final AxisAlignedBB[] BOUNDING_BOX_REGULAR = Bounds.getBoundingBoxes(COLLISION_BOXES_REGULAR);

    private static final AxisAlignedBB[] BASE_FRONT_LEFT = new Bounds(2, 0, 2, 3.5, 8, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_BOTTOM_RIGHT = new Bounds(3.5, 0, 12.5, 12.5, 1.5, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_BOTTOM_LEFT = new Bounds(3.5, 0, 2, 12.5, 1.5, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_FRONT_RIGHT = new Bounds(2, 0, 12.5, 3.5, 8, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BACKREST_MODERN = new Bounds(11.5, 9.5, 2.1, 13, 19.5, 13.9).getRotatedBounds();
    private static final AxisAlignedBB[] SEAT_MODERN = new Bounds(2, 8, 2.1, 13, 9.5, 13.9).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_LEFT = new Bounds(3.5, 6.5, 2, 11.5, 8, 3.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_RIGHT = new Bounds(3.5, 6.5, 12.5, 11.5, 8, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_BACK = new Bounds(11.5, 6.5, 2, 13, 8, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_FRONT = new Bounds(2, 7, 3.5, 3.5, 8, 12.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE_BOTTOM_BACK = new Bounds(12.5, 0, 2, 14, 1.5, 14).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_MODERN = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BASE_FRONT_LEFT, BASE_BOTTOM_RIGHT, BASE_BOTTOM_LEFT, BASE_FRONT_RIGHT, BACKREST_MODERN, SEAT_MODERN, BASE_LEFT, BASE_RIGHT, BASE_BACK, BASE_FRONT, BASE_BOTTOM_BACK);
    private static final AxisAlignedBB[] BOUNDING_BOX_MODERN = Bounds.getBoundingBoxes(COLLISION_BOXES_MODERN);

    public BlockChair(Material material, SoundType sound)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(sound);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        int i = state.getValue(FACING).getHorizontalIndex();
        return this == FurnitureBlocks.MODERN_CHAIR ? BOUNDING_BOX_MODERN[i] : BOUNDING_BOX_REGULAR[i];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (entity instanceof EntitySeat)
            return EMPTY;

        int i = state.getValue(FACING).getHorizontalIndex();
        return this == FurnitureBlocks.MODERN_CHAIR ? COLLISION_BOXES_MODERN[i] : COLLISION_BOXES_REGULAR[i];
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!playerIn.isSneaking())
        {
            if(SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 6 * 0.0625))
            {
                worldIn.updateComparatorOutputLevel(pos, this);
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return SeatUtil.isSomeoneSitting(worldIn, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
    }
}
