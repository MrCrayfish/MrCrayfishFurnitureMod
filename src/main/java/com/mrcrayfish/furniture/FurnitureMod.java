package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.client.ClientHandler;
import com.mrcrayfish.furniture.common.CommonHandler;
import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.core.ModEntities;
import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.core.ModRecipeTypes;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.datagen.BlockTagGen;
import com.mrcrayfish.furniture.datagen.ItemTagGen;
import com.mrcrayfish.furniture.datagen.LootTableGen;
import com.mrcrayfish.furniture.datagen.RecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class FurnitureMod
{
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static final CreativeModeTab GROUP = new FurnitureModTab(Reference.MOD_ID);

    public FurnitureMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.REGISTER.register(eventBus);
        ModItems.REGISTER.register(eventBus);
        ModEntities.REGISTER.register(eventBus);
        ModBlockEntities.REGISTER.register(eventBus);
        ModContainers.REGISTER.register(eventBus);
        ModSounds.REGISTER.register(eventBus);
        ModRecipeTypes.REGISTER.register(eventBus);
        ModRecipeSerializers.REGISTER.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FurnitureConfig.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FurnitureConfig.commonSpec);
        eventBus.addListener(this::onCommonSetup);
        eventBus.addListener(this::onClientSetup);
        eventBus.addListener(this::onGatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientHandler::onRegisterBlockColors);
            eventBus.addListener(ClientHandler::onRegisterItemColors);
            eventBus.addListener(ClientHandler::onRegisterRenderers);
            eventBus.addListener(ClientHandler::onRegisterGeometryLoaders);
        });
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(CommonHandler::setup);
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(ClientHandler::setup);
    }

    private void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        BlockTagGen blockTagGen = new BlockTagGen(generator, existingFileHelper);
        generator.addProvider(event.includeServer(), new RecipeGen(generator));
        generator.addProvider(event.includeServer(), new LootTableGen(generator));
        generator.addProvider(event.includeServer(), blockTagGen);
        generator.addProvider(event.includeServer(), new ItemTagGen(generator, blockTagGen, existingFileHelper));
    }
}
