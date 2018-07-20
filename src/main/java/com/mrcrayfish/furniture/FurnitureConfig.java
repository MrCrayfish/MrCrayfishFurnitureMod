package com.mrcrayfish.furniture;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.mrcrayfish.furniture.api.RecipeRegistry;
import com.mrcrayfish.furniture.api.Recipes;

@Config(modid = Reference.MOD_ID)
@LangKey(Reference.MOD_ID + ".config.title")
@EventBusSubscriber
public class FurnitureConfig
{
    @Name("client")
    @Comment("Client-only configs.")
    @LangKey(Reference.MOD_ID + ".config.client")
    public static final Client CLIENT = new Client();

    @Name("server")
    @Comment("Server-only configs.")
    @LangKey(Reference.MOD_ID + ".config.server")
    public static final Server SERVER = new Server();

    @Name("common")
    @Comment("Configs shared by both client and server.")
    @LangKey(Reference.MOD_ID + ".config.common")
    public static final Common COMMON = new Common();

    public static class Client
    {
        @Name("mirror")
        @Comment("Mirror render settings.")
        @LangKey(Reference.MOD_ID + ".config.client.mirror")
        public Mirror mirror = new Mirror();
    }

    public static class Mirror
    {
        @Name("Enabled")
        @Comment("Determines whether the mirror will be rendered.")
        @LangKey(Reference.MOD_ID + ".config.client.mirror.enabled")
        public boolean enabled = true;

        @Name("Clouds")
        @Comment("Set whether the mirror should render clouds.")
        @LangKey(Reference.MOD_ID + ".config.client.mirror.clouds")
        public boolean clouds = false;

        @Name("FOV")
        @Comment("Sets the field of view for the mirror.")
        @LangKey(Reference.MOD_ID + ".config.client.mirror.fov")
        @RangeDouble(min = 10, max = 100)
        public float fov = 80;

        @Name("Quality")
        @Comment("Sets the resolution for the mirror. High number means better quality but worse performace.")
        @LangKey(Reference.MOD_ID + ".config.client.mirror.quality")
        @RangeInt(min = 16, max = 512)
        public int quality = 64;
    }

    public static class Server
    {
        @Name("messages")
        @Comment("Enable or disable messages.")
        @LangKey(Reference.MOD_ID + ".config.server.messages")
        public Messages messages = new Messages();
    }

    public static class Messages
    {
        public static boolean hasDisplayedOnce = false;

        @Name("Welcome Message")
        @Comment("Enable or disable the welcome message")
        @LangKey(Reference.MOD_ID + ".config.server.messages.welcome_message")
        public boolean canDisplay = true;
    }

    public static class Common
    {
        @Name("recipe")
        @Comment("Recipe settings.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe")
        public Recipe recipe = new Recipe();
    }

    public static class Recipe
    {
        @Name("API Debug")
        @Comment("If true, prints out information about RecipeAPI. Recommended 'true' for people trying to add custom recipes. How to use RecipeAPI: http://mrcrayfishs-furniture-mod.wikia.com/wiki/Configuration")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.api_debug")
        public boolean apiDebug = false;

        @Name("Custom Recipes")
        @Comment("Insert custom recipes here. How to use RecipeAPI: http://mrcrayfishs-furniture-mod.wikia.com/wiki/Configuration")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.custom_recipes")
        public String[] items = {};

        @Name("enabled recipes")
        @Comment("Enabled or disable the default recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled")
        public EnabledRecipes enabled = new EnabledRecipes();
    }

    public static class EnabledRecipes
    {
        @Name("Printer")
        @Comment("Enabled printer recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.printer")
        public Printer printer = new Printer();

        @Name("Oven")
        @Comment("Enabled oven recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven")
        public Oven oven = new Oven();

        @Name("Freezer")
        @Comment("Enabled freezer recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.freezer")
        public Freezer freezer = new Freezer();

        @Name("Minebay")
        @Comment("Enabled minebay recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay")
        public Minebay minebay = new Minebay();

        @Name("Blender")
        @Comment("Enabled blender recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.blender")
        public Blender blender = new Blender();

        @Name("Chopping Board")
        @Comment("Enabled chopping board recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.chopping_board")
        public ChoppingBoard choppingBoard = new ChoppingBoard();

        @Name("Dishwasher")
        @Comment("Enabled dishwasher recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher")
        public Dishwasher dishwasher = new Dishwasher();

        @Name("Microwave")
        @Comment("Enabled microwave recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.microwave")
        public Microwave microwave = new Microwave();

        @Name("Toaster")
        @Comment("Enabled toaster recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.toaster")
        public Toaster toaster = new Toaster();

        @Name("Washing Machine")
        @Comment("Enabled washing machine recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine")
        public WashingMachine washingMachine = new WashingMachine();

        @Name("Grill")
        @Comment("Enabled grill recipes.")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.grill")
        public Grill grill = new Grill();
    }

