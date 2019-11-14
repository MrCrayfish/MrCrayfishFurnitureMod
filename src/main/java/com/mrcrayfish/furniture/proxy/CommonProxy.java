package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.network.PacketHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

/**
 * Author: MrCrayfish
 */
public class CommonProxy
{
    public void onSetupCommon()
    {
        PacketHandler.init();
    }

    public void onSetupClient() {}

    public void updateMailBoxes(CompoundNBT nbt) {}

    public boolean useFancyGraphics()
    {
        return false;
    }

    public void setGrillFlipping(BlockPos pos, int position) {}
}
