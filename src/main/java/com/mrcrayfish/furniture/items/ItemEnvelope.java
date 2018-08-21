package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemEnvelope extends Item implements IMail
{
    public ItemEnvelope()
    {
        setMaxStackSize(1);
        setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public boolean getShareTag()
    {
        return true;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile_entity = worldIn.getTileEntity(pos);
        if(!worldIn.isRemote)
        {
            if(tile_entity instanceof TileEntityMailBox)
            {
                if(player.isSneaking())
                {
                    player.sendMessage(new TextComponentTranslation("cfm.message.envelope_sign"));
                }
            }
            else
            {
                player.sendMessage(new TextComponentTranslation("cfm.message.envelope_open"));
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        if(!worldIn.isRemote)
        {
            playerIn.openGui(MrCrayfishFurnitureMod.instance, 5, worldIn, 0, 0, 0);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public static IInventory getInv(EntityPlayer par1EntityPlayer)
    {
        ItemStack mail = par1EntityPlayer.inventory.getCurrentItem();
        InventoryEnvelope invMail = null;
        if(mail != null && mail.getItem() instanceof ItemEnvelope)
        {
            invMail = new InventoryEnvelope(par1EntityPlayer, mail);
        }
        return invMail;
    }
}