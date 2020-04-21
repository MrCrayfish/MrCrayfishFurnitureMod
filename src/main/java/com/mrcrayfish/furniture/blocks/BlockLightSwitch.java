package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityLightSwitch;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class BlockLightSwitch extends BlockFurniture
{
    private static final AxisAlignedBB[] BOUNDING_BOXES = new Bounds(14, 3, 4, 16, 13, 12).getRotatedBounds();
    private static final AxisAlignedBB[] COLLISION_BOXES = new Bounds(15, 4, 5, 16, 12, 11).getRotatedBounds();

    public BlockLightSwitch()
    {
        super(Material.WOOD);
        this.setHardness(0.5F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()]);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityLightSwitch)
        {
            if(this == FurnitureBlocks.LIGHT_SWITCH_OFF)
            {
                ((TileEntityLightSwitch) tileEntity).setState(true);
            }
            else
            {
                ((TileEntityLightSwitch) tileEntity).setState(false);
            }
            worldIn.playSound(null, pos, FurnitureSounds.light_switch, SoundCategory.BLOCKS, 1.0F, 0.9F + RANDOM.nextFloat() * 0.1F);
            return true;
        }
        return false;
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
        return new TileEntityLightSwitch();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(FurnitureBlocks.LIGHT_SWITCH_OFF);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess blockAccess, BlockPos pos, IBlockState state, int fortune)
    {
        super.getDrops(drops, blockAccess, pos, state, fortune);
        if(blockAccess instanceof World && !((World) blockAccess).isRemote)
        {
            TileEntity tileEntity = blockAccess.getTileEntity(pos);
            if(tileEntity instanceof TileEntityLightSwitch)
            {
                TileEntityLightSwitch lightSwitch = (TileEntityLightSwitch) tileEntity;
                for (ItemStack drop : drops)
                {
                    if (drop.getItem() instanceof ItemBlock && ((ItemBlock) drop.getItem()).getBlock() == FurnitureBlocks.LIGHT_SWITCH_OFF)
                    {
                        NBTTagCompound tileEntityTag = new NBTTagCompound();
                        lightSwitch.writeToNBT(tileEntityTag);
                        tileEntityTag.removeTag("x");
                        tileEntityTag.removeTag("y");
                        tileEntityTag.removeTag("z");

                        NBTTagCompound compound = drop.hasTagCompound() ? drop.getTagCompound() : new NBTTagCompound();
                        compound.setTag("BlockEntityTag", tileEntityTag);
                        drop.setTagCompound(compound);
                    }
                }
            }
        }
        
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        super.harvestBlock(world, player, pos, state, te, stack);
        world.setBlockToAir(pos);
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return !(side == EnumFacing.UP || side == EnumFacing.DOWN) && world.isSideSolid(pos.offset(side.getOpposite()), side, true);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }
}
