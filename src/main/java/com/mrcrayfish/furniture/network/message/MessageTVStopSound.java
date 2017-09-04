package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.blocks.tv.Channels;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public class MessageTVStopSound implements IMessage, IMessageHandler<MessageTVStopSound, IMessage>
{
    private BlockPos pos;

    public MessageTVStopSound() {}

    public MessageTVStopSound(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(MessageTVStopSound message, MessageContext ctx)
    {
        ISound sound = Channels.SOUND_POSITIONS.get(message.pos);
        if(sound != null)
        {
            Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
            Channels.SOUND_POSITIONS.remove(message.pos);
        }
        return null;
    }
}
