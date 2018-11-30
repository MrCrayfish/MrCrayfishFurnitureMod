package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFart;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import org.lwjgl.input.Keyboard;

public class InputHandler
{
    public static final KeyBinding KEY_FART = new KeyBinding("key.fart.desc", Keyboard.KEY_G, "keys.cfm.category");
    public static boolean keyPressed = false;

    public InputHandler()
    {
        ClientRegistry.registerKeyBinding(KEY_FART);
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event)
    {
        if(KEY_FART.isPressed())
        {
            keyPressed = true;
        }
    }

    @SubscribeEvent
    public void playerTick(PlayerTickEvent event)
    {
        if(event.player.world.getBlockState(new BlockPos((int) Math.floor(event.player.posX), (int) Math.floor(event.player.posY), (int) Math.floor(event.player.posZ))).getBlock() == FurnitureBlocks.TOILET)
        {
            if(keyPressed)
            {
                keyPressed = false;
                PacketHandler.INSTANCE.sendToServer(new MessageFart());
            }
        }
    }
}
