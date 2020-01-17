package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import com.mrcrayfish.furniture.inventory.container.FreezerContainer;
import com.mrcrayfish.furniture.inventory.container.MailBoxContainer;
import com.mrcrayfish.furniture.inventory.container.PostBoxContainer;
import com.mrcrayfish.furniture.tileentity.CrateTileEntity;
import com.mrcrayfish.furniture.tileentity.FreezerTileEntity;
import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import com.mrcrayfish.furniture.util.ContainerNames;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.IContainerFactory;

import java.util.ArrayList;
import java.util.List;

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

    public static final ContainerType<PostBoxContainer> POST_BOX = register(ContainerNames.POST_BOX, PostBoxContainer::new);

    public static final ContainerType<MailBoxContainer> MAIL_BOX = register(ContainerNames.MAIL_BOX, (IContainerFactory<MailBoxContainer>) (windowId, playerInventory, data) -> {
        MailBoxTileEntity mailBoxTileEntity = (MailBoxTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
        return new MailBoxContainer(windowId, playerInventory, mailBoxTileEntity);
    });

    public static final ContainerType<FreezerContainer> FREEZER = register(ContainerNames.FREEZER, (IContainerFactory<FreezerContainer>) (windowId, playerInventory, data) -> {
        FreezerTileEntity freezerTileEntity = (FreezerTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
        return new FreezerContainer(windowId, playerInventory, freezerTileEntity);
    });

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
}
