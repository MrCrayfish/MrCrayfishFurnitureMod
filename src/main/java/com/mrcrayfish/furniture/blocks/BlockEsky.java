package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityEsky;
import com.mrcrayfish.furniture.tileentity.TileEntityKitchenCounter;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockEsky extends BlockFurnitureTile
{
    public static final PropertyBool OPENED = PropertyBool.create("open");
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);

    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 1.5 * 0.0625, 0.0, 0.0, 14.5 * 0.0625, 0.8, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 1.5 * 0.0625, 0.0, 0.0, 14.5 * 0.0625, 0.8, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 1.5 * 0.0625, 0.0, 0.0, 14.5 * 0.0625, 0.8, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 1.5 * 0.0625, 0.0, 0.0, 14.5 * 0.0625, 0.8, 1.0);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 2.5 * 0.0625, 0.0, 1 * 0.0625, 13.5 * 0.0625, 0.75, 15 * 0.0625);
    private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 2.5 * 0.0625, 0.0, 1 * 0.0625, 13.5 * 0.0625, 0.75, 15 * 0.0625);
    private static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 2.5 * 0.0625, 0.0, 1 * 0.0625, 13.5 * 0.0625, 0.75, 15 * 0.0625);
    private static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 2.5 * 0.0625, 0.0, 1 * 0.0625, 13.5 * 0.0625, 0.75, 15 * 0.0625);
    private static final AxisAlignedBB[] COLLISION_BOX = {COLLISION_BOX_SOUTH, COLLISION_BOX_WEST, COLLISION_BOX_NORTH, COLLISION_BOX_EAST};

    public BlockEsky(Material material)
    {
        super(material);
        this.setHardness(0.75F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPENED, Boolean.FALSE));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
        addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX[facing.getHorizontalIndex()]);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(playerIn.isSneaking())
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(state.getValue(OPENED))
            {
                worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.cabinet_close, SoundCategory.BLOCKS, 0.75F, 0.6F, false);
            }
            else
            {
                worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.cabinet_open, SoundCategory.BLOCKS, 0.75F, 0.7F, false);
            }
            worldIn.setBlockState(pos, state.withProperty(OPENED, !state.getValue(OPENED)));
            if(tileEntity != null)
            {
                tileEntity.validate();
                worldIn.setTileEntity(pos, tileEntity);
            }
        }
        else
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityEsky)
            {
                TileEntityEsky tileEntityEsky = (TileEntityEsky) tileEntity;
                ItemStack heldItem = playerIn.getHeldItem(hand);
                if(!heldItem.isEmpty())
                {
                    if(heldItem.getItem() instanceof ItemDye)
                    {
                        if(tileEntityEsky.getColour() != (15 - heldItem.getItemDamage()))
                        {
                            tileEntityEsky.setColour(heldItem.getItemDamage());
                            if(!playerIn.isCreative())
                            {
                                heldItem.shrink(1);
                            }
                            tileEntityEsky.sync();
                            return true;
                        }
                        return true;
                    }
                }
                if(state.getValue(OPENED))
                {
                    playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityEsky)
        {
            int colour = ((TileEntityEsky) tileEntity).getColour();
            state = state.withProperty(COLOUR, colour);
        }
        return state;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityEsky)
        {
            ((TileEntityEsky) tileEntity).setColour(15 - stack.getMetadata());
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if (tileEntity instanceof TileEntityEsky)
        {
            TileEntityEsky esky = (TileEntityEsky) tileEntity;
            ItemStack itemstack = new ItemStack(this, 1, esky.getColour());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, tileEntity, stack);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(OPENED, Boolean.FALSE);
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
        int metadata = 0;
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityKitchenCounter)
        {
            metadata = ((TileEntityKitchenCounter) tileEntity).getColour();
        }
        return new ItemStack(this, 1, metadata);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(OPENED) ? 4 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(OPENED, meta / 4 > 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, OPENED, COLOUR);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityEsky();
    }
}
