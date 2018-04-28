/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.init.FurnitureItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Server Side
public class MessagePackage implements IMessage, IMessageHandler<MessagePackage, IMessage>
{
	private ItemStack package_;
	private int slot;

	public MessagePackage()
	{
	}

	public MessagePackage(ItemStack package_, int slot)
	{
		this.package_ = package_;
		this.slot = slot;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.package_ = ByteBufUtils.readItemStack(buf);
		this.slot = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, package_);
		buf.writeInt(slot);
	}

	@Override
	public IMessage onMessage(MessagePackage message, MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().player;
		ItemStack mail = message.package_;
		ItemStack signedMail = new ItemStack(FurnitureItems.itemPackageSigned);
		signedMail.setTagCompound(mail.getTagCompound());
		signedMail.setTagInfo("Author", new NBTTagString(player.getName()));
		player.setHeldItem(player.getActiveHand(), signedMail);
		return null;
	}

}
