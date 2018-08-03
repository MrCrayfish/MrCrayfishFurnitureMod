/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.items;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.gui.inventory.InventoryEnvelope;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import com.mrcrayfish.furniture.util.NBTHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnvelopeSigned extends Item implements IMail
{
    public static boolean canUse;

    public ItemEnvelopeSigned()
    {
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(this == FurnitureItems.ENVELOPE_SIGNED)
        {
            return TextFormatting.YELLOW.toString() + TextFormatting.BOLD.toString() + I18n.format("item.item_envelope.name");
        }
        return I18n.format("item.item_envelope.name");
    }

    @Override
    public boolean getShareTag()
    {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

            if(nbttagstring != null)
            {
                tooltip.add(TextFormatting.GRAY + I18n.format("cfm.mail_signed.info", nbttagstring.getString()));
            }
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        TileEntity tile_entity = world.getTileEntity(pos);
        if(!world.isRemote)
        {
            NBTTagList var2 = (NBTTagList) NBTHelper.getCompoundTag(heldItem, "Envelope").getTag("Items");
            if(var2.tagCount() > 0)
            {
                if(player.capabilities.isCreativeMode && player.isSneaking() && tile_entity instanceof TileEntityMailBox)
                {
                    player.sendMessage(new TextComponentTranslation("cfm.message.mail_creative"));
                }
                else if(tile_entity instanceof TileEntityMailBox)
                {
                    TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tile_entity;
                    if(player.isSneaking())
                    {
                        if(!tileEntityMailBox.isMailBoxFull())
                        {
                            ItemStack itemStack = heldItem.copy();
                            tileEntityMailBox.addMail(itemStack);
                            player.sendMessage(new TextComponentTranslation("cfm.message.mail_receive", TextFormatting.YELLOW + tileEntityMailBox.getOwner()));
                            heldItem.shrink(1);
                            Triggers.trigger(Triggers.PLAYER_SENT_MAIL, player);
                        }
                        else
                        {
                            player.sendMessage(new TextComponentTranslation("cfm.message.mail_full", TextFormatting.YELLOW +tileEntityMailBox.getOwner()));
                        }
                    }
                }
            }
            else
            {
                player.sendMessage(new TextComponentTranslation("cfm.message.envelope_used"));
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
            playerIn.openGui(MrCrayfishFurnitureMod.instance, 6, worldIn, 0, 0, 0);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public static IInventory getInv(EntityPlayer par1EntityPlayer)
    {
        ItemStack mail = par1EntityPlayer.inventory.getCurrentItem();
        InventoryEnvelope invMail = null;
        if(mail != null && mail.getItem() instanceof ItemEnvelopeSigned)
        {
            invMail = new InventoryEnvelope(par1EntityPlayer, mail);
        }
        return invMail;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}