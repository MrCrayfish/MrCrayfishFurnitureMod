package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.data.ForgeShapedRecipeBuilder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
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
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
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
        // Desk Cabinet
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
        ShapedRecipeBuilder.shaped(ModBlocks.POST_BOX.get())
                .pattern("III")
                .pattern("ISI")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Blocks.BLUE_SHULKER_BOX)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_shulker_box", has(Blocks.BLUE_SHULKER_BOX))
                .save(consumer);
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
        ShapedRecipeBuilder.shaped(ModBlocks.ROCK_PATH.get(), 16)
                .pattern("SA")
                .pattern("AS")
                .define('S', Blocks.STONE)
                .define('A', Blocks.ANDESITE)
                .unlockedBy("has_stone", has(Blocks.STONE))
                .unlockedBy("has_andesite", has(Blocks.ANDESITE))
                .save(consumer);
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
        ShapedRecipeBuilder.shaped(ModBlocks.DOOR_MAT.get())
                .pattern("WWW")
                .pattern("WWW")
                .define('W', Items.WHEAT)
                .unlockedBy("has_wheat", has(Tags.Items.CROPS_WHEAT))
                .save(consumer);
        // Diving Board
        ShapedRecipeBuilder.shaped(ModBlocks.DIVING_BOARD.get())
                .pattern("CCC")
                .pattern("SSS")
                .pattern("G  ")
                .define('C', Blocks.WHITE_CONCRETE)
                .define('S', Tags.Items.SLIMEBALLS)
                .define('G', Blocks.LIGHT_GRAY_CONCRETE)
                .unlockedBy("has_wheat", has(Tags.Items.CROPS_WHEAT))
                .save(consumer);
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
        ShapedRecipeBuilder.shaped(ModBlocks.FRIDGE_LIGHT.get())
                .pattern("CIC")
                .pattern("IBI")
                .pattern("CIC")
                .define('C', Blocks.WHITE_CONCRETE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.CHESTS_WOODEN)
                .group("fridge")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.FRIDGE_DARK.get())
                .pattern("CIC")
                .pattern("IBI")
                .pattern("CIC")
                .define('C', Blocks.GRAY_CONCRETE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.CHESTS_WOODEN)
                .group("fridge")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
                .save(consumer);
        // Spatula
        ShapedRecipeBuilder.shaped(ModItems.SPATULA.get())
                .pattern("B")
                .pattern("I")
                .pattern("W")
                .define('B', Items.IRON_BARS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', Blocks.BLACK_WOOL)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(consumer);
        // Cooking Recipes
        cookingRecipesForMethod(consumer, "grill_cooking", ModRecipeSerializers.GRILL_COOKING.get(), 600);
        // Freezing Recipes

        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.WATER_BUCKET), Blocks.ICE, 1, 1000, ModRecipeSerializers.FREEZER_SOLIDIFY.get()).unlockedBy("has_water", has(Items.WATER_BUCKET)).save(consumer, "ice_from_freezing");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.ICE), Blocks.PACKED_ICE, 1, 2000, ModRecipeSerializers.FREEZER_SOLIDIFY.get()).unlockedBy("has_ice", has(Items.ICE)).save(consumer, "packed_ice_from_freezing");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.PACKED_ICE), Blocks.BLUE_ICE, 1, 4000, ModRecipeSerializers.FREEZER_SOLIDIFY.get()).unlockedBy("has_packed_ice", has(Items.PACKED_ICE)).save(consumer, "blue_ice_from_freezing");
    }

    private static void table(Consumer<FinishedRecipe> recipeConsumer, ItemLike table, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(table, 4)
                .pattern("LLL")
                .pattern(" P ")
                .pattern(" P ")
                .define('L', log)
                .define('P', planks)
                .group("table")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void chair(Consumer<FinishedRecipe> recipeConsumer, ItemLike chair, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(chair, 4)
                .pattern("L  ")
                .pattern("LLL")
                .pattern("P P")
                .define('L', log)
                .define('P', planks)
                .group("chair")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void coffeeTable(Consumer<FinishedRecipe> recipeConsumer, ItemLike table, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(table, 4)
                .pattern("LLL")
                .pattern("P P")
                .define('L', log)
                .define('P', planks)
                .group("coffee_table")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void cabinet(Consumer<FinishedRecipe> recipeConsumer, ItemLike cabinet, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(cabinet, 2)
                .pattern("PPL")
                .pattern("P L")
                .pattern("PPL")
                .define('L', log)
                .define('P', planks)
                .group("cabinet")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void bedsideCabinet(Consumer<FinishedRecipe> recipeConsumer, ItemLike cabinet, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(cabinet, 2)
                .pattern("LLL")
                .pattern("P P")
                .pattern("PPP")
                .define('L', log)
                .define('P', planks)
                .group("bedside_cabinet")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void desk(Consumer<FinishedRecipe> recipeConsumer, ItemLike desk, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(desk, 2)
                .pattern("LLL")
                .pattern("P P")
                .pattern("P P")
                .define('L', log)
                .define('P', planks)
                .group("desk")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void deskCabinet(Consumer<FinishedRecipe> recipeConsumer, ItemLike desk, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(desk, 2)
                .pattern("LLL")
                .pattern("PPP")
                .pattern("P P")
                .define('L', log)
                .define('P', planks)
                .group("desk_cabinet")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void sofa(Consumer<FinishedRecipe> recipeConsumer, ItemLike sofa, ItemLike wool)
    {
        ShapedRecipeBuilder.shaped(sofa, 2)
                .pattern("W  ")
                .pattern("WWW")
                .pattern("LLL")
                .define('W', wool)
                .define('L', ItemTags.LOGS)
                .group("sofa")
                .unlockedBy("has_wool", has(wool))
                .save(recipeConsumer);
    }

    private static void blinds(Consumer<FinishedRecipe> recipeConsumer, ItemLike blinds, ItemLike log)
    {
        ShapedRecipeBuilder.shaped(blinds, 2)
                .pattern("LLL")
                .pattern("SSS")
                .pattern("SSS")
                .define('L', log)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("blinds")
                .unlockedBy("has_log", has(log))
                .save(recipeConsumer);
    }

    private static void upgradedFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, ItemLike log)
    {
        ShapedRecipeBuilder.shaped(fence, 12)
                .pattern("LSL")
                .pattern("LSL")
                .define('L', log)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("upgraded_fence")
                .unlockedBy("has_log", has(log))
                .save(recipeConsumer);
    }

    private static void upgradedGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike gate, ItemLike log)
    {
        ShapedRecipeBuilder.shaped(gate, 2)
                .pattern("LGL")
                .define('L', log)
                .define('G', Tags.Items.FENCE_GATES_WOODEN)
                .group("upgraded_gate")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_gate", has(Tags.Items.FENCE_GATES_WOODEN))
                .save(recipeConsumer);
    }

    private static void picketFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, ItemLike concrete, TagKey<Item> dye)
    {
        ShapedRecipeBuilder.shaped(fence, 12)
                .pattern("CSC")
                .pattern("CSC")
                .define('C', concrete)
                .define('S', Tags.Items.RODS_WOODEN)
                .group("picket_fence")
                .unlockedBy("has_concrete", has(concrete))
                .save(recipeConsumer);
        if (fence == ModBlocks.PICKET_FENCE_WHITE.get())
            return;
        ResourceLocation registryName = fence.asItem().getRegistryName();
        ShapedRecipeBuilder.shaped(fence, 8)
                .pattern("FFF")
                .pattern("FDF")
                .pattern("FFF")
                .define('F', ModBlocks.PICKET_FENCE_WHITE.get())
                .define('D', dye)
                .group("picket_fence")
                .unlockedBy("has_fence", has(ModBlocks.PICKET_FENCE_WHITE.get()))
                .unlockedBy("has_dye", has(dye))
                .save(recipeConsumer, new ResourceLocation(registryName.getNamespace(), "dye_" + registryName.getPath()));
    }

    private static void picketGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike gate, ItemLike concrete, TagKey<Item> dye)
    {
        ShapedRecipeBuilder.shaped(gate, 2)
                .pattern("CGC")
                .define('C', concrete)
                .define('G', Tags.Items.FENCE_GATES_WOODEN)
                .group("picket_fence")
                .unlockedBy("has_concrete", has(concrete))
                .unlockedBy("has_gate", has(Tags.Items.FENCE_GATES_WOODEN))
                .save(recipeConsumer);
        if (gate == ModBlocks.PICKET_GATE_WHITE.get())
            return;
        ResourceLocation registryName = gate.asItem().getRegistryName();
        ShapedRecipeBuilder.shaped(gate, 8)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .define('G', ModBlocks.PICKET_GATE_WHITE.get())
                .define('D', dye)
                .group("picket_fence")
                .unlockedBy("has_gate", has(ModBlocks.PICKET_GATE_WHITE.get()))
                .unlockedBy("has_dye", has(dye))
                .save(recipeConsumer, new ResourceLocation(registryName.getNamespace(), "dye_" + registryName.getPath()));
    }

    private static void crate(Consumer<FinishedRecipe> recipeConsumer, ItemLike crate, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(crate, 2)
                .pattern("LPL")
                .pattern("P P")
                .pattern("LPL")
                .define('L', log)
                .define('P', planks)
                .group("crate")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void parkBench(Consumer<FinishedRecipe> recipeConsumer, ItemLike bench, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(bench, 4)
                .pattern("PPP")
                .pattern("PPP")
                .pattern("L L")
                .define('L', log)
                .define('P', planks)
                .group("park_bench")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void mailBox(Consumer<FinishedRecipe> recipeConsumer, ItemLike mailbox, ItemLike fence)
    {
        ShapedRecipeBuilder.shaped(mailbox)
                .pattern("C")
                .pattern("F")
                .pattern("F")
                .define('C', Tags.Items.CHESTS_WOODEN)
                .define('F', fence)
                .group("mail_box")
                .unlockedBy("has_postbox", has(ModBlocks.POST_BOX.get())) // Mailbox depends on post box so if you have a postbox unlock all mailboxes
                .unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
                .save(recipeConsumer);
    }

    private static void hedge(Consumer<FinishedRecipe> recipeConsumer, ItemLike hedge, ItemLike leaves)
    {
        ShapedRecipeBuilder.shaped(hedge, 12)
                .pattern("LLL")
                .pattern("LLL")
                .define('L', leaves)
                .group("hedge")
                .unlockedBy("has_leaves", has(leaves))
                .save(recipeConsumer);
    }

    private static void trampoline(Consumer<FinishedRecipe> recipeConsumer, String key, ItemStack trampoline, DyeColor color, ItemLike wool)
    {
        CompoundTag tag = new CompoundTag();
        CompoundTag blockEntityTag = new CompoundTag();
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
                .addCriterion("has_wool", has(wool))
                .addCriterion("has_iron", has(Tags.Items.INGOTS_IRON))
                .build(recipeConsumer);
    }

    private static void cooler(Consumer<FinishedRecipe> recipeConsumer, ItemLike cooler, ItemLike terracotta)
    {
        ShapedRecipeBuilder.shaped(cooler, 2)
                .pattern("TTT")
                .pattern("WCW")
                .pattern("TTT")
                .define('T', terracotta)
                .define('W', Blocks.WHITE_CONCRETE)
                .define('C', Tags.Items.CHESTS_WOODEN)
                .group("trampoline")
                .unlockedBy("has_terracotta", has(terracotta))
                .unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
                .save(recipeConsumer);
    }

    private static void grill(Consumer<FinishedRecipe> recipeConsumer, ItemLike grill, ItemLike terracotta)
    {
        ShapedRecipeBuilder.shaped(grill)
                .pattern("TBT")
                .pattern("I I")
                .pattern("I I")
                .define('T', terracotta)
                .define('B', Items.IRON_BARS)
                .define('I', Tags.Items.INGOTS_IRON)
                .group("grill")
                .unlockedBy("has_terracotta", has(terracotta))
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(recipeConsumer);
    }

    private static void kitchenCounter(Consumer<FinishedRecipe> recipeConsumer, ItemLike counter, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(counter, 8)
                .pattern("LLL")
                .pattern("PPP")
                .pattern("PPP")
                .define('L', log)
                .define('P', planks)
                .group("kitchen_counter")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .save(recipeConsumer);
    }

    private static void coloredKitchenCounter(Consumer<FinishedRecipe> recipeConsumer, ItemLike counter, TagKey<Item> dye)
    {
        ShapedRecipeBuilder.shaped(counter, 8)
                .pattern("SDS")
                .pattern("CCC")
                .pattern("CCC")
                .define('S', Blocks.TERRACOTTA)
                .define('D', dye)
                .define('C', Blocks.WHITE_CONCRETE)
                .group("kitchen_counter")
                .unlockedBy("has_stone", has(Blocks.TERRACOTTA))
                .unlockedBy("has_concrete", has(Blocks.WHITE_CONCRETE))
                .save(recipeConsumer);
    }

    private static void kitchenDrawer(Consumer<FinishedRecipe> recipeConsumer, ItemLike counter, ItemLike log, ItemLike planks)
    {
        ShapedRecipeBuilder.shaped(counter, 4)
                .pattern("LLL")
                .pattern("PCP")
                .pattern("PPP")
                .define('L', log)
                .define('P', planks)
                .define('C', Tags.Items.CHESTS_WOODEN)
                .group("kitchen_drawer")
                .unlockedBy("has_log", has(log))
                .unlockedBy("has_planks", has(planks))
                .unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
                .save(recipeConsumer);
    }

    private static void coloredKitchenDrawer(Consumer<FinishedRecipe> recipeConsumer, ItemLike counter, TagKey<Item> dye)
    {
        ShapedRecipeBuilder.shaped(counter, 4)
                .pattern("SDS")
                .pattern("CBC")
                .pattern("CCC")
                .define('S', Blocks.TERRACOTTA)
                .define('D', dye)
                .define('C', Blocks.WHITE_CONCRETE)
                .define('B', Tags.Items.CHESTS_WOODEN)
                .group("kitchen_drawer")
                .unlockedBy("has_terracotta", has(Blocks.TERRACOTTA))
                .unlockedBy("has_concrete", has(Blocks.WHITE_CONCRETE))
                .unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
                .save(recipeConsumer);
    }

    private static void kitchenSink(Consumer<FinishedRecipe> recipeConsumer, ItemLike sink, ItemLike top, ItemLike bottom)
    {
        ShapedRecipeBuilder.shaped(sink, 2)
                .pattern("CIC")
                .pattern("PBP")
                .pattern("PPP")
                .define('C', top)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', bottom)
                .define('B', Items.BUCKET)
                .group("kitchen_sink")
                .unlockedBy("has_top", has(top))
                .unlockedBy("has_bottom", has(bottom))
                .unlockedBy("has_bucket", has(Items.BUCKET))
                .save(recipeConsumer);
    }

    private static void coloredKitchenSink(Consumer<FinishedRecipe> recipeConsumer, ItemLike sink, ItemLike top)
    {
        ShapedRecipeBuilder.shaped(sink, 2)
                .pattern("CIC")
                .pattern("PBP")
                .pattern("PPP")
                .define('C', top)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Blocks.WHITE_CONCRETE)
                .define('B', Items.BUCKET)
                .group("kitchen_sink")
                .unlockedBy("has_top", has(top))
                .unlockedBy("has_concrete", has(Blocks.WHITE_CONCRETE))
                .unlockedBy("has_bucket", has(Items.BUCKET))
                .save(recipeConsumer);
    }

    private static void cookingRecipesForMethod(Consumer<FinishedRecipe> recipeConsumer, String recipeConsumerIn, SimpleCookingSerializer<?> cookingMethod, int cookingTime)
    {
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.BEEF), Items.COOKED_BEEF, 0.35F, cookingTime, cookingMethod).unlockedBy("has_beef", has(Items.BEEF)).save(recipeConsumer, "cooked_beef_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, cookingTime, cookingMethod).unlockedBy("has_chicken", has(Items.CHICKEN)).save(recipeConsumer, "cooked_chicken_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.COD), Items.COOKED_COD, 0.35F, cookingTime, cookingMethod).unlockedBy("has_cod", has(Items.COD)).save(recipeConsumer, "cooked_cod_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Blocks.KELP), Items.DRIED_KELP, 0.1F, cookingTime, cookingMethod).unlockedBy("has_kelp", has(Blocks.KELP)).save(recipeConsumer, "dried_kelp_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.SALMON), Items.COOKED_SALMON, 0.35F, cookingTime, cookingMethod).unlockedBy("has_salmon", has(Items.SALMON)).save(recipeConsumer, "cooked_salmon_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, cookingTime, cookingMethod).unlockedBy("has_mutton", has(Items.MUTTON)).save(recipeConsumer, "cooked_mutton_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, cookingTime, cookingMethod).unlockedBy("has_porkchop", has(Items.PORKCHOP)).save(recipeConsumer, "cooked_porkchop_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.POTATO), Items.BAKED_POTATO, 0.35F, cookingTime, cookingMethod).unlockedBy("has_potato", has(Items.POTATO)).save(recipeConsumer, "baked_potato_from_" + recipeConsumerIn);
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, cookingTime, cookingMethod).unlockedBy("has_rabbit", has(Items.RABBIT)).save(recipeConsumer, "cooked_rabbit_from_" + recipeConsumerIn);
    }

    private static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tag)
    {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
    }
}