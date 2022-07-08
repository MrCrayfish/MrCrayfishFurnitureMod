package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.gui.containers.ContainerComputer;
import com.mrcrayfish.furniture.gui.containers.ContainerWashingMachine;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWashingMachine implements IMessage, IMessageHandler<MessageWashingMachine, IMessage>
{
    private int type;
    private int x, y, z;

    public MessageWashingMachine() {}

    public MessageWashingMachine(int type, int x, int y, int z)
    {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.type = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(type);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageWashingMachine message, MessageContext ctx)
    {
        World world = ctx.getServerHandler().player.world;
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
        if(!world.isAreaLoaded(pos, 0))
            return null;

        Container container = ctx.getServerHandler().player.openContainer;
        if(!(container instanceof ContainerWashingMachine))
            return null;

        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityWashingMachine)
        {
            TileEntityWashingMachine tileEntityWashineMachine = (TileEntityWashingMachine) tileEntity;
            if(((ContainerWashingMachine) container).getWashingMachineInventory() != tileEntityWashineMachine)
                return null;

            if(message.type == 0)
            {
                tileEntityWashineMachine.startWashing();
            }
            if(message.type == 1)
            {
                tileEntityWashineMachine.stopWashing();
            }
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
        return null;
    }
}
