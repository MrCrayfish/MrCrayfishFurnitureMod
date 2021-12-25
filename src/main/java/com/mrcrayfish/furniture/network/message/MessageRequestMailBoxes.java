package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.common.mail.MailBox;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageRequestMailBoxes implements IMessage<MessageRequestMailBoxes>
{
    @Override
    public void encode(MessageRequestMailBoxes message, FriendlyByteBuf buffer) {}

    @Override
    public MessageRequestMailBoxes decode(FriendlyByteBuf buffer)
    {
        return new MessageRequestMailBoxes();
    }

    @Override
    public void handle(MessageRequestMailBoxes message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer entity = supplier.get().getSender();
            if(entity != null)
            {
                List<MailBox> mailBoxes = PostOffice.getMailBoxes(entity);
                CompoundTag compound = new CompoundTag();
                ListTag mailBoxList = new ListTag();
                mailBoxes.forEach(mailBox -> mailBoxList.add(mailBox.serializeDetails()));
                compound.put("MailBoxes", mailBoxList);
                PacketHandler.getPlayChannel()
                        .reply(new MessageUpdateMailBoxes(compound), supplier.get());
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
