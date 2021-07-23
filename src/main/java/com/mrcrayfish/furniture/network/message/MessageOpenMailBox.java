package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.tileentity.MailBoxBlockEntity;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

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
    public void encode(MessageOpenMailBox message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageOpenMailBox decode(FriendlyByteBuf buffer)
    {
        return new MessageOpenMailBox(buffer.readBlockPos());
    }

    @Override
    public void handle(MessageOpenMailBox message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer entity = supplier.get().getSender();
            if(entity == null)
                return;

            if(!(entity.level.getBlockEntity(message.pos) instanceof MailBoxBlockEntity blockEntity))
                return;

            if(!blockEntity.stillValid(entity))
                return;

            BlockEntityUtil.sendUpdatePacket(blockEntity);
            NetworkHooks.openGui(entity, blockEntity, message.pos);
        });
        supplier.get().setPacketHandled(true);
    }
}
