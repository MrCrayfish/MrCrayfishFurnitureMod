package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.entity.EntitySeat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;

public class ModEntities
{
    public static final EntityType<EntitySeat> SEAT = createType(EntitySeat.class, EntitySeat::new, "cfm:seat");

    private static <T extends Entity> EntityType<T> createType(Class<? extends T> clazz, Function<? super World, ? extends T> factory, String id)
    {
        EntityType<T> entityType = EntityType.Builder.create(clazz, factory).build(id);
        entityType.setRegistryName(id);
        return entityType;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void register(final RegistryEvent.Register<EntityType<?>> event)
        {
            event.getRegistry().register(SEAT);
        }
    }
}
