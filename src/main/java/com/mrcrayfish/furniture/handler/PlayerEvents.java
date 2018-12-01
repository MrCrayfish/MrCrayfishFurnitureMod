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
    public void onPlayerLogin(PlayerLoggedInEvent e)
    {
        EntityPlayer player = e.player;
        if(ConfigurationHandler.canDisplay)
        {
            if(!player.world.isRemote)
            {
                if(!ConfigurationHandler.hasDisplayedOnce)
                {
                    TextComponentTranslation prefix = new TextComponentTranslation("cfm.message.login1");
                    prefix.getStyle().setColor(TextFormatting.GOLD);
                    prefix.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("cfm.message.login1.info")));
                    player.sendMessage(prefix);

                    TextComponentString url;
                    Random rand = new Random();
                    int r = rand.nextInt(4);
                    String login = new TextComponentTranslation("cfm.message.login" + (r + 2)).getUnformattedComponentText();
                    player.sendMessage(new TextComponentString(PREFIX + TextFormatting.GREEN + login));
                    switch(r)
                    {
                        case 0:
                            url = new TextComponentString(PREFIX + TextFormatting.RESET + "mrcrayfish.com");
                            url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mrcrayfish.com/mods"));
                            url.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("cfm.message.login2.info")));
                            player.sendMessage(url);
                            break;
                        case 1:
                            url = new TextComponentString(PREFIX + TextFormatting.RESET + "mrcrayfishs-furniture-mod.wikia.com");
                            url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mrcrayfishs-furniture-mod.wikia.com/"));
                            url.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("cfm.message.login2.info")));
                            player.sendMessage(url);
                            break;
                        case 2:
                            url = new TextComponentString(PREFIX + TextFormatting.RESET + "youtube.com/user/MrCrayfishMinecraft");
                            url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/user/MrCrayfishMinecraft"));
                            url.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("cfm.message.login2.info")));
                            player.sendMessage(url);
                            break;
                        case 3:
                            url = new TextComponentString(PREFIX + TextFormatting.RESET + "mrcrayfish.com/furniture-comm-edition");
                            url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mrcrayfish.com/furniture-comm-edition"));
                            url.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("cfm.message.login2.info")));
                            player.sendMessage(url);
                            break;
                    }
                    //ConfigurationHandler.hasDisplayedOnce = true;
                }
            }
        }
    }

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