    public static class Printer
    {
        @Name("Enchanted Book")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.printer.book.enchanted")
        public boolean bookEnchanted = true;

        @Name("Written Book")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.printer.book.written")
        public boolean bookWritten = true;
    }

    public static class Oven
    {
        @Name("Beef -> Cooked Beef")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven.beef")
        public boolean beef = true;

        @Name("Porkchop -> Cooked Porkchop")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven.porkchop")
        public boolean porkchop = true;

        @Name("Potato -> Cooked Potato")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven.potato")
        public boolean potato = true;

        @Name("Chicken -> Cooked Chicken")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven.chicken")
        public boolean chicken = true;

        @Name("Raw Fish -> Cooked Fish")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven.fish")
        public boolean fish = true;

        @Name("Raw Salmon -> Cooked Salmon")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven.salmon")
        public boolean salmon = true;

        @Name("Flesh -> Cooked Flesh")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.oven.flesh")
        public boolean flesh = true;
    }

    public static class Freezer
    {
        @Name("Water Bucket -> Ice")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.freezer.water_to_ice")
        public boolean waterToIce = true;

        @Name("Ice -> Packet Ice")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.freezer.ice_to_packed")
        public boolean iceToPacked = true;

        @Name("Lava Bucket -> Obsidian")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.freezer.lava_to_obsidian")
        public boolean lavaToObsidian = true;

        @Name("Slime Ball -> Snow Ball")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.freezer.slime_to_snow")
        public boolean slimeToSnow = true;

        @Name("Poisonous Potato -> Potato")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.freezer.poisonous_to_potato")
        public boolean poisonousToPotato = true;

        @Name("Rotten Flesh -> Flesh")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.freezer.rotten_to_flesh")
        public boolean rottenToFlesh = true;
    }

    public static class Minebay
    {
        @Name("16 Hardened Clay for 1 Emerald")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.clay_for_emerald")
        public boolean clayForEmerald = true;

        @Name("1 Skeleton Skull for 8 Emeralds")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.skull_for_emerald")
        public boolean skullForEmerald = true;

        @Name("1 Saddle for 4 Emeralds")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.saddle_for_emerald")
        public boolean saddleForEmerald = true;

        @Name("1 Horse Spawn Egg for 8 Emeralds")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.horse_for_emerald")
        public boolean horseForEmerald = true;

        @Name("1 Diamond Horse Armour for 8 Diamonds")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.horse_armour_for_diamond")
        public boolean horse_armourForDiamond = true;

        @Name("1 Experience Bottle for 1 Iron Ingot")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.xp_for_iron")
        public boolean xpForIron = true;

        @Name("4 Christmas Firework for 1 Iron Ingot")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.firework_for_iron")
        public boolean fireworkForIron = true;

        @Name("1 Silk Touch Book for 8 Emeralds")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.silk_touch_for_emerald")
        public boolean silkTouchForEmerald = true;

        @Name("2 Night Vision Potion for 1 Emerald")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.night_vision_for_emerald")
        public boolean nightVisionForEmerald = true;

        @Name("1 Recipe Book for 1 Emerald")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.minebay.recipe_book_for_emerald")
        public boolean recipeBookForEmerald = true;
    }

    public static class Blender
    {
        @Name("Fruit Crush")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.blender.fruit")
        public boolean fruit = true;

        @Name("Veggie Juice")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.blender.veggie")
        public boolean veggie = true;

        @Name("Fishy Blend")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.blender.fish")
        public boolean fish = true;

        @Name("Energy Drink")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.blender.energy")
        public boolean energy = true;
    }

    public static class ChoppingBoard
    {
        @Name("Bread -> 6 Bread Slices")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.chopping_board.bread")
        public boolean bread = true;
    }

    public static class Dishwasher
    {
        @Name("Bow")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.bow")
        public boolean bow = true;

        @Name("Wooden Pickaxe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.wood.pickaxe")
        public boolean woodPickaxe = true;

        @Name("Wooden Axe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.wood.axe")
        public boolean woodAxe = true;

        @Name("Wooden Shovel")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.wood.shovel")
        public boolean woodShovel = true;

        @Name("Wooden Hoe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.wood.hoe")
        public boolean woodHoe = true;

        @Name("Wooden Sword")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.wood.sword")
        public boolean woodSword = true;

        @Name("Stone Pickaxe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.stone.pickaxe")
        public boolean stonePickaxe = true;

        @Name("Stone Axe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.stone.axe")
        public boolean stoneAxe = true;

        @Name("Stone Shovel")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.stone.shovel")
        public boolean stoneShovel = true;

        @Name("Stone Hoe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.stone.hoe")
        public boolean stoneHoe = true;

        @Name("Stone Sword")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.stone.sword")
        public boolean stoneSword = true;

        @Name("Iron Pickaxe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.iron.pickaxe")
        public boolean ironPickaxe = true;

        @Name("Iron Axe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.iron.axe")
        public boolean ironAxe = true;

