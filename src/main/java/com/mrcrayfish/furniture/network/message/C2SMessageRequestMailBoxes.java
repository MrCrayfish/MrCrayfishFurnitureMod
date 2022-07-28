package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class C2SMessageRequestMailBoxes implements IMessage<C2SMessageRequestMailBoxes>
{
    @Override
    public void encode(C2SMessageRequestMailBoxes message, FriendlyByteBuf buffer) {}

    @Override
    public C2SMessageRequestMailBoxes decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageRequestMailBoxes();
    }

    @Override
    public void handle(C2SMessageRequestMailBoxes message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> IMessage.callServerConsumer(message, supplier, ServerPlayHandler::handleRequestMailBoxesMessage));
        supplier.get().setPacketHandled(true);
    }
}
