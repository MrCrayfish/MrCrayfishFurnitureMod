package com.mrcrayfish.furniture.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.block.CoffeeTableBlock;
import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.data.loot.GiftLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.PiglinBarteringAddition;
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
            this.registerDropSelfLootTable(ModBlocks.TABLE_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_STONE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_GRANITE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_DIORITE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_ANDESITE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.TABLE_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STONE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_GRANITE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_DIORITE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_ANDESITE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.CHAIR_STRIPPED_DARK_OAK.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_OAK.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_SPRUCE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_BIRCH.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_JUNGLE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_ACACIA.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_DARK_OAK.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STONE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_GRANITE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_DIORITE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_ANDESITE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_OAK.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_SPRUCE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_BIRCH.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_JUNGLE.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_ACACIA.get());
            this.registerCoffeeTable(ModBlocks.COFFEE_TABLE_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_STONE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_GRANITE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_DIORITE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_ANDESITE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.CABINET_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STONE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_GRANITE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_DIORITE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_ANDESITE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.BEDSIDE_CABINET_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_STONE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_GRANITE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_DIORITE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_ANDESITE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STONE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_GRANITE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_DIORITE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_ANDESITE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.DESK_CABINET_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_RED.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.SOFA_RAINBOW.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.BLINDS_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_FENCE_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.UPGRADED_GATE_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_RED.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_FENCE_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_RED.get());
            this.registerDropSelfLootTable(ModBlocks.PICKET_GATE_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.CRATE_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.POST_BOX.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.MAIL_BOX_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.HEDGE_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.HEDGE_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.HEDGE_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.HEDGE_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.HEDGE_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.HEDGE_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.ROCK_PATH.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_RED.get());
            this.registerDropSelfLootTable(ModBlocks.TRAMPOLINE_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_RED.get());
            this.registerDropSelfLootTable(ModBlocks.COOLER_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_RED.get());
            this.registerDropSelfLootTable(ModBlocks.GRILL_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.DOOR_MAT.get());
            this.registerDropSelfLootTable(ModBlocks.DIVING_BOARD.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_RED.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_COUNTER_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_RED.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_DRAWER_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_WHITE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_ORANGE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_MAGENTA.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_YELLOW.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIME.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_PINK.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_CYAN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_PURPLE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_BLUE.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_BROWN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_GREEN.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_RED.get());
            this.registerDropSelfLootTable(ModBlocks.KITCHEN_SINK_BLACK.get());
            this.registerDropSelfLootTable(ModBlocks.FRIDGE_LIGHT.get());
            this.registerDropSelfLootTable(ModBlocks.FREEZER_LIGHT.get());
            this.registerDropSelfLootTable(ModBlocks.FRIDGE_DARK.get());
            this.registerDropSelfLootTable(ModBlocks.FREEZER_DARK.get());
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