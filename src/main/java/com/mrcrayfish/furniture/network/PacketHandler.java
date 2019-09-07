package com.mrcrayfish.furniture.network;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.message.MessageLockCrate;
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

    public static void init()
    {
        instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Reference.MOD_ID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();
        instance.registerMessage(0, MessageLockCrate.class, MessageLockCrate::encode, MessageLockCrate::decode, MessageLockCrate::handle);
    }
}
