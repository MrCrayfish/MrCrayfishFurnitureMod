package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ClientPlayHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class S2CMessageUpdateMailBoxes implements IMessage<S2CMessageUpdateMailBoxes>
{
    private CompoundTag compound;

    public S2CMessageUpdateMailBoxes() {}

    public S2CMessageUpdateMailBoxes(CompoundTag compound)
    {
        this.compound = compound;
    }

    @Override
    public void encode(S2CMessageUpdateMailBoxes message, FriendlyByteBuf buffer)
    {
        buffer.writeNbt(message.compound);
    }

    @Override
    public S2CMessageUpdateMailBoxes decode(FriendlyByteBuf buffer)
    {
        CompoundTag compound = buffer.readNbt();
        return new S2CMessageUpdateMailBoxes(compound);
    }

    @Override
    public void handle(S2CMessageUpdateMailBoxes message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleUpdateMailboxesMessage(message));
        supplier.get().setPacketHandled(true);
    }

    public CompoundTag getCompound()
    {
        return this.compound;
    }
}
