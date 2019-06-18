package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.entity.SeatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;

public class ModEntities
{
    public static final EntityType<SeatEntity> SEAT = buildType(new ResourceLocation(Reference.MOD_ID, "seat"), EntityType.Builder.create(SeatEntity::new, EntityClassification.MISC).size(1.0F, 1.0F));

    private static <T extends Entity> EntityType<T> buildType(ResourceLocation id, EntityType.Builder<T> builder)
    {
        EntityType<T> type = builder.build(id.toString());
        type.setRegistryName(id);
        return type;
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
