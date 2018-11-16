package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.tileentity.TileEntityComputer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMineBayBuy implements IMessage, IMessageHandler<MessageMineBayBuy, IMessage>
{
    private int itemNum, x, y, z;

    public MessageMineBayBuy() {}

    public MessageMineBayBuy(int itemNum, int x, int y, int z)
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
    public IMessage onMessage(MessageMineBayBuy message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().player;
        TileEntity tileEntity = player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
        if(tileEntity instanceof TileEntityComputer)
        {
            TileEntityComputer tileEntityComputer = (TileEntityComputer) tileEntity;
            ItemStack buySlot = tileEntityComputer.getStackInSlot(0);
            if(buySlot.isEmpty())
                return null;

            RecipeData[] data = Recipes.getMineBayItems();
            int price = data[message.itemNum].getPrice();
            if(buySlot.getCount() >= price)
            {
                tileEntityComputer.takeEmeraldFromSlot(price);
                EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY + 1, player.posZ, data[message.itemNum].getInput().copy());
                player.world.spawnEntity(entityItem);
                Triggers.trigger(Triggers.MINEBAY_PURCHASE, player);
            }
        }
        return null;
    }

}
