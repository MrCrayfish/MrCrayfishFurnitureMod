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

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.tileentity.TileEntityComputer;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMineBayBuy implements IMessage, IMessageHandler<MessageMineBayBuy, IMessage>
{

	private int itemNum, x, y, z;
	private boolean shouldClear;

	public MessageMineBayBuy()
	{
	}

	public MessageMineBayBuy(int itemNum, int x, int y, int z, boolean shouldClear)
	{
		this.itemNum = itemNum;
		this.x = x;
		this.y = y;
		this.z = z;
		this.shouldClear = shouldClear;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.itemNum = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.shouldClear = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(itemNum);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(shouldClear);
	}

	@Override
	public IMessage onMessage(MessageMineBayBuy message, MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;

		TileEntity tile_entity = player.worldObj.getTileEntity(new BlockPos(message.x, message.y, message.z));
		if (tile_entity instanceof TileEntityComputer)
		{
			TileEntityComputer tileEntityComputer = (TileEntityComputer) tile_entity;
			ItemStack buySlot = tileEntityComputer.getStackInSlot(0);
			RecipeData[] data = Recipes.getMineBayItems();
			int price = data[message.itemNum].getPrice();
			
			if(buySlot == null)
				return null;
			
			if (message.shouldClear)
			{
				tileEntityComputer.clearInventory();
			}
			else
			{
				tileEntityComputer.takeEmeraldFromSlot(price);
			}

			EntityItem var14 = new EntityItem(player.worldObj, player.posX, player.posY + 1, player.posZ, data[message.itemNum].getInput().copy());
			player.worldObj.spawnEntityInWorld(var14);
			player.triggerAchievement(FurnitureAchievements.buyItem);
		}
		return null;
	}

}
