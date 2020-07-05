package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

public class InputHandler
{
    public static final KeyBinding KEY_FART = new KeyBinding("key.fart.desc", Keyboard.KEY_G, "keys.cfm.category");

    public InputHandler()
    {
        ClientRegistry.registerKeyBinding(KEY_FART);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent event)
    {
        if (KEY_FART.isPressed())
        {
            World world = Minecraft.getMinecraft().world;
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (world.getBlockState(new BlockPos((int) Math.floor(player.posX), (int) Math.floor(player.posY), (int) Math.floor(player.posZ))).getBlock() == FurnitureBlocks.TOILET)
            {
                PacketHandler.INSTANCE.sendToServer(new MessageFart());
            }
        }
    }
}
