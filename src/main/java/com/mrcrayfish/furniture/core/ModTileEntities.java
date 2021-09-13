package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ModTileEntities
{
    public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID);

    public static final RegistryObject<TileEntityType<CabinetTileEntity>> CABINET = register("cabinet", CabinetTileEntity::new, () -> new Block[]{ModBlocks.CABINET_OAK.get(), ModBlocks.CABINET_SPRUCE.get(), ModBlocks.CABINET_BIRCH.get(), ModBlocks.CABINET_JUNGLE.get(), ModBlocks.CABINET_ACACIA.get(), ModBlocks.CABINET_DARK_OAK.get(), ModBlocks.CABINET_STONE.get(), ModBlocks.CABINET_GRANITE.get(), ModBlocks.CABINET_DIORITE.get(), ModBlocks.CABINET_ANDESITE.get(), ModBlocks.CABINET_STRIPPED_OAK.get(), ModBlocks.CABINET_STRIPPED_SPRUCE.get(), ModBlocks.CABINET_STRIPPED_BIRCH.get(), ModBlocks.CABINET_STRIPPED_JUNGLE.get(), ModBlocks.CABINET_STRIPPED_ACACIA.get(), ModBlocks.CABINET_STRIPPED_DARK_OAK.get()});
    public static final RegistryObject<TileEntityType<BedsideCabinetTileEntity>> BEDSIDE_CABINET = register("bedside_cabinet", BedsideCabinetTileEntity::new, () -> new Block[]{ModBlocks.BEDSIDE_CABINET_OAK.get(), ModBlocks.BEDSIDE_CABINET_SPRUCE.get(), ModBlocks.BEDSIDE_CABINET_BIRCH.get(), ModBlocks.BEDSIDE_CABINET_JUNGLE.get(), ModBlocks.BEDSIDE_CABINET_ACACIA.get(), ModBlocks.BEDSIDE_CABINET_DARK_OAK.get(), ModBlocks.BEDSIDE_CABINET_STONE.get(), ModBlocks.BEDSIDE_CABINET_GRANITE.get(), ModBlocks.BEDSIDE_CABINET_DIORITE.get(), ModBlocks.BEDSIDE_CABINET_ANDESITE.get(), ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK.get(), ModBlocks.BEDSIDE_CABINET_STRIPPED_SPRUCE.get(), ModBlocks.BEDSIDE_CABINET_STRIPPED_BIRCH.get(), ModBlocks.BEDSIDE_CABINET_STRIPPED_JUNGLE.get(), ModBlocks.BEDSIDE_CABINET_STRIPPED_ACACIA.get(), ModBlocks.BEDSIDE_CABINET_STRIPPED_DARK_OAK.get()});
    public static final RegistryObject<TileEntityType<DeskCabinetTileEntity>> DESK_CABINET = register("desk_cabinet", DeskCabinetTileEntity::new, () -> new Block[]{ModBlocks.DESK_CABINET_OAK.get(), ModBlocks.DESK_CABINET_SPRUCE.get(), ModBlocks.DESK_CABINET_BIRCH.get(), ModBlocks.DESK_CABINET_JUNGLE.get(), ModBlocks.DESK_CABINET_ACACIA.get(), ModBlocks.DESK_CABINET_DARK_OAK.get(), ModBlocks.DESK_CABINET_STONE.get(), ModBlocks.DESK_CABINET_GRANITE.get(), ModBlocks.DESK_CABINET_DIORITE.get(), ModBlocks.DESK_CABINET_ANDESITE.get(), ModBlocks.DESK_CABINET_STRIPPED_OAK.get(), ModBlocks.DESK_CABINET_STRIPPED_SPRUCE.get(), ModBlocks.DESK_CABINET_STRIPPED_BIRCH.get(), ModBlocks.DESK_CABINET_STRIPPED_JUNGLE.get(), ModBlocks.DESK_CABINET_STRIPPED_ACACIA.get(), ModBlocks.DESK_CABINET_STRIPPED_DARK_OAK.get()});
    public static final RegistryObject<TileEntityType<CrateTileEntity>> CRATE = register("crate", CrateTileEntity::new, () -> new Block[]{ModBlocks.CRATE_OAK.get(), ModBlocks.CRATE_SPRUCE.get(), ModBlocks.CRATE_BIRCH.get(), ModBlocks.CRATE_JUNGLE.get(), ModBlocks.CRATE_ACACIA.get(), ModBlocks.CRATE_DARK_OAK.get()});
    public static final RegistryObject<TileEntityType<MailBoxTileEntity>> MAIL_BOX = register("mail_box", MailBoxTileEntity::new, () -> new Block[]{ModBlocks.MAIL_BOX_OAK.get(), ModBlocks.MAIL_BOX_SPRUCE.get(), ModBlocks.MAIL_BOX_BIRCH.get(), ModBlocks.MAIL_BOX_JUNGLE.get(), ModBlocks.MAIL_BOX_ACACIA.get(), ModBlocks.MAIL_BOX_DARK_OAK.get(), ModBlocks.MAIL_BOX_STRIPPED_OAK.get(), ModBlocks.MAIL_BOX_STRIPPED_SPRUCE.get(), ModBlocks.MAIL_BOX_STRIPPED_BIRCH.get(), ModBlocks.MAIL_BOX_STRIPPED_JUNGLE.get(), ModBlocks.MAIL_BOX_STRIPPED_ACACIA.get(), ModBlocks.MAIL_BOX_STRIPPED_DARK_OAK.get()});
    public static final RegistryObject<TileEntityType<TrampolineTileEntity>> TRAMPOLINE = register("trampoline", TrampolineTileEntity::new, () -> new Block[]{ModBlocks.TRAMPOLINE.get()});
    public static final RegistryObject<TileEntityType<CoolerTileEntity>> COOLER = register("cooler", CoolerTileEntity::new, () -> new Block[]{ModBlocks.COOLER_WHITE.get(), ModBlocks.COOLER_ORANGE.get(), ModBlocks.COOLER_MAGENTA.get(), ModBlocks.COOLER_LIGHT_BLUE.get(), ModBlocks.COOLER_YELLOW.get(), ModBlocks.COOLER_LIME.get(), ModBlocks.COOLER_PINK.get(), ModBlocks.COOLER_GRAY.get(), ModBlocks.COOLER_LIGHT_GRAY.get(), ModBlocks.COOLER_CYAN.get(), ModBlocks.COOLER_PURPLE.get(), ModBlocks.COOLER_BLUE.get(), ModBlocks.COOLER_BROWN.get(), ModBlocks.COOLER_GREEN.get(), ModBlocks.COOLER_RED.get(), ModBlocks.COOLER_BLACK.get()});
    public static final RegistryObject<TileEntityType<GrillTileEntity>> GRILL = register("grill", GrillTileEntity::new, () -> new Block[]{ModBlocks.GRILL_WHITE.get(), ModBlocks.GRILL_ORANGE.get(), ModBlocks.GRILL_MAGENTA.get(), ModBlocks.GRILL_LIGHT_BLUE.get(), ModBlocks.GRILL_YELLOW.get(), ModBlocks.GRILL_LIME.get(), ModBlocks.GRILL_PINK.get(), ModBlocks.GRILL_GRAY.get(), ModBlocks.GRILL_LIGHT_GRAY.get(), ModBlocks.GRILL_CYAN.get(), ModBlocks.GRILL_PURPLE.get(), ModBlocks.GRILL_BLUE.get(), ModBlocks.GRILL_BROWN.get(), ModBlocks.GRILL_GREEN.get(), ModBlocks.GRILL_RED.get(), ModBlocks.GRILL_BLACK.get()});
    public static final RegistryObject<TileEntityType<DoorMatTileEntity>> DOOR_MAT = register("door_mat", DoorMatTileEntity::new, () -> new Block[]{ModBlocks.DOOR_MAT.get()});
    public static final RegistryObject<TileEntityType<KitchenDrawerTileEntity>> KITCHEN_DRAWER = register("kitchen_drawer", KitchenDrawerTileEntity::new, () -> new Block[]{ModBlocks.KITCHEN_DRAWER_OAK.get(), ModBlocks.KITCHEN_DRAWER_SPRUCE.get(), ModBlocks.KITCHEN_DRAWER_BIRCH.get(), ModBlocks.KITCHEN_DRAWER_JUNGLE.get(), ModBlocks.KITCHEN_DRAWER_ACACIA.get(), ModBlocks.KITCHEN_DRAWER_DARK_OAK.get(), ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK.get(), ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE.get(), ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH.get(), ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE.get(), ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA.get(), ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK.get(), ModBlocks.KITCHEN_DRAWER_WHITE.get(), ModBlocks.KITCHEN_DRAWER_ORANGE.get(), ModBlocks.KITCHEN_DRAWER_MAGENTA.get(), ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get(), ModBlocks.KITCHEN_DRAWER_YELLOW.get(), ModBlocks.KITCHEN_DRAWER_LIME.get(), ModBlocks.KITCHEN_DRAWER_PINK.get(), ModBlocks.KITCHEN_DRAWER_GRAY.get(), ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get(), ModBlocks.KITCHEN_DRAWER_CYAN.get(), ModBlocks.KITCHEN_DRAWER_PURPLE.get(), ModBlocks.KITCHEN_DRAWER_BLUE.get(), ModBlocks.KITCHEN_DRAWER_BROWN.get(), ModBlocks.KITCHEN_DRAWER_GREEN.get(), ModBlocks.KITCHEN_DRAWER_RED.get(), ModBlocks.KITCHEN_DRAWER_BLACK.get()});
    public static final RegistryObject<TileEntityType<KitchenSinkTileEntity>> KITCHEN_SINK = register("kitchen_sink", KitchenSinkTileEntity::new, () -> new Block[]{ModBlocks.KITCHEN_SINK_LIGHT_OAK.get(), ModBlocks.KITCHEN_SINK_LIGHT_SPRUCE.get(), ModBlocks.KITCHEN_SINK_LIGHT_BIRCH.get(), ModBlocks.KITCHEN_SINK_LIGHT_JUNGLE.get(), ModBlocks.KITCHEN_SINK_LIGHT_ACACIA.get(), ModBlocks.KITCHEN_SINK_LIGHT_DARK_OAK.get(), ModBlocks.KITCHEN_SINK_LIGHT_CRIMSON.get(), ModBlocks.KITCHEN_SINK_LIGHT_WARPED.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_CRIMSON.get(), ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_WARPED.get(), ModBlocks.KITCHEN_SINK_DARK_OAK.get(), ModBlocks.KITCHEN_SINK_DARK_SPRUCE.get(), ModBlocks.KITCHEN_SINK_DARK_BIRCH.get(), ModBlocks.KITCHEN_SINK_DARK_JUNGLE.get(), ModBlocks.KITCHEN_SINK_DARK_ACACIA.get(), ModBlocks.KITCHEN_SINK_DARK_DARK_OAK.get(), ModBlocks.KITCHEN_SINK_DARK_CRIMSON.get(), ModBlocks.KITCHEN_SINK_DARK_WARPED.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_CRIMSON.get(), ModBlocks.KITCHEN_SINK_DARK_STRIPPED_WARPED.get(), ModBlocks.KITCHEN_SINK_WHITE.get(), ModBlocks.KITCHEN_SINK_ORANGE.get(), ModBlocks.KITCHEN_SINK_MAGENTA.get(), ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get(), ModBlocks.KITCHEN_SINK_YELLOW.get(), ModBlocks.KITCHEN_SINK_LIME.get(), ModBlocks.KITCHEN_SINK_PINK.get(), ModBlocks.KITCHEN_SINK_GRAY.get(), ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get(), ModBlocks.KITCHEN_SINK_CYAN.get(), ModBlocks.KITCHEN_SINK_PURPLE.get(), ModBlocks.KITCHEN_SINK_BLUE.get(), ModBlocks.KITCHEN_SINK_BROWN.get(), ModBlocks.KITCHEN_SINK_GREEN.get(), ModBlocks.KITCHEN_SINK_RED.get(), ModBlocks.KITCHEN_SINK_BLACK.get()});
    public static final RegistryObject<TileEntityType<FridgeTileEntity>> FRIDGE = register("fridge", FridgeTileEntity::new, () -> new Block[]{ModBlocks.FRIDGE_LIGHT.get(), ModBlocks.FRIDGE_DARK.get()});
    public static final RegistryObject<TileEntityType<FreezerTileEntity>> FREEZER = register("freezer", FreezerTileEntity::new, () -> new Block[]{ModBlocks.FREEZER_LIGHT.get(), ModBlocks.FREEZER_DARK.get()});
    public static final RegistryObject<TileEntityType<ShowerHeadTileEntity>> SHOWER_HEAD = register("shower_head", ShowerHeadTileEntity::new, () -> new Block[]{ModBlocks.SHOWER_HEAD_LIGHT.get(), ModBlocks.SHOWER_HEAD_DARK.get()});

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier)
    {
        //Type<?> type = Util.attemptDataFix(TypeReferences.BLOCK_ENTITY, name);
        return REGISTER.register(name, () -> TileEntityType.Builder.create(factoryIn, validBlocksSupplier.get()).build(null)); //Null until someone can explain data fixers
    }
}
