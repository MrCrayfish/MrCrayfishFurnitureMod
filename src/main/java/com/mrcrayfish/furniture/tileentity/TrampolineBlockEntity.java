package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class TrampolineBlockEntity extends BlockEntity
{
    private int count = 1;
    private DyeColor colour = DyeColor.WHITE;

    public TrampolineBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.TRAMPOLINE.get(), pos, state);
    }

    protected TrampolineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public void onLoad()
    {
        BlockEntityUtil.sendUpdatePacket(this);
    }

    public void setCount(int count)
    {
        this.count = count;
        BlockEntityUtil.sendUpdatePacket(this);
    }

    public int getCount()
    {
        return this.count;
    }

    public DyeColor getColour()
    {
        return this.colour;
    }

    public void setColour(DyeColor colour)
    {
        this.colour = colour;
    }

    public void updateCount()
    {
        Set<TrampolineBlockEntity> trampolines = new HashSet<>();
        this.isTrampoline(trampolines, this.worldPosition);
        trampolines.forEach(trampoline -> trampoline.setCount(trampolines.size()));
    }

    private void isTrampoline(Set<TrampolineBlockEntity> trampolines, BlockPos pos)
    {
        if(this.level == null)
            return;

        BlockEntity tileEntity = this.level.getBlockEntity(pos);
        if(tileEntity instanceof TrampolineBlockEntity)
        {
            if(trampolines.contains(tileEntity))
                return;

            trampolines.add((TrampolineBlockEntity) tileEntity);
            this.isTrampoline(trampolines, pos.relative(Direction.NORTH));
            this.isTrampoline(trampolines, pos.relative(Direction.EAST));
            this.isTrampoline(trampolines, pos.relative(Direction.SOUTH));
            this.isTrampoline(trampolines, pos.relative(Direction.WEST));
        }
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.readData(compound);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        this.writeData(tag);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithFullMetadata();
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        CompoundTag compound = pkt.getTag();
        this.readData(compound);
    }

    private void readData(CompoundTag compound)
    {
        if(compound.contains("Count", Tag.TAG_INT))
        {
            this.count = compound.getInt("Count");
        }
        if(compound.contains("Color", Tag.TAG_INT))
        {
            this.colour = DyeColor.byId(compound.getInt("Color"));
        }
    }

    private CompoundTag writeData(CompoundTag compound)
    {
        compound.putInt("Count", this.count);
        compound.putInt("Color", this.colour.getId());
        return compound;
    }

    @Nonnull
    @Override
    public IModelData getModelData()
    {
        return super.getModelData();
    }
}
