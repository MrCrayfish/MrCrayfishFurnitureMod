package com.mrcrayfish.furniture.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public interface IMessage<T>
{
    void encode(T message, PacketBuffer buffer);

    T decode(PacketBuffer buffer);

    void handle(T message, Supplier<NetworkEvent.Context> supplier);
}
