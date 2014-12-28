/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
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

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityBath;

//Client Side
public class MessageFillBath implements IMessage, IMessageHandler<MessageFillBath, IMessage>
{

	private int waterLevel;
	private int bathOneX, bathOneY, bathOneZ;
	private int bathTwoX, bathTwoY, bathTwoZ;

	public MessageFillBath()
	{
	}

	public MessageFillBath(int waterLevel, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		this.waterLevel = waterLevel;
		this.bathOneX = x1;
		this.bathOneY = y1;
		this.bathOneZ = z1;
		this.bathTwoX = x2;
		this.bathTwoY = y2;
		this.bathTwoZ = z2;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.waterLevel = buf.readInt();
		this.bathOneX = buf.readInt();
		this.bathOneY = buf.readInt();
		this.bathOneZ = buf.readInt();
		this.bathTwoX = buf.readInt();
		this.bathTwoY = buf.readInt();
		this.bathTwoZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(waterLevel);
		buf.writeInt(bathOneX);
		buf.writeInt(bathOneY);
		buf.writeInt(bathOneZ);
		buf.writeInt(bathTwoX);
		buf.writeInt(bathTwoY);
		buf.writeInt(bathTwoZ);
	}

	@Override
	public IMessage onMessage(MessageFillBath message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		{
			EntityPlayer player = MrCrayfishFurnitureMod.proxy.getClientPlayer();

			TileEntity tile_entity = player.worldObj.getTileEntity(new BlockPos(message.bathOneX, message.bathOneY, message.bathOneZ));
			if (tile_entity instanceof TileEntityBath)
			{
				TileEntityBath tileEntityBath = (TileEntityBath) tile_entity;
				tileEntityBath.setWaterLevel(message.waterLevel);
				player.worldObj.markBlockForUpdate(new BlockPos(message.bathOneX, message.bathOneY, message.bathOneZ));
			}

			TileEntity tile_entity2 = player.worldObj.getTileEntity(new BlockPos(message.bathTwoX, message.bathTwoY, message.bathTwoZ));
			if (tile_entity2 instanceof TileEntityBath)
			{
				TileEntityBath tileEntityBath = (TileEntityBath) tile_entity2;
				tileEntityBath.setWaterLevel(message.waterLevel);
				player.worldObj.markBlockForUpdate(new BlockPos(message.bathTwoX, message.bathTwoY, message.bathTwoZ));
			}
		}
		return null;
	}

}
