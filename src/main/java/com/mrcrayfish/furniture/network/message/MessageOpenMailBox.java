package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageOpenMailBox implements IMessage<MessageOpenMailBox>
{
    private BlockPos pos;

    public MessageOpenMailBox() {}

    public MessageOpenMailBox(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void encode(MessageOpenMailBox message, PacketBuffer buffer)
    {
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageOpenMailBox decode(PacketBuffer buffer)
    {
        return new MessageOpenMailBox(buffer.readBlockPos());
    }

    @Override
    public void handle(MessageOpenMailBox message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity entity = supplier.get().getSender();
            if(entity != null)
            {
                TileEntity tileEntity = entity.world.getTileEntity(message.pos);
                if(tileEntity instanceof MailBoxTileEntity)
                {
                    if(((MailBoxTileEntity) tileEntity).isUsableByPlayer(entity))
                    {
                        TileEntityUtil.sendUpdatePacket(tileEntity);
                        NetworkHooks.openGui(entity, (INamedContainerProvider) tileEntity, message.pos);
                    }
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
