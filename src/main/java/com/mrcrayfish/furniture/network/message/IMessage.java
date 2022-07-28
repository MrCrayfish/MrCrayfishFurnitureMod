package com.mrcrayfish.furniture.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public interface IMessage<T>
{
    void encode(T message, FriendlyByteBuf buffer);

    T decode(FriendlyByteBuf buffer);

    void handle(T message, Supplier<NetworkEvent.Context> supplier);

    static <T extends IMessage<T>> void callServerConsumer(T t, Supplier<NetworkEvent.Context> supplier, BiConsumer<ServerPlayer, T> consumer)
    {
        ServerPlayer player = supplier.get().getSender();
        if(player != null)
        {
            consumer.accept(player, t);
        }
    }
}
