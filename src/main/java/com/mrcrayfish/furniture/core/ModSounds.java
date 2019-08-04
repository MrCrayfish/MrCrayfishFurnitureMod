package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.util.SoundNames;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds
{
    private static final List<SoundEvent> SOUNDS = new ArrayList<>();

    public static final SoundEvent BLOCK_CABINET_OPEN = register(SoundNames.CABINET_OPEN);
    public static final SoundEvent BLOCK_CABINET_CLOSE = register(SoundNames.CABINET_CLOSE);
    public static final SoundEvent BLOCK_BEDSIDE_CABINET_OPEN = register(SoundNames.BEDSIDE_CABINET_OPEN);
    public static final SoundEvent BLOCK_BEDSIDE_CABINET_CLOSE = register(SoundNames.BEDSIDE_CABINET_CLOSE);
    public static final SoundEvent BLOCK_BLINDS_OPEN = register(SoundNames.BLINDS_OPEN);
    public static final SoundEvent BLOCK_BLINDS_CLOSE = register(SoundNames.BLINDS_CLOSE);

    private static SoundEvent register(String name)
    {
        SoundEvent event = new SoundEvent(new ResourceLocation(name));
        event.setRegistryName(name);
        SOUNDS.add(event);
        return event;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event)
    {
        SOUNDS.forEach(soundEvent -> event.getRegistry().register(soundEvent));
        SOUNDS.clear();
    }
}
