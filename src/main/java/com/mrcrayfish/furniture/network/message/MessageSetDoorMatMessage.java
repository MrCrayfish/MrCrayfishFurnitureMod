package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.tileentity.DoorMatBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageSetDoorMatMessage implements IMessage<MessageSetDoorMatMessage>
{
    private BlockPos pos;
    private String message;

    public MessageSetDoorMatMessage() {}

    public MessageSetDoorMatMessage(BlockPos pos, String message)
    {
        this.pos = pos;
        this.message = message;
    }

    @Override
    public void encode(MessageSetDoorMatMessage message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeUtf(message.message, 64);
    }

    @Override
    public MessageSetDoorMatMessage decode(FriendlyByteBuf buffer)
    {
        return new MessageSetDoorMatMessage(buffer.readBlockPos(), buffer.readUtf(64));
    }

    @Override
    public void handle(MessageSetDoorMatMessage message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                Level level = player.getLevel();
                if(level.isLoaded(message.pos))
                {
                    if(level.getBlockEntity(message.pos) instanceof DoorMatBlockEntity blockEntity)
                    {
                        blockEntity.setMessage(message.message);
                    }
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
