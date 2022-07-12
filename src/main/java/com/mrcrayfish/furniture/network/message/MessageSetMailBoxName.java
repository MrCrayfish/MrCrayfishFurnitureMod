package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.tileentity.MailBoxBlockEntity;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

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
    public void encode(MessageSetMailBoxName message, FriendlyByteBuf buffer)
    {
        buffer.writeUtf(message.name, 32);
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageSetMailBoxName decode(FriendlyByteBuf buffer)
    {
        return new MessageSetMailBoxName(buffer.readUtf(32), buffer.readBlockPos());
    }

    @Override
    public void handle(MessageSetMailBoxName message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer entity = supplier.get().getSender();
            if(entity == null)
                return;

            if(!entity.level.isAreaLoaded(message.pos, 0))
                return;

            if(entity.level.getBlockEntity(message.pos) instanceof MailBoxBlockEntity blockEntity)
            {
                if(!entity.getUUID().equals(blockEntity.getOwnerId()))
                    return;

                if(!blockEntity.stillValid(entity))
                    return;

                if(!PostOffice.setMailBoxName(entity.getUUID(), blockEntity.getId(), message.name))
                    return;

                BlockEntityUtil.sendUpdatePacket(blockEntity);
                NetworkHooks.openScreen(entity, blockEntity, message.pos);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
