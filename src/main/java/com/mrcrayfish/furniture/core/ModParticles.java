package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

public class ModParticles
{
    public static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MOD_ID);

    public static final RegistryObject<BasicParticleType> SHOWER_PARTICLE = REGISTER.register("shower_particle", () -> new BasicParticleType(true));
}
