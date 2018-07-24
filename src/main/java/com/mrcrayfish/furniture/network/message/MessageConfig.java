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

import com.mrcrayfish.furniture.api.RecipeRegistryRemote;
import com.mrcrayfish.furniture.api.Recipes;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;

public class MessageConfig implements IMessage, IMessageHandler<MessageConfig, IMessage>
{

    private ArrayList<String> itemData = new ArrayList<String>();

    public MessageConfig()
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        List<String> dataList = Recipes.recipeData;
        buf.writeInt(dataList.size());
        for(String data : dataList)
        {
            ByteBufUtils.writeUTF8String(buf, data);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        int length = buf.readInt();
        int count = 0;
        while(count != length)
        {
            String data = ByteBufUtils.readUTF8String(buf);
            itemData.add(data);
            count++;
        }
    }

    @Override
    public IMessage onMessage(MessageConfig message, MessageContext ctx)
    {
        Recipes.clearRemoteRecipes();
        RecipeRegistryRemote.registerRemoteRecipes(message.itemData);
        return null;
    }
}
