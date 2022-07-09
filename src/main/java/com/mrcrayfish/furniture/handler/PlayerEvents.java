package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import java.util.Random;

public class PlayerEvents
{
    private final String PREFIX = TextFormatting.GOLD + "-> ";

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event)
    {
        EntityPlayer player = event.getPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityMailBox)
        {
            TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tileEntity;
            if(tileEntityMailBox.isClaimed() && !tileEntityMailBox.canOpen(player) && !isAuthorized(player))
            {
                if(!player.world.isRemote)
                {
                    String nobreak = new TextComponentTranslation("cfm.message.mailbox_break").getUnformattedComponentText();
                    player.sendMessage(new TextComponentString(TextFormatting.RED + nobreak));
                    event.setCanceled(true);
                }
            }
        }
    }

    private boolean isAuthorized(EntityPlayer player)
    {
        return player.capabilities.isCreativeMode && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == FurnitureItems.HAMMER;
    }
}
