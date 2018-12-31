package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityToaster;
import com.mrcrayfish.furniture.util.Bounds;
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
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class BlockToaster extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] FRONT = new Bounds(5.5, 0, 3.5, 6.5, 6.4, 12.5).getRotatedBounds();
    private static final AxisAlignedBB[] BACK = new Bounds(9.5, 0, 3.5, 10.5, 6.4, 12.5).getRotatedBounds();
    private static final AxisAlignedBB[] ELEMENT = new Bounds(7.5, 0, 4.5, 8.5, 6.4, 11.5).getRotatedBounds();
    private static final AxisAlignedBB[] LEFT = new Bounds(6.5, 0, 3.5, 9.5, 6.4, 4.5).getRotatedBounds();
    private static final AxisAlignedBB[] RIGHT = new Bounds(6.5, 0, 11.5, 9.5, 6.4, 12.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE = new Bounds(5, 0, 3, 11, 1, 13).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE = new Bounds(7, 4, 2.5, 9, 4.8, 3.5).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, FRONT, BACK, ELEMENT, LEFT, RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_PROTRUSIONS = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BASE, HANDLE);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_PROTRUSIONS);
    
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES_BODY);

    public BlockToaster(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.ANVIL);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
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
