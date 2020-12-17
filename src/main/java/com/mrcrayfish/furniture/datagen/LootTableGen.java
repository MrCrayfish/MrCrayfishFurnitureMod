package com.mrcrayfish.furniture.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.block.CoffeeTableBlock;
import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.*;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeLootTableProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Ocelot
 */
public class LootTableGen extends ForgeLootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = ImmutableList.of(Pair.of(FishingProvider::new, LootParameterSets.FISHING), Pair.of(ChestProvider::new, LootParameterSets.CHEST), Pair.of(EntityProvider::new, LootParameterSets.ENTITY), Pair.of(BlockProvider::new, LootParameterSets.BLOCK), Pair.of(PiglinBarteringProvider::new, LootParameterSets.field_237453_h_), Pair.of(GiftProvider::new, LootParameterSets.GIFT));

    public LootTableGen(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return tables;
    }

    private static class FishingProvider extends FishingLootTables
    {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
        {
        }
    }

    private static class ChestProvider extends ChestLootTables
    {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
        {
        }
    }

    private static class EntityProvider extends EntityLootTables
    {
        @Override
        protected void addTables()
        {
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities()
        {
            return ForgeRegistries.ENTITIES.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && Reference.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }

    private static class BlockProvider extends BlockLootTables
    {
        @Override
        protected void addTables()
        {
            this.registerDropSelfLootTable(ModBlocks.TABLE_OAK);
            this.registerDropSelfLootTable(ModBlocks.TABLE_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.TABLE_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.TABLE_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.TABLE_STONE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_GRANITE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_DIORITE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_ANDESITE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_OAK);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STONE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_GRANITE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_DIORITE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_ANDESITE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_DARK_OAK);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_OAK);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_SPRUCE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_BIRCH);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_JUNGLE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_ACACIA);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_DARK_OAK);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STONE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_GRANITE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_DIORITE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_ANDESITE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_OAK);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_SPRUCE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_BIRCH);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_JUNGLE);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_ACACIA);
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.CABINET_OAK);
            this.registerDropSelfLootTable(ModBlocks.CABINET_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.CABINET_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.CABINET_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.CABINET_STONE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_GRANITE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_DIORITE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_ANDESITE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_OAK);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STONE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_GRANITE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_DIORITE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_ANDESITE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.DESK_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.DESK_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.DESK_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.DESK_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_STONE);
            this.registerDropSelfLootTable(ModBlocks.DESK_GRANITE);
            this.registerDropSelfLootTable(ModBlocks.DESK_DIORITE);
            this.registerDropSelfLootTable(ModBlocks.DESK_ANDESITE);
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STONE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_GRANITE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_DIORITE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_ANDESITE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.SOFA_WHITE);
            this.registerDropSelfLootTable(ModBlocks.SOFA_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.SOFA_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.SOFA_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.SOFA_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.SOFA_LIME);
            this.registerDropSelfLootTable(ModBlocks.SOFA_PINK);
            this.registerDropSelfLootTable(ModBlocks.SOFA_GRAY);
            this.registerDropSelfLootTable(ModBlocks.SOFA_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.SOFA_CYAN);
            this.registerDropSelfLootTable(ModBlocks.SOFA_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.SOFA_BLUE);
            this.registerDropSelfLootTable(ModBlocks.SOFA_BROWN);
            this.registerDropSelfLootTable(ModBlocks.SOFA_GREEN);
            this.registerDropSelfLootTable(ModBlocks.SOFA_RED);
            this.registerDropSelfLootTable(ModBlocks.SOFA_BLACK);
            this.registerDropSelfLootTable(ModBlocks.SOFA_RAINBOW);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_OAK);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_WHITE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_LIME);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_PINK);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_GRAY);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_CYAN);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_BLUE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_BROWN);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_GREEN);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_RED);
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_BLACK);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_WHITE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_LIME);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_PINK);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_GRAY);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_CYAN);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_BLUE);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_BROWN);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_GREEN);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_RED);
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_BLACK);
            this.registerDropSelfLootTable(ModBlocks.CRATE_OAK);
            this.registerDropSelfLootTable(ModBlocks.CRATE_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.CRATE_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.CRATE_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.CRATE_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.CRATE_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_OAK);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.POST_BOX);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_OAK);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.HEDGE_OAK);
            this.registerDropSelfLootTable(ModBlocks.HEDGE_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.HEDGE_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.HEDGE_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.HEDGE_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.HEDGE_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.ROCK_PATH);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_WHITE);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_LIME);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_PINK);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_GRAY);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_CYAN);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_BLUE);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_BROWN);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_GREEN);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_RED);
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_BLACK);
            this.registerDropSelfLootTable(ModBlocks.COOLER_WHITE);
            this.registerDropSelfLootTable(ModBlocks.COOLER_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.COOLER_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.COOLER_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.COOLER_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.COOLER_LIME);
            this.registerDropSelfLootTable(ModBlocks.COOLER_PINK);
            this.registerDropSelfLootTable(ModBlocks.COOLER_GRAY);
            this.registerDropSelfLootTable(ModBlocks.COOLER_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.COOLER_CYAN);
            this.registerDropSelfLootTable(ModBlocks.COOLER_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.COOLER_BLUE);
            this.registerDropSelfLootTable(ModBlocks.COOLER_BROWN);
            this.registerDropSelfLootTable(ModBlocks.COOLER_GREEN);
            this.registerDropSelfLootTable(ModBlocks.COOLER_RED);
            this.registerDropSelfLootTable(ModBlocks.COOLER_BLACK);
            this.registerDropSelfLootTable(ModBlocks.GRILL_WHITE);
            this.registerDropSelfLootTable(ModBlocks.GRILL_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.GRILL_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.GRILL_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.GRILL_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.GRILL_LIME);
            this.registerDropSelfLootTable(ModBlocks.GRILL_PINK);
            this.registerDropSelfLootTable(ModBlocks.GRILL_GRAY);
            this.registerDropSelfLootTable(ModBlocks.GRILL_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.GRILL_CYAN);
            this.registerDropSelfLootTable(ModBlocks.GRILL_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.GRILL_BLUE);
            this.registerDropSelfLootTable(ModBlocks.GRILL_BROWN);
            this.registerDropSelfLootTable(ModBlocks.GRILL_GREEN);
            this.registerDropSelfLootTable(ModBlocks.GRILL_RED);
            this.registerDropSelfLootTable(ModBlocks.GRILL_BLACK);
            this.registerDropSelfLootTable(ModBlocks.DOOR_MAT);
            this.registerDropSelfLootTable(ModBlocks.DIVING_BOARD);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_WHITE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_LIME);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_PINK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_GRAY);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_CYAN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BLUE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BROWN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_GREEN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_RED);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BLACK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_WHITE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_LIME);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_PINK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_GRAY);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_CYAN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BLUE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BROWN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_GREEN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_RED);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BLACK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_WHITE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_ORANGE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_MAGENTA);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_BLUE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_YELLOW);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIME);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_PINK);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_GRAY);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_GRAY);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_CYAN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_PURPLE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_BLUE);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_BROWN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_GREEN);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_RED);
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_BLACK);
            this.registerDropSelfLootTable(ModBlocks.FRIDGE_LIGHT);
            this.registerDropSelfLootTable(ModBlocks.FREEZER_LIGHT);
            this.registerDropSelfLootTable(ModBlocks.FRIDGE_DARK);
            this.registerDropSelfLootTable(ModBlocks.FREEZER_DARK);
        }

        public void registerCoffeeTable(Block block)
        {
            this.registerLootTable(block, (coffeeTable) -> LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(withSurvivesExplosion(block, ItemLootEntry.builder(coffeeTable).acceptFunction(SetCount.builder(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(coffeeTable).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withBoolProp(CoffeeTableBlock.TALL, true))))))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && Reference.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }

    private static class PiglinBarteringProvider extends PiglinBarteringAddition
    {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
        {
        }
    }

    private static class GiftProvider extends GiftLootTables
    {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> registry)
        {
        }
    }
}