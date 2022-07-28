package com.mrcrayfish.furniture.network;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.message.C2SMessageLockCrate;
import com.mrcrayfish.furniture.network.message.C2SMessageOpenMailBox;
import com.mrcrayfish.furniture.network.message.C2SMessageRequestMailBoxes;
import com.mrcrayfish.furniture.network.message.C2SMessageSendMail;
import com.mrcrayfish.furniture.network.message.C2SMessageSetDoorMat;
import com.mrcrayfish.furniture.network.message.C2SMessageSetMailBoxName;
import com.mrcrayfish.furniture.network.message.IMessage;
import com.mrcrayfish.furniture.network.message.S2CMessageFlipGrill;
import com.mrcrayfish.furniture.network.message.S2CMessageUpdateMailBoxes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class PacketHandler
{
    public static final String PROTOCOL_VERSION = "1";

    private static SimpleChannel instance;
    private static int nextId = 0;

    public static void init()
    {
        instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Reference.MOD_ID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();
        register(C2SMessageLockCrate.class, new C2SMessageLockCrate(), NetworkDirection.PLAY_TO_SERVER);
        register(C2SMessageRequestMailBoxes.class, new C2SMessageRequestMailBoxes(), NetworkDirection.PLAY_TO_SERVER);
        register(S2CMessageUpdateMailBoxes.class, new S2CMessageUpdateMailBoxes(), NetworkDirection.PLAY_TO_CLIENT);
        register(C2SMessageSendMail.class, new C2SMessageSendMail(), NetworkDirection.PLAY_TO_SERVER);
        register(C2SMessageSetMailBoxName.class, new C2SMessageSetMailBoxName(), NetworkDirection.PLAY_TO_SERVER);
        register(C2SMessageOpenMailBox.class, new C2SMessageOpenMailBox(), NetworkDirection.PLAY_TO_SERVER);
        register(S2CMessageFlipGrill.class, new S2CMessageFlipGrill(), NetworkDirection.PLAY_TO_CLIENT);
        register(C2SMessageSetDoorMat.class, new C2SMessageSetDoorMat(), NetworkDirection.PLAY_TO_SERVER);
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message, @Nullable NetworkDirection direction)
    {
        instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle, Optional.ofNullable(direction));
    }

    public static SimpleChannel getPlayChannel()
    {
        return instance;
    }
}
