package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.inventory.container.CrateMenu;
import com.mrcrayfish.furniture.inventory.container.FreezerMenu;
import com.mrcrayfish.furniture.inventory.container.MailBoxMenu;
import com.mrcrayfish.furniture.inventory.container.PostBoxMenu;
import com.mrcrayfish.furniture.tileentity.CrateBlockEntity;
import com.mrcrayfish.furniture.tileentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.tileentity.MailBoxBlockEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Author: MrCrayfish
 */
public class ModContainers
{
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<MenuType<CrateMenu>> CRATE = register("crate", (IContainerFactory<CrateMenu>) (windowId, playerInventory, data) -> {
        CrateBlockEntity crateBlockEntity = (CrateBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new CrateMenu(windowId, playerInventory, crateBlockEntity, crateBlockEntity.isLocked());
    });

    public static final RegistryObject<MenuType<PostBoxMenu>> POST_BOX = register("post_box", PostBoxMenu::new);

    public static final RegistryObject<MenuType<MailBoxMenu>> MAIL_BOX = register("mail_box", (IContainerFactory<MailBoxMenu>) (windowId, playerInventory, data) -> {
        MailBoxBlockEntity mailBoxBlockEntity = (MailBoxBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new MailBoxMenu(windowId, playerInventory, mailBoxBlockEntity);
    });

    public static final RegistryObject<MenuType<FreezerMenu>> FREEZER = register("freezer", (IContainerFactory<FreezerMenu>) (windowId, playerInventory, data) -> {
        FreezerBlockEntity freezerBlockEntity = (FreezerBlockEntity) playerInventory.player.level.getBlockEntity(data.readBlockPos());
        return new FreezerMenu(windowId, playerInventory, freezerBlockEntity);
    });

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String key, MenuType.MenuSupplier<T> supplier)
    {
        MenuType<T> type = new MenuType<>(supplier);
        return REGISTER.register(key, () -> type);
    }
}
