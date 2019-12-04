package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

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
    public void encode(MessageSetDoorMatMessage message, PacketBuffer buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeString(message.message, 64);
    }

    @Override
    public MessageSetDoorMatMessage decode(PacketBuffer buffer)
    {
        return new MessageSetDoorMatMessage(buffer.readBlockPos(), buffer.readString(64));
    }

    @Override
    public void handle(MessageSetDoorMatMessage message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = supplier.get().getSender();
            if(player != null)
            {
                World world = player.getServerWorld();
                if(world.isAreaLoaded(message.pos, 0))
                {
                    TileEntity tileEntity = world.getTileEntity(message.pos);
                    if(tileEntity instanceof DoorMatTileEntity)
                    {
                        ((DoorMatTileEntity) tileEntity).setMessage(message.message);
                    }
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
