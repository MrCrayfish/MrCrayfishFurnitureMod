package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.common.mail.MailBox;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.network.PacketHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageRequestMailBoxes implements IMessage<MessageRequestMailBoxes>
{
    @Override
    public void encode(MessageRequestMailBoxes message, PacketBuffer buffer) {}

    @Override
    public MessageRequestMailBoxes decode(PacketBuffer buffer)
    {
        return new MessageRequestMailBoxes();
    }

    @Override
    public void handle(MessageRequestMailBoxes message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity entity = supplier.get().getSender();
            if(entity != null)
            {
                List<MailBox> mailBoxes = PostOffice.getMailBoxes(entity);
                CompoundNBT compound = new CompoundNBT();
                ListNBT mailBoxList = new ListNBT();
                mailBoxes.forEach(mailBox -> mailBoxList.add(mailBox.serializeDetails()));
                compound.put("MailBoxes", mailBoxList);
                PacketHandler.instance.reply(new MessageUpdateMailBoxes(compound), supplier.get());
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
