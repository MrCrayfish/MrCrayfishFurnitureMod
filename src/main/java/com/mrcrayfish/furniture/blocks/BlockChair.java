package com.mrcrayfish.furniture.blocks;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SittableUtil;

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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockChair extends BlockFurniture implements IRayTrace
{
    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 1.2, 0.9);

    public static final AxisAlignedBB CHAIR_SEAT = new AxisAlignedBB(1.6 * 0.0625, 8 * 0.0625, 1.6 * 0.0625, 14.4 * 0.0625, 9.6 * 0.0625, 14.4 * 0.0625);
    public static final AxisAlignedBB CHAIR_BACKREST_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.825, 0.6, 0.1, 0.9, 1.2, 0.9);
    public static final AxisAlignedBB CHAIR_BACKREST_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.825, 0.6, 0.1, 0.9, 1.2, 0.9);
    public static final AxisAlignedBB CHAIR_BACKREST_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.825, 0.6, 0.1, 0.9, 1.2, 0.9);
    public static final AxisAlignedBB CHAIR_BACKREST_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.825, 0.6, 0.1, 0.9, 1.2, 0.9);
    public static final AxisAlignedBB[] LEGS = {new AxisAlignedBB(1.6 * 0.0625, 0, 12.8 * 0.0625, 3.2 * 0.0625, 8 * 0.0625, 14.4 * 0.0625), new AxisAlignedBB(12.8 * 0.0625, 0, 12.8 * 0.0625, 14.4 * 0.0625, 8 * 0.0625, 14.4 * 0.0625), new AxisAlignedBB(12.8 * 0.0625, 0, 1.6 * 0.0625, 14.4 * 0.0625, 8 * 0.0625, 3.2 * 0.0625), new AxisAlignedBB(1.6 * 0.0625, 0, 1.6 * 0.0625, 3.2 * 0.0625, 8 * 0.0625, 3.2 * 0.0625)};

    public BlockChair(Material material, SoundType sound, String name)
    {
        super(material, name);
        this.setSoundType(sound);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
            if(SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 7 * 0.0625))
            {
                worldIn.updateComparatorOutputLevel(pos, this);
                return true;
            }
        }
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
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
        return SittableUtil.isSomeoneSitting(worldIn, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
    }

	@Override
	public void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes)
	{
        EnumFacing facing = state.getValue(FACING);
        switch(facing)
        {
            case NORTH:
                boxes.add(CHAIR_BACKREST_NORTH);
                break;
            case SOUTH:
                boxes.add(CHAIR_BACKREST_SOUTH);
                break;
            case WEST:
                boxes.add(CHAIR_BACKREST_WEST);
                break;
            default:
                boxes.add(CHAIR_BACKREST_EAST);
                break;
        }
        boxes.add(CHAIR_SEAT);
        Collections.addAll(boxes, LEGS);
	}
}
