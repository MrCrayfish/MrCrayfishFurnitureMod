package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.items.IAuthored;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Author: MrCrayfish
 */
public class MessageSignItem implements IMessage, IMessageHandler<MessageSignItem, IMessage>
{
    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(MessageSignItem message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().player;
        ItemStack stack = player.inventory.getCurrentItem();
        if(stack.getItem() instanceof IAuthored)
        {
            ItemStack signedItem = new ItemStack(((IAuthored) stack.getItem()).getSignedItem(), 1, stack.getMetadata());
            if(stack.getTagCompound() != null)
            {
                signedItem.setTagCompound(stack.getTagCompound().copy());
            }
            signedItem.setTagInfo("Author", new NBTTagString(player.getName()));
            player.inventory.setInventorySlotContents(player.inventory.currentItem, signedItem);
            player.closeScreen();
        }
        return null;
    }
}