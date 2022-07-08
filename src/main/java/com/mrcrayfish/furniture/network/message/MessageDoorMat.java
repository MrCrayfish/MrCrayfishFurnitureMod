package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.gui.containers.ContainerFreezer;
import com.mrcrayfish.furniture.tileentity.TileEntityDoorMat;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDoorMat implements IMessage, IMessageHandler<MessageDoorMat, IMessage>
{
    public int x, y, z;
    private String message;

    public MessageDoorMat() {}

    public MessageDoorMat(int x, int y, int z, String message)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.message = message;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        ByteBufUtils.writeUTF8String(buf, message);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.message = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public IMessage onMessage(MessageDoorMat message, MessageContext ctx)
    {
        World world = ctx.getServerHandler().player.world;
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        if(!world.isAreaLoaded(pos, 0))
            return null;
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityDoorMat)
        {
            TileEntityDoorMat doorMat = (TileEntityDoorMat) tileEntity;
            doorMat.setMessage(message.message);
            TileEntityUtil.markBlockForUpdate(world, pos);
        }
        return null;
    }
}
