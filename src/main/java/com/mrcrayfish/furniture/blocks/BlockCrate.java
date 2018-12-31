package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityCrate;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrate extends BlockCollisionRaytrace implements ITileEntityProvider
{
    private static final AxisAlignedBB INSIDE = new Bounds(1, 1, 1, 15, 15, 15).toAABB();
    private static final AxisAlignedBB BACK_LEFT_SEAL = new Bounds(0, 0, 0, 2, 16, 2).toAABB();
    private static final AxisAlignedBB BACK_RIGHT_SEAL = new Bounds(14, 0, 0, 16, 16, 2).toAABB();
    private static final AxisAlignedBB FRONT_RIGHT_SEAL = new Bounds(14, 0, 14, 16, 16, 16).toAABB();
    private static final AxisAlignedBB FRONT_LEFT_SEAL = new Bounds(0, 0, 14, 2, 16, 16).toAABB();
    private static final AxisAlignedBB BACK_BOTTOM_SEAL = new Bounds(2, 0, 0, 14, 2, 2).toAABB();
    private static final AxisAlignedBB BACK_TOP_SEAL = new Bounds(2, 14, 0, 14, 16, 2).toAABB();
    private static final AxisAlignedBB FRONT_TOP_SEAL = new Bounds(2, 14, 14, 14, 16, 16).toAABB();
    private static final AxisAlignedBB FRONT_BOTTOM_SEAL = new Bounds(2, 0, 14, 14, 2, 16).toAABB();
    private static final AxisAlignedBB LEFT_BOTTOM_SEAL = new Bounds(0, 0, 2, 2, 2, 14).toAABB();
    private static final AxisAlignedBB LEFT_TOP_SEAL = new Bounds(0, 14, 2, 2, 16, 14).toAABB();
    private static final AxisAlignedBB RIGHT_TOP_SEAL = new Bounds(14, 14, 2, 16, 16, 14).toAABB();
    private static final AxisAlignedBB RIGHT_BOTTOM_SEAL = new Bounds(14, 0, 2, 16, 2, 14).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(INSIDE, BACK_LEFT_SEAL, BACK_RIGHT_SEAL, FRONT_RIGHT_SEAL, FRONT_LEFT_SEAL, BACK_BOTTOM_SEAL, BACK_TOP_SEAL, FRONT_TOP_SEAL, FRONT_BOTTOM_SEAL, LEFT_BOTTOM_SEAL, LEFT_TOP_SEAL, RIGHT_TOP_SEAL, RIGHT_BOTTOM_SEAL);

    public BlockCrate(Material materialIn)
    {
        super(materialIn);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            TileEntity crate = new TileEntityCrate();
            NBTTagCompound compound = stack.copy().getTagCompound();
            if(compound.getBoolean("Sealed"))
            {
                compound.setInteger("x", pos.getX());
                compound.setInteger("y", pos.getY());
                compound.setInteger("z", pos.getZ());
                compound.setString("id", "cfmCrate");
                crate.readFromNBT(compound);
                crate.validate();
                worldIn.setTileEntity(pos, crate);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, boolean willHarvest)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityCrate)
            {
                TileEntityCrate crate = (TileEntityCrate) tileEntity;
                if(crate.sealed)
                {
                    ItemStack drop = new ItemStack(this);

                    NBTTagCompound compound = new NBTTagCompound();
                    crate.writeToNBT(compound);
                    compound.removeTag("x");
                    compound.removeTag("y");
                    compound.removeTag("z");
                    compound.removeTag("id");
                    drop.setTagCompound(compound);

                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
                }
            }
        }
        return super.removedByPlayer(state, worldIn, pos, playerIn, willHarvest);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if(tileEntity instanceof TileEntityCrate)
            {
                TileEntityCrate crate = (TileEntityCrate) tileEntity;
                if(!crate.sealed)
                {
                    playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
                else
                {
                    if(!heldItem.isEmpty() && heldItem.getItem() == FurnitureItems.CROWBAR)
                    {
                        worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        InventoryHelper.dropInventoryItems(worldIn, pos, crate);
                        worldIn.destroyBlock(pos, false);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityCrate();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
