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

import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWashingMachine implements IMessage, IMessageHandler<MessageWashingMachine, IMessage>
{
	private int type;
	private int x, y, z;

	public MessageWashingMachine()
	{
	}

	public MessageWashingMachine(int type, int x, int y, int z)
	{
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.type = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(type);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public IMessage onMessage(MessageWashingMachine message, MessageContext ctx)
	{
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileEntity = world.getTileEntity(new BlockPos(message.x, message.y, message.z));
		if (tileEntity instanceof TileEntityWashingMachine)
		{
			TileEntityWashingMachine tileEntityWashineMachine = (TileEntityWashingMachine) tileEntity;
			if (message.type == 0)
			{
				tileEntityWashineMachine.startWashing();
			}
			if (message.type == 1)
			{
				tileEntityWashineMachine.stopWashing();
			}
			world.markBlockForUpdate(new BlockPos(message.x, message.y, message.z));
		}
		return null;
	}
}
