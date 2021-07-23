package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> SPATULA = register("spatula", new SwordItem(Tiers.IRON, 3, -1.4F, new Item.Properties().tab(FurnitureMod.GROUP)));

    private static RegistryObject<Item> register(String name, Item item)
    {
        return REGISTER.register(name, () -> item);
    }
}
