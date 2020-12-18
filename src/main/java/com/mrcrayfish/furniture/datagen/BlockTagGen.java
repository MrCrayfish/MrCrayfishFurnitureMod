package com.mrcrayfish.furniture.datagen;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.common.ModTags;
import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

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
    protected void registerTags()
    {
        // Picket fences connect to other picket fences
        this.getOrCreateBuilder(ModTags.Blocks.PICKET_FENCES)
                .add(ModBlocks.PICKET_FENCE_WHITE)
                .add(ModBlocks.PICKET_FENCE_ORANGE)
                .add(ModBlocks.PICKET_FENCE_MAGENTA)
                .add(ModBlocks.PICKET_FENCE_LIGHT_BLUE)
                .add(ModBlocks.PICKET_FENCE_YELLOW)
                .add(ModBlocks.PICKET_FENCE_LIME)
                .add(ModBlocks.PICKET_FENCE_PINK)
                .add(ModBlocks.PICKET_FENCE_GRAY)
                .add(ModBlocks.PICKET_FENCE_LIGHT_GRAY)
                .add(ModBlocks.PICKET_FENCE_CYAN)
                .add(ModBlocks.PICKET_FENCE_PURPLE)
                .add(ModBlocks.PICKET_FENCE_BLUE)
                .add(ModBlocks.PICKET_FENCE_BROWN)
                .add(ModBlocks.PICKET_FENCE_GREEN)
                .add(ModBlocks.PICKET_FENCE_RED)
                .add(ModBlocks.PICKET_FENCE_BLACK);

        // Picket fences are considered "upgraded" since they work the same. The tag is used for connecting
        this.getOrCreateBuilder(ModTags.Blocks.UPGRADED_FENCES)
                .addTag(ModTags.Blocks.PICKET_FENCES)
                .add(ModBlocks.UPGRADED_FENCE_OAK)
                .add(ModBlocks.UPGRADED_FENCE_SPRUCE)
                .add(ModBlocks.UPGRADED_FENCE_BIRCH)
                .add(ModBlocks.UPGRADED_FENCE_JUNGLE)
                .add(ModBlocks.UPGRADED_FENCE_ACACIA)
                .add(ModBlocks.UPGRADED_FENCE_DARK_OAK);

        // Picket fence gates connect to other picket fence gates
        this.getOrCreateBuilder(ModTags.Blocks.PICKET_FENCE_GATES)
                .add(ModBlocks.PICKET_GATE_WHITE)
                .add(ModBlocks.PICKET_GATE_ORANGE)
                .add(ModBlocks.PICKET_GATE_MAGENTA)
                .add(ModBlocks.PICKET_GATE_LIGHT_BLUE)
                .add(ModBlocks.PICKET_GATE_YELLOW)
                .add(ModBlocks.PICKET_GATE_LIME)
                .add(ModBlocks.PICKET_GATE_PINK)
                .add(ModBlocks.PICKET_GATE_GRAY)
                .add(ModBlocks.PICKET_GATE_LIGHT_GRAY)
                .add(ModBlocks.PICKET_GATE_CYAN)
                .add(ModBlocks.PICKET_GATE_PURPLE)
                .add(ModBlocks.PICKET_GATE_BLUE)
                .add(ModBlocks.PICKET_GATE_BROWN)
                .add(ModBlocks.PICKET_GATE_GREEN)
                .add(ModBlocks.PICKET_GATE_RED)
                .add(ModBlocks.PICKET_GATE_BLACK);

        // Picket fence gates are considered "upgraded" since they work the same.
        this.getOrCreateBuilder(ModTags.Blocks.UPGRADED_FENCE_GATES)
                .addTag(ModTags.Blocks.PICKET_FENCE_GATES)
                .add(ModBlocks.UPGRADED_GATE_OAK)
                .add(ModBlocks.UPGRADED_GATE_SPRUCE)
                .add(ModBlocks.UPGRADED_GATE_BIRCH)
                .add(ModBlocks.UPGRADED_GATE_JUNGLE)
                .add(ModBlocks.UPGRADED_GATE_ACACIA)
                .add(ModBlocks.UPGRADED_GATE_DARK_OAK);

        this.getOrCreateBuilder(ModTags.Blocks.HEDGES)
                .add(ModBlocks.HEDGE_OAK)
                .add(ModBlocks.HEDGE_SPRUCE)
                .add(ModBlocks.HEDGE_BIRCH)
                .add(ModBlocks.HEDGE_JUNGLE)
                .add(ModBlocks.HEDGE_ACACIA)
                .add(ModBlocks.HEDGE_DARK_OAK);

        this.getOrCreateBuilder(Tags.Blocks.FENCES_WOODEN).addTag(ModTags.Blocks.UPGRADED_FENCES);
        this.getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).addTag(ModTags.Blocks.UPGRADED_FENCE_GATES);
    }
}
