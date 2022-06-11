package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class GeneratorData
{
    public static final FurnitureType TABLE = new FurnitureType("table", "TableBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType CHAIR = new FurnitureType("chair", "ChairBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType COFFEE_TABLE = new FurnitureType("coffee_table", "CoffeeTableBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType CABINET = new FurnitureType("cabinet", "CabinetBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType BEDSIDE_CABINET = new FurnitureType("bedside_cabinet", "BedsideCabinetBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType DESK = new FurnitureType("desk", "DeskBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)", "DeskBlock.MaterialType.OAK"});
    public static final FurnitureType DESK_CABINET = new FurnitureType("desk_cabinet", "DeskCabinetBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)", "DeskBlock.MaterialType.OAK"});
    public static final FurnitureType BLINDS = new FurnitureType("blinds", "BlindsBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType UPGRADED_FENCE = new FurnitureType("upgraded_fence", "UpgradedFenceBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType UPGRADED_GATE = new FurnitureType("upgraded_gate", "UpgradedGateBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType CRATE = new FurnitureType("crate", "CrateBlock", new String[]{"Block.Properties.from(Blocks.CHESTS)"});
    public static final FurnitureType PARK_BENCH = new FurnitureType("park_bench", "ParkBenchBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType MAIL_BOX = new FurnitureType("mail_box", "MailBoxBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType KITCHEN_COUNTER = new FurnitureType("kitchen_counter", "KitchenCounterBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType KITCHEN_DRAWER = new FurnitureType("kitchen_drawer", "KitchenDrawerBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)"});
    public static final FurnitureType KITCHEN_SINK_LIGHT = new FurnitureType("kitchen_sink_light", "KitchenSinkBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)", "true"});
    public static final FurnitureType KITCHEN_SINK_DARK = new FurnitureType("kitchen_sink_dark", "KitchenSinkBlock", new String[]{"Block.Properties.from(Blocks.OAK_PLANKS)", "true"});
    public static final FurnitureType[] ALL_TYPES = {TABLE, CHAIR, COFFEE_TABLE, CABINET, BEDSIDE_CABINET, DESK, DESK_CABINET, BLINDS, UPGRADED_FENCE, UPGRADED_GATE, CRATE, PARK_BENCH, MAIL_BOX, KITCHEN_COUNTER, KITCHEN_DRAWER, KITCHEN_SINK_LIGHT, KITCHEN_SINK_DARK};

    public static final Variant OAK = new Variant("oak", () -> Blocks.OAK_PLANKS, () -> Blocks.OAK_LOG, () -> Blocks.STRIPPED_OAK_LOG, false);
    public static final Variant BIRCH = new Variant("birch", () -> Blocks.BIRCH_PLANKS, () -> Blocks.BIRCH_LOG, () -> Blocks.STRIPPED_BIRCH_LOG, false);
    public static final Variant SPRUCE = new Variant("spruce", () -> Blocks.SPRUCE_PLANKS, () -> Blocks.SPRUCE_LOG, () -> Blocks.STRIPPED_SPRUCE_LOG, false);
    public static final Variant JUNGLE = new Variant("jungle", () -> Blocks.JUNGLE_PLANKS, () -> Blocks.JUNGLE_LOG, () -> Blocks.STRIPPED_JUNGLE_LOG, false);
    public static final Variant ACACIA = new Variant("acacia", () -> Blocks.ACACIA_PLANKS, () -> Blocks.ACACIA_LOG, () -> Blocks.STRIPPED_ACACIA_LOG, false);
    public static final Variant DARK_OAK = new Variant("dark_oak", () -> Blocks.DARK_OAK_PLANKS, () -> Blocks.DARK_OAK_LOG, () -> Blocks.STRIPPED_DARK_OAK_LOG, false);
    public static final Variant CRIMSON = new Variant("crimson", () -> Blocks.CRIMSON_PLANKS, () -> Blocks.CRIMSON_STEM, () -> Blocks.STRIPPED_CRIMSON_STEM, true);
    public static final Variant WARPED = new Variant("warped", () -> Blocks.WARPED_PLANKS, () -> Blocks.WARPED_STEM, () -> Blocks.STRIPPED_WARPED_STEM, true);
    public static final Variant MANGROVE = new Variant("mangrove", () -> Blocks.MANGROVE_PLANKS, () -> Blocks.MANGROVE_LOG, () -> Blocks.STRIPPED_MANGROVE_LOG, false);
    public static final Variant[] ALL_VARIANTS = {OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARK_OAK, CRIMSON, WARPED, MANGROVE};

    public record FurnitureType(String id, String className, String[] args) {}
    public record Variant(String id, Supplier<Block> planks, Supplier<Block> log, @Nullable Supplier<Block> strippedLog, boolean stem) {}

    @SuppressWarnings("unchecked")
    public static ResourceLocation getResultBlock(FurnitureType type, Variant variant, boolean stripped)
    {
        try
        {
            String fieldFormat = stripped ? "%s_STRIPPED_%s" : "%s_%s";
            String fieldName = String.format(fieldFormat, type.id().toUpperCase(), variant.id().toUpperCase());
            RegistryObject<Block> block = (RegistryObject<Block>) ModBlocks.class.getField(fieldName).get(null);
            return block.getId();
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException("Failed to find field");
        }
    }

    @SuppressWarnings("unchecked")
    public static ResourceLocation getUpgradedFence(Variant variant, boolean stripped)
    {
        try
        {
            String fieldFormat = stripped ? "UPGRADED_FENCE_STRIPPED_%s" : "UPGRADED_FENCE_%s";
            String fieldName = String.format(fieldFormat, variant.id().toUpperCase());
            RegistryObject<Block> block = (RegistryObject<Block>) ModBlocks.class.getField(fieldName).get(null);
            return block.getId();
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException("Failed to find field");
        }
    }
}
