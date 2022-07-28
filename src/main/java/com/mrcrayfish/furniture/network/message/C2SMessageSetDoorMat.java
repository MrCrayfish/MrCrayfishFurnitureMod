package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class C2SMessageSetDoorMat implements IMessage<C2SMessageSetDoorMat>
{
    private BlockPos pos;
    private String message;

    public C2SMessageSetDoorMat() {}

    public C2SMessageSetDoorMat(BlockPos pos, String message)
    {
        this.pos = pos;
        this.message = message;
    }

    @Override
    public void encode(C2SMessageSetDoorMat message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeUtf(message.message, 64);
    }

    @Override
    public C2SMessageSetDoorMat decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageSetDoorMat(buffer.readBlockPos(), buffer.readUtf(64));
    }

    @Override
    public void handle(C2SMessageSetDoorMat message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> IMessage.callServerConsumer(message, supplier, ServerPlayHandler::handleSetDoorMatMessage));
        supplier.get().setPacketHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public String getMessage()
    {
        return this.message;
    }
}
