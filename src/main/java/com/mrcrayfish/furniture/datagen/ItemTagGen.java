package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.common.ModTags;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author Ocelot
 */
public class ItemTagGen extends ItemTagsProvider
{
    public ItemTagGen(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper)
    {
        super(generator, blockTagsProvider, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(ModTags.Items.GENERAL)
                // Sofa
                .add(ModBlocks.SOFA_WHITE.get().asItem())
                .add(ModBlocks.SOFA_ORANGE.get().asItem())
                .add(ModBlocks.SOFA_MAGENTA.get().asItem())
                .add(ModBlocks.SOFA_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.SOFA_YELLOW.get().asItem())
                .add(ModBlocks.SOFA_LIME.get().asItem())
                .add(ModBlocks.SOFA_PINK.get().asItem())
                .add(ModBlocks.SOFA_GRAY.get().asItem())
                .add(ModBlocks.SOFA_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.SOFA_CYAN.get().asItem())
                .add(ModBlocks.SOFA_PURPLE.get().asItem())
                .add(ModBlocks.SOFA_BLUE.get().asItem())
                .add(ModBlocks.SOFA_BROWN.get().asItem())
                .add(ModBlocks.SOFA_GREEN.get().asItem())
                .add(ModBlocks.SOFA_RED.get().asItem())
                .add(ModBlocks.SOFA_BLACK.get().asItem());

        this.tag(ModTags.Items.KITCHEN)
                // Kitchen Counters
                .add(ModBlocks.KITCHEN_COUNTER_WHITE.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_ORANGE.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_MAGENTA.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_YELLOW.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_LIME.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_PINK.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_CYAN.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_PURPLE.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_BROWN.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_GREEN.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_RED.get().asItem())
                .add(ModBlocks.KITCHEN_COUNTER_BLACK.get().asItem())
                // Kitchen Drawers
                .add(ModBlocks.KITCHEN_DRAWER_WHITE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_ORANGE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_MAGENTA.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_YELLOW.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_LIME.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_PINK.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_CYAN.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_PURPLE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_BROWN.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_GREEN.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_RED.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_BLACK.get().asItem())
                // Spatula
                .add(ModItems.SPATULA.get())
                // Kitchen Sinks
                .add(ModBlocks.KITCHEN_SINK_WHITE.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_ORANGE.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_MAGENTA.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_YELLOW.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_LIME.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_PINK.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_CYAN.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_PURPLE.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_BROWN.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_GREEN.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_RED.get().asItem())
                .add(ModBlocks.KITCHEN_SINK_BLACK.get().asItem())
                //Fridge
                .add(ModBlocks.FRIDGE_LIGHT.get().asItem())
                .add(ModBlocks.FRIDGE_DARK.get().asItem());

        this.tag(ModTags.Items.OUTDOORS)
                // Fences and Fence Gates
                .addTag(ModTags.Items.UPGRADED_FENCE_GATES)
                .addTag(ModTags.Items.UPGRADED_FENCES)
                // Post Box
                .add(ModBlocks.POST_BOX.get().asItem())
                // Hedge
                .addTag(ModTags.Items.HEDGES)
                // Rock Path
                .add(ModBlocks.ROCK_PATH.get().asItem())
                // Trampolines
                .add(ModBlocks.TRAMPOLINE.get().asItem())
                // Coolers
                .add(ModBlocks.COOLER_WHITE.get().asItem())
                .add(ModBlocks.COOLER_ORANGE.get().asItem())
                .add(ModBlocks.COOLER_MAGENTA.get().asItem())
                .add(ModBlocks.COOLER_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.COOLER_YELLOW.get().asItem())
                .add(ModBlocks.COOLER_LIME.get().asItem())
                .add(ModBlocks.COOLER_PINK.get().asItem())
                .add(ModBlocks.COOLER_GRAY.get().asItem())
                .add(ModBlocks.COOLER_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.COOLER_CYAN.get().asItem())
                .add(ModBlocks.COOLER_PURPLE.get().asItem())
                .add(ModBlocks.COOLER_BLUE.get().asItem())
                .add(ModBlocks.COOLER_BROWN.get().asItem())
                .add(ModBlocks.COOLER_GREEN.get().asItem())
                .add(ModBlocks.COOLER_RED.get().asItem())
                .add(ModBlocks.COOLER_BLACK.get().asItem())
                // Grill
                .add(ModBlocks.GRILL_WHITE.get().asItem())
                .add(ModBlocks.GRILL_ORANGE.get().asItem())
                .add(ModBlocks.GRILL_MAGENTA.get().asItem())
                .add(ModBlocks.GRILL_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.GRILL_YELLOW.get().asItem())
                .add(ModBlocks.GRILL_LIME.get().asItem())
                .add(ModBlocks.GRILL_PINK.get().asItem())
                .add(ModBlocks.GRILL_GRAY.get().asItem())
                .add(ModBlocks.GRILL_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.GRILL_CYAN.get().asItem())
                .add(ModBlocks.GRILL_PURPLE.get().asItem())
                .add(ModBlocks.GRILL_BLUE.get().asItem())
                .add(ModBlocks.GRILL_BROWN.get().asItem())
                .add(ModBlocks.GRILL_GREEN.get().asItem())
                .add(ModBlocks.GRILL_RED.get().asItem())
                .add(ModBlocks.GRILL_BLACK.get().asItem())
                // Spatula
                .add(ModItems.SPATULA.get());

        this.tag(ModTags.Items.STORAGE)
                // Coolers
                .add(ModBlocks.COOLER_WHITE.get().asItem())
                .add(ModBlocks.COOLER_ORANGE.get().asItem())
                .add(ModBlocks.COOLER_MAGENTA.get().asItem())
                .add(ModBlocks.COOLER_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.COOLER_YELLOW.get().asItem())
                .add(ModBlocks.COOLER_LIME.get().asItem())
                .add(ModBlocks.COOLER_PINK.get().asItem())
                .add(ModBlocks.COOLER_GRAY.get().asItem())
                .add(ModBlocks.COOLER_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.COOLER_CYAN.get().asItem())
                .add(ModBlocks.COOLER_PURPLE.get().asItem())
                .add(ModBlocks.COOLER_BLUE.get().asItem())
                .add(ModBlocks.COOLER_BROWN.get().asItem())
                .add(ModBlocks.COOLER_GREEN.get().asItem())
                .add(ModBlocks.COOLER_RED.get().asItem())
                .add(ModBlocks.COOLER_BLACK.get().asItem())
                // Post Box
                .add(ModBlocks.POST_BOX.get().asItem())
                // Coolers
                .add(ModBlocks.COOLER_WHITE.get().asItem())
                .add(ModBlocks.COOLER_ORANGE.get().asItem())
                .add(ModBlocks.COOLER_MAGENTA.get().asItem())
                .add(ModBlocks.COOLER_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.COOLER_YELLOW.get().asItem())
                .add(ModBlocks.COOLER_LIME.get().asItem())
                .add(ModBlocks.COOLER_PINK.get().asItem())
                .add(ModBlocks.COOLER_GRAY.get().asItem())
                .add(ModBlocks.COOLER_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.COOLER_CYAN.get().asItem())
                .add(ModBlocks.COOLER_PURPLE.get().asItem())
                .add(ModBlocks.COOLER_BLUE.get().asItem())
                .add(ModBlocks.COOLER_BROWN.get().asItem())
                .add(ModBlocks.COOLER_GREEN.get().asItem())
                .add(ModBlocks.COOLER_RED.get().asItem())
                .add(ModBlocks.COOLER_BLACK.get().asItem())
                // Kitchen Drawers
                .add(ModBlocks.KITCHEN_DRAWER_WHITE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_ORANGE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_MAGENTA.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_LIGHT_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_YELLOW.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_LIME.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_PINK.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_LIGHT_GRAY.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_CYAN.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_PURPLE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_BLUE.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_BROWN.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_GREEN.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_RED.get().asItem())
                .add(ModBlocks.KITCHEN_DRAWER_BLACK.get().asItem());

        this.tag(ModTags.Items.TRAMPOLINE).add(ModBlocks.TRAMPOLINE.get().asItem());
        this.tag(ModTags.Items.ITEMS).add(ModItems.SPATULA.get());

        this.copy(ModTags.Blocks.UPGRADED_FENCE_GATES, ModTags.Items.UPGRADED_FENCE_GATES);
        this.copy(ModTags.Blocks.PICKET_FENCE_GATES, ModTags.Items.PICKET_FENCE_GATES);
        this.copy(ModTags.Blocks.UPGRADED_FENCES, ModTags.Items.UPGRADED_FENCES);
        this.copy(ModTags.Blocks.PICKET_FENCES, ModTags.Items.PICKET_FENCES);
        this.copy(ModTags.Blocks.HEDGES, ModTags.Items.HEDGES);

        TagBuilder bedroomTag = this.getOrCreateRawBuilder(ModTags.Items.BEDROOM).replace(false);
        for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
        {
            bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.BEDSIDE_CABINET, variant, false));
            if(variant.strippedLog() != null)
            {
                bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.BEDSIDE_CABINET, variant, true));
            }

            bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.DESK, variant, false));
            if(variant.strippedLog() != null)
            {
                bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.DESK, variant, true));
            }

            bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.DESK_CABINET, variant, false));
            if(variant.strippedLog() != null)
            {
                bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.DESK_CABINET, variant, true));
            }

            bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.BLINDS, variant, false));
            if(variant.strippedLog() != null)
            {
                bedroomTag.addElement(GeneratorData.getResultBlock(GeneratorData.BLINDS, variant, true));
            }
        }

        TagBuilder generalTag = this.getOrCreateRawBuilder(ModTags.Items.GENERAL).replace(false);
        for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
        {
            generalTag.addElement(GeneratorData.getResultBlock(GeneratorData.TABLE, variant, false));
            if(variant.strippedLog() != null)
            {
                generalTag.addElement(GeneratorData.getResultBlock(GeneratorData.TABLE, variant, true));
            }

            generalTag.addElement(GeneratorData.getResultBlock(GeneratorData.CHAIR, variant, false));
            if(variant.strippedLog() != null)
            {
                generalTag.addElement(GeneratorData.getResultBlock(GeneratorData.CHAIR, variant, true));
            }

            generalTag.addElement(GeneratorData.getResultBlock(GeneratorData.COFFEE_TABLE, variant, false));
            if(variant.strippedLog() != null)
            {
                generalTag.addElement(GeneratorData.getResultBlock(GeneratorData.COFFEE_TABLE, variant, true));
            }
        }

        TagBuilder kitchenTag = this.getOrCreateRawBuilder(ModTags.Items.KITCHEN).replace(false);
        for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
        {
            kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_COUNTER, variant, false));
            if(variant.strippedLog() != null)
            {
                kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_COUNTER, variant, true));
            }

            kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_DRAWER, variant, false));
            if(variant.strippedLog() != null)
            {
                kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_DRAWER, variant, true));
            }

            kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_LIGHT, variant, false));
            if(variant.strippedLog() != null)
            {
                kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_LIGHT, variant, true));
            }

            kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_DARK, variant, false));
            if(variant.strippedLog() != null)
            {
                kitchenTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_DARK, variant, true));
            }
        }

        TagBuilder outdoorTag = this.getOrCreateRawBuilder(ModTags.Items.OUTDOORS).replace(false);
        for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
        {
            outdoorTag.addElement(GeneratorData.getResultBlock(GeneratorData.PARK_BENCH, variant, false));
            if(variant.strippedLog() != null)
            {
                outdoorTag.addElement(GeneratorData.getResultBlock(GeneratorData.PARK_BENCH, variant, true));
            }

            outdoorTag.addElement(GeneratorData.getResultBlock(GeneratorData.MAIL_BOX, variant, false));
            if(variant.strippedLog() != null)
            {
                outdoorTag.addElement(GeneratorData.getResultBlock(GeneratorData.MAIL_BOX, variant, true));
            }

            outdoorTag.addElement(GeneratorData.getResultBlock(GeneratorData.MAIL_BOX, variant, false));
            if(variant.strippedLog() != null)
            {
                outdoorTag.addElement(GeneratorData.getResultBlock(GeneratorData.MAIL_BOX, variant, true));
            }
        }

        TagBuilder storageTag = this.getOrCreateRawBuilder(ModTags.Items.STORAGE).replace(false);
        for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
        {
            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.CABINET, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.CABINET, variant, true));
            }

            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.BEDSIDE_CABINET, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.BEDSIDE_CABINET, variant, true));
            }

            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.DESK_CABINET, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.DESK_CABINET, variant, true));
            }

            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.CRATE, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.CRATE, variant, true));
            }

            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.MAIL_BOX, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.MAIL_BOX, variant, true));
            }

            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_DRAWER, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_DRAWER, variant, true));
            }

            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_LIGHT, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_LIGHT, variant, true));
            }

            storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_DARK, variant, false));
            if(variant.strippedLog() != null)
            {
                storageTag.addElement(GeneratorData.getResultBlock(GeneratorData.KITCHEN_SINK_DARK, variant, true));
            }
        }
    }
}
