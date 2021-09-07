package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.inventory.container.*;
import com.mrcrayfish.furniture.tileentity.CrateTileEntity;
import com.mrcrayfish.furniture.tileentity.FreezerTileEntity;
import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class ModContainers
{
    public static final DeferredRegister<ContainerType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<ContainerType<CrateContainer>> CRATE = register("crate", (IContainerFactory<CrateContainer>) (windowId, playerInventory, data) -> {
        CrateTileEntity crateTileEntity = (CrateTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
        return new CrateContainer(windowId, playerInventory, crateTileEntity, crateTileEntity.isLocked());
    });

    public static final RegistryObject<ContainerType<PostBoxContainer>> POST_BOX = register("post_box", PostBoxContainer::new);

    public static final RegistryObject<ContainerType<MailBoxContainer>> MAIL_BOX = register("mail_box", (IContainerFactory<MailBoxContainer>) (windowId, playerInventory, data) -> {
        MailBoxTileEntity mailBoxTileEntity = (MailBoxTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
        return new MailBoxContainer(windowId, playerInventory, mailBoxTileEntity);
    });

    public static final RegistryObject<ContainerType<FreezerContainer>> FREEZER = register("freezer", (IContainerFactory<FreezerContainer>) (windowId, playerInventory, data) -> {
        FreezerTileEntity freezerTileEntity = (FreezerTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
        return new FreezerContainer(windowId, playerInventory, freezerTileEntity);
    });

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String key, ContainerType.IFactory<T> factory)
    {
        ContainerType<T> type = new ContainerType<>(factory);
        return REGISTER.register(key, () -> type);
    }
}
