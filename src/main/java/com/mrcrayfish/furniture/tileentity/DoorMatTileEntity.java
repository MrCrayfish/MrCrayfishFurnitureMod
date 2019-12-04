package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class DoorMatTileEntity extends TileEntity
{
    private String message = "";

    public DoorMatTileEntity()
    {
        super(ModTileEntities.DOOR_MAT);
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if(compound.contains("Message", Constants.NBT.TAG_STRING))
        {
            this.message = compound.getString("Message");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.putString("Message", this.message);
        return super.write(compound);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
}
