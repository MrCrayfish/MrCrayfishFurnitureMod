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

import com.mrcrayfish.furniture.gui.inventory.InventoryPresent;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.util.NBTHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Server Side
public class MessagePresent implements IMessage, IMessageHandler<MessagePresent, IMessage>
{

	private ItemStack present;
	private int x, y, z;

	public MessagePresent()
	{
	}

	public MessagePresent(ItemStack present, int x, int y, int z)
	{
		this.present = present;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.present = ByteBufUtils.readItemStack(buf);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, present);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public IMessage onMessage(MessagePresent message, MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().player;
		InventoryPresent presentInventory = new InventoryPresent(player, message.present);

		String author = NBTHelper.getString(message.present, "Author");
		TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
		if (tile_entity instanceof TileEntityPresent)
		{
			TileEntityPresent tileEntityPresent = (TileEntityPresent) tile_entity;
			for (int i = 0; i < presentInventory.getSizeInventory(); i++)
			{
				tileEntityPresent.setInventorySlotContents(i, presentInventory.getStackInSlot(i));
			}
			tileEntityPresent.setOwner(author);
		}
		return null;
	}

}
