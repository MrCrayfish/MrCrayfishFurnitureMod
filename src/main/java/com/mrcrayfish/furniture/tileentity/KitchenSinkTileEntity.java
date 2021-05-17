package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraftforge.fluids.FluidAttributes;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkTileEntity extends FluidHandlerSyncedTileEntity
{
    public KitchenSinkTileEntity()
    {
        super(ModTileEntities.KITCHEN_SINK.get(), FluidAttributes.BUCKET_VOLUME * 10);
    }
}
