package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class MicrowaveTileEntity extends TileEntity implements ITickableTileEntity
{

    public MicrowaveTileEntity(TileEntityType<?> tileEntityType)
    {
        super(tileEntityType);
    }

    public MicrowaveTileEntity() {
        this(ModTileEntities.MICROWAVE.get());
    }

    @Override
    public void tick() {

    }
}