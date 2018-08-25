package com.mrcrayfish.furniture.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTakeWater implements IMessage, IMessageHandler<MessageTakeWater, IMessage>
{

    private int x, y, z;

    public MessageTakeWater()
    {
    }

    public MessageTakeWater(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageTakeWater message, MessageContext ctx)
    {
        ctx.getServerHandler().player.world.setBlockToAir(new BlockPos(message.x, message.y, message.z));
        return null;
    }

}
