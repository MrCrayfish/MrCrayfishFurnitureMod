package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.common.mail.Mail;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.inventory.container.PostBoxMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageSendMail implements IMessage<MessageSendMail>
{
    private UUID playerId;
    private UUID mailBoxId;

    public MessageSendMail() {}

    public MessageSendMail(UUID playerId, UUID mailBoxId)
    {
        this.playerId = playerId;
        this.mailBoxId = mailBoxId;
    }

    @Override
    public void encode(MessageSendMail message, FriendlyByteBuf buffer)
    {
        buffer.writeUUID(message.playerId);
        buffer.writeUUID(message.mailBoxId);
    }

    @Override
    public MessageSendMail decode(FriendlyByteBuf buffer)
    {
        return new MessageSendMail(buffer.readUUID(), buffer.readUUID());
    }

    @Override
    public void handle(MessageSendMail message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer entity = supplier.get().getSender();
            if(entity != null)
            {
                if(entity.containerMenu instanceof PostBoxMenu)
                {
                    PostBoxMenu container = (PostBoxMenu) entity.containerMenu;
                    if(!container.getMail().isEmpty())
                    {
                        Mail mail = new Mail("Yo", container.getMail(), entity.getName().getString());
                        if(PostOffice.sendMailToPlayer(message.playerId, message.mailBoxId, mail))
                        {
                            container.removeMail();
                        }
                        else
                        {
                            entity.sendSystemMessage(Component.translatable("message.cfm.mail_queue_full"));
                        }
                    }
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
