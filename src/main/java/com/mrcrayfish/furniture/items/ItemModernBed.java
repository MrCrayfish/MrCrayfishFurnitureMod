package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.blocks.BlockFurniture;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityColoured;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class ItemModernBed extends ItemBlock implements SubItems
{
    public ItemModernBed()
    {
        super(FurnitureBlocks.MODERN_BED_BOTTOM);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        IBlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        EnumFacing playerFacing = player.getHorizontalFacing();
        BlockPos otherPos = pos.offset(playerFacing);
        ItemStack stack = player.getHeldItem(hand);
        if(worldIn.isAirBlock(otherPos))
        {
            if(player.canPlayerEdit(pos, facing, stack) && player.canPlayerEdit(otherPos, facing, stack))
            {
                if(worldIn.getBlockState(pos.down()).isTopSolid() && worldIn.getBlockState(otherPos.down()).isTopSolid())
                {
                    worldIn.setBlockState(pos, FurnitureBlocks.MODERN_BED_BOTTOM.getDefaultState().withProperty(BlockFurniture.FACING, playerFacing));
                    worldIn.setBlockState(otherPos, FurnitureBlocks.MODERN_BED_TOP.getDefaultState().withProperty(BlockFurniture.FACING, playerFacing));

                    TileEntity tileEntity = worldIn.getTileEntity(pos);
                    if (tileEntity instanceof TileEntityColoured)
                    {
                        ((TileEntityColoured) tileEntity).setColour(15 - stack.getMetadata());
                    }
                    TileEntity otherTileEntity = worldIn.getTileEntity(otherPos);
                    if (otherTileEntity instanceof TileEntityColoured)
                    {
                        ((TileEntityColoured) otherTileEntity).setColour(15 - stack.getMetadata());
                    }

                    worldIn.notifyNeighborsRespectDebug(pos, FurnitureBlocks.MODERN_BED_BOTTOM, false);
                    worldIn.notifyNeighborsRespectDebug(otherPos, FurnitureBlocks.MODERN_BED_TOP, false);

                    if (player instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
                    }

                    stack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + "_" + EnumDyeColor.values()[stack.getItemDamage()].getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(isInCreativeTab(tab))
        {
            for(EnumDyeColor color : EnumDyeColor.values())
            {
                items.add(new ItemStack(this, 1, color.getMetadata()));
            }
        }
    }

    @Override
    public NonNullList<ResourceLocation> getModels()
    {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        for(EnumDyeColor color : EnumDyeColor.values())
        {
            modelLocations.add(new ResourceLocation(Reference.MOD_ID, getUnlocalizedName().substring(5) + "/" + color.getName()));
        }
        return modelLocations;
    }
}
