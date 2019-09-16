package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.screen.inventory.CrateScreen;
import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import com.mrcrayfish.furniture.inventory.container.PostBoxContainer;
import com.mrcrayfish.furniture.tileentity.CrateTileEntity;
import com.mrcrayfish.furniture.util.ContainerNames;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.IContainerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers
{
    private static final List<ContainerType<?>> CONTAINER_TYPES = new ArrayList<>();

    @SuppressWarnings("ConstantConditions")
    public static final ContainerType<CrateContainer> CRATE = register(ContainerNames.CRATE, (IContainerFactory<CrateContainer>) (windowId, playerInventory, data) -> {
        CrateTileEntity crateTileEntity = (CrateTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
        return new CrateContainer(windowId, playerInventory, crateTileEntity, crateTileEntity.isLocked());
    });

    @SuppressWarnings("ConstantConditions")
    public static final ContainerType<PostBoxContainer> POST_BOX = register(ContainerNames.POST_BOX, PostBoxContainer::new);

    private static <T extends Container> ContainerType<T> register(String key, ContainerType.IFactory<T> factory)
    {
        ContainerType<T> type = new ContainerType<>(factory);
        type.setRegistryName(key);
        CONTAINER_TYPES.add(type);
        return type;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerTypes(final RegistryEvent.Register<ContainerType<?>> event)
    {
        CONTAINER_TYPES.forEach(type -> event.getRegistry().register(type));
        CONTAINER_TYPES.clear();
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreenFactories()
    {
        ScreenManager.registerFactory(CRATE, CrateScreen::new);
    }
}
