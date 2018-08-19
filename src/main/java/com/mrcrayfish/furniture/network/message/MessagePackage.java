package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.init.FurnitureItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Server Side
public class MessagePackage implements IMessage, IMessageHandler<MessagePackage, IMessage>
{
    private ItemStack package_;
    private int slot;

    public MessagePackage()
    {
    }

    public MessagePackage(ItemStack package_, int slot)
    {
        this.package_ = package_;
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.package_ = ByteBufUtils.readItemStack(buf);
        this.slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeItemStack(buf, package_);
        buf.writeInt(slot);
    }

    @Override
    public IMessage onMessage(MessagePackage message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        ItemStack mail = player.getHeldItem(EnumHand.MAIN_HAND);
		
		if (mail.getItem() != FurnitureItems.PACKAGE) return null;
		
        ItemStack signedMail = new ItemStack(FurnitureItems.PACKAGE_SIGNED);
        signedMail.setTagCompound(mail.getTagCompound());
        signedMail.setTagInfo("Author", new NBTTagString(player.getName()));
        player.setHeldItem(player.getActiveHand(), signedMail);
        return null;
    }

}
