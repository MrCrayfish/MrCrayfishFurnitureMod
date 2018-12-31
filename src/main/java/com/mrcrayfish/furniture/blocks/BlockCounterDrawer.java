package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityKitchenCounterDrawer;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockCounterDrawer extends BlockFurnitureTile
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);

    private static final AxisAlignedBB[] BODY = new Bounds(2, 0, 0, 16, 14, 16).getRotatedBounds();
    private static final AxisAlignedBB[] TOP = new Bounds(0, 14, 0, 16, 16, 16).getRotatedBounds();
    private static final AxisAlignedBB[] DRAWER_TOP = new Bounds(1, 8, 1, 2, 13, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DRAWER_BOTTOM = new Bounds(1, 2, 1, 2, 7, 15).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_TOP = new Bounds(0, 10, 5.5, 1, 11, 10.5).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_BOTTOM = new Bounds(0, 4, 5.5, 1, 5, 10.5).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY, TOP, DRAWER_TOP, DRAWER_BOTTOM, HANDLE_TOP, HANDLE_BOTTOM);

    public BlockCounterDrawer(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityKitchenCounterDrawer)
        {
            TileEntityKitchenCounterDrawer tileEntityCouch = (TileEntityKitchenCounterDrawer) tileEntity;
            if(!heldItem.isEmpty())
            {
                if(heldItem.getItem() instanceof ItemDye)
                {
                    if(tileEntityCouch.getColour() != (15 - heldItem.getItemDamage()))
                    {
                        tileEntityCouch.setColour(15 - heldItem.getItemDamage());
                        if(!playerIn.isCreative())
                        {
                            heldItem.shrink(1);
                        }
                        TileEntityUtil.syncToClient(tileEntity);
                    }
                    return true;
                }
            }
            if(!worldIn.isRemote)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, COLOUR);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityKitchenCounterDrawer)
        {
            int colour = ((TileEntityKitchenCounterDrawer) tileEntity).getColour();
            state = state.withProperty(COLOUR, colour);
        }
        return state;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityKitchenCounterDrawer)
        {
            ((TileEntityKitchenCounterDrawer) tileEntity).setColour(15 - stack.getMetadata());
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if (tileEntity instanceof TileEntityKitchenCounterDrawer)
        {
            TileEntityKitchenCounterDrawer counter = (TileEntityKitchenCounterDrawer) tileEntity;
            ItemStack itemstack = new ItemStack(this, 1, counter.getColour());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, tileEntity, stack);
        }
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
        if(tileEntity instanceof TileEntityKitchenCounterDrawer)
        {
            metadata = ((TileEntityKitchenCounterDrawer) tileEntity).getColour();
        }
        return new ItemStack(this, 1, metadata);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityKitchenCounterDrawer();
    }
}
