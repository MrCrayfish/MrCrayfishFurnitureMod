package com.mrcrayfish.furniture.blocks;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.SeatUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBarStool extends BlockCollisionRaytrace
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);

    private static final AxisAlignedBB CUSHION = new Bounds(3.5, 12, 3.5, 12.5, 12.5, 12.5).toAABB();
    private static final AxisAlignedBB TOP = new Bounds(3, 9, 3, 13, 12, 13).toAABB();
    private static final AxisAlignedBB LEG_1 = new Bounds(4, 0, 4, 6, 9, 6).toAABB();
    private static final AxisAlignedBB LEG_2 = new Bounds(10, 0, 4, 12, 9, 6).toAABB();
    private static final AxisAlignedBB LEG_3 = new Bounds(4, 0, 10, 6, 9, 12).toAABB();
    private static final AxisAlignedBB LEG_4 = new Bounds(10, 0, 10, 12, 9, 12).toAABB();
    private static final AxisAlignedBB BRACE_4 = new Bounds(4.5, 4, 6, 5.5, 5, 10).toAABB();
    private static final AxisAlignedBB BRACE_2 = new Bounds(10.5, 4, 6, 11.5, 5, 10).toAABB();
    private static final AxisAlignedBB BRACE_3 = new Bounds(6, 4, 10.5, 10, 5, 11.5).toAABB();
    private static final AxisAlignedBB BRACE_1 = new Bounds(6, 4, 4.5, 10, 5, 5.5).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES_BODY = Lists.newArrayList(TOP, LEG_1, LEG_2, LEG_3, LEG_4, BRACE_4, BRACE_2, BRACE_3, BRACE_1);
    private static final List<AxisAlignedBB> COLLISION_BOXES_CUSHION = Lists.newArrayList(CUSHION);
    private static final List<AxisAlignedBB> COLLISION_BOXES = Stream.of(COLLISION_BOXES_BODY, COLLISION_BOXES_CUSHION).flatMap(Collection::stream).collect(Collectors.toList());
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES_BODY);

    public BlockBarStool(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, 0));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return entity instanceof EntitySeat ? EMPTY : COLLISION_BOXES;
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

    // TODO you should really try to support modded dyes as well as vanilla ones
    // TODO no because blockstates are not dynamic
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!heldItem.isEmpty())
        {
            if(heldItem.getItem() instanceof ItemDye)
            {
                if(state.getValue(COLOUR) != 15 - heldItem.getItemDamage())
                {
                    worldIn.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()));
                    if(!playerIn.isCreative())
                    {
                        heldItem.shrink(1);
                    }
                    return true;
                }
            }
        }
        return !playerIn.isSneaking() && SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 9 * 0.0625);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOUR, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOUR);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOUR);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(COLOUR, Math.min(meta, 15));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(new ItemStack(this, 1, Math.min(state.getValue(COLOUR), 15)));
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
    {
        for(int i = 0; i < EnumDyeColor.values().length; i++)
        {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, state.getValue(COLOUR));
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
