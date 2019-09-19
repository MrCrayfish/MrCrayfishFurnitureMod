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
    private final ArrayList<String> itemData = new ArrayList<>();

    public MessageConfig() {}

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
