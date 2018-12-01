package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Server Packet
public class MessageFart implements IMessage, IMessageHandler<MessageFart, IMessage>
{
    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(MessageFart message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        if(player.getRidingEntity() instanceof EntitySittableBlock)
        {
            player.world.playSound(null, player.getPosition(), FurnitureSounds.getRandomFart(player.getRNG()), SoundCategory.BLOCKS, 0.75F, player.getRNG().nextFloat());
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            server.addScheduledTask(() -> Triggers.trigger(Triggers.PLAYER_FART, player));
        }
        return null;
    }
}
