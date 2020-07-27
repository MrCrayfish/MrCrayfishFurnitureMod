package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public abstract class FluidHandlerSyncedTileEntity extends TileEntity
{
    protected final FluidTank tank;

    public FluidHandlerSyncedTileEntity(TileEntityType<?> tileEntityTypeIn, int capacity)
    {
        super(tileEntityTypeIn);
        this.tank = new FluidTank(capacity)
        {
            @Override
            protected void onContentsChanged()
            {
                FluidHandlerSyncedTileEntity.this.syncFluidToClient();
            }
        };
    }

    public FluidTank getTank()
    {
        return this.tank;
    }

    private void syncFluidToClient()
    {
        TileEntityUtil.sendUpdatePacket(this, this.write(new CompoundNBT()));
    }

    @Override
    public void read(BlockState blockState, CompoundNBT tag)
    {
        //TODO may need to implement fluid fix from Vehicle Mod. See FluidUtils#fixEmptyTag(NBTTagCompound tag)
        super.read(blockState, tag);
        this.tank.readFromNBT(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        this.tank.writeToNBT(tag);
        return super.write(tag);
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.read(this.getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return LazyOptional.of(() -> this.tank).cast();
        return super.getCapability(capability, facing);
    }
}
