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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityBasin;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Client Packet
public class MessageFillBasin implements IMessage, IMessageHandler<MessageFillBasin, IMessage>
{

    private boolean hasWater;
    private int x, y, z;

    public MessageFillBasin()
    {
    }

    public MessageFillBasin(boolean hasWater, int x, int y, int z)
    {
        this.hasWater = hasWater;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hasWater = buf.readBoolean();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(hasWater);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageFillBasin message, MessageContext ctx)
    {
        if(ctx.side.isClient())
        {
            EntityPlayer player = MrCrayfishFurnitureMod.proxy.getClientPlayer();
            TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
            if(tile_entity instanceof TileEntityBasin)
            {
                TileEntityBasin tileEntityBasin = (TileEntityBasin) tile_entity;
                tileEntityBasin.setHasWater(message.hasWater);
            }
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityUtil.markBlockForUpdate(player.world, pos);
        }
        return null;
    }
}
