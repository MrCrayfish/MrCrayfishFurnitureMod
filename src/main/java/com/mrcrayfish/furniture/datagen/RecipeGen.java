package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

/**
 * @author Ocelot
 */
public class RecipeGen extends RecipeProvider
{
    public RecipeGen(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        // Tables
        table(consumer, ModBlocks.TABLE_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        table(consumer, ModBlocks.TABLE_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        table(consumer, ModBlocks.TABLE_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        table(consumer, ModBlocks.TABLE_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        table(consumer, ModBlocks.TABLE_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        table(consumer, ModBlocks.TABLE_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        table(consumer, ModBlocks.TABLE_STONE, Blocks.STONE, Blocks.COBBLESTONE);
        table(consumer, ModBlocks.TABLE_GRANITE, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        table(consumer, ModBlocks.TABLE_DIORITE, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        table(consumer, ModBlocks.TABLE_ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        table(consumer, ModBlocks.TABLE_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        // Chairs
        chair(consumer, ModBlocks.CHAIR_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        chair(consumer, ModBlocks.CHAIR_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        chair(consumer, ModBlocks.CHAIR_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        chair(consumer, ModBlocks.CHAIR_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        chair(consumer, ModBlocks.CHAIR_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        chair(consumer, ModBlocks.CHAIR_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        chair(consumer, ModBlocks.CHAIR_STONE, Blocks.STONE, Blocks.COBBLESTONE);
        chair(consumer, ModBlocks.CHAIR_GRANITE, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        chair(consumer, ModBlocks.CHAIR_DIORITE, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        chair(consumer, ModBlocks.CHAIR_ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        // Coffee Table
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STONE, Blocks.STONE, Blocks.COBBLESTONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_GRANITE, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_DIORITE, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        // Cabinet
        cabinet(consumer, ModBlocks.CABINET_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_STONE, Blocks.STONE, Blocks.COBBLESTONE);
        cabinet(consumer, ModBlocks.CABINET_GRANITE, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        cabinet(consumer, ModBlocks.CABINET_DIORITE, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        cabinet(consumer, ModBlocks.CABINET_ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_OAK, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_SPRUCE, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_BIRCH, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_JUNGLE, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_ACACIA, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        // Coffee Table
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STONE, Blocks.STONE, Blocks.COBBLESTONE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_GRANITE, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_DIORITE, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        // Desk
        desk(consumer, ModBlocks.DESK_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        desk(consumer, ModBlocks.DESK_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        desk(consumer, ModBlocks.DESK_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        desk(consumer, ModBlocks.DESK_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        desk(consumer, ModBlocks.DESK_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        desk(consumer, ModBlocks.DESK_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        desk(consumer, ModBlocks.DESK_STONE, Blocks.STONE, Blocks.COBBLESTONE);
        desk(consumer, ModBlocks.DESK_GRANITE, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        desk(consumer, ModBlocks.DESK_DIORITE, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        desk(consumer, ModBlocks.DESK_ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        desk(consumer, ModBlocks.DESK_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        // Desk Cabinet
        deskCabinet(consumer, ModBlocks.DESK_CABINET_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STONE, Blocks.STONE, Blocks.COBBLESTONE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_GRANITE, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_DIORITE, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        // Desk Cabinet
        sofa(consumer, ModBlocks.SOFA_WHITE, Blocks.WHITE_WOOL);
        sofa(consumer, ModBlocks.SOFA_ORANGE, Blocks.ORANGE_WOOL);
        sofa(consumer, ModBlocks.SOFA_MAGENTA, Blocks.MAGENTA_WOOL);
        sofa(consumer, ModBlocks.SOFA_LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        sofa(consumer, ModBlocks.SOFA_YELLOW, Blocks.YELLOW_WOOL);
        sofa(consumer, ModBlocks.SOFA_LIME, Blocks.LIME_WOOL);
        sofa(consumer, ModBlocks.SOFA_PINK, Blocks.PINK_WOOL);
        sofa(consumer, ModBlocks.SOFA_GRAY, Blocks.GRAY_WOOL);
        sofa(consumer, ModBlocks.SOFA_LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        sofa(consumer, ModBlocks.SOFA_CYAN, Blocks.CYAN_WOOL);
        sofa(consumer, ModBlocks.SOFA_PURPLE, Blocks.PURPLE_WOOL);
        sofa(consumer, ModBlocks.SOFA_BLUE, Blocks.BLUE_WOOL);
        sofa(consumer, ModBlocks.SOFA_BROWN, Blocks.BROWN_WOOL);
        sofa(consumer, ModBlocks.SOFA_GREEN, Blocks.GREEN_WOOL);
        sofa(consumer, ModBlocks.SOFA_RED, Blocks.RED_WOOL);
        sofa(consumer, ModBlocks.SOFA_BLACK, Blocks.BLACK_WOOL);
        // Blinds
        blinds(consumer, ModBlocks.BLINDS_OAK, Blocks.OAK_LOG);
        blinds(consumer, ModBlocks.BLINDS_SPRUCE, Blocks.SPRUCE_LOG);
        blinds(consumer, ModBlocks.BLINDS_BIRCH, Blocks.BIRCH_LOG);
        blinds(consumer, ModBlocks.BLINDS_JUNGLE, Blocks.JUNGLE_LOG);
        blinds(consumer, ModBlocks.BLINDS_ACACIA, Blocks.ACACIA_LOG);
        blinds(consumer, ModBlocks.BLINDS_DARK_OAK, Blocks.DARK_OAK_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG);
        // Upgraded Fence
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_OAK, Blocks.OAK_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_SPRUCE, Blocks.SPRUCE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_BIRCH, Blocks.BIRCH_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_JUNGLE, Blocks.JUNGLE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_ACACIA, Blocks.ACACIA_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_DARK_OAK, Blocks.DARK_OAK_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG);
        // Upgraded Gate
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_OAK, Blocks.OAK_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_SPRUCE, Blocks.SPRUCE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_BIRCH, Blocks.BIRCH_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_JUNGLE, Blocks.JUNGLE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_ACACIA, Blocks.ACACIA_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_DARK_OAK, Blocks.DARK_OAK_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG);
        // Picket Fence
        picketFence(consumer, ModBlocks.PICKET_FENCE_WHITE, Blocks.WHITE_CONCRETE, Tags.Items.DYES_WHITE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_ORANGE, Blocks.ORANGE_CONCRETE, Tags.Items.DYES_ORANGE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_MAGENTA, Blocks.MAGENTA_CONCRETE, Tags.Items.DYES_MAGENTA);
        picketFence(consumer, ModBlocks.PICKET_FENCE_LIGHT_BLUE, Blocks.LIGHT_BLUE_CONCRETE, Tags.Items.DYES_LIGHT_BLUE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_YELLOW, Blocks.YELLOW_CONCRETE, Tags.Items.DYES_YELLOW);
        picketFence(consumer, ModBlocks.PICKET_FENCE_LIME, Blocks.LIME_CONCRETE, Tags.Items.DYES_LIME);
        picketFence(consumer, ModBlocks.PICKET_FENCE_PINK, Blocks.PINK_CONCRETE, Tags.Items.DYES_PINK);
        picketFence(consumer, ModBlocks.PICKET_FENCE_GRAY, Blocks.GRAY_CONCRETE, Tags.Items.DYES_GRAY);
        picketFence(consumer, ModBlocks.PICKET_FENCE_LIGHT_GRAY, Blocks.LIGHT_GRAY_CONCRETE, Tags.Items.DYES_LIGHT_GRAY);
        picketFence(consumer, ModBlocks.PICKET_FENCE_CYAN, Blocks.CYAN_CONCRETE, Tags.Items.DYES_CYAN);
        picketFence(consumer, ModBlocks.PICKET_FENCE_PURPLE, Blocks.PURPLE_CONCRETE, Tags.Items.DYES_PURPLE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_BLUE, Blocks.BLUE_CONCRETE, Tags.Items.DYES_BLUE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_BROWN, Blocks.BROWN_CONCRETE, Tags.Items.DYES_BROWN);
        picketFence(consumer, ModBlocks.PICKET_FENCE_GREEN, Blocks.GREEN_CONCRETE, Tags.Items.DYES_GREEN);
        picketFence(consumer, ModBlocks.PICKET_FENCE_RED, Blocks.RED_CONCRETE, Tags.Items.DYES_RED);
        picketFence(consumer, ModBlocks.PICKET_FENCE_BLACK, Blocks.BLACK_CONCRETE, Tags.Items.DYES_BLACK);
        // Picket Gate
        picketGate(consumer, ModBlocks.PICKET_GATE_WHITE, Blocks.WHITE_CONCRETE, Tags.Items.DYES_WHITE);
        picketGate(consumer, ModBlocks.PICKET_GATE_ORANGE, Blocks.ORANGE_CONCRETE, Tags.Items.DYES_ORANGE);
        picketGate(consumer, ModBlocks.PICKET_GATE_MAGENTA, Blocks.MAGENTA_CONCRETE, Tags.Items.DYES_MAGENTA);
        picketGate(consumer, ModBlocks.PICKET_GATE_LIGHT_BLUE, Blocks.LIGHT_BLUE_CONCRETE, Tags.Items.DYES_LIGHT_BLUE);
        picketGate(consumer, ModBlocks.PICKET_GATE_YELLOW, Blocks.YELLOW_CONCRETE, Tags.Items.DYES_YELLOW);
        picketGate(consumer, ModBlocks.PICKET_GATE_LIME, Blocks.LIME_CONCRETE, Tags.Items.DYES_LIME);
        picketGate(consumer, ModBlocks.PICKET_GATE_PINK, Blocks.PINK_CONCRETE, Tags.Items.DYES_PINK);
        picketGate(consumer, ModBlocks.PICKET_GATE_GRAY, Blocks.GRAY_CONCRETE, Tags.Items.DYES_GRAY);
        picketGate(consumer, ModBlocks.PICKET_GATE_LIGHT_GRAY, Blocks.LIGHT_GRAY_CONCRETE, Tags.Items.DYES_LIGHT_GRAY);
        picketGate(consumer, ModBlocks.PICKET_GATE_CYAN, Blocks.CYAN_CONCRETE, Tags.Items.DYES_CYAN);
        picketGate(consumer, ModBlocks.PICKET_GATE_PURPLE, Blocks.PURPLE_CONCRETE, Tags.Items.DYES_PURPLE);
        picketGate(consumer, ModBlocks.PICKET_GATE_BLUE, Blocks.BLUE_CONCRETE, Tags.Items.DYES_BLUE);
        picketGate(consumer, ModBlocks.PICKET_GATE_BROWN, Blocks.BROWN_CONCRETE, Tags.Items.DYES_BROWN);
        picketGate(consumer, ModBlocks.PICKET_GATE_GREEN, Blocks.GREEN_CONCRETE, Tags.Items.DYES_GREEN);
        picketGate(consumer, ModBlocks.PICKET_GATE_RED, Blocks.RED_CONCRETE, Tags.Items.DYES_RED);
        picketGate(consumer, ModBlocks.PICKET_GATE_BLACK, Blocks.BLACK_CONCRETE, Tags.Items.DYES_BLACK);
        // Crate
        crate(consumer, ModBlocks.CRATE_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        crate(consumer, ModBlocks.CRATE_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        crate(consumer, ModBlocks.CRATE_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        crate(consumer, ModBlocks.CRATE_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        crate(consumer, ModBlocks.CRATE_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        crate(consumer, ModBlocks.CRATE_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        crate(consumer, ModBlocks.CRATE_STRIPPED_OAK, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_SPRUCE, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_BIRCH, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_JUNGLE, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_ACACIA, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_DARK_OAK, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        // Park Bench
        parkBench(consumer, ModBlocks.PARK_BENCH_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_OAK, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_SPRUCE, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_BIRCH, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_JUNGLE, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_ACACIA, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        // Post Box
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.POST_BOX)
                .patternLine("III")
                .patternLine("ISI")
                .patternLine("III")
                .key('I', Tags.Items.INGOTS_IRON)
                .key('S', Blocks.BLUE_SHULKER_BOX)
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_shulker_box", hasItem(Blocks.BLUE_SHULKER_BOX))
                .build(consumer);
        // Park Bench
        mailBox(consumer, ModBlocks.MAIL_BOX_OAK, ModBlocks.UPGRADED_FENCE_OAK);
        mailBox(consumer, ModBlocks.MAIL_BOX_SPRUCE, ModBlocks.UPGRADED_FENCE_SPRUCE);
        mailBox(consumer, ModBlocks.MAIL_BOX_BIRCH, ModBlocks.UPGRADED_FENCE_BIRCH);
        mailBox(consumer, ModBlocks.MAIL_BOX_JUNGLE, ModBlocks.UPGRADED_FENCE_JUNGLE);
        mailBox(consumer, ModBlocks.MAIL_BOX_ACACIA, ModBlocks.UPGRADED_FENCE_ACACIA);
        mailBox(consumer, ModBlocks.MAIL_BOX_DARK_OAK, ModBlocks.UPGRADED_FENCE_DARK_OAK);
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_OAK, ModBlocks.UPGRADED_FENCE_STRIPPED_OAK);
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_SPRUCE, ModBlocks.UPGRADED_FENCE_STRIPPED_SPRUCE);
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_BIRCH, ModBlocks.UPGRADED_FENCE_STRIPPED_BIRCH);
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_JUNGLE, ModBlocks.UPGRADED_FENCE_STRIPPED_JUNGLE);
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_ACACIA, ModBlocks.UPGRADED_FENCE_STRIPPED_ACACIA);
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_DARK_OAK, ModBlocks.UPGRADED_FENCE_STRIPPED_DARK_OAK);
        // Hedge
        hedge(consumer, ModBlocks.HEDGE_OAK, Blocks.OAK_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_SPRUCE, Blocks.SPRUCE_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_BIRCH, Blocks.BIRCH_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_JUNGLE, Blocks.JUNGLE_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_ACACIA, Blocks.ACACIA_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_DARK_OAK, Blocks.DARK_OAK_LEAVES);
        // Rock Path
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ROCK_PATH, 16)
                .patternLine("SA")
                .patternLine("AS")
                .key('S', Blocks.STONE)
                .key('A', Blocks.ANDESITE)
                .addCriterion("has_stone", hasItem(Blocks.STONE))
                .addCriterion("has_andesite", hasItem(Blocks.ANDESITE))
                .build(consumer);
        // Trampoline
        trampoline(consumer, ModBlocks.TRAMPOLINE_WHITE, Blocks.WHITE_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_ORANGE, Blocks.ORANGE_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_MAGENTA, Blocks.MAGENTA_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_YELLOW, Blocks.YELLOW_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_LIME, Blocks.LIME_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_PINK, Blocks.PINK_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_GRAY, Blocks.GRAY_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_CYAN, Blocks.CYAN_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_PURPLE, Blocks.PURPLE_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_BLUE, Blocks.BLUE_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_BROWN, Blocks.BROWN_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_GREEN, Blocks.GREEN_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_RED, Blocks.RED_WOOL);
        trampoline(consumer, ModBlocks.TRAMPOLINE_BLACK, Blocks.BLACK_WOOL);
        // Cooler
        cooler(consumer, ModBlocks.COOLER_WHITE, Blocks.WHITE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_ORANGE, Blocks.ORANGE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_MAGENTA, Blocks.MAGENTA_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_LIGHT_BLUE, Blocks.LIGHT_BLUE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_YELLOW, Blocks.YELLOW_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_LIME, Blocks.LIME_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_PINK, Blocks.PINK_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_GRAY, Blocks.GRAY_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_LIGHT_GRAY, Blocks.LIGHT_GRAY_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_CYAN, Blocks.CYAN_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_PURPLE, Blocks.PURPLE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_BLUE, Blocks.BLUE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_BROWN, Blocks.BROWN_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_GREEN, Blocks.GREEN_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_RED, Blocks.RED_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_BLACK, Blocks.BLACK_TERRACOTTA);
        // Grill
        grill(consumer, ModBlocks.GRILL_WHITE, Blocks.WHITE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_ORANGE, Blocks.ORANGE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_MAGENTA, Blocks.MAGENTA_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_LIGHT_BLUE, Blocks.LIGHT_BLUE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_YELLOW, Blocks.YELLOW_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_LIME, Blocks.LIME_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_PINK, Blocks.PINK_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_GRAY, Blocks.GRAY_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_LIGHT_GRAY, Blocks.LIGHT_GRAY_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_CYAN, Blocks.CYAN_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_PURPLE, Blocks.PURPLE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_BLUE, Blocks.BLUE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_BROWN, Blocks.BROWN_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_GREEN, Blocks.GREEN_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_RED, Blocks.RED_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_BLACK, Blocks.BLACK_TERRACOTTA);
        // Door Mat
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.DOOR_MAT)
                .patternLine("WWW")
                .patternLine("WWW")
                .key('W', Items.WHEAT)
                .addCriterion("has_wheat", hasItem(Tags.Items.CROPS_WHEAT))
                .build(consumer);
        // Diving Board
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.DIVING_BOARD)
                .patternLine("CCC")
                .patternLine("SSS")
                .patternLine("G  ")
                .key('C', Blocks.WHITE_CONCRETE)
                .key('S', Tags.Items.SLIMEBALLS)
                .key('G', Blocks.LIGHT_GRAY_CONCRETE)
                .addCriterion("has_wheat", hasItem(Tags.Items.CROPS_WHEAT))
                .build(consumer);
        // Kitchen Counter
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_WHITE, Tags.Items.DYES_WHITE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_ORANGE, Tags.Items.DYES_ORANGE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_MAGENTA, Tags.Items.DYES_MAGENTA);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIGHT_BLUE, Tags.Items.DYES_LIGHT_BLUE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_YELLOW, Tags.Items.DYES_YELLOW);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIME, Tags.Items.DYES_LIME);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_PINK, Tags.Items.DYES_PINK);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_GRAY, Tags.Items.DYES_GRAY);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIGHT_GRAY, Tags.Items.DYES_LIGHT_GRAY);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_CYAN, Tags.Items.DYES_CYAN);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_PURPLE, Tags.Items.DYES_PURPLE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BLUE, Tags.Items.DYES_BLUE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BROWN, Tags.Items.DYES_BROWN);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_GREEN, Tags.Items.DYES_GREEN);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_RED, Tags.Items.DYES_RED);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BLACK, Tags.Items.DYES_BLACK);
        // Kitchen Drawer
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_OAK, Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_SPRUCE, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BIRCH, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_JUNGLE, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_ACACIA, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_WHITE, Tags.Items.DYES_WHITE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_ORANGE, Tags.Items.DYES_ORANGE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_MAGENTA, Tags.Items.DYES_MAGENTA);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE, Tags.Items.DYES_LIGHT_BLUE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_YELLOW, Tags.Items.DYES_YELLOW);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_LIME, Tags.Items.DYES_LIME);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_PINK, Tags.Items.DYES_PINK);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_GRAY, Tags.Items.DYES_GRAY);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY, Tags.Items.DYES_LIGHT_GRAY);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_CYAN, Tags.Items.DYES_CYAN);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_PURPLE, Tags.Items.DYES_PURPLE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BLUE, Tags.Items.DYES_BLUE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BROWN, Tags.Items.DYES_BROWN);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_GREEN, Tags.Items.DYES_GREEN);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_RED, Tags.Items.DYES_RED);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BLACK, Tags.Items.DYES_BLACK);
        // Kitchen Sink
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_OAK, Blocks.WHITE_CONCRETE, Blocks.OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_SPRUCE, Blocks.WHITE_CONCRETE, Blocks.SPRUCE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_BIRCH, Blocks.WHITE_CONCRETE, Blocks.BIRCH_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_JUNGLE, Blocks.WHITE_CONCRETE, Blocks.JUNGLE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_ACACIA, Blocks.WHITE_CONCRETE, Blocks.ACACIA_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_DARK_OAK, Blocks.WHITE_CONCRETE, Blocks.DARK_OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK, Blocks.WHITE_CONCRETE, Blocks.STRIPPED_OAK_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE, Blocks.WHITE_CONCRETE, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH, Blocks.WHITE_CONCRETE, Blocks.STRIPPED_BIRCH_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE, Blocks.WHITE_CONCRETE, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA, Blocks.WHITE_CONCRETE, Blocks.STRIPPED_ACACIA_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK, Blocks.WHITE_CONCRETE, Blocks.STRIPPED_DARK_OAK_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_OAK, Blocks.GRAY_CONCRETE, Blocks.OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_SPRUCE, Blocks.GRAY_CONCRETE, Blocks.SPRUCE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_BIRCH, Blocks.GRAY_CONCRETE, Blocks.BIRCH_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_JUNGLE, Blocks.GRAY_CONCRETE, Blocks.JUNGLE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_ACACIA, Blocks.GRAY_CONCRETE, Blocks.ACACIA_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_DARK_OAK, Blocks.GRAY_CONCRETE, Blocks.DARK_OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK, Blocks.GRAY_CONCRETE, Blocks.STRIPPED_OAK_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE, Blocks.GRAY_CONCRETE, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH, Blocks.GRAY_CONCRETE, Blocks.STRIPPED_BIRCH_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE, Blocks.GRAY_CONCRETE, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA, Blocks.GRAY_CONCRETE, Blocks.STRIPPED_ACACIA_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK, Blocks.GRAY_CONCRETE, Blocks.STRIPPED_DARK_OAK_LOG);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_WHITE, Blocks.WHITE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_ORANGE, Blocks.ORANGE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_MAGENTA, Blocks.MAGENTA_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_BLUE, Blocks.LIGHT_BLUE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_YELLOW, Blocks.YELLOW_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIME, Blocks.LIME_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_PINK, Blocks.PINK_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_GRAY, Blocks.GRAY_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_GRAY, Blocks.LIGHT_GRAY_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_CYAN, Blocks.CYAN_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_PURPLE, Blocks.PURPLE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_BLUE, Blocks.BLUE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_BROWN, Blocks.BROWN_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_GREEN, Blocks.GREEN_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_RED, Blocks.RED_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_BLACK, Blocks.BLACK_TERRACOTTA);
        // Fridges
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.FREEZER_LIGHT)
                .patternLine("CIC")
                .patternLine("IBI")
                .patternLine("CIC")
                .key('C', Blocks.WHITE_CONCRETE)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('B', Tags.Items.CHESTS_WOODEN)
                .setGroup("fridge")
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_chest", hasItem(Tags.Items.CHESTS_WOODEN))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.FREEZER_DARK)
                .patternLine("CIC")
                .patternLine("IBI")
                .patternLine("CIC")
                .key('C', Blocks.GRAY_CONCRETE)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('B', Tags.Items.CHESTS_WOODEN)
                .setGroup("fridge")
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_chest", hasItem(Tags.Items.CHESTS_WOODEN))
                .build(consumer);
        // Spatula
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPATULA)
                .patternLine("B")
                .patternLine("I")
                .patternLine("W")
                .key('B', Items.IRON_BARS)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('W', Blocks.BLACK_WOOL)
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer);
        // Cooking Recipes
        cookingRecipesForMethod(consumer, "grill_cooking", ModRecipeSerializers.GRILL_COOKING, 600);
        // Freezing Recipes
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.WATER_BUCKET), Blocks.ICE, 1, 1000, ModRecipeSerializers.FREEZER_SOLIDIFY).addCriterion("has_water", hasItem(Items.WATER_BUCKET)).build(consumer, "ice_from_freezing");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.ICE), Blocks.PACKED_ICE, 1, 2000, ModRecipeSerializers.FREEZER_SOLIDIFY).addCriterion("has_ice", hasItem(Items.ICE)).build(consumer, "packed_ice_from_freezing");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.PACKED_ICE), Blocks.BLUE_ICE, 1, 4000, ModRecipeSerializers.FREEZER_SOLIDIFY).addCriterion("has_packed_ice", hasItem(Items.PACKED_ICE)).build(consumer, "blue_ice_from_freezing");
    }

    private static void table(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider table, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(table, 4)
                .patternLine("LLL")
                .patternLine(" P ")
                .patternLine(" P ")
                .key('L', log)
                .key('P', planks)
                .setGroup("table")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void chair(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider chair, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(chair, 4)
                .patternLine("L  ")
                .patternLine("LLL")
                .patternLine("P P")
                .key('L', log)
                .key('P', planks)
                .setGroup("chair")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void coffeeTable(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider table, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(table, 4)
                .patternLine("LLL")
                .patternLine("P P")
                .key('L', log)
                .key('P', planks)
                .setGroup("coffee_table")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void cabinet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider cabinet, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(cabinet, 2)
                .patternLine("PPL")
                .patternLine("P L")
                .patternLine("PPL")
                .key('L', log)
                .key('P', planks)
                .setGroup("cabinet")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void bedsideCabinet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider cabinet, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(cabinet, 2)
                .patternLine("LLL")
                .patternLine("P P")
                .patternLine("PPP")
                .key('L', log)
                .key('P', planks)
                .setGroup("bedside_cabinet")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void desk(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider desk, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(desk, 2)
                .patternLine("LLL")
                .patternLine("P P")
                .patternLine("P P")
                .key('L', log)
                .key('P', planks)
                .setGroup("desk")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void deskCabinet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider desk, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(desk, 2)
                .patternLine("LLL")
                .patternLine("PPP")
                .patternLine("P P")
                .key('L', log)
                .key('P', planks)
                .setGroup("desk_cabinet")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void sofa(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider sofa, IItemProvider wool)
    {
        ShapedRecipeBuilder.shapedRecipe(sofa, 2)
                .patternLine("W  ")
                .patternLine("WWW")
                .patternLine("LLL")
                .key('W', wool)
                .key('L', ItemTags.LOGS)
                .setGroup("sofa")
                .addCriterion("has_wool", hasItem(wool))
                .build(recipeConsumer);
    }

    private static void blinds(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider blinds, IItemProvider log)
    {
        ShapedRecipeBuilder.shapedRecipe(blinds, 2)
                .patternLine("LLL")
                .patternLine("SSS")
                .patternLine("SSS")
                .key('L', log)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup("blinds")
                .addCriterion("has_log", hasItem(log))
                .build(recipeConsumer);
    }

    private static void upgradedFence(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fence, IItemProvider log)
    {
        ShapedRecipeBuilder.shapedRecipe(fence, 12)
                .patternLine("LSL")
                .patternLine("LSL")
                .key('L', log)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup("upgraded_fence")
                .addCriterion("has_log", hasItem(log))
                .build(recipeConsumer);
    }

    private static void upgradedGate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider gate, IItemProvider log)
    {
        ShapedRecipeBuilder.shapedRecipe(gate, 2)
                .patternLine("LGL")
                .key('L', log)
                .key('G', Tags.Items.FENCE_GATES_WOODEN)
                .setGroup("upgraded_gate")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_gate", hasItem(Tags.Items.FENCE_GATES_WOODEN))
                .build(recipeConsumer);
    }

    private static void picketFence(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fence, IItemProvider concrete, ITag<Item> dye)
    {
        ShapedRecipeBuilder.shapedRecipe(fence, 12)
                .patternLine("CSC")
                .patternLine("CSC")
                .key('C', concrete)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup("picket_fence")
                .addCriterion("has_concrete", hasItem(concrete))
                .build(recipeConsumer);
        if (fence == ModBlocks.PICKET_FENCE_WHITE)
            return;
        ResourceLocation registryName = fence.asItem().getRegistryName();
        ShapedRecipeBuilder.shapedRecipe(fence, 8)
                .patternLine("FFF")
                .patternLine("FDF")
                .patternLine("FFF")
                .key('F', ModBlocks.PICKET_FENCE_WHITE)
                .key('D', dye)
                .setGroup("picket_fence")
                .addCriterion("has_fence", hasItem(ModBlocks.PICKET_FENCE_WHITE))
                .addCriterion("has_dye", hasItem(dye))
                .build(recipeConsumer, new ResourceLocation(registryName.getNamespace(), "dye_" + registryName.getPath()));
    }

    private static void picketGate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider gate, IItemProvider concrete, ITag<Item> dye)
    {
        ShapedRecipeBuilder.shapedRecipe(gate, 2)
                .patternLine("CGC")
                .key('C', concrete)
                .key('G', Tags.Items.FENCE_GATES_WOODEN)
                .setGroup("picket_fence")
                .addCriterion("has_concrete", hasItem(concrete))
                .addCriterion("has_gate", hasItem(Tags.Items.FENCE_GATES_WOODEN))
                .build(recipeConsumer);
        if (gate == ModBlocks.PICKET_GATE_WHITE)
            return;
        ResourceLocation registryName = gate.asItem().getRegistryName();
        ShapedRecipeBuilder.shapedRecipe(gate, 8)
                .patternLine("GGG")
                .patternLine("GDG")
                .patternLine("GGG")
                .key('G', ModBlocks.PICKET_GATE_WHITE)
                .key('D', dye)
                .setGroup("picket_fence")
                .addCriterion("has_gate", hasItem(ModBlocks.PICKET_GATE_WHITE))
                .addCriterion("has_dye", hasItem(dye))
                .build(recipeConsumer, new ResourceLocation(registryName.getNamespace(), "dye_" + registryName.getPath()));
    }

    private static void crate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider crate, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(crate, 2)
                .patternLine("LPL")
                .patternLine("P P")
                .patternLine("LPL")
                .key('L', log)
                .key('P', planks)
                .setGroup("crate")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void parkBench(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider bench, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(bench, 4)
                .patternLine("PPP")
                .patternLine("PPP")
                .patternLine("L L")
                .key('L', log)
                .key('P', planks)
                .setGroup("park_bench")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void mailBox(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider mailbox, IItemProvider fence)
    {
        ShapedRecipeBuilder.shapedRecipe(mailbox)
                .patternLine("C")
                .patternLine("F")
                .patternLine("F")
                .key('C', Tags.Items.CHESTS_WOODEN)
                .key('F', fence)
                .setGroup("mail_box")
                .addCriterion("has_postbox", hasItem(ModBlocks.POST_BOX)) // Mailbox depends on post box so if you have a postbox unlock all mailboxes
                .addCriterion("has_chest", hasItem(Tags.Items.CHESTS_WOODEN))
                .build(recipeConsumer);
    }

    private static void hedge(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider hedge, IItemProvider leaves)
    {
        ShapedRecipeBuilder.shapedRecipe(hedge, 12)
                .patternLine("LLL")
                .patternLine("LLL")
                .key('L', leaves)
                .setGroup("hedge")
                .addCriterion("has_leaves", hasItem(leaves))
                .build(recipeConsumer);
    }

    private static void trampoline(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider trampoline, IItemProvider wool)
    {
        ShapedRecipeBuilder.shapedRecipe(trampoline, 4)
                .patternLine("WSW")
                .patternLine("I I")
                .patternLine("III")
                .key('W', wool)
                .key('S', Tags.Items.SLIMEBALLS)
                .key('I', Tags.Items.INGOTS_IRON)
                .setGroup("trampoline")
                .addCriterion("has_wool", hasItem(wool))
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .build(recipeConsumer);
    }

    private static void cooler(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider cooler, IItemProvider terracotta)
    {
        ShapedRecipeBuilder.shapedRecipe(cooler, 2)
                .patternLine("TTT")
                .patternLine("WCW")
                .patternLine("TTT")
                .key('T', terracotta)
                .key('W', Blocks.WHITE_CONCRETE)
                .key('C', Tags.Items.CHESTS_WOODEN)
                .setGroup("trampoline")
                .addCriterion("has_terracotta", hasItem(terracotta))
                .addCriterion("has_chest", hasItem(Tags.Items.CHESTS_WOODEN))
                .build(recipeConsumer);
    }

    private static void grill(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider grill, IItemProvider terracotta)
    {
        ShapedRecipeBuilder.shapedRecipe(grill)
                .patternLine("TBT")
                .patternLine("I I")
                .patternLine("I I")
                .key('T', terracotta)
                .key('B', Items.IRON_BARS)
                .key('I', Tags.Items.INGOTS_IRON)
                .setGroup("grill")
                .addCriterion("has_terracotta", hasItem(terracotta))
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .build(recipeConsumer);
    }

    private static void kitchenCounter(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider counter, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(counter, 8)
                .patternLine("LLL")
                .patternLine("PPP")
                .patternLine("PPP")
                .key('L', log)
                .key('P', planks)
                .setGroup("kitchen_counter")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .build(recipeConsumer);
    }

    private static void coloredKitchenCounter(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider counter, ITag<Item> dye)
    {
        ShapedRecipeBuilder.shapedRecipe(counter, 8)
                .patternLine("SDS")
                .patternLine("CCC")
                .patternLine("CCC")
                .key('S', Tags.Items.STONE)
                .key('D', dye)
                .key('C', Blocks.TERRACOTTA)
                .setGroup("kitchen_counter")
                .addCriterion("has_stone", hasItem(Tags.Items.STONE))
                .addCriterion("has_terracotta", hasItem(Blocks.TERRACOTTA))
                .build(recipeConsumer);
    }

    private static void kitchenDrawer(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider counter, IItemProvider log, IItemProvider planks)
    {
        ShapedRecipeBuilder.shapedRecipe(counter, 4)
                .patternLine("LLL")
                .patternLine("PCP")
                .patternLine("PPP")
                .key('L', log)
                .key('P', planks)
                .key('C', Tags.Items.CHESTS_WOODEN)
                .setGroup("kitchen_drawer")
                .addCriterion("has_log", hasItem(log))
                .addCriterion("has_planks", hasItem(planks))
                .addCriterion("has_chest", hasItem(Tags.Items.CHESTS_WOODEN))
                .build(recipeConsumer);
    }

    private static void coloredKitchenDrawer(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider counter, ITag<Item> dye)
    {
        ShapedRecipeBuilder.shapedRecipe(counter, 4)
                .patternLine("SDS")
                .patternLine("CBC")
                .patternLine("CCC")
                .key('S', Tags.Items.STONE)
                .key('D', dye)
                .key('C', Blocks.TERRACOTTA)
                .key('B', Tags.Items.CHESTS_WOODEN)
                .setGroup("kitchen_drawer")
                .addCriterion("has_stone", hasItem(Tags.Items.STONE))
                .addCriterion("has_terracotta", hasItem(Blocks.TERRACOTTA))
                .addCriterion("has_chest", hasItem(Tags.Items.CHESTS_WOODEN))
                .build(recipeConsumer);
    }

    private static void kitchenSink(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider sink, IItemProvider top, IItemProvider bottom)
    {
        ShapedRecipeBuilder.shapedRecipe(sink, 2)
                .patternLine("CIC")
                .patternLine("PBP")
                .patternLine("PPP")
                .key('C', top)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', bottom)
                .key('B', Items.BUCKET)
                .setGroup("kitchen_sink")
                .addCriterion("has_top", hasItem(top))
                .addCriterion("has_bottom", hasItem(bottom))
                .addCriterion("has_bucket", hasItem(Items.BUCKET))
                .build(recipeConsumer);
    }

    private static void coloredKitchenSink(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider sink, IItemProvider top)
    {
        ShapedRecipeBuilder.shapedRecipe(sink, 2)
                .patternLine("CIC")
                .patternLine("PBP")
                .patternLine("PPP")
                .key('C', top)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', Tags.Items.STONE)
                .key('B', Items.BUCKET)
                .setGroup("kitchen_sink")
                .addCriterion("has_top", hasItem(top))
                .addCriterion("has_stone", hasItem(Tags.Items.STONE))
                .addCriterion("has_bucket", hasItem(Items.BUCKET))
                .build(recipeConsumer);
    }

    private static void cookingRecipesForMethod(Consumer<IFinishedRecipe> recipeConsumer, String recipeConsumerIn, CookingRecipeSerializer<?> cookingMethod, int cookingTime)
    {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.BEEF), Items.COOKED_BEEF, 0.35F, cookingTime, cookingMethod).addCriterion("has_beef", hasItem(Items.BEEF)).build(recipeConsumer, "cooked_beef_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, cookingTime, cookingMethod).addCriterion("has_chicken", hasItem(Items.CHICKEN)).build(recipeConsumer, "cooked_chicken_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.COD), Items.COOKED_COD, 0.35F, cookingTime, cookingMethod).addCriterion("has_cod", hasItem(Items.COD)).build(recipeConsumer, "cooked_cod_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Blocks.KELP), Items.DRIED_KELP, 0.1F, cookingTime, cookingMethod).addCriterion("has_kelp", hasItem(Blocks.KELP)).build(recipeConsumer, "dried_kelp_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.SALMON), Items.COOKED_SALMON, 0.35F, cookingTime, cookingMethod).addCriterion("has_salmon", hasItem(Items.SALMON)).build(recipeConsumer, "cooked_salmon_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, cookingTime, cookingMethod).addCriterion("has_mutton", hasItem(Items.MUTTON)).build(recipeConsumer, "cooked_mutton_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, cookingTime, cookingMethod).addCriterion("has_porkchop", hasItem(Items.PORKCHOP)).build(recipeConsumer, "cooked_porkchop_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.POTATO), Items.BAKED_POTATO, 0.35F, cookingTime, cookingMethod).addCriterion("has_potato", hasItem(Items.POTATO)).build(recipeConsumer, "baked_potato_from_" + recipeConsumerIn);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, cookingTime, cookingMethod).addCriterion("has_rabbit", hasItem(Items.RABBIT)).build(recipeConsumer, "cooked_rabbit_from_" + recipeConsumerIn);
    }
}