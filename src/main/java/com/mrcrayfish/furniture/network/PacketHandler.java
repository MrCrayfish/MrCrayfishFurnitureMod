package com.mrcrayfish.furniture.network;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.message.IMessage;
import com.mrcrayfish.furniture.network.message.MessageFlipGrill;
import com.mrcrayfish.furniture.network.message.MessageLockCrate;
import com.mrcrayfish.furniture.network.message.MessageOpenMailBox;
import com.mrcrayfish.furniture.network.message.MessageRequestMailBoxes;
import com.mrcrayfish.furniture.network.message.MessageSendMail;
import com.mrcrayfish.furniture.network.message.MessageSetDoorMatMessage;
import com.mrcrayfish.furniture.network.message.MessageSetMailBoxName;
import com.mrcrayfish.furniture.network.message.MessageUpdateMailBoxes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * Author: MrCrayfish
 */
public class PacketHandler
{
    public static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel instance;
    private static int nextId = 0;

    public static void init()
    {
        instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Reference.MOD_ID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();
        register(MessageLockCrate.class, new MessageLockCrate());
        register(MessageRequestMailBoxes.class, new MessageRequestMailBoxes());
        register(MessageUpdateMailBoxes.class, new MessageUpdateMailBoxes());
        register(MessageSendMail.class, new MessageSendMail());
        register(MessageSetMailBoxName.class, new MessageSetMailBoxName());
        register(MessageOpenMailBox.class, new MessageOpenMailBox());
        register(MessageFlipGrill.class, new MessageFlipGrill());
        register(MessageSetDoorMatMessage.class, new MessageSetDoorMatMessage());
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message)
    {
        instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}
