package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public abstract class BlockCurtains extends BlockFurniture
{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", Type.class);

    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    public BlockCurtains(Material material, String name)
    {
        super(material, name);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.CLOTH);
        if(!isOpen())
        {
            this.setLightOpacity(255);
        }
        else
        {
            this.setLightOpacity(0);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BLINDS_OR_CURTAINS, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public boolean isTranslucent(IBlockState state)
    {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        EnumFacing facing = state.getValue(FACING);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX[facing.getHorizontalIndex()]);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(isOpen())
        {
            return worldIn.setBlockState(pos, FurnitureBlocks.CURTAINS_CLOSED.getDefaultState().withProperty(FACING, state.getValue(FACING)));
        }
        else
        {
            return worldIn.setBlockState(pos, FurnitureBlocks.CURTAINS.getDefaultState().withProperty(FACING, state.getValue(FACING)));
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if(isOpen())
        {
            EnumFacing facing = state.getValue(FACING);
            IBlockState left_block = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
            IBlockState right_block = worldIn.getBlockState(pos.offset(facing.rotateY()));
            boolean left_open = left_block.getBlock() instanceof BlockCurtainsOpen && left_block.getValue(FACING).equals(facing);
            boolean right_open = right_block.getBlock() instanceof BlockCurtainsOpen && right_block.getValue(FACING).equals(facing);
            boolean left_closed = left_block.getBlock() instanceof BlockCurtainsClosed && left_block.getValue(FACING).equals(facing);
            boolean right_closed = right_block.getBlock() instanceof BlockCurtainsClosed && right_block.getValue(FACING).equals(facing);

            if(right_open)
            {
                if(left_open)
                {
                    return state.withProperty(TYPE, Type.NONE);
                }
                else if(left_closed)
                {
                    return state.withProperty(TYPE, Type.LEFT_CLOSED);
                }
                else
                {
                    return state.withProperty(TYPE, Type.RIGHT_OPEN);
                }
            }
            else if(left_open)
            {
                if(right_open)
                {
                    return state.withProperty(TYPE, Type.NONE);
                }
                else if(right_closed)
                {
                    return state.withProperty(TYPE, Type.RIGHT_CLOSED);
                }
                else
                {
                    return state.withProperty(TYPE, Type.LEFT_OPEN);
                }
            }
            else if(right_closed)
            {
                if(left_closed)
                {
                    return state.withProperty(TYPE, Type.BOTH);
                }
                else if(left_open)
                {
                    return state.withProperty(TYPE, Type.LEFT);
                }
                else
                {
                    return state.withProperty(TYPE, Type.RIGHT);
                }
            }
            else if(left_closed)
            {
                if(right_closed)
                {
                    return state.withProperty(TYPE, Type.BOTH);
                }
                else if(right_open)
                {
                    return state.withProperty(TYPE, Type.LEFT_CLOSED);
                }
                else
                {
                    return state.withProperty(TYPE, Type.LEFT);
                }
            }
            return state.withProperty(TYPE, Type.DEFAULT);
        }
        return state;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.CURTAINS_CLOSED).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.CURTAINS_CLOSED);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return isOpen() ? new BlockStateContainer(this, FACING, TYPE) : super.createBlockState();
    }

    public abstract boolean isOpen();

    public enum Type implements IStringSerializable
    {
        DEFAULT,
        LEFT,
        RIGHT,
        LEFT_OPEN,
        RIGHT_OPEN,
        LEFT_CLOSED,
        RIGHT_CLOSED,
        BOTH,
        NONE;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
