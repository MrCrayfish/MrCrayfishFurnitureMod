package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.FluidAttributes;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkTileEntity extends FluidHandlerSyncedTileEntity
{
    protected KitchenSinkTileEntity(TileEntityType<?> type, int capacity)
    {
        super(type, capacity);
    }

    public KitchenSinkTileEntity()
    {
        super(ModTileEntities.KITCHEN_SINK.get(), FluidAttributes.BUCKET_VOLUME * 10);
    }
}
