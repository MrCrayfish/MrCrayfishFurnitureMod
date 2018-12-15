package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
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

import javax.annotation.Nullable;
import java.util.List;

public class ItemPackageSigned extends Item implements IItemInventory
{
    public ItemPackageSigned()
    {
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(this == FurnitureItems.PACKAGE_SIGNED)
        {
            return TextFormatting.YELLOW.toString() + TextFormatting.BOLD.toString() + I18n.format("item.item_package.name");
        }
        return I18n.format("item.item_package.name");
    }

    @Override
    public boolean getShareTag()
    {
        return true;
    }

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
        TileEntity tileEntity = world.getTileEntity(pos);
        if(!world.isRemote)
        {
            NBTTagList var2 = (NBTTagList) NBTHelper.getCompoundTag(heldItem, "Package").getTag("Items");
            if(var2.tagCount() > 0)
            {
                if(player.capabilities.isCreativeMode && player.isSneaking() && tileEntity instanceof TileEntityMailBox)
                {
                    player.sendMessage(new TextComponentTranslation("cfm.message.mail_creative"));
                }
                else if(tileEntity instanceof TileEntityMailBox)
                {
                    TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tileEntity;
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
                player.sendMessage(new TextComponentTranslation("cfm.message.package_used"));
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
            if(this == FurnitureItems.PACKAGE_SIGNED)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 8, worldIn, 0, 0, 0);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}