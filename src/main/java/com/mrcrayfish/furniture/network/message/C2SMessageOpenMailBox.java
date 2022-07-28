package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class C2SMessageOpenMailBox implements IMessage<C2SMessageOpenMailBox>
{
    private BlockPos pos;

    public C2SMessageOpenMailBox() {}

    public C2SMessageOpenMailBox(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void encode(C2SMessageOpenMailBox message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public C2SMessageOpenMailBox decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageOpenMailBox(buffer.readBlockPos());
    }

    @Override
    public void handle(C2SMessageOpenMailBox message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> IMessage.callServerConsumer(message, supplier, ServerPlayHandler::handleOpenMailBoxMessage));
        supplier.get().setPacketHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}
