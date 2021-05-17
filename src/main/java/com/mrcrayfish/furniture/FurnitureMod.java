package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.core.ModEntities;
import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.datagen.BlockTagGen;
import com.mrcrayfish.furniture.datagen.EntityTypeTagGen;
import com.mrcrayfish.furniture.datagen.FluidTagGen;
import com.mrcrayfish.furniture.datagen.ItemTagGen;
import com.mrcrayfish.furniture.datagen.LootTableGen;
import com.mrcrayfish.furniture.datagen.RecipeGen;
import com.mrcrayfish.furniture.proxy.ClientProxy;
import com.mrcrayfish.furniture.proxy.CommonProxy;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
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
    public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public FurnitureMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.REGISTER.register(eventBus);
        ModItems.REGISTER.register(eventBus);
        ModEntities.REGISTER.register(eventBus);
        ModTileEntities.REGISTER.register(eventBus);
        ModContainers.REGISTER.register(eventBus);
        ModSounds.REGISTER.register(eventBus);
        ModRecipeSerializers.REGISTER.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FurnitureConfig.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FurnitureConfig.commonSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onDataSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        PROXY.onSetupCommon();
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        PROXY.onSetupClient();
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
