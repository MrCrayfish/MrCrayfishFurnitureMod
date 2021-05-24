package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.client.ClientPlayHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageFlipGrill implements IMessage<MessageFlipGrill>
{
    private BlockPos pos;
    private int position;

    public MessageFlipGrill() {}

    public MessageFlipGrill(BlockPos pos, int position)
    {
        this.pos = pos;
        this.position = position;
    }

    @Override
    public void encode(MessageFlipGrill message, PacketBuffer buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeInt(message.position);
    }

    @Override
    public MessageFlipGrill decode(PacketBuffer buffer)
    {
        return new MessageFlipGrill(buffer.readBlockPos(), buffer.readInt());
    }

    @Override
    public void handle(MessageFlipGrill message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleFlipGrillMessage(this));
        supplier.get().setPacketHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public int getPosition()
    {
        return this.position;
    }
}
