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
public class MessageLockCrate
{
    public MessageLockCrate() {}

    public static void encode(MessageLockCrate message, PacketBuffer buffer) {}

    public static MessageLockCrate decode(PacketBuffer buffer)
    {
        return new MessageLockCrate();
    }

    public static void handle(MessageLockCrate message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = supplier.get().getSender();
            if(player != null && player.openContainer instanceof CrateContainer)
            {
                IInventory inventory = ((CrateContainer) player.openContainer).getCrateTileEntity();
                if(inventory instanceof CrateTileEntity)
                {
                    CrateTileEntity crateTileEntity = (CrateTileEntity) inventory;
                    if(crateTileEntity.getOwner() == null)
                    {
                        crateTileEntity.setOwner(player.getUniqueID());
                    }
                    if(player.getUniqueID().equals(crateTileEntity.getOwner()))
                    {
                        crateTileEntity.setLocked(!crateTileEntity.isLocked());
                        crateTileEntity.removeUnauthorisedPlayers();
                    }
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
