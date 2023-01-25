package com.mrcrayfish.furniture.network.play;

import com.mrcrayfish.furniture.network.message.S2CMessageFlipGrill;
import com.mrcrayfish.furniture.tileentity.GrillBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class ClientPlayHandler
{
    public static void handleFlipGrillMessage(S2CMessageFlipGrill message)
    {
        Level level = Minecraft.getInstance().level;
        if(level != null && level.getBlockEntity(message.getPos()) instanceof GrillBlockEntity grill)
        {
            grill.setFlipping(message.getPosition());
        }
    }
}
