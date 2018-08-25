package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityBasin;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Client Packet
public class MessageFillBasin implements IMessage, IMessageHandler<MessageFillBasin, IMessage>
{

    private boolean hasWater;
    private int x, y, z;

    public MessageFillBasin()
    {
    }

    public MessageFillBasin(boolean hasWater, int x, int y, int z)
    {
        this.hasWater = hasWater;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hasWater = buf.readBoolean();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(hasWater);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageFillBasin message, MessageContext ctx)
    {
        if(ctx.side.isClient())
        {
            EntityPlayer player = MrCrayfishFurnitureMod.proxy.getClientPlayer();
            TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
            if(tile_entity instanceof TileEntityBasin)
            {
                TileEntityBasin tileEntityBasin = (TileEntityBasin) tile_entity;
                tileEntityBasin.setHasWater(message.hasWater);
            }
            BlockPos pos = new BlockPos(message.x, message.y, message.z);
            TileEntityUtil.markBlockForUpdate(player.world, pos);
        }
        return null;
    }
}
