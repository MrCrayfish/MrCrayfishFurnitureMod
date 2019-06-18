package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.common.GuiFactory;
import com.mrcrayfish.furniture.proxy.ClientProxy;
import com.mrcrayfish.furniture.proxy.CommonProxy;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.LifecycleEventProvider;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        //ScreenManager.registerFactory();
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        PROXY.setup();
    }

    public void gatherData(GatherDataEvent event)
    {
        System.out.println("HELLO MY FRIENDS");
    }
}
