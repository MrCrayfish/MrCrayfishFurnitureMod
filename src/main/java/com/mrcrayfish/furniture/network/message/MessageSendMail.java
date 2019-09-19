package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.common.mail.Mail;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.inventory.container.PostBoxContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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
    public void encode(MessageSendMail message, PacketBuffer buffer)
    {
        buffer.writeUniqueId(message.playerId);
        buffer.writeUniqueId(message.mailBoxId);
    }

    @Override
    public MessageSendMail decode(PacketBuffer buffer)
    {
        return new MessageSendMail(buffer.readUniqueId(), buffer.readUniqueId());
    }

    @Override
    public void handle(MessageSendMail message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity entity = supplier.get().getSender();
            if(entity != null)
            {
                if(entity.openContainer instanceof PostBoxContainer)
                {
                    PostBoxContainer container = (PostBoxContainer) entity.openContainer;
                    if(!container.getMail().isEmpty())
                    {
                        Mail mail = new Mail("Yo", container.getMail(), entity.getName().getString());
                        PostOffice.sendMailToPlayer(message.playerId, message.mailBoxId, mail);
                        container.removeMail();
                    }
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
