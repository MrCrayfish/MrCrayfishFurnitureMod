package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.BedsideCabinetTileEntity;
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
    public static final TileEntityType<CabinetTileEntity> CABINET = buildType(new ResourceLocation(Reference.MOD_ID, "cabinet"), TileEntityType.Builder.create(CabinetTileEntity::new, ModBlocks.CABINET_OAK, ModBlocks.CABINET_SPRUCE, ModBlocks.CABINET_BIRCH, ModBlocks.CABINET_JUNGLE, ModBlocks.CABINET_ACACIA, ModBlocks.CABINET_DARK_OAK, ModBlocks.CABINET_STONE, ModBlocks.CABINET_GRANITE, ModBlocks.CABINET_DIORITE, ModBlocks.CABINET_ANDESITE, ModBlocks.CABINET_STRIPPED_OAK, ModBlocks.CABINET_STRIPPED_SPRUCE, ModBlocks.CABINET_STRIPPED_BIRCH, ModBlocks.CABINET_STRIPPED_JUNGLE, ModBlocks.CABINET_STRIPPED_ACACIA, ModBlocks.CABINET_STRIPPED_DARK_OAK));
    public static final TileEntityType<BedsideCabinetTileEntity> BEDSIDE_CABINET = buildType(new ResourceLocation(Reference.MOD_ID, "bedside_cabinet"), TileEntityType.Builder.create(BedsideCabinetTileEntity::new, ModBlocks.BEDSIDE_CABINET_OAK, ModBlocks.BEDSIDE_CABINET_SPRUCE, ModBlocks.BEDSIDE_CABINET_BIRCH, ModBlocks.BEDSIDE_CABINET_JUNGLE, ModBlocks.BEDSIDE_CABINET_ACACIA, ModBlocks.BEDSIDE_CABINET_DARK_OAK, ModBlocks.BEDSIDE_CABINET_STONE, ModBlocks.BEDSIDE_CABINET_GRANITE, ModBlocks.BEDSIDE_CABINET_DIORITE, ModBlocks.BEDSIDE_CABINET_ANDESITE, ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK, ModBlocks.BEDSIDE_CABINET_STRIPPED_SPRUCE, ModBlocks.BEDSIDE_CABINET_STRIPPED_BIRCH, ModBlocks.BEDSIDE_CABINET_STRIPPED_JUNGLE, ModBlocks.BEDSIDE_CABINET_STRIPPED_ACACIA, ModBlocks.BEDSIDE_CABINET_STRIPPED_DARK_OAK));
    
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
            event.getRegistry().register(BEDSIDE_CABINET);
        }
    }
}
