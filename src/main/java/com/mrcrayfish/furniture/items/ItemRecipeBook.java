package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemRecipeBook extends Item
{
    public ItemRecipeBook()
    {
        setMaxStackSize(16);
        setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        setMaxStackSize(16);
        ItemStack stack = playerIn.getHeldItem(hand);
        if(worldIn.isRemote)
        {
            playerIn.openGui(MrCrayfishFurnitureMod.instance, 10, worldIn, 0, 0, 0);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
