package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.item.BlockSupplierItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> SPATULA = register("spatula", new SwordItem(ItemTier.IRON, 3, -1.4F, new Item.Properties().group(FurnitureMod.GROUP)));

    private static RegistryObject<Item> register(String name, Item item)
    {
        return REGISTER.register(name, () -> item);
    }
}
