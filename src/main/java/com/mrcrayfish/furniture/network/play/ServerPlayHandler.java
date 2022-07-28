package com.mrcrayfish.furniture.network.play;

import com.mrcrayfish.furniture.common.mail.Mail;
import com.mrcrayfish.furniture.common.mail.MailBox;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.inventory.container.CrateMenu;
import com.mrcrayfish.furniture.inventory.container.PostBoxMenu;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.C2SMessageLockCrate;
import com.mrcrayfish.furniture.network.message.C2SMessageOpenMailBox;
import com.mrcrayfish.furniture.network.message.C2SMessageRequestMailBoxes;
import com.mrcrayfish.furniture.network.message.C2SMessageSendMail;
import com.mrcrayfish.furniture.network.message.C2SMessageSetDoorMat;
import com.mrcrayfish.furniture.network.message.C2SMessageSetMailBoxName;
import com.mrcrayfish.furniture.network.message.S2CMessageUpdateMailBoxes;
import com.mrcrayfish.furniture.tileentity.CrateBlockEntity;
import com.mrcrayfish.furniture.tileentity.DoorMatBlockEntity;
import com.mrcrayfish.furniture.tileentity.MailBoxBlockEntity;
import com.mrcrayfish.furniture.util.BlockEntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ServerPlayHandler
{
    public static void handleLockCrateMessage(ServerPlayer player, C2SMessageLockCrate message)
    {
        if(!(player.containerMenu instanceof CrateMenu crateMenu))
            return;

        CrateBlockEntity crate = crateMenu.getBlockEntity();
        if(crate == null)
            return;

        if(crate.getOwner() == null)
        {
            crate.setOwner(player.getUUID());
        }

        if(player.getUUID().equals(crate.getOwner()))
        {
            crate.setLocked(!crate.isLocked());
            crate.removeUnauthorisedPlayers();
        }
    }

    public static void handleOpenMailBoxMessage(ServerPlayer player, C2SMessageOpenMailBox message)
    {
        Level level = player.getLevel();
        if(!level.isLoaded(message.getPos()))
            return;

        if(!(level.getBlockEntity(message.getPos()) instanceof MailBoxBlockEntity blockEntity))
            return;

        if(!blockEntity.stillValid(player))
            return;

        BlockEntityUtil.sendUpdatePacket(blockEntity);
        NetworkHooks.openScreen(player, blockEntity, message.getPos());
    }

    public static void handleRequestMailBoxesMessage(ServerPlayer player, C2SMessageRequestMailBoxes message)
    {
        if(player.containerMenu instanceof PostBoxMenu)
        {
            List<MailBox> mailBoxes = PostOffice.getMailBoxes(player);
            CompoundTag compound = new CompoundTag();
            ListTag mailBoxList = new ListTag();
            mailBoxes.forEach(mailBox -> mailBoxList.add(mailBox.serializeDetails()));
            compound.put("MailBoxes", mailBoxList);
            PacketHandler.getPlayChannel().send(PacketDistributor.PLAYER.with(() -> player), new S2CMessageUpdateMailBoxes(compound));
        }
    }

    public static void handleSendMailMessage(ServerPlayer player, C2SMessageSendMail message)
    {
        if(!(player.containerMenu instanceof PostBoxMenu postBox))
            return;

        if(postBox.getMail().isEmpty())
            return;

        Mail mail = new Mail("Yo", postBox.getMail(), player.getName().getString());
        if(!PostOffice.sendMailToPlayer(message.getPlayerId(), message.getMailBoxId(), mail))
{
            player.sendSystemMessage(Component.translatable("message.cfm.mail_queue_full"));
            return;
        }

        postBox.removeMail();
    }

    public static void handleSetDoorMatMessage(ServerPlayer player, C2SMessageSetDoorMat message)
    {
        Level level = player.getLevel();
        if(!level.isLoaded(message.getPos()))
            return;

        if(!(level.getBlockEntity(message.getPos()) instanceof DoorMatBlockEntity doorMat))
            return;

        doorMat.setMessage(message.getMessage());
    }

    public static void handleSetMailBoxNameMessage(ServerPlayer player, C2SMessageSetMailBoxName message)
    {
        Level level = player.getLevel();
        if(!level.isLoaded(message.getPos()))
            return;

        if(!(level.getBlockEntity(message.getPos()) instanceof MailBoxBlockEntity mailBox))
            return;

        if(!player.getUUID().equals(mailBox.getOwnerId()))
            return;

        if(!mailBox.stillValid(player))
            return;

        if(!PostOffice.setMailBoxName(player.getUUID(), mailBox.getId(), message.getName()))
            return;

        BlockEntityUtil.sendUpdatePacket(mailBox);
        NetworkHooks.openScreen(player, mailBox, message.getPos());
    }
}
