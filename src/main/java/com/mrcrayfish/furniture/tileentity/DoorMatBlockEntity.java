package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlockEntity extends BlockEntity
{
    private String message = null;

    protected DoorMatBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public DoorMatBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.DOOR_MAT.get(), pos, state);
    }

    public void setMessage(String message)
    {
        if(this.message == null)
        {
            this.message = message;
            BlockEntityUtil.sendUpdatePacket(this);
        }
    }

    public String getMessage()
    {
        return message != null ? message : "";
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        if(compound.contains("Message", Constants.NBT.TAG_STRING))
        {
            this.message = compound.getString("Message");
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        if(this.message != null)
        {
            compound.putString("Message", this.message);
        }
        return super.save(compound);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        this.load(pkt.getTag());
    }
}
