/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.items.ItemCup;
import com.mrcrayfish.furniture.items.ItemEnvelope;
import com.mrcrayfish.furniture.items.ItemEnvelopeSigned;
import com.mrcrayfish.furniture.items.ItemGeneric;
import com.mrcrayfish.furniture.items.ItemKnife;
import com.mrcrayfish.furniture.items.ItemLog;
import com.mrcrayfish.furniture.items.ItemPackage;
import com.mrcrayfish.furniture.items.ItemPackageSigned;
import com.mrcrayfish.furniture.items.ItemRecipeBook;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;

public class FurnitureItems
{
    /**
     * Initial Furniture
     */
    public static final Item FLESH, COOKED_FLESH, COOL_PACK;

    /**
     * Garden Update
     */
    public static final Item HAMMER;
    public static final Item ENVELOPE, ENVELOPE_SIGNED;
    public static final Item PACKAGE, PACKAGE_SIGNED;

    /**
     * Electronic Update
     */
    public static final Item INK_CARTRIDGE;

    /** Bathroom Update */

    /**
     * Kitchen Update
     */
    public static final Item KNIFE, CUP, DRINK, SOAP, SOAPY_WATER, SUPER_SOAPY_WATER;

    /**
     * Christmas Update
     */
    public static final Item LOG;

    /**
     * Outdoor Update
     */
    public static final Item SPATULA, SAUSAGE, SAUSAGE_COOKED, KEBAB, KEBAB_COOKED, CROWBAR;

    /**
     * Food
     */
    public static final Item BREAD_SLICE, TOAST;

    /**
     * Misc
     */
    public static final Item RECIPE_BOOk;
    public static final Item CRAYFISH;

    static
    {
        COOL_PACK = new ItemGeneric().setUnlocalizedName("item_cool_pack").setRegistryName("item_cool_pack");
        INK_CARTRIDGE = new ItemGeneric().setUnlocalizedName("item_ink_cartridge").setRegistryName("item_ink_cartridge");
        FLESH = new ItemFood(1, 2, false).setUnlocalizedName("item_flesh").setRegistryName("item_flesh").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        COOKED_FLESH = new ItemFood(4, 4, false).setUnlocalizedName("item_flesh_cooked").setRegistryName("item_flesh_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        ENVELOPE = new ItemEnvelope().setUnlocalizedName("item_envelope").setRegistryName("item_envelope");
        ENVELOPE_SIGNED = new ItemEnvelopeSigned().setUnlocalizedName("item_envelope_signed").setRegistryName("item_envelope_signed");
        PACKAGE = new ItemPackage().setUnlocalizedName("item_package").setRegistryName("item_package");
        PACKAGE_SIGNED = new ItemPackageSigned().setUnlocalizedName("item_package_signed").setRegistryName("item_package_signed");
        HAMMER = new Item().setUnlocalizedName("item_hammer").setRegistryName("item_hammer");
        BREAD_SLICE = new ItemFood(2, false).setUnlocalizedName("item_bread_slice").setRegistryName("item_bread_slice").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        TOAST = new ItemFood(4, false).setUnlocalizedName("item_toast").setRegistryName("item_toast").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        KNIFE = new ItemKnife().setMaxDamage(100).setUnlocalizedName("item_knife").setRegistryName("item_knife");
        CUP = new ItemCup(false).setUnlocalizedName("item_cup").setRegistryName("item_cup");
        DRINK = new ItemCup(true).setUnlocalizedName("item_drink").setRegistryName("item_drink");
        SOAP = new ItemGeneric().setUnlocalizedName("item_soap").setRegistryName("item_soap");
        SOAPY_WATER = new ItemGeneric().setUnlocalizedName("item_soap_water").setRegistryName("item_soap_water").setContainerItem(Items.BUCKET).setMaxStackSize(1);
        SUPER_SOAPY_WATER = new ItemGeneric().setUnlocalizedName("item_super_soap_water").setRegistryName("item_super_soap_water").setContainerItem(Items.BUCKET).setMaxStackSize(1);
        RECIPE_BOOk = new ItemRecipeBook().setUnlocalizedName("item_recipe_book").setRegistryName("item_recipe_book");
        CRAYFISH = new Item().setUnlocalizedName("item_crayfish").setRegistryName("item_crayfish").setMaxStackSize(1);
        LOG = new ItemLog(FurnitureBlocks.FIRE_PIT_OFF).setUnlocalizedName("item_log").setRegistryName("item_log").setMaxStackSize(16);
        SPATULA = new Item().setUnlocalizedName("item_spatula").setRegistryName("item_spatula").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        SAUSAGE = new ItemFood(1, false).setUnlocalizedName("item_sausage").setRegistryName("item_sausage").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        SAUSAGE_COOKED = new ItemFood(4, false).setUnlocalizedName("item_sausage_cooked").setRegistryName("item_sausage_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        KEBAB = new ItemFood(1, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setUnlocalizedName("item_kebab").setRegistryName("item_kebab").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        KEBAB_COOKED = new ItemFood(4, false).setUnlocalizedName("item_kebab_cooked").setRegistryName("item_kebab_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        CROWBAR = new Item().setUnlocalizedName("item_crow_bar").setRegistryName("item_crow_bar").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    public static void register()
    {
        registerItem(FLESH);
        registerItem(COOKED_FLESH);
        registerItem(COOL_PACK);
        registerItem(HAMMER);
        registerItem(ENVELOPE);
        registerItem(ENVELOPE_SIGNED);
        registerItem(PACKAGE);
        registerItem(PACKAGE_SIGNED);
        registerItem(INK_CARTRIDGE);
        registerItem(BREAD_SLICE);
        registerItem(TOAST);
        registerItem(KNIFE);
        registerItem(CUP);
        registerItem(DRINK);
        registerItem(SOAP);
        registerItem(SOAPY_WATER);
        registerItem(SUPER_SOAPY_WATER);
        registerItem(RECIPE_BOOk);
        registerItem(CRAYFISH);
        registerItem(LOG);
        registerItem(SPATULA);
        registerItem(SAUSAGE);
        registerItem(SAUSAGE_COOKED);
        registerItem(KEBAB);
        registerItem(KEBAB_COOKED);
        registerItem(CROWBAR);
    }

    private static void registerItem(Item item)
    {
        RegistrationHandler.Items.add(item);
    }
}
