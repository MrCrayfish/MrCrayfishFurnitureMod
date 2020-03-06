package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.FluidAttributes;

/**
 * Author: MrCrayfish
 */
public class KitchenSinkTileEntity extends FluidHandlerSyncedTileEntity
{
    public KitchenSinkTileEntity()
    {
        super(ModTileEntities.KITCHEN_SINK, FluidAttributes.BUCKET_VOLUME * 10);
    }

    /**
     * Alternative Constructor useful when reusing this class.
     * Needed because registering tile entities requires valid blocks.
     * @see TileEntityType
     * @param tileEntityType : The TileEntityType you use instead.
     */
    public KitchenSinkTileEntity(TileEntityType<?> tileEntityType) { super(tileEntityType, FluidAttributes.BUCKET_VOLUME * 10); }

}
