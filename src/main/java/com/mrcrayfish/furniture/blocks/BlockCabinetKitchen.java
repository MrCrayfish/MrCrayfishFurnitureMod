package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCabinetKitchen;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCabinetKitchen extends BlockFurnitureTile
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);

    public BlockCabinetKitchen(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BlockCabinet.BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, Entity entity, boolean isActualState)
    {
        return BlockCabinet.COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityCabinetKitchen)
            {
                TileEntityCabinetKitchen cabinetKitchen = (TileEntityCabinetKitchen) tileEntity;
                if(!heldItem.isEmpty())
                {
                    if(heldItem.getItem() instanceof ItemDye)
                    {
                        if(cabinetKitchen.getColour() != (15 - heldItem.getItemDamage()))
                        {
                            cabinetKitchen.setColour(heldItem.getItemDamage());
                            if(!playerIn.isCreative())
                            {
                                heldItem.shrink(1);
                            }
                            TileEntityUtil.markBlockForUpdate(worldIn, pos);
                        }
                        return true;
                    }
                }
            }
            TileEntity tile_entity = worldIn.getTileEntity(pos);
            if(tile_entity instanceof TileEntityCabinetKitchen)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityCabinetKitchen();
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
        if(tileEntity instanceof TileEntityCabinetKitchen)
        {
            int colour = ((TileEntityCabinetKitchen) tileEntity).getColour();
            state = state.withProperty(COLOUR, colour);
        }
        return state;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityCabinetKitchen)
        {
            ((TileEntityCabinetKitchen) tileEntity).setColour(15 - stack.getMetadata());
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if (tileEntity instanceof TileEntityCabinetKitchen)
        {
            TileEntityCabinetKitchen cabinet = (TileEntityCabinetKitchen) tileEntity;
            ItemStack itemstack = new ItemStack(this, 1, cabinet.getColour());
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
        if(tileEntity instanceof TileEntityCabinetKitchen)
        {
            metadata = ((TileEntityCabinetKitchen) tileEntity).getColour();
        }
        return new ItemStack(this, 1, metadata);
    }
}
