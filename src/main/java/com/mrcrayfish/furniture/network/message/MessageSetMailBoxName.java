package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageSetMailBoxName implements IMessage<MessageSetMailBoxName>
{
    private UUID mailBoxId;
    private String name;
    private BlockPos pos;

    public MessageSetMailBoxName() {}

    public MessageSetMailBoxName(UUID mailBoxId, String name, BlockPos pos)
    {
        this.mailBoxId = mailBoxId;
        this.name = name;
        this.pos = pos;
    }

    @Override
    public void encode(MessageSetMailBoxName message, PacketBuffer buffer)
    {
        buffer.writeUniqueId(message.mailBoxId);
        buffer.writeString(message.name);
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageSetMailBoxName decode(PacketBuffer buffer)
    {
        return new MessageSetMailBoxName(buffer.readUniqueId(), buffer.readString(), buffer.readBlockPos());
    }

    @Override
    public void handle(MessageSetMailBoxName message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity entity = supplier.get().getSender();
            if(entity != null)
            {
                if(PostOffice.setMailBoxName(entity.getUniqueID(), message.mailBoxId, message.name))
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
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
