package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.*;
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
    public static final TileEntityType<CrateTileEntity> CRATE = buildType(TileEntityNames.CRATE, TileEntityType.Builder.create(CrateTileEntity::new, ModBlocks.CRATE_OAK, ModBlocks.CRATE_SPRUCE, ModBlocks.CRATE_BIRCH, ModBlocks.CRATE_JUNGLE, ModBlocks.CRATE_ACACIA, ModBlocks.CRATE_DARK_OAK));
    public static final TileEntityType<MailBoxTileEntity> MAIL_BOX = buildType(TileEntityNames.MAIL_BOX, TileEntityType.Builder.create(MailBoxTileEntity::new, ModBlocks.MAIL_BOX_OAK, ModBlocks.MAIL_BOX_SPRUCE, ModBlocks.MAIL_BOX_BIRCH, ModBlocks.MAIL_BOX_JUNGLE, ModBlocks.MAIL_BOX_ACACIA, ModBlocks.MAIL_BOX_DARK_OAK, ModBlocks.MAIL_BOX_STRIPPED_OAK, ModBlocks.MAIL_BOX_STRIPPED_SPRUCE, ModBlocks.MAIL_BOX_STRIPPED_BIRCH, ModBlocks.MAIL_BOX_STRIPPED_JUNGLE, ModBlocks.MAIL_BOX_STRIPPED_ACACIA, ModBlocks.MAIL_BOX_STRIPPED_DARK_OAK));
    public static final TileEntityType<TrampolineTileEntity> TRAMPOLINE = buildType(TileEntityNames.TRAMPOLINE, TileEntityType.Builder.create(TrampolineTileEntity::new, ModBlocks.TRAMPOLINE_WHITE, ModBlocks.TRAMPOLINE_ORANGE, ModBlocks.TRAMPOLINE_MAGENTA, ModBlocks.TRAMPOLINE_LIGHT_BLUE, ModBlocks.TRAMPOLINE_YELLOW, ModBlocks.TRAMPOLINE_LIME, ModBlocks.TRAMPOLINE_PINK, ModBlocks.TRAMPOLINE_GRAY, ModBlocks.TRAMPOLINE_LIGHT_GRAY, ModBlocks.TRAMPOLINE_CYAN, ModBlocks.TRAMPOLINE_PURPLE, ModBlocks.TRAMPOLINE_BLUE, ModBlocks.TRAMPOLINE_BROWN, ModBlocks.TRAMPOLINE_GREEN, ModBlocks.TRAMPOLINE_RED, ModBlocks.TRAMPOLINE_BLACK));
    public static final TileEntityType<CoolerTileEntity> COOLER = buildType(TileEntityNames.COOLER, TileEntityType.Builder.create(CoolerTileEntity::new, ModBlocks.COOLER_WHITE, ModBlocks.COOLER_ORANGE, ModBlocks.COOLER_MAGENTA, ModBlocks.COOLER_LIGHT_BLUE, ModBlocks.COOLER_YELLOW, ModBlocks.COOLER_LIME, ModBlocks.COOLER_PINK, ModBlocks.COOLER_GRAY, ModBlocks.COOLER_LIGHT_GRAY, ModBlocks.COOLER_CYAN, ModBlocks.COOLER_PURPLE, ModBlocks.COOLER_BLUE, ModBlocks.COOLER_BROWN, ModBlocks.COOLER_GREEN, ModBlocks.COOLER_RED, ModBlocks.COOLER_BLACK));
    public static final TileEntityType<GrillTileEntity> GRILL = buildType(TileEntityNames.GRILL, TileEntityType.Builder.create(GrillTileEntity::new, ModBlocks.GRILL_WHITE, ModBlocks.GRILL_ORANGE, ModBlocks.GRILL_MAGENTA, ModBlocks.GRILL_LIGHT_BLUE, ModBlocks.GRILL_YELLOW, ModBlocks.GRILL_LIME, ModBlocks.GRILL_PINK, ModBlocks.GRILL_GRAY, ModBlocks.GRILL_LIGHT_GRAY, ModBlocks.GRILL_CYAN, ModBlocks.GRILL_PURPLE, ModBlocks.GRILL_BLUE, ModBlocks.GRILL_BROWN, ModBlocks.GRILL_GREEN, ModBlocks.GRILL_RED, ModBlocks.GRILL_BLACK));
    public static final TileEntityType<DoorMatTileEntity> DOOR_MAT = buildType(TileEntityNames.DOOR_MAT, TileEntityType.Builder.create(DoorMatTileEntity::new, ModBlocks.DOOR_MAT));
    public static final TileEntityType<KitchenDrawerTileEntity> KITCHEN_DRAWER = buildType(TileEntityNames.KITCHEN_DRAWER, TileEntityType.Builder.create(KitchenDrawerTileEntity::new, ModBlocks.KITCHEN_DRAWER_OAK, ModBlocks.KITCHEN_DRAWER_SPRUCE, ModBlocks.KITCHEN_DRAWER_BIRCH, ModBlocks.KITCHEN_DRAWER_JUNGLE, ModBlocks.KITCHEN_DRAWER_ACACIA, ModBlocks.KITCHEN_DRAWER_DARK_OAK, ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK, ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE, ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH, ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE, ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA, ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK, ModBlocks.KITCHEN_DRAWER_WHITE, ModBlocks.KITCHEN_DRAWER_ORANGE, ModBlocks.KITCHEN_DRAWER_MAGENTA, ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE, ModBlocks.KITCHEN_DRAWER_YELLOW, ModBlocks.KITCHEN_DRAWER_LIME, ModBlocks.KITCHEN_DRAWER_PINK, ModBlocks.KITCHEN_DRAWER_GRAY, ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY, ModBlocks.KITCHEN_DRAWER_CYAN, ModBlocks.KITCHEN_DRAWER_PURPLE, ModBlocks.KITCHEN_DRAWER_BLUE, ModBlocks.KITCHEN_DRAWER_BROWN, ModBlocks.KITCHEN_DRAWER_GREEN, ModBlocks.KITCHEN_DRAWER_RED, ModBlocks.KITCHEN_DRAWER_BLACK));

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
