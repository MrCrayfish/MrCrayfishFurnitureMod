package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.IColored;
import com.mrcrayfish.furniture.tileentity.TileEntityDigitalClock;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockDigitalClock extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] COLLISION_BOXES = new Bounds(6, 0, 4, 9, 4, 12).getRotatedBounds();
    private static final AxisAlignedBB[] SELECTION_BOXES = new Bounds(5, 0, 3, 10, 5, 13).getRotatedBounds();

    public BlockDigitalClock()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("digital_clock");
        this.setRegistryName("digital_clock");
        this.setLightLevel(0.5F);
        this.setHardness(0.5F);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()]);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SELECTION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof IColored)
        {
            IColored colored = (IColored) tileEntity;
            state = state.withProperty(BlockColored.COLOR, colored.getColor());
        }
        return state;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof IColored)
        {
            drops.add(new ItemStack(Item.getItemFromBlock(this), 1, ((IColored) tileEntity).getColor().getMetadata()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof IColored)
        {
            return new ItemStack(Item.getItemFromBlock(this), 1, ((IColored) tileEntity).getColor().getMetadata());
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof IColored)
        {
            IColored colored = (IColored) tileEntity;
            colored.setColor(EnumDyeColor.byMetadata(stack.getMetadata()));
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if(!heldItem.isEmpty() && heldItem.getItem() instanceof ItemDye)
            {
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if(tileEntity instanceof TileEntityDigitalClock)
                {
                    TileEntityDigitalClock digitalClock = (TileEntityDigitalClock) tileEntity;
                    digitalClock.setTextColorColor(EnumDyeColor.byDyeDamage(heldItem.getMetadata()));
                    if(!playerIn.capabilities.isCreativeMode)
                    {
                        heldItem.shrink(1);
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, BlockColored.COLOR);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDigitalClock();
    }
}
