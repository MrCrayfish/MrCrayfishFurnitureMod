package com.mrcrayfish.furniture.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateFields implements IMessage, IMessageHandler<MessageUpdateFields, IMessage>
{
	private int[] fields;
	private int x, y, z;
	
	public MessageUpdateFields() {}
	
	public MessageUpdateFields(IInventory inventory, BlockPos pos) 
	{
		this.fields = new int[inventory.getFieldCount()];
		for(int i = 0; i < fields.length; i++)
		{
			this.fields[i] = inventory.getField(i);
		}
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}
	
	@Override
	public IMessage onMessage(MessageUpdateFields message, MessageContext ctx) 
	{
		if (ctx.side.isClient())
		{
			BlockPos pos = new BlockPos(message.x, message.y, message.z);
			TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(pos);
			if(tileEntity instanceof IInventory)
			{
				IInventory inventory = (IInventory) tileEntity;
				for(int i = 0; i < message.fields.length; i++)
				{
					inventory.setField(i, message.fields[i]);
				}
			}
		}
		return null;
	}
	
	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(fields.length);
		for(int i = 0; i < fields.length; i++)
		{
			buf.writeInt(fields[i]);
		}
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		int length = buf.readInt();
		fields = new int[length];
		for(int i = 0; i < length; i++)
		{
			fields[i] = buf.readInt();
		}
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}
}
