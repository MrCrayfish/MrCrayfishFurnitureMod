package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.FluidAttributes;

/**
 * Author: MrCrayfish
 */
public class SinkTileEntity extends FluidHandlerSyncedTileEntity
{
    protected SinkTileEntity(TileEntityType<?> type, int capacity)
    {
        super(type, capacity);
    }

    public SinkTileEntity()
    {
        super(ModTileEntities.SINK.get(), FluidAttributes.BUCKET_VOLUME * 10);
    }
}
