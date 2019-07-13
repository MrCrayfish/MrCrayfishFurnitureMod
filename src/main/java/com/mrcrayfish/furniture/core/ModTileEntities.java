package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.CabinetTileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Author: MrCrayfish
 */
public class ModTileEntities
{
    public static final TileEntityType<CabinetTileEntity> CABINET = buildType(new ResourceLocation(Reference.MOD_ID, "cabinet"), TileEntityType.Builder.create(CabinetTileEntity::new, Blocks.FURNACE));

    private static <T extends TileEntity> TileEntityType<T> buildType(ResourceLocation id, TileEntityType.Builder<T> builder)
    {
        TileEntityType<T> type = builder.build(null); //TODO may not allow null
        type.setRegistryName(id);
        return type;
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void register(final RegistryEvent.Register<TileEntityType<?>> event)
        {
            event.getRegistry().register(CABINET);
        }
    }
}
