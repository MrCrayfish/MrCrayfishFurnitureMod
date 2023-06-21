package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Author: MrCrayfish
 */
public class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = REGISTER.register("creative_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder();
        builder.icon(() -> new ItemStack(ModBlocks.CHAIR_OAK.get()));
        builder.title(Component.translatable("itemGroup.cfm"));
        builder.displayItems((flags, output) -> {
            ModItems.REGISTER.getEntries().forEach(registryObject -> {
                output.accept(registryObject.get());
            });
        });
        return builder.build();
    });
}
