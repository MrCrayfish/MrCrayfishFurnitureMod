package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.client.ClientHandler;
import com.mrcrayfish.furniture.common.CommonHandler;
import com.mrcrayfish.furniture.core.*;
import com.mrcrayfish.furniture.datagen.BlockTagGen;
import com.mrcrayfish.furniture.datagen.EntityTypeTagGen;
import com.mrcrayfish.furniture.datagen.FluidTagGen;
import com.mrcrayfish.furniture.datagen.ItemTagGen;
import com.mrcrayfish.furniture.datagen.LootTableGen;
import com.mrcrayfish.furniture.datagen.RecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class FurnitureMod
{
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static final ItemGroup GROUP = new FurnitureGroup(Reference.MOD_ID);

    public FurnitureMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.REGISTER.register(eventBus);
        ModItems.REGISTER.register(eventBus);
        ModEntities.REGISTER.register(eventBus);
        ModTileEntities.REGISTER.register(eventBus);
        ModContainers.REGISTER.register(eventBus);
        ModSounds.REGISTER.register(eventBus);
        ModParticles.REGISTER.register(eventBus);
        ModRecipeSerializers.REGISTER.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FurnitureConfig.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FurnitureConfig.commonSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onDataSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        CommonHandler.setup();
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        ClientHandler.setup();
    }

    private void onDataSetup(GatherDataEvent event)
    {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        BlockTagGen blockTagGen = new BlockTagGen(dataGenerator, existingFileHelper);
        dataGenerator.addProvider(new RecipeGen(dataGenerator));
        dataGenerator.addProvider(new LootTableGen(dataGenerator));
        dataGenerator.addProvider(blockTagGen);
        dataGenerator.addProvider(new ItemTagGen(dataGenerator, blockTagGen, existingFileHelper));
        dataGenerator.addProvider(new FluidTagGen(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(new EntityTypeTagGen(dataGenerator, existingFileHelper));
    }
}
