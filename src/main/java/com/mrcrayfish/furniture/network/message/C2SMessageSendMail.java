package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class C2SMessageSendMail implements IMessage<C2SMessageSendMail>
{
    private UUID playerId;
    private UUID mailBoxId;

    public C2SMessageSendMail() {}

    public C2SMessageSendMail(UUID playerId, UUID mailBoxId)
    {
        this.playerId = playerId;
        this.mailBoxId = mailBoxId;
    }

    @Override
    public void encode(C2SMessageSendMail message, FriendlyByteBuf buffer)
    {
        buffer.writeUUID(message.playerId);
        buffer.writeUUID(message.mailBoxId);
    }

    @Override
    public C2SMessageSendMail decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageSendMail(buffer.readUUID(), buffer.readUUID());
    }

    @Override
    public void handle(C2SMessageSendMail message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> IMessage.callServerConsumer(message, supplier, ServerPlayHandler::handleSendMailMessage));
        supplier.get().setPacketHandled(true);
    }

    public UUID getPlayerId()
    {
        return this.playerId;
    }

    public UUID getMailBoxId()
    {
        return this.mailBoxId;
    }
}
