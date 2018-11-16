package com.mrcrayfish.furniture.network;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.message.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    private static int messageId = 0;
    
    public static void init()
    {
        registerMessage(MessageConfig.class, MessageConfig.class, Side.CLIENT);
        registerMessage(MessageEmptyBin.class, MessageEmptyBin.class, Side.SERVER);
        registerMessage(MessageFart.class, MessageFart.class, Side.SERVER);
        registerMessage(MessageFillBath.class, MessageFillBath.class, Side.CLIENT);
        registerMessage(MessageMineBayBrowse.class, MessageMineBayBrowse.class, Side.SERVER);
        registerMessage(MessageMineBayBuy.class, MessageMineBayBuy.class, Side.SERVER);
        registerMessage(MessageMineBayClosed.class, MessageMineBayClosed.class, Side.SERVER);
        registerMessage(MessageMicrowave.class, MessageMicrowave.class, Side.SERVER);
        registerMessage(MessageWashingMachine.class, MessageWashingMachine.class, Side.SERVER);
        registerMessage(MessageDishwasher.class, MessageDishwasher.class, Side.SERVER);
        registerMessage(MessageFreezer.class, MessageFreezer.class, Side.SERVER);
        registerMessage(MessageUpdateFields.class, MessageUpdateFields.class, Side.CLIENT);
        registerMessage(MessageDoorMat.class, MessageDoorMat.class, Side.SERVER);
        registerMessage(MessageSealCrate.class, MessageSealCrate.class, Side.SERVER);
        registerMessage(MessageUpdateValueContainer.class, MessageUpdateValueContainer.class, Side.SERVER);
        registerMessage(MessageSignItem.class, MessageSignItem.class, Side.SERVER);
    }
    
    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        INSTANCE.registerMessage(messageHandler, requestMessageType, messageId++, side);
    }
}
