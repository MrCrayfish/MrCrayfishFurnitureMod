package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class C2SMessageLockCrate implements IMessage<C2SMessageLockCrate>
{
    @Override
    public void encode(C2SMessageLockCrate message, FriendlyByteBuf buffer) {}

    public C2SMessageLockCrate decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageLockCrate();
    }

    @Override
    public void handle(C2SMessageLockCrate message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> IMessage.callServerConsumer(message, supplier, ServerPlayHandler::handleLockCrateMessage));
        supplier.get().setPacketHandled(true);
    }
}
