package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.client.ClientPlayHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageUpdateMailBoxes implements IMessage<MessageUpdateMailBoxes>
{
    private CompoundTag compound;

    public MessageUpdateMailBoxes() {}

    public MessageUpdateMailBoxes(CompoundTag compound)
    {
        this.compound = compound;
    }

    @Override
    public void encode(MessageUpdateMailBoxes message, FriendlyByteBuf buffer)
    {
        buffer.writeNbt(message.compound);
    }

    @Override
    public MessageUpdateMailBoxes decode(FriendlyByteBuf buffer)
    {
        CompoundTag compound = buffer.readNbt();
        return new MessageUpdateMailBoxes(compound);
    }

    @Override
    public void handle(MessageUpdateMailBoxes message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleUpdateMailboxesMessage(message));
        supplier.get().setPacketHandled(true);
    }

    public CompoundTag getCompound()
    {
        return this.compound;
    }
}
