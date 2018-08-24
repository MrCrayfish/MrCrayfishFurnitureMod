package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class ItemTVRemote extends Item
{
    public ItemTVRemote()
    {
        this.setUnlocalizedName("tv_remote");
        this.setRegistryName("tv_remote");
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        return activateTelevision(world, player);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        return new ActionResult<>(activateTelevision(worldIn, playerIn), playerIn.getHeldItem(handIn));
    }

    public EnumActionResult activateTelevision(World worldIn, EntityPlayer playerIn)
    {
        Vec3d startVec = playerIn.getPositionEyes(0F);
        Vec3d endVec = startVec.add(playerIn.getLookVec().normalize().scale(16));
        RayTraceResult result = worldIn.rayTraceBlocks(startVec, endVec, false);
        if(result != null && result.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            BlockPos pos = result.getBlockPos();
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityTV)
            {
                TileEntityTV tileEntityTV = (TileEntityTV) tileEntity;
                if(playerIn.isSneaking())
                {
                    tileEntityTV.setPowered(!tileEntityTV.isPowered());
                }
                else
                {
                    if(tileEntityTV.nextChannel())
                    {
                        worldIn.playSound(null, pos, FurnitureSounds.white_noise, SoundCategory.BLOCKS, 0.5F, 1.0F);
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }
}
