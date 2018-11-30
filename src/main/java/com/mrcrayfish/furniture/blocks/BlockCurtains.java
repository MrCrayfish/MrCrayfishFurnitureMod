package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityColoured;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public abstract class BlockCurtains extends BlockFurniture
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    public BlockCurtains(Material material)
    {
        super(material);
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
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            ((TileEntityColoured) tileEntity).setColour(15 - stack.getMetadata());
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
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(isOpen())
        {
            worldIn.setBlockState(pos, FurnitureBlocks.CURTAINS_CLOSED.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(COLOUR, state.getValue(COLOUR)), 3);
        }
        else
        {
            worldIn.setBlockState(pos, FurnitureBlocks.CURTAINS.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(COLOUR, state.getValue(COLOUR)), 3);
        }
        if(tileEntity != null)
        {
            tileEntity.validate();
            worldIn.setTileEntity(pos, tileEntity);
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            int colour = ((TileEntityColoured) tileEntity).getColour();
            state = state.withProperty(COLOUR, colour);
        }

        if(isOpen())
        {
            EnumFacing facing = state.getValue(FACING);
            IBlockState leftState = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
            IBlockState rightState = worldIn.getBlockState(pos.offset(facing.rotateY()));
            boolean isLeftOpen = leftState.getBlock() instanceof BlockCurtainsOpen && leftState.getValue(FACING).equals(facing);
            boolean isRightOpen = rightState.getBlock() instanceof BlockCurtainsOpen && rightState.getValue(FACING).equals(facing);
            boolean isLeftClosed = leftState.getBlock() instanceof BlockCurtainsClosed && leftState.getValue(FACING).equals(facing);
            boolean isRightClosed = rightState.getBlock() instanceof BlockCurtainsClosed && rightState.getValue(FACING).equals(facing);

            if(isRightOpen)
            {
                if(isLeftOpen)
                {
                    return state.withProperty(TYPE, Type.NONE);
                }
                else if(isLeftClosed)
                {
                    return state.withProperty(TYPE, Type.LEFT_CLOSED);
                }
                else
                {
                    return state.withProperty(TYPE, Type.RIGHT_OPEN);
                }
            }
            else if(isLeftOpen)
            {
                if(isRightOpen)
                {
                    return state.withProperty(TYPE, Type.NONE);
                }
                else if(isRightClosed)
                {
                    return state.withProperty(TYPE, Type.RIGHT_CLOSED);
                }
                else
                {
                    return state.withProperty(TYPE, Type.LEFT_OPEN);
                }
            }
            else if(isRightClosed)
            {
                if(isLeftClosed)
                {
                    return state.withProperty(TYPE, Type.BOTH);
                }
                else if(isLeftOpen)
                {
                    return state.withProperty(TYPE, Type.LEFT);
                }
                else
                {
                    return state.withProperty(TYPE, Type.RIGHT);
                }
            }
            else if(isLeftClosed)
            {
                if(isRightClosed)
                {
                    return state.withProperty(TYPE, Type.BOTH);
                }
                else if(isRightOpen)
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
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if (tileEntity instanceof TileEntityColoured)
        {
            TileEntityColoured couch = (TileEntityColoured) tileEntity;
            ItemStack itemstack = new ItemStack(FurnitureBlocks.CURTAINS_CLOSED, 1, couch.getColour());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, tileEntity, stack);
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return isOpen() ? new BlockStateContainer(this, FACING, TYPE, COLOUR) : new BlockStateContainer(this, FACING, COLOUR);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(COLOUR, meta);
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
    {
        for(int i = 0; i < EnumDyeColor.values().length; i++)
        {
            items.add(new ItemStack(FurnitureBlocks.CURTAINS_CLOSED, 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        int metadata = 0;
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            metadata = ((TileEntityColoured) tileEntity).getColour();
        }
        return new ItemStack(FurnitureBlocks.CURTAINS_CLOSED, 1, metadata);
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
