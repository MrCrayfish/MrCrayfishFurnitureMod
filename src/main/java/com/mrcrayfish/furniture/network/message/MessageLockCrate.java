package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import com.mrcrayfish.furniture.tileentity.CrateTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MessageLockCrate implements IMessage<MessageLockCrate>
{
    @Override
    public void encode(MessageLockCrate message, PacketBuffer buffer) {}

    public MessageLockCrate decode(PacketBuffer buffer)
    {
        return new MessageLockCrate();
    }

    @Override
    public void handle(MessageLockCrate message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = supplier.get().getSender();
            if(player != null && player.openContainer instanceof CrateContainer)
            {
                CrateTileEntity inventory = ((CrateContainer) player.openContainer).getCrateTileEntity();
                if(inventory != null)
                {
                    if(inventory.getOwner() == null)
                    {
                        inventory.setOwner(player.getUniqueID());
                    }
                    if(player.getUniqueID().equals(inventory.getOwner()))
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
