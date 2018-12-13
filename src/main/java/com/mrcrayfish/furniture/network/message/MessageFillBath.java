package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityBath;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageFillBath implements IMessage, IMessageHandler<MessageFillBath, IMessage>
{
    private int waterLevel;
    private int bathOneX, bathOneY, bathOneZ;
    private int bathTwoX, bathTwoY, bathTwoZ;

    public MessageFillBath() {}

    public MessageFillBath(int waterLevel, int x1, int y1, int z1, int x2, int y2, int z2)
    {
        this.waterLevel = waterLevel;
        this.bathOneX = x1;
        this.bathOneY = y1;
        this.bathOneZ = z1;
        this.bathTwoX = x2;
        this.bathTwoY = y2;
        this.bathTwoZ = z2;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.waterLevel = buf.readInt();
        this.bathOneX = buf.readInt();
        this.bathOneY = buf.readInt();
        this.bathOneZ = buf.readInt();
        this.bathTwoX = buf.readInt();
        this.bathTwoY = buf.readInt();
        this.bathTwoZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(waterLevel);
        buf.writeInt(bathOneX);
        buf.writeInt(bathOneY);
        buf.writeInt(bathOneZ);
        buf.writeInt(bathTwoX);
        buf.writeInt(bathTwoY);
        buf.writeInt(bathTwoZ);
    }

    @Override
    public IMessage onMessage(MessageFillBath message, MessageContext ctx)
    {
        if(ctx.side.isClient())
        {
            EntityPlayer player = MrCrayfishFurnitureMod.proxy.getClientPlayer();

            TileEntity tile_entity = player.world.getTileEntity(new BlockPos(message.bathOneX, message.bathOneY, message.bathOneZ));
            if(tile_entity instanceof TileEntityBath)
            {
                TileEntityBath tileEntityBath = (TileEntityBath) tile_entity;
                tileEntityBath.setWaterLevel(message.waterLevel);
                BlockPos pos = new BlockPos(message.bathOneX, message.bathOneY, message.bathOneZ);
                TileEntityUtil.markBlockForUpdate(player.world, pos);
            }

            TileEntity tile_entity2 = player.world.getTileEntity(new BlockPos(message.bathTwoX, message.bathTwoY, message.bathTwoZ));
            if(tile_entity2 instanceof TileEntityBath)
            {
                TileEntityBath tileEntityBath = (TileEntityBath) tile_entity2;
                tileEntityBath.setWaterLevel(message.waterLevel);
                BlockPos pos = new BlockPos(message.bathTwoX, message.bathTwoY, message.bathTwoZ);
                TileEntityUtil.markBlockForUpdate(player.world, pos);
            }
        }
        return null;
    }
}