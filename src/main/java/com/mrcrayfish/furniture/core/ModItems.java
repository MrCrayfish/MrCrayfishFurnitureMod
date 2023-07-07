package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> SPATULA = register("spatula", () -> new SwordItem(Tiers.IRON, 3, -1.4F, new Item.Properties()));
    public static ArrayList<Item> ITEMS = new ArrayList<>();

    public static RegistryObject<Item> register(String name, Supplier<Item> item)
    {
        if (ITEMS == null) ITEMS = new ArrayList<>(); // just in-case static init fails
        return REGISTER.register(name, () -> {
            Item registeredItem = item.get();
            ITEMS.add(registeredItem);
            return registeredItem;
        });
    }
}
