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

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Server
public class MessagePresentContents implements IMessage, IMessageHandler<MessagePresentContents, IMessage>
{

    private ItemStack envelope;

    public MessagePresentContents()
    {
    }

    public MessagePresentContents(ItemStack envelope)
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
    public IMessage onMessage(MessagePresentContents message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        ItemStack present = message.envelope;

        ItemStack signedPresent = new ItemStack(FurnitureBlocks.present, 1, present.getMetadata());
        signedPresent.setTagCompound(present.getTagCompound());
        signedPresent.setTagInfo("Author", new NBTTagString(player.getName()));
        signedPresent.setStackDisplayName(TextFormatting.GREEN + "Wrapped Present");
        player.inventory.setInventorySlotContents(player.inventory.currentItem, signedPresent);

        return null;
    }

}
