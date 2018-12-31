package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class BlockLamp extends BlockCollisionRaytrace
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private static final AxisAlignedBB SHADE = new Bounds(1.5, 8, 1.5, 14.5, 15, 14.5).toAABB();
    private static final AxisAlignedBB SUPPORT = new Bounds(7.2, 0, 7.2, 8.8, 16, 8.8).toAABB();
    private static final AxisAlignedBB SUPPORT_TOP = new Bounds(7.2, 0, 7.2, 8.8, 15, 8.8).toAABB();
    private static final AxisAlignedBB BASE = new Bounds(4, 0, 4, 12, 5, 12).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES_TOP = Lists.newArrayList(SHADE, SUPPORT_TOP);
    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(SHADE, SUPPORT_TOP, BASE);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

    private static final AxisAlignedBB BASE_BOTTOM = new Bounds(4, 0, 4, 12, 2, 12).toAABB();
    private static final List<AxisAlignedBB> COLLISION_BOXES_BOTTOM = Lists.newArrayList(SUPPORT, BASE_BOTTOM);
    private static final AxisAlignedBB BOUNDING_BOX_BOTTOM = Bounds.getBoundingBox(COLLISION_BOXES_BOTTOM);

    private static final List<AxisAlignedBB> COLLISION_BOXES_CENTER = Lists.newArrayList(SUPPORT);
    private static final AxisAlignedBB BOUNDING_BOX_CENTER = Bounds.getBoundingBox(COLLISION_BOXES_CENTER);

    public BlockLamp(Material material, boolean on)
    {
        super(material);
        this.setHardness(0.75F);
        this.setSoundType(SoundType.CLOTH);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, 0));
        if(!on)
        {
            this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);
        return !state.getValue(UP) ? BOUNDING_BOX : (state.getValue(DOWN) ? BOUNDING_BOX_CENTER : BOUNDING_BOX_BOTTOM);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (entity instanceof EntitySeat)
            return EMPTY;

        if (!isActualState)
            state = state.getActualState(world, pos);

        boolean up = state.getValue(UP);
        return up == state.getValue(DOWN) ? (up ? COLLISION_BOXES_CENTER : COLLISION_BOXES) : (up ? COLLISION_BOXES_BOTTOM : COLLISION_BOXES_TOP);
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
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!heldItem.isEmpty())
        {
            if(heldItem.getItem() instanceof ItemDye)
            {
                worldIn.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()));
                if(!playerIn.isCreative()) heldItem.shrink(1);
                return true;
            }
        }

        if(!(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLamp))
        {
            worldIn.setBlockState(pos, FurnitureBlocks.LAMP_ON.getDefaultState().withProperty(BlockLampOn.COLOUR, state.getValue(COLOUR)), 3);
        }
        else
        {
            int yOffset = 1;
            while(worldIn.getBlockState(pos.up(yOffset)).getBlock() instanceof BlockLamp) yOffset++;

            int colour = worldIn.getBlockState(pos.up(yOffset).down()).getValue(COLOUR);

            if(worldIn.getBlockState(pos.up(yOffset).down()).getBlock() instanceof BlockLampOn)
            {
                worldIn.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.LAMP_OFF.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
            }
            else
            {
                worldIn.setBlockState(pos.up(yOffset).down(), FurnitureBlocks.LAMP_ON.getDefaultState().withProperty(BlockLampOn.COLOUR, colour));
            }
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        boolean up = worldIn.getBlockState(pos.up()).getBlock() instanceof BlockLamp;
        boolean down = worldIn.getBlockState(pos.down()).getBlock() instanceof BlockLamp;
        return state.withProperty(UP, up).withProperty(DOWN, down);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(COLOUR, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOUR);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOUR, UP, DOWN);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(COLOUR, Math.min(meta, 15));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(new ItemStack(FurnitureBlocks.LAMP_OFF, 1, Math.min(state.getValue(COLOUR), 15)));
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
        return new ItemStack(FurnitureBlocks.LAMP_OFF, 1, state.getValue(COLOUR));
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
