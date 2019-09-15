package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.tileentity.TileEntityComputer;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMineBayBrowse implements IMessage, IMessageHandler<MessageMineBayBrowse, IMessage>
{
    private int itemNum, x, y, z;

    public MessageMineBayBrowse() {}

    public MessageMineBayBrowse(int itemNum, int x, int y, int z)
    {
        this.itemNum = itemNum;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.itemNum = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(itemNum);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageMineBayBrowse message, MessageContext ctx)
    {
        final WorldServer world = ctx.getServerHandler().player.getServerWorld();
        world.addScheduledTask(() -> {
            EntityPlayerMP player = ctx.getServerHandler().player;

            TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
            if(tile_entity instanceof TileEntityComputer)
            {
                TileEntityComputer tileEntityComputer = (TileEntityComputer) tile_entity;
                tileEntityComputer.setBrowsingInfo(message.itemNum);
                BlockPos pos = new BlockPos(message.x, message.y, message.z);
                TileEntityUtil.markBlockForUpdate(ctx.getServerHandler().player.world, pos);
            }
        });

        return null;
    }
}
