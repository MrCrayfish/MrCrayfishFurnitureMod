package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class DoorMatTileEntity extends TileEntity
{
    private String message = null;

    public DoorMatTileEntity()
    {
        super(ModTileEntities.DOOR_MAT.get());
    }

    public void setMessage(String message)
    {
        if(this.message == null)
        {
            this.message = message;
            TileEntityUtil.sendUpdatePacket(this);
        }
    }

    public String getMessage()
    {
        return message != null ? message : "";
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound)
    {
        super.read(blockState, compound);
        if(compound.contains("Message", Constants.NBT.TAG_STRING))
        {
            this.message = compound.getString("Message");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        if(this.message != null)
        {
            compound.putString("Message", this.message);
        }
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

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.read(this.getBlockState() ,pkt.getNbtCompound());
    }
}
