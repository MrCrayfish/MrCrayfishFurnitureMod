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
        signedMail.setTagCompound((NBTTagCompound) mail.getTagCompound().copy());
        signedMail.setTagInfo("Author", new NBTTagString(player.getName()));
        player.setHeldItem(player.getActiveHand(), signedMail);
        return null;
    }

}
