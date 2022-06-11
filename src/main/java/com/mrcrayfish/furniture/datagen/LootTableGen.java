package com.mrcrayfish.furniture.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.block.CoffeeTableBlock;
import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Ocelot
 */
public class LootTableGen extends LootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(BlockProvider::new, LootContextParamSets.BLOCK));

    public LootTableGen(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables()
    {
        return this.tables;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {}

    private static class BlockProvider extends BlockLoot
    {
        @Override
        protected void addTables()
        {
            this.dropSelf(ModBlocks.SOFA_WHITE.get());
            this.dropSelf(ModBlocks.SOFA_ORANGE.get());
            this.dropSelf(ModBlocks.SOFA_MAGENTA.get());
            this.dropSelf(ModBlocks.SOFA_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.SOFA_YELLOW.get());
            this.dropSelf(ModBlocks.SOFA_LIME.get());
            this.dropSelf(ModBlocks.SOFA_PINK.get());
            this.dropSelf(ModBlocks.SOFA_GRAY.get());
            this.dropSelf(ModBlocks.SOFA_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.SOFA_CYAN.get());
            this.dropSelf(ModBlocks.SOFA_PURPLE.get());
            this.dropSelf(ModBlocks.SOFA_BLUE.get());
            this.dropSelf(ModBlocks.SOFA_BROWN.get());
            this.dropSelf(ModBlocks.SOFA_GREEN.get());
            this.dropSelf(ModBlocks.SOFA_RED.get());
            this.dropSelf(ModBlocks.SOFA_BLACK.get());
            this.dropSelf(ModBlocks.SOFA_RAINBOW.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_WHITE.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_ORANGE.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_MAGENTA.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_YELLOW.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_LIME.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_PINK.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_GRAY.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_CYAN.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_PURPLE.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_BLUE.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_BROWN.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_GREEN.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_RED.get());
            this.dropSelf(ModBlocks.PICKET_FENCE_BLACK.get());
            this.dropSelf(ModBlocks.PICKET_GATE_WHITE.get());
            this.dropSelf(ModBlocks.PICKET_GATE_ORANGE.get());
            this.dropSelf(ModBlocks.PICKET_GATE_MAGENTA.get());
            this.dropSelf(ModBlocks.PICKET_GATE_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.PICKET_GATE_YELLOW.get());
            this.dropSelf(ModBlocks.PICKET_GATE_LIME.get());
            this.dropSelf(ModBlocks.PICKET_GATE_PINK.get());
            this.dropSelf(ModBlocks.PICKET_GATE_GRAY.get());
            this.dropSelf(ModBlocks.PICKET_GATE_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.PICKET_GATE_CYAN.get());
            this.dropSelf(ModBlocks.PICKET_GATE_PURPLE.get());
            this.dropSelf(ModBlocks.PICKET_GATE_BLUE.get());
            this.dropSelf(ModBlocks.PICKET_GATE_BROWN.get());
            this.dropSelf(ModBlocks.PICKET_GATE_GREEN.get());
            this.dropSelf(ModBlocks.PICKET_GATE_RED.get());
            this.dropSelf(ModBlocks.PICKET_GATE_BLACK.get());
            this.dropSelf(ModBlocks.POST_BOX.get());
            this.dropSelf(ModBlocks.HEDGE_OAK.get());
            this.dropSelf(ModBlocks.HEDGE_SPRUCE.get());
            this.dropSelf(ModBlocks.HEDGE_BIRCH.get());
            this.dropSelf(ModBlocks.HEDGE_JUNGLE.get());
            this.dropSelf(ModBlocks.HEDGE_ACACIA.get());
            this.dropSelf(ModBlocks.HEDGE_DARK_OAK.get());
            this.dropSelf(ModBlocks.HEDGE_MANGROVE.get());
            this.dropSelf(ModBlocks.HEDGE_AZALEA.get());
            this.dropSelf(ModBlocks.HEDGE_FLOWERING_AZALEA.get());
            this.dropSelf(ModBlocks.ROCK_PATH.get());
            this.registerTrampoline(ModBlocks.TRAMPOLINE.get());
            this.dropSelf(ModBlocks.COOLER_WHITE.get());
            this.dropSelf(ModBlocks.COOLER_ORANGE.get());
            this.dropSelf(ModBlocks.COOLER_MAGENTA.get());
            this.dropSelf(ModBlocks.COOLER_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.COOLER_YELLOW.get());
            this.dropSelf(ModBlocks.COOLER_LIME.get());
            this.dropSelf(ModBlocks.COOLER_PINK.get());
            this.dropSelf(ModBlocks.COOLER_GRAY.get());
            this.dropSelf(ModBlocks.COOLER_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.COOLER_CYAN.get());
            this.dropSelf(ModBlocks.COOLER_PURPLE.get());
            this.dropSelf(ModBlocks.COOLER_BLUE.get());
            this.dropSelf(ModBlocks.COOLER_BROWN.get());
            this.dropSelf(ModBlocks.COOLER_GREEN.get());
            this.dropSelf(ModBlocks.COOLER_RED.get());
            this.dropSelf(ModBlocks.COOLER_BLACK.get());
            this.dropSelf(ModBlocks.GRILL_WHITE.get());
            this.dropSelf(ModBlocks.GRILL_ORANGE.get());
            this.dropSelf(ModBlocks.GRILL_MAGENTA.get());
            this.dropSelf(ModBlocks.GRILL_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.GRILL_YELLOW.get());
            this.dropSelf(ModBlocks.GRILL_LIME.get());
            this.dropSelf(ModBlocks.GRILL_PINK.get());
            this.dropSelf(ModBlocks.GRILL_GRAY.get());
            this.dropSelf(ModBlocks.GRILL_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.GRILL_CYAN.get());
            this.dropSelf(ModBlocks.GRILL_PURPLE.get());
            this.dropSelf(ModBlocks.GRILL_BLUE.get());
            this.dropSelf(ModBlocks.GRILL_BROWN.get());
            this.dropSelf(ModBlocks.GRILL_GREEN.get());
            this.dropSelf(ModBlocks.GRILL_RED.get());
            this.dropSelf(ModBlocks.GRILL_BLACK.get());
            this.dropSelf(ModBlocks.DOOR_MAT.get());
            this.dropSelf(ModBlocks.DIVING_BOARD.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_WHITE.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_ORANGE.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_MAGENTA.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_YELLOW.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_LIME.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_PINK.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_GRAY.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_CYAN.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_PURPLE.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_BLUE.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_BROWN.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_GREEN.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_RED.get());
            this.dropSelf(ModBlocks.KITCHEN_COUNTER_BLACK.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_WHITE.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_ORANGE.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_MAGENTA.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_YELLOW.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_LIME.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_PINK.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_GRAY.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_CYAN.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_PURPLE.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_BLUE.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_BROWN.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_GREEN.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_RED.get());
            this.dropSelf(ModBlocks.KITCHEN_DRAWER_BLACK.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_WHITE.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_ORANGE.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_MAGENTA.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_YELLOW.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_LIME.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_PINK.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_GRAY.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_CYAN.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_PURPLE.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_BLUE.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_BROWN.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_GREEN.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_RED.get());
            this.dropSelf(ModBlocks.KITCHEN_SINK_BLACK.get());
            this.dropSelf(ModBlocks.FRIDGE_LIGHT.get());
            this.dropSelf(ModBlocks.FRIDGE_DARK.get());
            this.dropOther(ModBlocks.FREEZER_LIGHT.get(), ModBlocks.FRIDGE_LIGHT.get());
            this.dropOther(ModBlocks.FREEZER_DARK.get(), ModBlocks.FRIDGE_DARK.get());

            // Dynamically registers drops for wooden furniture
            for(GeneratorData.FurnitureType type : GeneratorData.ALL_TYPES)
            {
                for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
                {
                    Consumer<Block> register = type == GeneratorData.COFFEE_TABLE ? this::registerCoffeeTable : this::dropSelf;
                    register.accept(ForgeRegistries.BLOCKS.getValue(GeneratorData.getResultBlock(type, variant, false)));
                    if(variant.strippedLog() != null)
                    {
                        register.accept(ForgeRegistries.BLOCKS.getValue(GeneratorData.getResultBlock(type, variant, true)));
                    }
                }
            }
        }

        public void registerCoffeeTable(Block block)
        {
            this.add(block, coffeeTable -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(applyExplosionCondition(block, LootItem.lootTableItem(coffeeTable).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(coffeeTable).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CoffeeTableBlock.TALL, true))))))));
        }

        public void registerTrampoline(Block block)
        {
            this.add(block, trampoline -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(trampoline).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("BlockEntityTag.Count", "")))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> Reference.MOD_ID.equals(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getNamespace())).collect(Collectors.toSet());
        }
    }
}