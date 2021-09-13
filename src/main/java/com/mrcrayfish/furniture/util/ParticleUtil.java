package com.mrcrayfish.furniture.util;

import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.core.ModParticles;
import com.mrcrayfish.furniture.particles.ShowerParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleUtil
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particles.registerFactory(ModParticles.SHOWER_PARTICLE.get(), ShowerParticle.Factory::new);
    }
}
