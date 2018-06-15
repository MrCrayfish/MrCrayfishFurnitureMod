package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.blocks.tv.Channels;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Author: MrCrayfish
 */
public class ClientEvents
{
    public static TextureMap map;

    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event)
    {
        Channels.SOUND_POSITIONS.clear();
    }

    @SubscribeEvent
    public void onTextureStitchPre(TextureStitchEvent.Pre event)
    {
        map = event.getMap();
        registerSprite(new ResourceLocation(Reference.MOD_ID, "items/outline_pickaxe"));
        registerSprite(new ResourceLocation(Reference.MOD_ID, "items/outline_shovel"));
        registerSprite(new ResourceLocation(Reference.MOD_ID, "items/outline_sword"));
        registerSprite(new ResourceLocation(Reference.MOD_ID, "items/outline_axe"));
        registerSprite(new ResourceLocation(Reference.MOD_ID, "items/outline_hoe"));
        registerSprite(new ResourceLocation(Reference.MOD_ID, "items/outline_bucket"));
    }

    private void registerSprite(ResourceLocation location)
    {
        map.registerSprite(location);
    }
}