        @Name("Iron Shovel")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.iron.shovel")
        public boolean ironShovel = true;

        @Name("Iron Hoe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.iron.hoe")
        public boolean ironHoe = true;

        @Name("Iron Sword")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.iron.sword")
        public boolean ironSword = true;

        @Name("Golden Pickaxe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.gold.pickaxe")
        public boolean goldPickaxe = true;

        @Name("Golden Axe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.gold.axe")
        public boolean goldAxe = true;

        @Name("Golden Shovel")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.gold.shovel")
        public boolean goldShovel = true;

        @Name("Golden Hoe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.gold.hoe")
        public boolean goldhoe = true;

        @Name("Golden Sword")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.gold.sword")
        public boolean goldSword = true;

        @Name("Diamond Pickaxe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.diamond.pickaxe")
        public boolean diamondPickaxe = true;

        @Name("Diamond Axe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.diamond.axe")
        public boolean diamondAxe = true;

        @Name("Diamond Shovel")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.diamond.shovel")
        public boolean diamondShovel = true;

        @Name("Diamond Hoe")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.diamond.hoe")
        public boolean diamondHoe = true;

        @Name("Diamond Sword")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.diamond.sword")
        public boolean diamondSword = true;

        @Name("Fishing Rod")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.fishing_rod")
        public boolean fishingRod = true;

        @Name("Flint and Steel")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.flint_and_steel")
        public boolean flintAndSteel = true;

        @Name("Shears")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.shears")
        public boolean shears = true;

        @Name("Shield")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.dishwasher.shield")
        public boolean shield = true;
    }

    public static class Microwave
    {
        @Name("Beef -> Cooked Beef")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.microwave.beef")
        public boolean beef = true;

        @Name("Potato -> Baked Potato")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.microwave.potato")
        public boolean potato = true;
    }

    public static class Toaster
    {
        @Name("Bread Slice -> Toast")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.toaster.toast")
        public boolean toast = true;
    }

    public static class WashingMachine
    {
        @Name("Leather Helmet")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.leather.helmet")
        public boolean leatherHelmet = true;

        @Name("Leather Chestplate")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.leather.chestplate")
        public boolean leatherChestplate = true;

        @Name("Leather Leggings")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.leather.leggings")
        public boolean leatherLeggings = true;

        @Name("Leather Boots")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.leather.boots")
        public boolean leatherBoots = true;

        @Name("Chainmail Helmet")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.chainmail.helmet")
        public boolean chainmailHelmet = true;

        @Name("Chainmail Chestplate")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.chainmail.chestplate")
        public boolean chainmailChestplate = true;

        @Name("Chainmail Leggings")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.chainmail.leggings")
        public boolean chainmailLeggings = true;

        @Name("Chainmail Boots")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.chainmail.boots")
        public boolean chainmailBoots = true;

        @Name("Iron Helmet")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.iron.helmet")
        public boolean ironHelmet = true;

        @Name("Iron Chestplate")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.iron.chestplate")
        public boolean ironChestplate = true;

        @Name("Iron Leggings")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.iron.leggings")
        public boolean ironLeggings = true;

        @Name("Iron Boots")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.iron.boots")
        public boolean ironBoots = true;

        @Name("Golden Helmet")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.gold.helmet")
        public boolean goldHelmet = true;

        @Name("Golden Chestplate")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.gold.chestplate")
        public boolean goldChestplate = true;

        @Name("Golden Leggings")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.gold.leggings")
        public boolean goldLeggings = true;

        @Name("Golden Boots")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.gold.boots")
        public boolean goldBoots = true;

        @Name("Diamond Helmet")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.diamond.helmet")
        public boolean diamondHelmet = true;

        @Name("Diamond Chestplate")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.diamond.chestplate")
        public boolean diamondChestplate = true;

        @Name("Diamond Leggings")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.diamond.leggings")
        public boolean diamondLeggings = true;

        @Name("Diamond Boots")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.diamond.boots")
        public boolean diamondBoots = true;

        @Name("Elytra")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.washing_machine.elytra")
        public boolean elytra = true;
    }

    public static class Grill
    {
        @Name("Beef -> Cooked Beef")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.grill.beef")
        public boolean beef = true;

        @Name("Sausage -> Cooked Sausage")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.grill.sausage")
        public boolean sausage = true;

        @Name("Raw Kebab -> Cooked Kebab")
        @LangKey(Reference.MOD_ID + ".config.common.recipe.enabled.grill.kebab")
        public boolean kebab = true;
    }

    @SubscribeEvent
    public static void onConfigChanged(OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
        {
            ConfigManager.sync(Reference.MOD_ID, Type.INSTANCE);
            Recipes.clearLocalRecipes();
            Recipes.clearCommRecipes();
            RecipeRegistry.registerDefaultRecipes();
            RecipeRegistry.registerConfigRecipes();
            Recipes.addCommRecipesToLocal();
            Recipes.updateDataList();
        }
    }
}
