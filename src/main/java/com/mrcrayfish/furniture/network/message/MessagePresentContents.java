package com.mrcrayfish.furniture.network.message;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//Server
public class MessagePresentContents implements IMessage, IMessageHandler<MessagePresentContents, IMessage>
{

    private ItemStack envelope;

    public MessagePresentContents()
    {
    }

    public MessagePresentContents(ItemStack envelope)
    {
        this.envelope = envelope;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.envelope = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeItemStack(buf, envelope);
    }

    @Override
    public IMessage onMessage(MessagePresentContents message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        ItemStack present = player.getHeldItem(EnumHand.MAIN_HAND);
		
		if (present.getItem() != Item.getItemFromBlock(FurnitureBlocks.PRESENT)) return null;
		
        ItemStack signedPresent = new ItemStack(FurnitureBlocks.PRESENT, 1, present.getMetadata());
        signedPresent.setTagCompound(present.getTagCompound());
        signedPresent.setTagInfo("Author", new NBTTagString(player.getName()));
        String text = new TextComponentTranslation("tile.present_wrapped.name").getUnformattedComponentText();
        signedPresent.setStackDisplayName(TextFormatting.GREEN + text);
        player.inventory.setInventorySlotContents(player.inventory.currentItem, signedPresent);

        return null;
    }

}
