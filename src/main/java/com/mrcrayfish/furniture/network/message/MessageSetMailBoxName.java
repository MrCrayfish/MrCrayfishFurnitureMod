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
    private String name;
    private BlockPos pos;

    public MessageSetMailBoxName() {}

    public MessageSetMailBoxName(String name, BlockPos pos)
    {
        this.name = name;
        this.pos = pos;
    }

    @Override
    public void encode(MessageSetMailBoxName message, PacketBuffer buffer)
    {
        buffer.writeString(message.name, 32);
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageSetMailBoxName decode(PacketBuffer buffer)
    {
        return new MessageSetMailBoxName(buffer.readString(32), buffer.readBlockPos());
    }

    @Override
    public void handle(MessageSetMailBoxName message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity entity = supplier.get().getSender();
            if(entity == null)
                return;

            if(!entity.world.isAreaLoaded(message.pos, 0))
                return;

            TileEntity tileEntity = entity.world.getTileEntity(message.pos);
            if(tileEntity instanceof MailBoxTileEntity)
            {
                MailBoxTileEntity mailBox = (MailBoxTileEntity) tileEntity;
                if(!entity.getUniqueID().equals(mailBox.getOwnerId()))
                    return;

                if(!((MailBoxTileEntity) tileEntity).isUsableByPlayer(entity))
                    return;

                if(!PostOffice.setMailBoxName(entity.getUniqueID(), mailBox.getId(), message.name))
                    return;

                TileEntityUtil.sendUpdatePacket(tileEntity);
                NetworkHooks.openGui(entity, (INamedContainerProvider) tileEntity, message.pos);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
