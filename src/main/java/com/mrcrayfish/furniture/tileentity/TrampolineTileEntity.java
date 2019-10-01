package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class TrampolineTileEntity extends TileEntity
{
    private int count = 1;

    public TrampolineTileEntity()
    {
        super(ModTileEntities.TRAMPOLINE);
    }

    @Override
    public void onLoad()
    {
        TileEntityUtil.sendUpdatePacket(this);
    }

    public void setCount(int count)
    {
        this.count = count;
        TileEntityUtil.sendUpdatePacket(this);
    }

    public int getCount()
    {
        return count;
    }

    public void updateCount()
    {
        Set<TrampolineTileEntity> trampolines = new HashSet<>();
        this.isTrampoline(trampolines, this.pos);
        trampolines.forEach(trampoline -> trampoline.setCount(trampolines.size()));
    }

    private void isTrampoline(Set<TrampolineTileEntity> trampolines, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TrampolineTileEntity)
        {
            if(trampolines.contains(tileEntity))
                return;

            trampolines.add((TrampolineTileEntity) tileEntity);
            this.isTrampoline(trampolines, pos.offset(Direction.NORTH));
            this.isTrampoline(trampolines, pos.offset(Direction.EAST));
            this.isTrampoline(trampolines, pos.offset(Direction.SOUTH));
            this.isTrampoline(trampolines, pos.offset(Direction.WEST));
        }
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        this.readData(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        this.writeData(compound);
        return super.write(compound);
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
        CompoundNBT compound = pkt.getNbtCompound();
        this.readData(compound);
    }

    private void readData(CompoundNBT compound)
    {
        if(compound.contains("Count", Constants.NBT.TAG_INT))
        {
            this.count = compound.getInt("Count");
        }
    }

    private CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("Count", this.count);
        return compound;
    }
}
