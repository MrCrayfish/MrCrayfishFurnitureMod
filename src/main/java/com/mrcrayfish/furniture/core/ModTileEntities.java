package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.BedsideCabinetTileEntity;
import com.mrcrayfish.furniture.tileentity.CabinetTileEntity;
import com.mrcrayfish.furniture.tileentity.DeskCabinetTileEntity;
import com.mrcrayfish.furniture.util.TileEntityNames;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities
{
    private static final List<TileEntityType> TILE_ENTITY_TYPES = new ArrayList<>();

    public static final TileEntityType<CabinetTileEntity> CABINET = buildType(TileEntityNames.CABINET, TileEntityType.Builder.create(CabinetTileEntity::new, ModBlocks.CABINET_OAK, ModBlocks.CABINET_SPRUCE, ModBlocks.CABINET_BIRCH, ModBlocks.CABINET_JUNGLE, ModBlocks.CABINET_ACACIA, ModBlocks.CABINET_DARK_OAK, ModBlocks.CABINET_STONE, ModBlocks.CABINET_GRANITE, ModBlocks.CABINET_DIORITE, ModBlocks.CABINET_ANDESITE, ModBlocks.CABINET_STRIPPED_OAK, ModBlocks.CABINET_STRIPPED_SPRUCE, ModBlocks.CABINET_STRIPPED_BIRCH, ModBlocks.CABINET_STRIPPED_JUNGLE, ModBlocks.CABINET_STRIPPED_ACACIA, ModBlocks.CABINET_STRIPPED_DARK_OAK));
    public static final TileEntityType<BedsideCabinetTileEntity> BEDSIDE_CABINET = buildType(TileEntityNames.BEDSIDE_CABINET, TileEntityType.Builder.create(BedsideCabinetTileEntity::new, ModBlocks.BEDSIDE_CABINET_OAK, ModBlocks.BEDSIDE_CABINET_SPRUCE, ModBlocks.BEDSIDE_CABINET_BIRCH, ModBlocks.BEDSIDE_CABINET_JUNGLE, ModBlocks.BEDSIDE_CABINET_ACACIA, ModBlocks.BEDSIDE_CABINET_DARK_OAK, ModBlocks.BEDSIDE_CABINET_STONE, ModBlocks.BEDSIDE_CABINET_GRANITE, ModBlocks.BEDSIDE_CABINET_DIORITE, ModBlocks.BEDSIDE_CABINET_ANDESITE, ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK, ModBlocks.BEDSIDE_CABINET_STRIPPED_SPRUCE, ModBlocks.BEDSIDE_CABINET_STRIPPED_BIRCH, ModBlocks.BEDSIDE_CABINET_STRIPPED_JUNGLE, ModBlocks.BEDSIDE_CABINET_STRIPPED_ACACIA, ModBlocks.BEDSIDE_CABINET_STRIPPED_DARK_OAK));
    public static final TileEntityType<DeskCabinetTileEntity> DESK_CABINET = buildType(TileEntityNames.DESK_CABINET, TileEntityType.Builder.create(DeskCabinetTileEntity::new, ModBlocks.DESK_CABINET_OAK, ModBlocks.DESK_CABINET_SPRUCE, ModBlocks.DESK_CABINET_BIRCH, ModBlocks.DESK_CABINET_JUNGLE, ModBlocks.DESK_CABINET_ACACIA, ModBlocks.DESK_CABINET_DARK_OAK, ModBlocks.DESK_CABINET_STONE, ModBlocks.DESK_CABINET_GRANITE, ModBlocks.DESK_CABINET_DIORITE, ModBlocks.DESK_CABINET_ANDESITE, ModBlocks.DESK_CABINET_STRIPPED_OAK, ModBlocks.DESK_CABINET_STRIPPED_SPRUCE, ModBlocks.DESK_CABINET_STRIPPED_BIRCH, ModBlocks.DESK_CABINET_STRIPPED_JUNGLE, ModBlocks.DESK_CABINET_STRIPPED_ACACIA, ModBlocks.DESK_CABINET_STRIPPED_DARK_OAK));
    
    private static <T extends TileEntity> TileEntityType<T> buildType(String id, TileEntityType.Builder<T> builder)
    {
        TileEntityType<T> type = builder.build(null); //TODO may not allow null
        type.setRegistryName(id);
        TILE_ENTITY_TYPES.add(type);
        return type;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerTypes(final RegistryEvent.Register<TileEntityType<?>> event)
    {
        TILE_ENTITY_TYPES.forEach(type -> event.getRegistry().register(type));
        TILE_ENTITY_TYPES.clear();
    }
}
