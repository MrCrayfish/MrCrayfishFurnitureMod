package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.common.ModTags;
import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author Ocelot
 */
public class BlockTagGen extends BlockTagsProvider
{
    public BlockTagGen(DataGenerator generatorIn, ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        // Picket fences connect to other picket fences
        this.tag(ModTags.Blocks.PICKET_FENCES)
                .add(ModBlocks.PICKET_FENCE_WHITE.get())
                .add(ModBlocks.PICKET_FENCE_ORANGE.get())
                .add(ModBlocks.PICKET_FENCE_MAGENTA.get())
                .add(ModBlocks.PICKET_FENCE_LIGHT_BLUE.get())
                .add(ModBlocks.PICKET_FENCE_YELLOW.get())
                .add(ModBlocks.PICKET_FENCE_LIME.get())
                .add(ModBlocks.PICKET_FENCE_PINK.get())
                .add(ModBlocks.PICKET_FENCE_GRAY.get())
                .add(ModBlocks.PICKET_FENCE_LIGHT_GRAY.get())
                .add(ModBlocks.PICKET_FENCE_CYAN.get())
                .add(ModBlocks.PICKET_FENCE_PURPLE.get())
                .add(ModBlocks.PICKET_FENCE_BLUE.get())
                .add(ModBlocks.PICKET_FENCE_BROWN.get())
                .add(ModBlocks.PICKET_FENCE_GREEN.get())
                .add(ModBlocks.PICKET_FENCE_RED.get())
                .add(ModBlocks.PICKET_FENCE_BLACK.get());

        // Picket fence gates connect to other picket fence gates
        this.tag(ModTags.Blocks.PICKET_FENCE_GATES)
                .add(ModBlocks.PICKET_GATE_WHITE.get())
                .add(ModBlocks.PICKET_GATE_ORANGE.get())
                .add(ModBlocks.PICKET_GATE_MAGENTA.get())
                .add(ModBlocks.PICKET_GATE_LIGHT_BLUE.get())
                .add(ModBlocks.PICKET_GATE_YELLOW.get())
                .add(ModBlocks.PICKET_GATE_LIME.get())
                .add(ModBlocks.PICKET_GATE_PINK.get())
                .add(ModBlocks.PICKET_GATE_GRAY.get())
                .add(ModBlocks.PICKET_GATE_LIGHT_GRAY.get())
                .add(ModBlocks.PICKET_GATE_CYAN.get())
                .add(ModBlocks.PICKET_GATE_PURPLE.get())
                .add(ModBlocks.PICKET_GATE_BLUE.get())
                .add(ModBlocks.PICKET_GATE_BROWN.get())
                .add(ModBlocks.PICKET_GATE_GREEN.get())
                .add(ModBlocks.PICKET_GATE_RED.get())
                .add(ModBlocks.PICKET_GATE_BLACK.get());

        this.tag(ModTags.Blocks.HEDGES)
                .add(ModBlocks.HEDGE_OAK.get())
                .add(ModBlocks.HEDGE_SPRUCE.get())
                .add(ModBlocks.HEDGE_BIRCH.get())
                .add(ModBlocks.HEDGE_JUNGLE.get())
                .add(ModBlocks.HEDGE_ACACIA.get())
                .add(ModBlocks.HEDGE_DARK_OAK.get())
                .add(ModBlocks.HEDGE_MANGROVE.get())
                .add(ModBlocks.HEDGE_AZALEA.get())
                .add(ModBlocks.HEDGE_FLOWERING_AZALEA.get());

        // Dynamically registers upgraded fences to the upgraded fences tag
        TagBuilder upgradedFences = this.getOrCreateRawBuilder(ModTags.Blocks.UPGRADED_FENCES).replace(false);
        for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
        {
            upgradedFences.addElement(GeneratorData.getResultBlock(GeneratorData.UPGRADED_FENCE, variant, false));
            if(variant.strippedLog() != null)
            {
                upgradedFences.addElement(GeneratorData.getResultBlock(GeneratorData.UPGRADED_FENCE, variant, true));
            }
        }

        // Dynamically registers upgraded gates to the upgraded gates tag
        TagBuilder upgradedGates = this.getOrCreateRawBuilder(ModTags.Blocks.UPGRADED_FENCE_GATES).replace(false);
        for(GeneratorData.Variant variant : GeneratorData.ALL_VARIANTS)
        {
            upgradedGates.addElement(GeneratorData.getResultBlock(GeneratorData.UPGRADED_GATE, variant, false));
            if(variant.strippedLog() != null)
            {
                upgradedGates.addElement(GeneratorData.getResultBlock(GeneratorData.UPGRADED_GATE, variant, true));
            }
        }

        // Adds upgraded fences and gates to normal fence and gate tags
        this.tag(BlockTags.FENCES).addTag(ModTags.Blocks.UPGRADED_FENCES);
        this.tag(BlockTags.FENCE_GATES).addTag(ModTags.Blocks.UPGRADED_FENCE_GATES);

        // Dynamically gets all wooden blocks and marks them as mineable with an axe
        TagsProvider.TagAppender<Block> mineableWithAxe = this.tag(BlockTags.MINEABLE_WITH_AXE);
        ModBlocks.REGISTER.getEntries().stream()
                .filter(s -> s.get().defaultBlockState().getMaterial() == Material.WOOD)
                .map(RegistryObject::get)
                .forEach(mineableWithAxe::add);

        // Dynamically gets all stone blocks and marks them as mineable with an pickaxe
        TagsProvider.TagAppender<Block> mineableWithPickaxe = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
        ModBlocks.REGISTER.getEntries().stream()
                .filter(s -> s.get().defaultBlockState().getMaterial() == Material.STONE || s.get().defaultBlockState().getMaterial() == Material.METAL)
                .map(RegistryObject::get)
                .forEach(mineableWithPickaxe::add);
    }
}
