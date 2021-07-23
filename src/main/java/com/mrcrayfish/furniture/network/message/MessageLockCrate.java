package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.inventory.container.CrateMenu;
import com.mrcrayfish.furniture.tileentity.CrateBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageLockCrate implements IMessage<MessageLockCrate>
{
    @Override
    public void encode(MessageLockCrate message, FriendlyByteBuf buffer) {}

    public MessageLockCrate decode(FriendlyByteBuf buffer)
    {
        return new MessageLockCrate();
    }

    @Override
    public void handle(MessageLockCrate message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && player.containerMenu instanceof CrateMenu)
            {
                CrateBlockEntity inventory = ((CrateMenu) player.containerMenu).getBlockEntity();
                if(inventory != null)
                {
                    if(inventory.getOwner() == null)
                    {
                        inventory.setOwner(player.getUUID());
                    }
                    if(player.getUUID().equals(inventory.getOwner()))
                    {
                        inventory.setLocked(!inventory.isLocked());
                        inventory.removeUnauthorisedPlayers();
                    }
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
