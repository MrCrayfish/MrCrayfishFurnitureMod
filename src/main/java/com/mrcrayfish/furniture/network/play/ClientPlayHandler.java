package com.mrcrayfish.furniture.network.play;

import com.mrcrayfish.furniture.client.MailBoxEntry;
import com.mrcrayfish.furniture.client.gui.screen.inventory.PostBoxScreen;
import com.mrcrayfish.furniture.network.message.S2CMessageFlipGrill;
import com.mrcrayfish.furniture.network.message.S2CMessageUpdateMailBoxes;
import com.mrcrayfish.furniture.tileentity.GrillBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ClientPlayHandler
{
    public static void handleUpdateMailboxesMessage(S2CMessageUpdateMailBoxes message)
    {
        if(Minecraft.getInstance().screen instanceof PostBoxScreen screen)
        {
            CompoundTag compound = message.getCompound();
            if(compound.contains("MailBoxes", Tag.TAG_LIST))
            {
                List<MailBoxEntry> entries = new ArrayList<>();
                ListTag mailBoxList = compound.getList("MailBoxes", Tag.TAG_COMPOUND);
                mailBoxList.forEach(nbt -> entries.add(new MailBoxEntry((CompoundTag) nbt)));
                screen.updateMailBoxes(entries);
            }
        }
    }

    public static void handleFlipGrillMessage(S2CMessageFlipGrill message)
    {
        Level level = Minecraft.getInstance().level;
        if(level != null && level.getBlockEntity(message.getPos()) instanceof GrillBlockEntity grill)
        {
            grill.setFlipping(message.getPosition());
        }
    }
}
