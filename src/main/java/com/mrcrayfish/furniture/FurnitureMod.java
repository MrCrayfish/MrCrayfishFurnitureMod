package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.proxy.ClientProxy;
import com.mrcrayfish.furniture.proxy.CommonProxy;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        //ScreenManager.registerFactory();
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        PROXY.onSetupCommon();
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        PROXY.onSetupClient();
    }
}
