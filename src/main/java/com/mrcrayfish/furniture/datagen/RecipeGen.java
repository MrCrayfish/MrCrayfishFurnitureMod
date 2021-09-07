package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.data.ForgeShapedRecipeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;

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
        table(consumer, ModBlocks.TABLE_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        table(consumer, ModBlocks.TABLE_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        table(consumer, ModBlocks.TABLE_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        table(consumer, ModBlocks.TABLE_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        table(consumer, ModBlocks.TABLE_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        table(consumer, ModBlocks.TABLE_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        table(consumer, ModBlocks.TABLE_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        table(consumer, ModBlocks.TABLE_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        table(consumer, ModBlocks.TABLE_STONE.get(), Blocks.STONE, Blocks.COBBLESTONE);
        table(consumer, ModBlocks.TABLE_GRANITE.get(), Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        table(consumer, ModBlocks.TABLE_DIORITE.get(), Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        table(consumer, ModBlocks.TABLE_ANDESITE.get(), Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        table(consumer, ModBlocks.TABLE_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        table(consumer, ModBlocks.TABLE_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM);
        table(consumer, ModBlocks.TABLE_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_STEM);
        table(consumer, ModBlocks.TABLE_WHITE_MODERN.get(), Blocks.WHITE_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_ORANGE_MODERN.get(), Blocks.ORANGE_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_MAGENTA_MODERN.get(), Blocks.MAGENTA_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_LIGHT_BLUE_MODERN.get(), Blocks.LIGHT_BLUE_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_YELLOW_MODERN.get(), Blocks.YELLOW_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_LIME_MODERN.get(), Blocks.LIME_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_PINK_MODERN.get(), Blocks.PINK_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_GRAY_MODERN.get(), Blocks.GRAY_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_LIGHT_GRAY_MODERN.get(), Blocks.LIGHT_GRAY_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_CYAN_MODERN.get(), Blocks.CYAN_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_PURPLE_MODERN.get(), Blocks.PURPLE_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_BLUE_MODERN.get(), Blocks.BLUE_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_BROWN_MODERN.get(), Blocks.BROWN_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_GREEN_MODERN.get(), Blocks.GREEN_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_RED_MODERN.get(), Blocks.RED_CONCRETE, Blocks.STONE);
        table(consumer, ModBlocks.TABLE_BLACK_MODERN.get(), Blocks.BLACK_CONCRETE, Blocks.STONE);
        // Chairs
        chair(consumer, ModBlocks.CHAIR_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        chair(consumer, ModBlocks.CHAIR_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        chair(consumer, ModBlocks.CHAIR_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        chair(consumer, ModBlocks.CHAIR_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        chair(consumer, ModBlocks.CHAIR_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        chair(consumer, ModBlocks.CHAIR_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        chair(consumer, ModBlocks.CHAIR_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        chair(consumer, ModBlocks.CHAIR_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        chair(consumer, ModBlocks.CHAIR_STONE.get(), Blocks.STONE, Blocks.COBBLESTONE);
        chair(consumer, ModBlocks.CHAIR_GRANITE.get(), Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        chair(consumer, ModBlocks.CHAIR_DIORITE.get(), Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        chair(consumer, ModBlocks.CHAIR_ANDESITE.get(), Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM);
        chair(consumer, ModBlocks.CHAIR_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_STEM);
        chair(consumer, ModBlocks.CHAIR_WHITE_MODERN.get(), Blocks.WHITE_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_ORANGE_MODERN.get(), Blocks.ORANGE_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_MAGENTA_MODERN.get(), Blocks.MAGENTA_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_LIGHT_BLUE_MODERN.get(), Blocks.LIGHT_BLUE_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_YELLOW_MODERN.get(), Blocks.YELLOW_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_LIME_MODERN.get(), Blocks.LIME_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_PINK_MODERN.get(), Blocks.PINK_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_GRAY_MODERN.get(), Blocks.GRAY_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_LIGHT_GRAY_MODERN.get(), Blocks.LIGHT_GRAY_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_CYAN_MODERN.get(), Blocks.CYAN_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_PURPLE_MODERN.get(), Blocks.PURPLE_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_BLUE_MODERN.get(), Blocks.BLUE_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_BROWN_MODERN.get(), Blocks.BROWN_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_GREEN_MODERN.get(), Blocks.GREEN_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_RED_MODERN.get(), Blocks.RED_CONCRETE, Blocks.STONE);
        chair(consumer, ModBlocks.CHAIR_BLACK_MODERN.get(), Blocks.BLACK_CONCRETE, Blocks.STONE);
        // Coffee Table
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STONE.get(), Blocks.STONE, Blocks.COBBLESTONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_GRANITE.get(), Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_DIORITE.get(), Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_ANDESITE.get(), Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_STEM);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_WHITE_MODERN.get(), Blocks.WHITE_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_ORANGE_MODERN.get(), Blocks.ORANGE_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_MAGENTA_MODERN.get(), Blocks.MAGENTA_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_LIGHT_BLUE_MODERN.get(), Blocks.LIGHT_BLUE_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_YELLOW_MODERN.get(), Blocks.YELLOW_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_LIME_MODERN.get(), Blocks.LIME_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_PINK_MODERN.get(), Blocks.PINK_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_GRAY_MODERN.get(), Blocks.GRAY_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_LIGHT_GRAY_MODERN.get(), Blocks.LIGHT_GRAY_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_CYAN_MODERN.get(), Blocks.CYAN_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_PURPLE_MODERN.get(), Blocks.PURPLE_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_BLUE_MODERN.get(), Blocks.BLUE_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_BROWN_MODERN.get(), Blocks.BROWN_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_GREEN_MODERN.get(), Blocks.GREEN_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_RED_MODERN.get(), Blocks.RED_CONCRETE, Blocks.STONE);
        coffeeTable(consumer, ModBlocks.COFFEE_TABLE_BLACK_MODERN.get(), Blocks.BLACK_CONCRETE, Blocks.STONE);
        // Cabinet
        cabinet(consumer, ModBlocks.CABINET_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        cabinet(consumer, ModBlocks.CABINET_STONE.get(), Blocks.STONE, Blocks.COBBLESTONE);
        cabinet(consumer, ModBlocks.CABINET_GRANITE.get(), Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        cabinet(consumer, ModBlocks.CABINET_DIORITE.get(), Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        cabinet(consumer, ModBlocks.CABINET_ANDESITE.get(), Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_OAK.get(), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        cabinet(consumer, ModBlocks.CABINET_STRIPPED_WARPED.get(), Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        cabinet(consumer, ModBlocks.CABINET_WHITE_MODERN.get(), Blocks.WHITE_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_ORANGE_MODERN.get(), Blocks.ORANGE_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_MAGENTA_MODERN.get(), Blocks.MAGENTA_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_LIGHT_BLUE_MODERN.get(), Blocks.LIGHT_BLUE_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_YELLOW_MODERN.get(), Blocks.YELLOW_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_LIME_MODERN.get(), Blocks.LIME_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_PINK_MODERN.get(), Blocks.PINK_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_GRAY_MODERN.get(), Blocks.GRAY_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_LIGHT_GRAY_MODERN.get(), Blocks.LIGHT_GRAY_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_CYAN_MODERN.get(), Blocks.CYAN_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_PURPLE_MODERN.get(), Blocks.PURPLE_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_BLUE_MODERN.get(), Blocks.BLUE_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_BROWN_MODERN.get(), Blocks.BROWN_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_GREEN_MODERN.get(), Blocks.GREEN_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_RED_MODERN.get(), Blocks.RED_CONCRETE, Blocks.STONE);
        cabinet(consumer, ModBlocks.CABINET_BLACK_MODERN.get(), Blocks.BLACK_CONCRETE, Blocks.STONE);
        // Coffee Table
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STONE.get(), Blocks.STONE, Blocks.COBBLESTONE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_GRANITE.get(), Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_DIORITE.get(), Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_ANDESITE.get(), Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_STEM);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_WHITE_MODERN.get(), Blocks.STONE, Blocks.WHITE_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_ORANGE_MODERN.get(), Blocks.STONE, Blocks.ORANGE_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_MAGENTA_MODERN.get(), Blocks.STONE, Blocks.MAGENTA_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_LIGHT_BLUE_MODERN.get(), Blocks.STONE, Blocks.LIGHT_BLUE_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_YELLOW_MODERN.get(), Blocks.STONE, Blocks.YELLOW_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_LIME_MODERN.get(), Blocks.STONE, Blocks.LIME_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_PINK_MODERN.get(), Blocks.STONE, Blocks.PINK_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_GRAY_MODERN.get(), Blocks.STONE, Blocks.GRAY_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_LIGHT_GRAY_MODERN.get(), Blocks.STONE, Blocks.LIGHT_GRAY_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_CYAN_MODERN.get(), Blocks.STONE, Blocks.CYAN_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_PURPLE_MODERN.get(), Blocks.STONE, Blocks.PURPLE_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_BLUE_MODERN.get(), Blocks.STONE, Blocks.BLUE_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_BROWN_MODERN.get(), Blocks.STONE, Blocks.BROWN_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_GREEN_MODERN.get(), Blocks.STONE, Blocks.GREEN_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_RED_MODERN.get(), Blocks.STONE, Blocks.RED_CONCRETE);
        bedsideCabinet(consumer, ModBlocks.BEDSIDE_CABINET_BLACK_MODERN.get(), Blocks.STONE, Blocks.BLACK_CONCRETE);
        // Desk
        desk(consumer, ModBlocks.DESK_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        desk(consumer, ModBlocks.DESK_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        desk(consumer, ModBlocks.DESK_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        desk(consumer, ModBlocks.DESK_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        desk(consumer, ModBlocks.DESK_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        desk(consumer, ModBlocks.DESK_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        desk(consumer, ModBlocks.DESK_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        desk(consumer, ModBlocks.DESK_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        desk(consumer, ModBlocks.DESK_STONE.get(), Blocks.STONE, Blocks.COBBLESTONE);
        desk(consumer, ModBlocks.DESK_GRANITE.get(), Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        desk(consumer, ModBlocks.DESK_DIORITE.get(), Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        desk(consumer, ModBlocks.DESK_ANDESITE.get(), Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        desk(consumer, ModBlocks.DESK_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        desk(consumer, ModBlocks.DESK_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM);
        desk(consumer, ModBlocks.DESK_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_STEM);
        desk(consumer, ModBlocks.DESK_WHITE_MODERN.get(), Blocks.STONE, Blocks.WHITE_CONCRETE);
        desk(consumer, ModBlocks.DESK_ORANGE_MODERN.get(), Blocks.STONE, Blocks.ORANGE_CONCRETE);
        desk(consumer, ModBlocks.DESK_MAGENTA_MODERN.get(), Blocks.STONE, Blocks.MAGENTA_CONCRETE);
        desk(consumer, ModBlocks.DESK_LIGHT_BLUE_MODERN.get(), Blocks.STONE, Blocks.LIGHT_BLUE_CONCRETE);
        desk(consumer, ModBlocks.DESK_YELLOW_MODERN.get(), Blocks.STONE, Blocks.YELLOW_CONCRETE);
        desk(consumer, ModBlocks.DESK_LIME_MODERN.get(), Blocks.STONE, Blocks.LIME_CONCRETE);
        desk(consumer, ModBlocks.DESK_PINK_MODERN.get(), Blocks.STONE, Blocks.PINK_CONCRETE);
        desk(consumer, ModBlocks.DESK_GRAY_MODERN.get(), Blocks.STONE, Blocks.GRAY_CONCRETE);
        desk(consumer, ModBlocks.DESK_LIGHT_GRAY_MODERN.get(), Blocks.STONE, Blocks.LIGHT_GRAY_CONCRETE);
        desk(consumer, ModBlocks.DESK_CYAN_MODERN.get(), Blocks.STONE, Blocks.CYAN_CONCRETE);
        desk(consumer, ModBlocks.DESK_PURPLE_MODERN.get(), Blocks.STONE, Blocks.PURPLE_CONCRETE);
        desk(consumer, ModBlocks.DESK_BLUE_MODERN.get(), Blocks.STONE, Blocks.BLUE_CONCRETE);
        desk(consumer, ModBlocks.DESK_BROWN_MODERN.get(), Blocks.STONE, Blocks.BROWN_CONCRETE);
        desk(consumer, ModBlocks.DESK_GREEN_MODERN.get(), Blocks.STONE, Blocks.GREEN_CONCRETE);
        desk(consumer, ModBlocks.DESK_RED_MODERN.get(), Blocks.STONE, Blocks.RED_CONCRETE);
        desk(consumer, ModBlocks.DESK_BLACK_MODERN.get(), Blocks.STONE, Blocks.BLACK_CONCRETE);
        // Desk Cabinet
        deskCabinet(consumer, ModBlocks.DESK_CABINET_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STONE.get(), Blocks.STONE, Blocks.COBBLESTONE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_GRANITE.get(), Blocks.POLISHED_GRANITE, Blocks.GRANITE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_DIORITE.get(), Blocks.POLISHED_DIORITE, Blocks.DIORITE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_ANDESITE.get(), Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_STEM);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_WHITE_MODERN.get(), Blocks.STONE, Blocks.WHITE_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_ORANGE_MODERN.get(), Blocks.STONE, Blocks.ORANGE_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_MAGENTA_MODERN.get(), Blocks.STONE, Blocks.MAGENTA_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_LIGHT_BLUE_MODERN.get(), Blocks.STONE, Blocks.LIGHT_BLUE_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_YELLOW_MODERN.get(), Blocks.STONE, Blocks.YELLOW_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_LIME_MODERN.get(), Blocks.STONE, Blocks.LIME_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_PINK_MODERN.get(), Blocks.STONE, Blocks.PINK_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_GRAY_MODERN.get(), Blocks.STONE, Blocks.GRAY_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_LIGHT_GRAY_MODERN.get(), Blocks.STONE, Blocks.LIGHT_GRAY_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_CYAN_MODERN.get(), Blocks.STONE, Blocks.CYAN_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_PURPLE_MODERN.get(), Blocks.STONE, Blocks.PURPLE_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_BLUE_MODERN.get(), Blocks.STONE, Blocks.BLUE_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_BROWN_MODERN.get(), Blocks.STONE, Blocks.BROWN_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_GREEN_MODERN.get(), Blocks.STONE, Blocks.GREEN_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_RED_MODERN.get(), Blocks.STONE, Blocks.RED_CONCRETE);
        deskCabinet(consumer, ModBlocks.DESK_CABINET_BLACK_MODERN.get(), Blocks.STONE, Blocks.BLACK_CONCRETE);
        // Sofa
        sofa(consumer, ModBlocks.SOFA_WHITE.get(), Blocks.WHITE_WOOL);
        sofa(consumer, ModBlocks.SOFA_ORANGE.get(), Blocks.ORANGE_WOOL);
        sofa(consumer, ModBlocks.SOFA_MAGENTA.get(), Blocks.MAGENTA_WOOL);
        sofa(consumer, ModBlocks.SOFA_LIGHT_BLUE.get(), Blocks.LIGHT_BLUE_WOOL);
        sofa(consumer, ModBlocks.SOFA_YELLOW.get(), Blocks.YELLOW_WOOL);
        sofa(consumer, ModBlocks.SOFA_LIME.get(), Blocks.LIME_WOOL);
        sofa(consumer, ModBlocks.SOFA_PINK.get(), Blocks.PINK_WOOL);
        sofa(consumer, ModBlocks.SOFA_GRAY.get(), Blocks.GRAY_WOOL);
        sofa(consumer, ModBlocks.SOFA_LIGHT_GRAY.get(), Blocks.LIGHT_GRAY_WOOL);
        sofa(consumer, ModBlocks.SOFA_CYAN.get(), Blocks.CYAN_WOOL);
        sofa(consumer, ModBlocks.SOFA_PURPLE.get(), Blocks.PURPLE_WOOL);
        sofa(consumer, ModBlocks.SOFA_BLUE.get(), Blocks.BLUE_WOOL);
        sofa(consumer, ModBlocks.SOFA_BROWN.get(), Blocks.BROWN_WOOL);
        sofa(consumer, ModBlocks.SOFA_GREEN.get(), Blocks.GREEN_WOOL);
        sofa(consumer, ModBlocks.SOFA_RED.get(), Blocks.RED_WOOL);
        sofa(consumer, ModBlocks.SOFA_BLACK.get(), Blocks.BLACK_WOOL);
        sofa(consumer, ModBlocks.SOFA_WHITE_MODERN.get(), Blocks.WHITE_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_ORANGE_MODERN.get(), Blocks.ORANGE_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_MAGENTA_MODERN.get(), Blocks.MAGENTA_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_LIGHT_BLUE_MODERN.get(), Blocks.LIGHT_BLUE_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_YELLOW_MODERN.get(), Blocks.YELLOW_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_LIME_MODERN.get(), Blocks.LIME_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_PINK_MODERN.get(), Blocks.PINK_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_GRAY_MODERN.get(), Blocks.GRAY_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_LIGHT_GRAY_MODERN.get(), Blocks.LIGHT_GRAY_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_CYAN_MODERN.get(), Blocks.CYAN_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_PURPLE_MODERN.get(), Blocks.PURPLE_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_BLUE_MODERN.get(), Blocks.BLUE_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_BROWN_MODERN.get(), Blocks.BROWN_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_GREEN_MODERN.get(), Blocks.GREEN_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_RED_MODERN.get(), Blocks.RED_CONCRETE);
        sofa(consumer, ModBlocks.SOFA_BLACK_MODERN.get(), Blocks.BLACK_CONCRETE);
        // Blinds
        blinds(consumer, ModBlocks.BLINDS_OAK.get(), Blocks.OAK_LOG);
        blinds(consumer, ModBlocks.BLINDS_SPRUCE.get(), Blocks.SPRUCE_LOG);
        blinds(consumer, ModBlocks.BLINDS_BIRCH.get(), Blocks.BIRCH_LOG);
        blinds(consumer, ModBlocks.BLINDS_JUNGLE.get(), Blocks.JUNGLE_LOG);
        blinds(consumer, ModBlocks.BLINDS_ACACIA.get(), Blocks.ACACIA_LOG);
        blinds(consumer, ModBlocks.BLINDS_DARK_OAK.get(), Blocks.DARK_OAK_LOG);
        blinds(consumer, ModBlocks.BLINDS_CRIMSON.get(), Blocks.CRIMSON_STEM);
        blinds(consumer, ModBlocks.BLINDS_WARPED.get(), Blocks.WARPED_STEM);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM);
        blinds(consumer, ModBlocks.BLINDS_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM);
        // Upgraded Fence
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_OAK.get(), Blocks.OAK_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_SPRUCE.get(), Blocks.SPRUCE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_BIRCH.get(), Blocks.BIRCH_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_JUNGLE.get(), Blocks.JUNGLE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_ACACIA.get(), Blocks.ACACIA_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_DARK_OAK.get(), Blocks.DARK_OAK_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_CRIMSON.get(), Blocks.CRIMSON_STEM);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_WARPED.get(), Blocks.WARPED_STEM);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM);
        upgradedFence(consumer, ModBlocks.UPGRADED_FENCE_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM);
        // Upgraded Gate
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_OAK.get(), Blocks.OAK_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_SPRUCE.get(), Blocks.SPRUCE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_BIRCH.get(), Blocks.BIRCH_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_JUNGLE.get(), Blocks.JUNGLE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_ACACIA.get(), Blocks.ACACIA_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_DARK_OAK.get(), Blocks.DARK_OAK_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_CRIMSON.get(), Blocks.CRIMSON_STEM);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_WARPED.get(), Blocks.WARPED_STEM);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM);
        upgradedGate(consumer, ModBlocks.UPGRADED_GATE_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM);
        // Picket Fence
        picketFence(consumer, ModBlocks.PICKET_FENCE_WHITE.get(), Blocks.WHITE_CONCRETE, Tags.Items.DYES_WHITE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_ORANGE.get(), Blocks.ORANGE_CONCRETE, Tags.Items.DYES_ORANGE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_MAGENTA.get(), Blocks.MAGENTA_CONCRETE, Tags.Items.DYES_MAGENTA);
        picketFence(consumer, ModBlocks.PICKET_FENCE_LIGHT_BLUE.get(), Blocks.LIGHT_BLUE_CONCRETE, Tags.Items.DYES_LIGHT_BLUE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_YELLOW.get(), Blocks.YELLOW_CONCRETE, Tags.Items.DYES_YELLOW);
        picketFence(consumer, ModBlocks.PICKET_FENCE_LIME.get(), Blocks.LIME_CONCRETE, Tags.Items.DYES_LIME);
        picketFence(consumer, ModBlocks.PICKET_FENCE_PINK.get(), Blocks.PINK_CONCRETE, Tags.Items.DYES_PINK);
        picketFence(consumer, ModBlocks.PICKET_FENCE_GRAY.get(), Blocks.GRAY_CONCRETE, Tags.Items.DYES_GRAY);
        picketFence(consumer, ModBlocks.PICKET_FENCE_LIGHT_GRAY.get(), Blocks.LIGHT_GRAY_CONCRETE, Tags.Items.DYES_LIGHT_GRAY);
        picketFence(consumer, ModBlocks.PICKET_FENCE_CYAN.get(), Blocks.CYAN_CONCRETE, Tags.Items.DYES_CYAN);
        picketFence(consumer, ModBlocks.PICKET_FENCE_PURPLE.get(), Blocks.PURPLE_CONCRETE, Tags.Items.DYES_PURPLE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_BLUE.get(), Blocks.BLUE_CONCRETE, Tags.Items.DYES_BLUE);
        picketFence(consumer, ModBlocks.PICKET_FENCE_BROWN.get(), Blocks.BROWN_CONCRETE, Tags.Items.DYES_BROWN);
        picketFence(consumer, ModBlocks.PICKET_FENCE_GREEN.get(), Blocks.GREEN_CONCRETE, Tags.Items.DYES_GREEN);
        picketFence(consumer, ModBlocks.PICKET_FENCE_RED.get(), Blocks.RED_CONCRETE, Tags.Items.DYES_RED);
        picketFence(consumer, ModBlocks.PICKET_FENCE_BLACK.get(), Blocks.BLACK_CONCRETE, Tags.Items.DYES_BLACK);
        // Picket Gate
        picketGate(consumer, ModBlocks.PICKET_GATE_WHITE.get(), Blocks.WHITE_CONCRETE, Tags.Items.DYES_WHITE);
        picketGate(consumer, ModBlocks.PICKET_GATE_ORANGE.get(), Blocks.ORANGE_CONCRETE, Tags.Items.DYES_ORANGE);
        picketGate(consumer, ModBlocks.PICKET_GATE_MAGENTA.get(), Blocks.MAGENTA_CONCRETE, Tags.Items.DYES_MAGENTA);
        picketGate(consumer, ModBlocks.PICKET_GATE_LIGHT_BLUE.get(), Blocks.LIGHT_BLUE_CONCRETE, Tags.Items.DYES_LIGHT_BLUE);
        picketGate(consumer, ModBlocks.PICKET_GATE_YELLOW.get(), Blocks.YELLOW_CONCRETE, Tags.Items.DYES_YELLOW);
        picketGate(consumer, ModBlocks.PICKET_GATE_LIME.get(), Blocks.LIME_CONCRETE, Tags.Items.DYES_LIME);
        picketGate(consumer, ModBlocks.PICKET_GATE_PINK.get(), Blocks.PINK_CONCRETE, Tags.Items.DYES_PINK);
        picketGate(consumer, ModBlocks.PICKET_GATE_GRAY.get(), Blocks.GRAY_CONCRETE, Tags.Items.DYES_GRAY);
        picketGate(consumer, ModBlocks.PICKET_GATE_LIGHT_GRAY.get(), Blocks.LIGHT_GRAY_CONCRETE, Tags.Items.DYES_LIGHT_GRAY);
        picketGate(consumer, ModBlocks.PICKET_GATE_CYAN.get(), Blocks.CYAN_CONCRETE, Tags.Items.DYES_CYAN);
        picketGate(consumer, ModBlocks.PICKET_GATE_PURPLE.get(), Blocks.PURPLE_CONCRETE, Tags.Items.DYES_PURPLE);
        picketGate(consumer, ModBlocks.PICKET_GATE_BLUE.get(), Blocks.BLUE_CONCRETE, Tags.Items.DYES_BLUE);
        picketGate(consumer, ModBlocks.PICKET_GATE_BROWN.get(), Blocks.BROWN_CONCRETE, Tags.Items.DYES_BROWN);
        picketGate(consumer, ModBlocks.PICKET_GATE_GREEN.get(), Blocks.GREEN_CONCRETE, Tags.Items.DYES_GREEN);
        picketGate(consumer, ModBlocks.PICKET_GATE_RED.get(), Blocks.RED_CONCRETE, Tags.Items.DYES_RED);
        picketGate(consumer, ModBlocks.PICKET_GATE_BLACK.get(), Blocks.BLACK_CONCRETE, Tags.Items.DYES_BLACK);
        // Crate
        crate(consumer, ModBlocks.CRATE_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        crate(consumer, ModBlocks.CRATE_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        crate(consumer, ModBlocks.CRATE_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        crate(consumer, ModBlocks.CRATE_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        crate(consumer, ModBlocks.CRATE_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        crate(consumer, ModBlocks.CRATE_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        crate(consumer, ModBlocks.CRATE_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        crate(consumer, ModBlocks.CRATE_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        crate(consumer, ModBlocks.CRATE_STRIPPED_OAK.get(), Blocks.STRIPPED_OAK_LOG, Blocks.OAK_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_SPRUCE.get(), Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_BIRCH.get(), Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_JUNGLE.get(), Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_ACACIA.get(), Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_DARK_OAK.get(), Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG);
        crate(consumer, ModBlocks.CRATE_STRIPPED_CRIMSON.get(), Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM);
        crate(consumer, ModBlocks.CRATE_STRIPPED_WARPED.get(), Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_STEM);
        // Park Bench
        parkBench(consumer, ModBlocks.PARK_BENCH_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_OAK.get(), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        parkBench(consumer, ModBlocks.PARK_BENCH_STRIPPED_WARPED.get(), Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        // Post Box
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.POST_BOX.get())
                .patternLine("III")
                .patternLine("ISI")
                .patternLine("III")
                .key('I', Tags.Items.INGOTS_IRON)
                .key('S', Blocks.BLUE_SHULKER_BOX)
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_shulker_box", hasItem(Blocks.BLUE_SHULKER_BOX))
                .build(consumer);
        // Park Bench
        mailBox(consumer, ModBlocks.MAIL_BOX_OAK.get(), ModBlocks.UPGRADED_FENCE_OAK.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_SPRUCE.get(), ModBlocks.UPGRADED_FENCE_SPRUCE.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_BIRCH.get(), ModBlocks.UPGRADED_FENCE_BIRCH.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_JUNGLE.get(), ModBlocks.UPGRADED_FENCE_JUNGLE.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_ACACIA.get(), ModBlocks.UPGRADED_FENCE_ACACIA.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_DARK_OAK.get(), ModBlocks.UPGRADED_FENCE_DARK_OAK.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_CRIMSON.get(), ModBlocks.UPGRADED_FENCE_CRIMSON.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_WARPED.get(), ModBlocks.UPGRADED_FENCE_WARPED.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_OAK.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_OAK.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_SPRUCE.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_SPRUCE.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_BIRCH.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_BIRCH.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_JUNGLE.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_JUNGLE.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_ACACIA.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_ACACIA.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_DARK_OAK.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_DARK_OAK.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_CRIMSON.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_CRIMSON.get());
        mailBox(consumer, ModBlocks.MAIL_BOX_STRIPPED_WARPED.get(), ModBlocks.UPGRADED_FENCE_STRIPPED_WARPED.get());
        // Hedge
        hedge(consumer, ModBlocks.HEDGE_OAK.get(), Blocks.OAK_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_SPRUCE.get(), Blocks.SPRUCE_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_BIRCH.get(), Blocks.BIRCH_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_JUNGLE.get(), Blocks.JUNGLE_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_ACACIA.get(), Blocks.ACACIA_LEAVES);
        hedge(consumer, ModBlocks.HEDGE_DARK_OAK.get(), Blocks.DARK_OAK_LEAVES);
        // Rock Path
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ROCK_PATH.get(), 16)
                .patternLine("SA")
                .patternLine("AS")
                .key('S', Blocks.STONE)
                .key('A', Blocks.ANDESITE)
                .addCriterion("has_stone", hasItem(Blocks.STONE))
                .addCriterion("has_andesite", hasItem(Blocks.ANDESITE))
                .build(consumer);
        // Trampoline
        trampoline(consumer, "white_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.WHITE, Blocks.WHITE_WOOL);
        trampoline(consumer, "orange_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        trampoline(consumer, "magenta_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        trampoline(consumer, "light_blue_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        trampoline(consumer, "yellow_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        trampoline(consumer, "lime_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.LIME, Blocks.LIME_WOOL);
        trampoline(consumer, "pink_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.PINK, Blocks.PINK_WOOL);
        trampoline(consumer, "gray_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.GRAY, Blocks.GRAY_WOOL);
        trampoline(consumer, "light_gray_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        trampoline(consumer, "cyan_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.CYAN, Blocks.CYAN_WOOL);
        trampoline(consumer, "purple_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        trampoline(consumer, "blue_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.BLUE, Blocks.BLUE_WOOL);
        trampoline(consumer, "brown_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.BROWN, Blocks.BROWN_WOOL);
        trampoline(consumer, "green_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.GREEN, Blocks.GREEN_WOOL);
        trampoline(consumer, "red_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.RED, Blocks.RED_WOOL);
        trampoline(consumer, "black_trampoline", new ItemStack(ModBlocks.TRAMPOLINE.get()), DyeColor.BLACK, Blocks.BLACK_WOOL);
        // Cooler
        cooler(consumer, ModBlocks.COOLER_WHITE.get(), Blocks.WHITE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_ORANGE.get(), Blocks.ORANGE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_MAGENTA.get(), Blocks.MAGENTA_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_LIGHT_BLUE.get(), Blocks.LIGHT_BLUE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_YELLOW.get(), Blocks.YELLOW_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_LIME.get(), Blocks.LIME_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_PINK.get(), Blocks.PINK_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_GRAY.get(), Blocks.GRAY_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_LIGHT_GRAY.get(), Blocks.LIGHT_GRAY_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_CYAN.get(), Blocks.CYAN_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_PURPLE.get(), Blocks.PURPLE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_BLUE.get(), Blocks.BLUE_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_BROWN.get(), Blocks.BROWN_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_GREEN.get(), Blocks.GREEN_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_RED.get(), Blocks.RED_TERRACOTTA);
        cooler(consumer, ModBlocks.COOLER_BLACK.get(), Blocks.BLACK_TERRACOTTA);
        // Grill
        grill(consumer, ModBlocks.GRILL_WHITE.get(), Blocks.WHITE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_ORANGE.get(), Blocks.ORANGE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_MAGENTA.get(), Blocks.MAGENTA_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_LIGHT_BLUE.get(), Blocks.LIGHT_BLUE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_YELLOW.get(), Blocks.YELLOW_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_LIME.get(), Blocks.LIME_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_PINK.get(), Blocks.PINK_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_GRAY.get(), Blocks.GRAY_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_LIGHT_GRAY.get(), Blocks.LIGHT_GRAY_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_CYAN.get(), Blocks.CYAN_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_PURPLE.get(), Blocks.PURPLE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_BLUE.get(), Blocks.BLUE_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_BROWN.get(), Blocks.BROWN_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_GREEN.get(), Blocks.GREEN_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_RED.get(), Blocks.RED_TERRACOTTA);
        grill(consumer, ModBlocks.GRILL_BLACK.get(), Blocks.BLACK_TERRACOTTA);
        // Door Mat
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.DOOR_MAT.get())
                .patternLine("WWW")
                .patternLine("WWW")
                .key('W', Items.WHEAT)
                .addCriterion("has_wheat", hasItem(Tags.Items.CROPS_WHEAT))
                .build(consumer);
        // Diving Board
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.DIVING_BOARD.get())
                .patternLine("CCC")
                .patternLine("SSS")
                .patternLine("G  ")
                .key('C', Blocks.WHITE_CONCRETE)
                .key('S', Tags.Items.SLIMEBALLS)
                .key('G', Blocks.LIGHT_GRAY_CONCRETE)
                .addCriterion("has_wheat", hasItem(Tags.Items.CROPS_WHEAT))
                .build(consumer);
        // Kitchen Counter
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK.get(), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        kitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_STRIPPED_WARPED.get(), Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_WHITE.get(), Tags.Items.DYES_WHITE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_ORANGE.get(), Tags.Items.DYES_ORANGE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_MAGENTA.get(), Tags.Items.DYES_MAGENTA);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIGHT_BLUE.get(), Tags.Items.DYES_LIGHT_BLUE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_YELLOW.get(), Tags.Items.DYES_YELLOW);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIME.get(), Tags.Items.DYES_LIME);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_PINK.get(), Tags.Items.DYES_PINK);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_GRAY.get(), Tags.Items.DYES_GRAY);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIGHT_GRAY.get(), Tags.Items.DYES_LIGHT_GRAY);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_CYAN.get(), Tags.Items.DYES_CYAN);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_PURPLE.get(), Tags.Items.DYES_PURPLE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BLUE.get(), Tags.Items.DYES_BLUE);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BROWN.get(), Tags.Items.DYES_BROWN);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_GREEN.get(), Tags.Items.DYES_GREEN);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_RED.get(), Tags.Items.DYES_RED);
        coloredKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BLACK.get(), Tags.Items.DYES_BLACK);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_WHITE_MODERN.get(), Tags.Items.DYES_WHITE);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_ORANGE_MODERN.get(), Tags.Items.DYES_ORANGE);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_MAGENTA_MODERN.get(), Tags.Items.DYES_MAGENTA);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIGHT_BLUE_MODERN.get(), Tags.Items.DYES_LIGHT_BLUE);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_YELLOW_MODERN.get(), Tags.Items.DYES_YELLOW);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIME_MODERN.get(), Tags.Items.DYES_LIME);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_PINK_MODERN.get(), Tags.Items.DYES_PINK);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_GRAY_MODERN.get(), Tags.Items.DYES_GRAY);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_LIGHT_GRAY_MODERN.get(), Tags.Items.DYES_LIGHT_GRAY);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_CYAN_MODERN.get(), Tags.Items.DYES_CYAN);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_PURPLE_MODERN.get(), Tags.Items.DYES_PURPLE);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BLUE_MODERN.get(), Tags.Items.DYES_BLUE);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BROWN_MODERN.get(), Tags.Items.DYES_BROWN);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_GREEN_MODERN.get(), Tags.Items.DYES_GREEN);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_RED_MODERN.get(), Tags.Items.DYES_RED);
        modernKitchenCounter(consumer, ModBlocks.KITCHEN_COUNTER_BLACK_MODERN.get(), Tags.Items.DYES_BLACK);
        // Kitchen Drawer
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_OAK.get(), Blocks.OAK_LOG, Blocks.OAK_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_WARPED.get(), Blocks.WARPED_STEM, Blocks.WARPED_PLANKS);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK.get(), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE.get(), Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH.get(), Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE.get(), Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA.get(), Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK.get(), Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_CRIMSON.get(), Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        kitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_STRIPPED_WARPED.get(), Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_WHITE.get(), Tags.Items.DYES_WHITE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_ORANGE.get(), Tags.Items.DYES_ORANGE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_MAGENTA.get(), Tags.Items.DYES_MAGENTA);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get(), Tags.Items.DYES_LIGHT_BLUE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_YELLOW.get(), Tags.Items.DYES_YELLOW);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_LIME.get(), Tags.Items.DYES_LIME);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_PINK.get(), Tags.Items.DYES_PINK);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_GRAY.get(), Tags.Items.DYES_GRAY);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get(), Tags.Items.DYES_LIGHT_GRAY);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_CYAN.get(), Tags.Items.DYES_CYAN);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_PURPLE.get(), Tags.Items.DYES_PURPLE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BLUE.get(), Tags.Items.DYES_BLUE);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BROWN.get(), Tags.Items.DYES_BROWN);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_GREEN.get(), Tags.Items.DYES_GREEN);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_RED.get(), Tags.Items.DYES_RED);
        coloredKitchenDrawer(consumer, ModBlocks.KITCHEN_DRAWER_BLACK.get(), Tags.Items.DYES_BLACK);
        // Kitchen Sink
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_OAK.get(), Blocks.WHITE_CONCRETE, Blocks.OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_SPRUCE.get(), Blocks.WHITE_CONCRETE, Blocks.SPRUCE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_BIRCH.get(), Blocks.WHITE_CONCRETE, Blocks.BIRCH_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_JUNGLE.get(), Blocks.WHITE_CONCRETE, Blocks.JUNGLE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_ACACIA.get(), Blocks.WHITE_CONCRETE, Blocks.ACACIA_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_DARK_OAK.get(), Blocks.WHITE_CONCRETE, Blocks.DARK_OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_CRIMSON.get(), Blocks.WHITE_CONCRETE, Blocks.CRIMSON_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_WARPED.get(), Blocks.WHITE_CONCRETE, Blocks.WARPED_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_OAK_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_BIRCH_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_ACACIA_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_DARK_OAK_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_CRIMSON.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_CRIMSON_STEM);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_WARPED.get(), Blocks.WHITE_CONCRETE, Blocks.STRIPPED_WARPED_STEM);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_OAK.get(), Blocks.GRAY_CONCRETE, Blocks.OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_SPRUCE.get(), Blocks.GRAY_CONCRETE, Blocks.SPRUCE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_BIRCH.get(), Blocks.GRAY_CONCRETE, Blocks.BIRCH_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_JUNGLE.get(), Blocks.GRAY_CONCRETE, Blocks.JUNGLE_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_ACACIA.get(), Blocks.GRAY_CONCRETE, Blocks.ACACIA_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_DARK_OAK.get(), Blocks.GRAY_CONCRETE, Blocks.DARK_OAK_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_CRIMSON.get(), Blocks.GRAY_CONCRETE, Blocks.CRIMSON_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_WARPED.get(), Blocks.GRAY_CONCRETE, Blocks.WARPED_PLANKS);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_OAK_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_SPRUCE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_BIRCH_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_JUNGLE_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_ACACIA_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_DARK_OAK_LOG);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_CRIMSON.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_CRIMSON_STEM);
        kitchenSink(consumer, ModBlocks.KITCHEN_SINK_DARK_STRIPPED_WARPED.get(), Blocks.GRAY_CONCRETE, Blocks.STRIPPED_WARPED_STEM);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_WHITE.get(), Blocks.WHITE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_ORANGE.get(), Blocks.ORANGE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_MAGENTA.get(), Blocks.MAGENTA_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get(), Blocks.LIGHT_BLUE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_YELLOW.get(), Blocks.YELLOW_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIME.get(), Blocks.LIME_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_PINK.get(), Blocks.PINK_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_GRAY.get(), Blocks.GRAY_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get(), Blocks.LIGHT_GRAY_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_CYAN.get(), Blocks.CYAN_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_PURPLE.get(), Blocks.PURPLE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_BLUE.get(), Blocks.BLUE_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_BROWN.get(), Blocks.BROWN_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_GREEN.get(), Blocks.GREEN_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_RED.get(), Blocks.RED_TERRACOTTA);
        coloredKitchenSink(consumer, ModBlocks.KITCHEN_SINK_BLACK.get(), Blocks.BLACK_TERRACOTTA);
        // Fridges
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.FREEZER_LIGHT.get())
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
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.FREEZER_DARK.get())
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
        //Microwave
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.MICROWAVE_LIGHT.get())
                .patternLine("IIC")
                .patternLine("GGB")
                .patternLine("IIC")
                .key('C', Blocks.WHITE_CONCRETE)
                .key('B', Blocks.STONE_BUTTON)
                .key('G', Blocks.WHITE_STAINED_GLASS_PANE)
                .key('I', Items.IRON_INGOT)
                .setGroup("microwave")
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.MICROWAVE_DARK.get())
                .patternLine("IIC")
                .patternLine("GGB")
                .patternLine("IIC")
                .key('C', Blocks.GRAY_CONCRETE)
                .key('B', Blocks.STONE_BUTTON)
                .key('G', Blocks.GRAY_STAINED_GLASS_PANE)
                .key('I', Items.IRON_INGOT)
                .setGroup("microwave")
                .build(consumer);
        //Toilet
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.TOILET_LIGHT.get())
                .patternLine("WG ")
                .patternLine("WWW")
                .patternLine(" W ")
                .key('W', Blocks.WHITE_CONCRETE)
                .key('G', Blocks.GRAY_CONCRETE)
                .setGroup("toilet")
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.TOILET_DARK.get())
                .patternLine("GW ")
                .patternLine("GGG")
                .patternLine(" G ")
                .key('W', Blocks.WHITE_CONCRETE)
                .key('G', Blocks.GRAY_CONCRETE)
                .setGroup("toilet")
                .build(consumer);
        // Spatula
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPATULA.get())
                .patternLine("B")
                .patternLine("I")
                .patternLine("W")
                .key('B', Items.IRON_BARS)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('W', Blocks.BLACK_WOOL)
                .addCriterion("has_iron", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer);
        // Cooking Recipes
        cookingRecipesForMethod(consumer, "grill_cooking", ModRecipeSerializers.GRILL_COOKING.get(), 600);
        // Freezing Recipes
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.WATER_BUCKET), Blocks.ICE, 1, 1000, ModRecipeSerializers.FREEZER_SOLIDIFY.get()).addCriterion("has_water", hasItem(Items.WATER_BUCKET)).build(consumer, "ice_from_freezing");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.ICE), Blocks.PACKED_ICE, 1, 2000, ModRecipeSerializers.FREEZER_SOLIDIFY.get()).addCriterion("has_ice", hasItem(Items.ICE)).build(consumer, "packed_ice_from_freezing");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.PACKED_ICE), Blocks.BLUE_ICE, 1, 4000, ModRecipeSerializers.FREEZER_SOLIDIFY.get()).addCriterion("has_packed_ice", hasItem(Items.PACKED_ICE)).build(consumer, "blue_ice_from_freezing");
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
        if (fence == ModBlocks.PICKET_FENCE_WHITE.get())
            return;
        ResourceLocation registryName = fence.asItem().getRegistryName();
        ShapedRecipeBuilder.shapedRecipe(fence, 8)
                .patternLine("FFF")
                .patternLine("FDF")
                .patternLine("FFF")
                .key('F', ModBlocks.PICKET_FENCE_WHITE.get())
                .key('D', dye)
                .setGroup("picket_fence")
                .addCriterion("has_fence", hasItem(ModBlocks.PICKET_FENCE_WHITE.get()))
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
        if (gate == ModBlocks.PICKET_GATE_WHITE.get())
            return;
        ResourceLocation registryName = gate.asItem().getRegistryName();
        ShapedRecipeBuilder.shapedRecipe(gate, 8)
                .patternLine("GGG")
                .patternLine("GDG")
                .patternLine("GGG")
                .key('G', ModBlocks.PICKET_GATE_WHITE.get())
                .key('D', dye)
                .setGroup("picket_fence")
                .addCriterion("has_gate", hasItem(ModBlocks.PICKET_GATE_WHITE.get()))
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
                .addCriterion("has_postbox", hasItem(ModBlocks.POST_BOX.get())) // Mailbox depends on post box so if you have a postbox unlock all mailboxes
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

    private static void trampoline(Consumer<IFinishedRecipe> recipeConsumer, String key, ItemStack trampoline, DyeColor color, IItemProvider wool)
    {
        CompoundNBT tag = new CompoundNBT();
        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.putInt("Color", color.getId());
        tag.put("BlockEntityTag", blockEntityTag);
        trampoline.setTag(tag);
        ForgeShapedRecipeBuilder.shapedRecipe(key, trampoline)
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
                .key('S', Blocks.TERRACOTTA)
                .key('D', dye)
                .key('C', Blocks.WHITE_CONCRETE)
                .setGroup("kitchen_counter")
                .addCriterion("has_stone", hasItem(Blocks.TERRACOTTA))
                .addCriterion("has_concrete", hasItem(Blocks.WHITE_CONCRETE))
                .build(recipeConsumer);
    }

    private static void modernKitchenCounter(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider counter, ITag<Item> dye)
    {
        ShapedRecipeBuilder.shapedRecipe(counter, 8)
                .patternLine("CDC")
                .patternLine("SSS")
                .patternLine("SSS")
                .key('S', Blocks.STONE)
                .key('D', dye)
                .key('C', Blocks.WHITE_CONCRETE)
                .setGroup("kitchen_counter")
                .addCriterion("has_stone", hasItem(Blocks.STONE))
                .addCriterion("has_concrete", hasItem(Blocks.WHITE_CONCRETE))
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
                .key('S', Blocks.TERRACOTTA)
                .key('D', dye)
                .key('C', Blocks.WHITE_CONCRETE)
                .key('B', Tags.Items.CHESTS_WOODEN)
                .setGroup("kitchen_drawer")
                .addCriterion("has_terracotta", hasItem(Blocks.TERRACOTTA))
                .addCriterion("has_concrete", hasItem(Blocks.WHITE_CONCRETE))
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
                .key('P', Blocks.WHITE_CONCRETE)
                .key('B', Items.BUCKET)
                .setGroup("kitchen_sink")
                .addCriterion("has_top", hasItem(top))
                .addCriterion("has_concrete", hasItem(Blocks.WHITE_CONCRETE))
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