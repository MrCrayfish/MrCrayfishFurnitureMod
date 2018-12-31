package com.mrcrayfish.furniture.blocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCoffeeTable;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCoffeeTable extends BlockCollisionRaytrace
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public static final AxisAlignedBB TABLE_TOP = new Bounds(0, 6.5, 0, 16, 8, 16).toAABB();
    public static final AxisAlignedBB[] LEGS = new Bounds(0, 0, 14.4, 1.6, 6.5, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
    public static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(Collections.singletonList(TABLE_TOP), Arrays.asList(LEGS));

    public BlockCoffeeTable(Material material, SoundType sound, String unlocalizedName)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(sound);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);
        return (state.getValue(NORTH) && state.getValue(SOUTH)) || (state.getValue(EAST) && state.getValue(WEST)) ? TABLE_TOP : BOUNDING_BOX;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = state.getActualState(world, pos);

        List<AxisAlignedBB> list = Lists.newArrayList();
        boolean north = state.getValue(NORTH);
        boolean south = state.getValue(SOUTH);
        boolean east = state.getValue(EAST);
        boolean west = state.getValue(WEST);
        list.add(TABLE_TOP);
        if(!north)
        {
            if(!west) list.add(new Bounds(0, 0, 14.4, 1.6, 6.5, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90)[0]);
            if(!east) list.add(new Bounds(0, 0, 14.4, 1.6, 6.5, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90)[1]);
        }
        if(!south)
        {
            if(!west) list.add(new Bounds(0, 0, 14.4, 1.6, 6.5, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90)[3]);
            if(!east) list.add(new Bounds(0, 0, 14.4, 1.6, 6.5, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90)[2]);
        }
        return list;
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
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(NORTH, isCoffeeTable(world, pos.north())).withProperty(EAST, isCoffeeTable(world, pos.east())).withProperty(SOUTH, isCoffeeTable(world, pos.south())).withProperty(WEST, isCoffeeTable(world, pos.west()));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
    }

    public boolean isCoffeeTable(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == this;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityCoffeeTable)
        {
            TileEntityCoffeeTable tileEntityCoffeeTable = (TileEntityCoffeeTable) tileEntity;
            if(!heldItem.isEmpty() && tileEntityCoffeeTable.getFood().isEmpty())
            {
                ItemStack copy = heldItem.copy();
                copy.setCount(1);
                tileEntityCoffeeTable.setFood(copy);
                tileEntityCoffeeTable.setRotation(playerIn.getHorizontalFacing().getHorizontalIndex());
                tileEntityCoffeeTable.sync();
                heldItem.shrink(1);
                return true;
            }
            if(!tileEntityCoffeeTable.getFood().isEmpty())
            {
                if(!worldIn.isRemote)
                {
                    EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityCoffeeTable.getFood());
                    worldIn.spawnEntity(entityFood);
                }
                tileEntityCoffeeTable.setFood(ItemStack.EMPTY);
                tileEntityCoffeeTable.sync();
            }
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityCoffeeTable();
    }
}
