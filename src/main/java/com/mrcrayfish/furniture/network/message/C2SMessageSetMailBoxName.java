package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ServerPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class C2SMessageSetMailBoxName implements IMessage<C2SMessageSetMailBoxName>
{
    private String name;
    private BlockPos pos;

    public C2SMessageSetMailBoxName() {}

    public C2SMessageSetMailBoxName(String name, BlockPos pos)
    {
        this.name = name;
        this.pos = pos;
    }

    @Override
    public void encode(C2SMessageSetMailBoxName message, FriendlyByteBuf buffer)
    {
        buffer.writeUtf(message.name, 32);
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public C2SMessageSetMailBoxName decode(FriendlyByteBuf buffer)
    {
        return new C2SMessageSetMailBoxName(buffer.readUtf(32), buffer.readBlockPos());
    }

    @Override
    public void handle(C2SMessageSetMailBoxName message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> IMessage.callServerConsumer(message, supplier, ServerPlayHandler::handleSetMailBoxNameMessage));
        supplier.get().setPacketHandled(true);
    }

    public String getName()
    {
        return this.name;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}
