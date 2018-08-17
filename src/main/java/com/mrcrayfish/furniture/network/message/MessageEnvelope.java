package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.init.FurnitureItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Server Packet
public class MessageEnvelope implements IMessage, IMessageHandler<MessageEnvelope, IMessage>
{

    private ItemStack envelope;

    public MessageEnvelope()
    {
    }

    public MessageEnvelope(ItemStack envelope)
    {
        this.envelope = envelope;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.envelope = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeItemStack(buf, envelope);
    }

    @Override
    public IMessage onMessage(MessageEnvelope message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        ItemStack mail = message.envelope;
        ItemStack signedMail = new ItemStack(FurnitureItems.ENVELOPE_SIGNED);
        signedMail.setTagCompound(mail.getTagCompound().copy());
        signedMail.setTagInfo("Author", new NBTTagString(player.getName()));
        player.setHeldItem(player.getActiveHand(), signedMail);
        return null;
    }

}
