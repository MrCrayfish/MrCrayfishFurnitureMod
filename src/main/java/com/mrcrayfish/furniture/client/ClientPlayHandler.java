package com.mrcrayfish.furniture.client;

import com.mrcrayfish.furniture.network.message.MessageFlipGrill;
import com.mrcrayfish.furniture.tileentity.GrillBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class ClientPlayHandler
{
    public static void handleFlipGrillMessage(MessageFlipGrill message)
    {
        Level level = Minecraft.getInstance().level;
        if(level != null && level.getBlockEntity(message.getPos()) instanceof GrillBlockEntity blockEntity)
        {
            blockEntity.setFlipping(message.getPosition());
        }
    }
}
