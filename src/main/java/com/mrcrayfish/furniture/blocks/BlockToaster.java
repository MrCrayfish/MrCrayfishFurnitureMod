package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockToaster extends BlockFurnitureTile
{
    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.45, 13 * 0.0625);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
    private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
    private static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
    private static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 5 * 0.0625, 0.0, 3 * 0.0625, 11 * 0.0625, 0.4, 13 * 0.0625);
    private static final AxisAlignedBB[] COLLISION_BOX = {COLLISION_BOX_SOUTH, COLLISION_BOX_WEST, COLLISION_BOX_NORTH, COLLISION_BOX_EAST};

    public BlockToaster()
    {
        super(Material.ANVIL, "toaster");
        this.setHardness(0.5F);
        this.setSoundType(SoundType.ANVIL);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_APPLIANCE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityToaster)
        {
            TileEntityToaster tileEntityToaster = (TileEntityToaster) tileEntity;
            if(!heldItem.isEmpty() && !tileEntityToaster.isToasting())
            {
                RecipeData data = RecipeAPI.getToasterRecipeFromInput(heldItem);
                if(data != null)
                {
                    if(tileEntityToaster.addSlice(new ItemStack(heldItem.getItem(), 1)))
                    {
                        TileEntityUtil.markBlockForUpdate(worldIn, pos);
                        heldItem.shrink(1);
                    }
                }
                else
                {
                    tileEntityToaster.removeSlice();
                }
            }
            else
            {
                if(playerIn.isSneaking())
                {
                    if(!tileEntityToaster.isToasting())
                    {
                        tileEntityToaster.startToasting();
                        worldIn.updateComparatorOutputLevel(pos, this);
                        if(!worldIn.isRemote)
                        {
                            worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.toaster_down, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
                        }
                    }
                }
                else if(!tileEntityToaster.isToasting())
                {
                    tileEntityToaster.removeSlice();
                }
            }
        }
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
        addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX[facing.getHorizontalIndex()]);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityToaster();
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityToaster toaster = (TileEntityToaster) world.getTileEntity(pos);
        return toaster.isToasting() ? 1 : 0;
    }
}
