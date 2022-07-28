package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.network.play.ClientPlayHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class S2CMessageFlipGrill implements IMessage<S2CMessageFlipGrill>
{
    private BlockPos pos;
    private int position;

    public S2CMessageFlipGrill() {}

    public S2CMessageFlipGrill(BlockPos pos, int position)
    {
        this.pos = pos;
        this.position = position;
    }

    @Override
    public void encode(S2CMessageFlipGrill message, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(message.pos);
        buffer.writeInt(message.position);
    }

    @Override
    public S2CMessageFlipGrill decode(FriendlyByteBuf buffer)
    {
        return new S2CMessageFlipGrill(buffer.readBlockPos(), buffer.readInt());
    }

    @Override
    public void handle(S2CMessageFlipGrill message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleFlipGrillMessage(message));
        supplier.get().setPacketHandled(true);
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public int getPosition()
    {
        return this.position;
    }
}
