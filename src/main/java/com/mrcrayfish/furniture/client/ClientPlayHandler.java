package com.mrcrayfish.furniture.client;

import com.mrcrayfish.furniture.client.gui.screen.DoorMatScreen;
import com.mrcrayfish.furniture.client.gui.screen.inventory.PostBoxScreen;
import com.mrcrayfish.furniture.network.message.MessageFlipGrill;
import com.mrcrayfish.furniture.network.message.MessageUpdateMailBoxes;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ClientPlayHandler
{
    public static void handleUpdateMailboxesMessage(MessageUpdateMailBoxes message)
    {
        if(Minecraft.getInstance().currentScreen instanceof PostBoxScreen)
        {
            CompoundNBT compound = message.getCompound();
            if(compound.contains("MailBoxes", Constants.NBT.TAG_LIST))
            {
                List<MailBoxEntry> entries = new ArrayList<>();
                ListNBT mailBoxList = compound.getList("MailBoxes", Constants.NBT.TAG_COMPOUND);
                mailBoxList.forEach(nbt -> entries.add(new MailBoxEntry((CompoundNBT) nbt)));
                ((PostBoxScreen) Minecraft.getInstance().currentScreen).updateMailBoxes(entries);
            }
        }
    }

    public static void handleFlipGrillMessage(MessageFlipGrill message)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft.world != null)
        {
            TileEntity tileEntity = minecraft.world.getTileEntity(message.getPos());
            if(tileEntity instanceof GrillTileEntity)
            {
                ((GrillTileEntity) tileEntity).setFlipping(message.getPosition());
            }
        }
    }
}
