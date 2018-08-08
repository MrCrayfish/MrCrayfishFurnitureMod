package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.blocks.*;
import com.mrcrayfish.furniture.items.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class FurnitureBlocks
{
    /* Initial Furniture */
    public static final Block COFFEE_TABLE_OAK, COFFEE_TABLE_STONE;
    public static final Block TABLE_OAK, TABLE_STONE;
    public static final Block CHAIR_OAK, CHAIR_STONE;
    public static final Block FREEZER, FRIDGE;
    public static final Block CABINET_OAK, BEDSIDE_CABINET_OAK;
    public static final Block COUCH, COUCH_JEB;
    public static final Block BLINDS, BLINDS_CLOSED;
    public static final Block CURTAINS, CURTAINS_CLOSED;
    public static final Block OVEN, RANGE_HOOD;

    /* Garden Update */
    public static final Block HEDGE_OAK, HEDGE_SPRUCE, HEDGE_BIRCH, HEDGE_JUNGLE, HEDGE_ACACIA, HEDGE_DARK_OAK;
    public static final Block BIRD_BATH, STONE_PATH, WHITE_FENCE, TAP, MAIL_BOX;

    /* Electronic Update */
    public static final Block TV, COMPUTER, PRINTER, ELECTRIC_FENCE, DOOR_BELL, STEREO;
    public static final Block FIRE_ALARM_OFF, FIRE_ALARM_ON;
    public static final Block CEILING_LIGHT_OFF, CEILING_LIGHT_ON;
    public static final Block LAMP_ON, LAMP_OFF;

    /* Bathroom Update */
    public static final Block TOILET, BASIN, BATH_1, BATH_2;
    public static final Block SHOWER_BOTTOM, SHOWER_TOP, SHOWER_HEAD_OFF, SHOWER_HEAD_ON;
    public static final Block WALL_CABINET;
    public static final Block BIN;

    /* Kitchen Update */
    public static final Block TOASTER, MICROWAVE, BLENDER, WASHING_MACHINE, DISHWASHER;
    public static final Block COUNTER, COUNTER_SINK, KITCHEN_CABINET;
    public static final Block CUP, PLATE, COOKIE_JAR, BAR_STOOL, CHOPPING_BOARD;

    /* Christmas Update */
    public static final Block PRESENT, TREE_BOTTOM, TREE_TOP;
    public static final Block MANTEL_PIECE, GRAND_CHAIR_TOP, GRAND_CHAIR_BOTTOM, CANDLE, CHIMNEY, WREATH, FAIRY_LIGHT, FIRE_PIT_OFF, FIRE_PIT_ON;

    /* Outdoor Update */
    public static final Block TRAMPOLINE, CRATE, BENCH, TABLE_OUTDOOR, GRILL, DIVING_BOARD_BASE, DIVING_BOARD_PLANK, DOOR_MAT, COOLER;
    public static final Block COFFEE_TABLE_SPRUCE, COFFEE_TABLE_BIRCH, COFFEE_TABLE_JUNGLE, COFFEE_TABLE_ACACIA, COFFEE_TABLE_DARK_OAK;
    public static final Block CHAIR_SPRUCE, CHAIR_BIRCH, CHAIR_JUNGLE, CHAIR_ACACIA, CHAIR_DARK_OAK;
    public static final Block TABLE_SPRUCE, TABLE_BIRCH, TABLE_JUNGLE, TABLE_ACACIA, TABLE_DARK_OAK;
    public static final Block CABINET_SPRUCE, CABINET_BIRCH, CABINET_JUNGLE, CABINET_ACACIA, CABINET_DARK_OAK;
    public static final Block BEDSIDE_CABINET_SPRUCE, BEDSIDE_CABINET_BIRCH, BEDSIDE_CABINET_JUNGLE, BEDSIDE_CABINET_ACACIA, BEDSIDE_CABINET_DARK_OAK;

    /* More Stone Furniture Update */
    public static final Block COFFEE_TABLE_GRANITE, COFFEE_TABLE_DIORITE, COFFEE_TABLE_ANDESITE;
    public static final Block TABLE_GRANITE, TABLE_DIORITE, TABLE_ANDESITE;
    public static final Block CHAIR_GRANITE, CHAIR_DIORITE, CHAIR_ANDESITE;

    /* The Modern Update */
    public static final Block BEDSIDE_CABINET_STONE, BEDSIDE_CABINET_GRANITE, BEDSIDE_CABINET_DIORITE, BEDSIDE_CABINET_ANDESITE;
    public static final Block MODERN_WINDOW, MODERN_SLIDING_DOOR, MODERN_TV;
    public static final Block LIGHT_SWITCH_OFF, LIGHT_SWITCH_ON;
    public static final Block CEILING_FAN;
    public static final Block DESK_OAK, DESK_SPRUCE, DESK_BIRCH, DESK_JUNGLE, DESK_ACACIA, DESK_DARK_OAK, DESK_STONE, DESK_GRANITE, DESK_DIORITE, DESK_ANDESITE;
    public static final Block DESK_CABINET_OAK, DESK_CABINET_SPRUCE, DESK_CABINET_BIRCH, DESK_CABINET_JUNGLE, DESK_CABINET_ACACIA, DESK_CABINET_DARK_OAK, DESK_CABINET_STONE, DESK_CABINET_GRANITE, DESK_CABINET_DIORITE, DESK_CABINET_ANDESITE;
    public static final Block CABINET_STONE, CABINET_GRANITE, CABINET_DIORITE, CABINET_ANDESITE;
    public static final Block DIGITAL_CLOCK;

    /* Special */
    public static final Block MIRROR;

    static
    {
        COFFEE_TABLE_OAK = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_oak");
        COFFEE_TABLE_SPRUCE = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_spruce");
        COFFEE_TABLE_BIRCH = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_birch");
        COFFEE_TABLE_JUNGLE = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_jungle");
        COFFEE_TABLE_ACACIA = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_acacia");
        COFFEE_TABLE_DARK_OAK = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_dark_oak");
        COFFEE_TABLE_STONE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_stone");
        TABLE_OAK = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_oak").setRegistryName("table_oak");
        TABLE_SPRUCE = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_spruce").setRegistryName("table_spruce");
        TABLE_BIRCH = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_birch").setRegistryName("table_birch");
        TABLE_JUNGLE = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_jungle").setRegistryName("table_jungle");
        TABLE_ACACIA = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_acacia").setRegistryName("table_acacia");
        TABLE_DARK_OAK = new BlockTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_dark_oak").setRegistryName("table_dark_oak");
        TABLE_STONE = new BlockTable(Material.ROCK, SoundType.STONE).setUnlocalizedName("table_stone").setRegistryName("table_stone");
        CHAIR_OAK = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_oak").setRegistryName("chair_oak");
        CHAIR_SPRUCE = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_spruce").setRegistryName("chair_spruce");
        CHAIR_BIRCH = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_birch").setRegistryName("chair_birch");
        CHAIR_JUNGLE = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_jungle").setRegistryName("chair_jungle");
        CHAIR_ACACIA = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_acacia").setRegistryName("chair_acacia");
        CHAIR_DARK_OAK = new BlockChair(Material.WOOD, SoundType.WOOD).setUnlocalizedName("chair_dark_oak").setRegistryName("chair_dark_oak");
        CHAIR_STONE = new BlockChair(Material.ROCK, SoundType.STONE).setUnlocalizedName("chair_stone").setRegistryName("chair_stone");
        FREEZER = new BlockFreezer(Material.ROCK).setUnlocalizedName("freezer").setRegistryName("freezer");
        FRIDGE = new BlockFridge(Material.ROCK).setUnlocalizedName("fridge").setRegistryName("fridge");
        CABINET_OAK = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_oak").setRegistryName("cabinet_oak");
        CABINET_SPRUCE = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_spruce").setRegistryName("cabinet_spruce");
        CABINET_BIRCH = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_birch").setRegistryName("cabinet_birch");
        CABINET_JUNGLE = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_jungle").setRegistryName("cabinet_jungle");
        CABINET_ACACIA = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_acacia").setRegistryName("cabinet_acacia");
        CABINET_DARK_OAK = new BlockCabinet(Material.WOOD).setUnlocalizedName("cabinet_dark_oak").setRegistryName("cabinet_dark_oak");
        COUCH = new BlockCouchNormal().setUnlocalizedName("couch").setRegistryName("couch");
        COUCH_JEB = new BlockCouchJeb().setUnlocalizedName("couch_jeb").setRegistryName("couch_jeb");
        LAMP_ON = new BlockLampOn(Material.GLASS).setUnlocalizedName("lamp_on").setRegistryName("lamp_on");
        LAMP_OFF = new BlockLamp(Material.GLASS, false).setUnlocalizedName("lamp_off").setRegistryName("lamp_off");
        BLINDS = new BlockBlinds(Material.WOOD, true).setUnlocalizedName("blinds_open").setRegistryName("blinds_open");
        BLINDS_CLOSED = new BlockBlinds(Material.WOOD, false).setUnlocalizedName("blinds_closed").setRegistryName("blinds_closed");
        CURTAINS = new BlockCurtainsOpen(Material.CLOTH).setUnlocalizedName("curtains_open").setRegistryName("curtains_open");
        CURTAINS_CLOSED = new BlockCurtainsClosed(Material.CLOTH).setUnlocalizedName("curtains_closed").setRegistryName("curtains_closed");
        BEDSIDE_CABINET_OAK = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_oak").setRegistryName("bedside_cabinet_oak");
        BEDSIDE_CABINET_SPRUCE = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_spruce").setRegistryName("bedside_cabinet_spruce");
        BEDSIDE_CABINET_BIRCH = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_birch").setRegistryName("bedside_cabinet_birch");
        BEDSIDE_CABINET_JUNGLE = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_jungle").setRegistryName("bedside_cabinet_jungle");
        BEDSIDE_CABINET_ACACIA = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_acacia").setRegistryName("bedside_cabinet_acacia");
        BEDSIDE_CABINET_DARK_OAK = new BlockBedsideCabinet(Material.WOOD).setUnlocalizedName("bedside_cabinet_dark_oak").setRegistryName("bedside_cabinet_dark_oak");
        OVEN = new BlockOven(Material.ROCK).setUnlocalizedName("oven").setRegistryName("oven");
        RANGE_HOOD = new BlockRangeHood(Material.ROCK).setUnlocalizedName("range_hood").setRegistryName("range_hood");
        HEDGE_OAK = new BlockHedge().setUnlocalizedName("hedge_oak").setRegistryName("hedge_oak");
        HEDGE_SPRUCE = new BlockHedge().setUnlocalizedName("hedge_spruce").setRegistryName("hedge_spruce");
        HEDGE_BIRCH = new BlockHedge().setUnlocalizedName("hedge_birch").setRegistryName("hedge_birch");
        HEDGE_JUNGLE = new BlockHedge().setUnlocalizedName("hedge_jungle").setRegistryName("hedge_jungle");
        HEDGE_ACACIA = new BlockHedge().setUnlocalizedName("hedge_acacia").setRegistryName("hedge_acacia");
        HEDGE_DARK_OAK = new BlockHedge().setUnlocalizedName("hedge_dark_oak").setRegistryName("hedge_dark_oak");
        BIRD_BATH = new BlockBirdBath(Material.ROCK).setUnlocalizedName("bird_bath").setRegistryName("bird_bath");
        STONE_PATH = new BlockStonePath(Material.ROCK).setUnlocalizedName("stone_path").setRegistryName("stone_path");
        WHITE_FENCE = new BlockWhiteFence(Material.WOOD).setUnlocalizedName("white_fence").setRegistryName("white_fence");
        TAP = new BlockTap(Material.ROCK).setUnlocalizedName("tap").setRegistryName("tap");
        MAIL_BOX = new BlockMailBox(Material.WOOD).setUnlocalizedName("mail_box").setRegistryName("mail_box");
        TV = new BlockTV(Material.WOOD).setUnlocalizedName("tv").setRegistryName("tv");
        COMPUTER = new BlockComputer(Material.IRON).setUnlocalizedName("computer").setRegistryName("computer");
        PRINTER = new BlockPrinter(Material.IRON).setUnlocalizedName("printer").setRegistryName("printer");
        ELECTRIC_FENCE = new BlockElectricFence(Material.ROCK).setUnlocalizedName("electric_fence").setRegistryName("electric_fence");
        DOOR_BELL = new BlockDoorBell(Material.WOOD).setUnlocalizedName("door_bell").setRegistryName("door_bell");
        FIRE_ALARM_ON = new BlockFireAlarm(Material.ROCK, true).setUnlocalizedName("fire_alarm_on").setRegistryName("fire_alarm_on");
        FIRE_ALARM_OFF = new BlockFireAlarm(Material.ROCK, false).setUnlocalizedName("fire_alarm_off").setRegistryName("fire_alarm_off");
        CEILING_LIGHT_ON = new BlockCeilingLight(Material.GLASS, true).setUnlocalizedName("ceiling_light_on").setRegistryName("ceiling_light_on");
        CEILING_LIGHT_OFF = new BlockCeilingLight(Material.GLASS, false).setUnlocalizedName("ceiling_light_off").setRegistryName("ceiling_light_off");
        STEREO = new BlockStereo(Material.WOOD).setUnlocalizedName("stereo").setRegistryName("stereo");
        TOILET = new BlockToilet(Material.ROCK).setUnlocalizedName("toilet").setRegistryName("toilet");
        BASIN = new BlockBasin(Material.ROCK).setUnlocalizedName("basin").setRegistryName("basin");
        WALL_CABINET = new BlockWallCabinet(Material.ROCK).setUnlocalizedName("wall_cabinet").setRegistryName("wall_cabinet");
        BATH_1 = new BlockBath(Material.ROCK, false).setUnlocalizedName("bath_bottom").setRegistryName("bath_bottom");
        BATH_2 = new BlockBath(Material.ROCK, true).setUnlocalizedName("bath_top").setRegistryName("bath_top");
        SHOWER_BOTTOM = new BlockShower(Material.ROCK, false).setUnlocalizedName("shower_bottom").setRegistryName("shower_bottom");
        SHOWER_TOP = new BlockShower(Material.ROCK, true).setUnlocalizedName("shower_top").setRegistryName("shower_top");
        SHOWER_HEAD_OFF = new BlockShowerHeadOff(Material.ROCK).setUnlocalizedName("shower_head_off").setRegistryName("shower_head_off");
        SHOWER_HEAD_ON = new BlockShowerHeadOn(Material.ROCK).setUnlocalizedName("shower_head_on").setRegistryName("shower_head_on");
        BIN = new BlockBin(Material.ROCK).setUnlocalizedName("bin").setRegistryName("bin");
        TREE_TOP = new BlockTree(Material.WOOD, true).setUnlocalizedName("tree_top").setRegistryName("tree_top");
        TREE_BOTTOM = new BlockTree(Material.WOOD, false).setUnlocalizedName("tree_bottom").setRegistryName("tree_bottom");
        PRESENT = new BlockPresent(Material.CLOTH).setUnlocalizedName("present").setRegistryName("present");
        TOASTER = new BlockToaster(Material.WOOD).setUnlocalizedName("toaster").setRegistryName("toaster");
        MICROWAVE = new BlockMicrowave(Material.WOOD).setUnlocalizedName("microwave").setRegistryName("microwave");
        WASHING_MACHINE = new BlockWashingMachine(Material.ROCK).setUnlocalizedName("washing_machine").setRegistryName("washing_machine");
        COOKIE_JAR = new BlockCookieJar(Material.GLASS).setUnlocalizedName("cookie_jar").setRegistryName("cookie_jar");
        BLENDER = new BlockBlender(Material.GLASS).setUnlocalizedName("blender").setRegistryName("blender");
        CUP = new BlockCup(Material.GLASS).setUnlocalizedName("cup").setRegistryName("cup");
        PLATE = new BlockPlate(Material.GLASS).setUnlocalizedName("plate").setRegistryName("plate");
        COUNTER = new BlockCounter(Material.WOOD).setUnlocalizedName("counter").setRegistryName("counter");
        COUNTER_SINK = new BlockCounterSink(Material.WOOD).setUnlocalizedName("counter_sink").setRegistryName("counter_sink");
        DISHWASHER = new BlockDishwasher(Material.ROCK).setUnlocalizedName("dishwasher").setRegistryName("dishwasher");
        KITCHEN_CABINET = new BlockCabinetKitchen(Material.WOOD).setUnlocalizedName("cabinet_kitchen").setRegistryName("cabinet_kitchen");
        CHOPPING_BOARD = new BlockChoppingBoard(Material.WOOD).setUnlocalizedName("chopping_board").setRegistryName("chopping_board");
        BAR_STOOL = new BlockBarStool(Material.WOOD).setUnlocalizedName("bar_stool").setRegistryName("bar_stool");
        MIRROR = new BlockMirror(Material.GLASS).setUnlocalizedName("mirror").setRegistryName("mirror");
        MANTEL_PIECE = new BlockMantelPiece(Material.ROCK).setUnlocalizedName("mantel_piece").setRegistryName("mantel_piece");
        GRAND_CHAIR_TOP = new BlockGrandChair(Material.WOOD, true).setUnlocalizedName("grand_chair_top").setRegistryName("grand_chair_top");
        GRAND_CHAIR_BOTTOM = new BlockGrandChair(Material.WOOD, false).setUnlocalizedName("grand_chair_bottom").setRegistryName("grand_chair_bottom");
        CANDLE = new BlockCandle(Material.ROCK).setUnlocalizedName("candle").setRegistryName("candle");
        CHIMNEY = new BlockChimney(Material.ROCK).setUnlocalizedName("chimney").setRegistryName("chimney");
        WREATH = new BlockWreath(Material.LEAVES).setUnlocalizedName("wreath").setRegistryName("wreath");
        FAIRY_LIGHT = new BlockFairyLight(Material.GLASS).setUnlocalizedName("fairy_light").setRegistryName("fairy_light");
        FIRE_PIT_OFF = new BlockFirePitOff(Material.WOOD).setUnlocalizedName("fire_pit_off").setRegistryName("fire_pit_off");
        FIRE_PIT_ON = new BlockFirePitOn(Material.WOOD).setUnlocalizedName("fire_pit_on").setRegistryName("fire_pit_on");
        TRAMPOLINE = new BlockTrampoline(Material.ROCK).setUnlocalizedName("trampoline").setRegistryName("trampoline");
        CRATE = new BlockCrate(Material.WOOD).setUnlocalizedName("crate").setRegistryName("crate");
        BENCH = new BlockBench(Material.WOOD).setUnlocalizedName("bench").setRegistryName("bench");
        TABLE_OUTDOOR = new BlockOutdoorTable(Material.WOOD, SoundType.WOOD).setUnlocalizedName("table_outdoor").setRegistryName("table_outdoor");
        GRILL = new BlockGrill(Material.ANVIL).setUnlocalizedName("grill").setRegistryName("grill");
        DIVING_BOARD_BASE = new BlockDivingboard(Material.ROCK, false).setUnlocalizedName("divingboard_base").setRegistryName("divingboard_base");
        DIVING_BOARD_PLANK = new BlockDivingboard(Material.ROCK, true).setUnlocalizedName("divingboard_plank").setRegistryName("divingboard_plank");
        DOOR_MAT = new BlockDoorMat(Material.CLOTH).setUnlocalizedName("door_mat").setRegistryName("door_mat");
        COOLER = new BlockEsky(Material.CLAY).setUnlocalizedName("esky").setRegistryName("esky");
        COFFEE_TABLE_GRANITE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_granite");
        COFFEE_TABLE_DIORITE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_diorite");
        COFFEE_TABLE_ANDESITE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_andesite");
        TABLE_GRANITE = new BlockTable(Material.ROCK, SoundType.STONE).setUnlocalizedName("table_granite").setRegistryName("table_granite");
        TABLE_DIORITE = new BlockTable(Material.ROCK, SoundType.STONE).setUnlocalizedName("table_diorite").setRegistryName("table_diorite");
        TABLE_ANDESITE = new BlockTable(Material.ROCK, SoundType.STONE).setUnlocalizedName("table_andesite").setRegistryName("table_andesite");
        CHAIR_GRANITE = new BlockChair(Material.ROCK, SoundType.STONE).setUnlocalizedName("chair_granite").setRegistryName("chair_granite");
        CHAIR_DIORITE = new BlockChair(Material.ROCK, SoundType.STONE).setUnlocalizedName("chair_diorite").setRegistryName("chair_diorite");
        CHAIR_ANDESITE = new BlockChair(Material.ROCK, SoundType.STONE).setUnlocalizedName("chair_andesite").setRegistryName("chair_andesite");
        BEDSIDE_CABINET_STONE = new BlockBedsideCabinet(Material.ROCK).setUnlocalizedName("bedside_cabinet_stone").setRegistryName("bedside_cabinet_stone");
        BEDSIDE_CABINET_GRANITE = new BlockBedsideCabinet(Material.ROCK).setUnlocalizedName("bedside_cabinet_granite").setRegistryName("bedside_cabinet_granite");
        BEDSIDE_CABINET_DIORITE = new BlockBedsideCabinet(Material.ROCK).setUnlocalizedName("bedside_cabinet_diorite").setRegistryName("bedside_cabinet_diorite");
        BEDSIDE_CABINET_ANDESITE = new BlockBedsideCabinet(Material.ROCK).setUnlocalizedName("bedside_cabinet_andesite").setRegistryName("bedside_cabinet_andesite");
        MODERN_WINDOW = new BlockModernWindow().setUnlocalizedName("modern_window").setRegistryName("modern_window");
        MODERN_SLIDING_DOOR = new BlockModernSlidingDoor().setUnlocalizedName("modern_sliding_door").setRegistryName("modern_sliding_door");
        LIGHT_SWITCH_OFF = new BlockLightSwitch().setUnlocalizedName("light_switch_off").setRegistryName("light_switch_off");
        LIGHT_SWITCH_ON = new BlockLightSwitch().setUnlocalizedName("light_switch_on").setRegistryName("light_switch_on");
        CEILING_FAN = new BlockCeilingFan().setUnlocalizedName("ceiling_fan").setRegistryName("ceiling_fan");
        DESK_OAK = new BlockDesk(Material.WOOD).setUnlocalizedName("desk_oak").setRegistryName("desk_oak");
        DESK_SPRUCE = new BlockDesk(Material.WOOD).setUnlocalizedName("desk_spruce").setRegistryName("desk_spruce");
        DESK_BIRCH = new BlockDesk(Material.WOOD).setUnlocalizedName("desk_birch").setRegistryName("desk_birch");
        DESK_JUNGLE = new BlockDesk(Material.WOOD).setUnlocalizedName("desk_jungle").setRegistryName("desk_jungle");
        DESK_ACACIA = new BlockDesk(Material.WOOD).setUnlocalizedName("desk_acacia").setRegistryName("desk_acacia");
        DESK_DARK_OAK = new BlockDesk(Material.WOOD).setUnlocalizedName("desk_dark_oak").setRegistryName("desk_dark_oak");
        DESK_STONE = new BlockDesk(Material.ROCK).setUnlocalizedName("desk_stone").setRegistryName("desk_stone");
        DESK_GRANITE = new BlockDesk(Material.ROCK).setUnlocalizedName("desk_granite").setRegistryName("desk_granite");
        DESK_DIORITE = new BlockDesk(Material.ROCK).setUnlocalizedName("desk_diorite").setRegistryName("desk_diorite");
        DESK_ANDESITE = new BlockDesk(Material.ROCK).setUnlocalizedName("desk_andesite").setRegistryName("desk_andesite");
        DESK_CABINET_OAK = new BlockDeskCabinet(Material.WOOD).setUnlocalizedName("desk_cabinet_oak").setRegistryName("desk_cabinet_oak");
        DESK_CABINET_SPRUCE = new BlockDeskCabinet(Material.WOOD).setUnlocalizedName("desk_cabinet_spruce").setRegistryName("desk_cabinet_spruce");
        DESK_CABINET_BIRCH = new BlockDeskCabinet(Material.WOOD).setUnlocalizedName("desk_cabinet_birch").setRegistryName("desk_cabinet_birch");
        DESK_CABINET_JUNGLE = new BlockDeskCabinet(Material.WOOD).setUnlocalizedName("desk_cabinet_jungle").setRegistryName("desk_cabinet_jungle");
        DESK_CABINET_ACACIA = new BlockDeskCabinet(Material.WOOD).setUnlocalizedName("desk_cabinet_acacia").setRegistryName("desk_cabinet_acacia");
        DESK_CABINET_DARK_OAK = new BlockDeskCabinet(Material.WOOD).setUnlocalizedName("desk_cabinet_dark_oak").setRegistryName("desk_cabinet_dark_oak");
        DESK_CABINET_STONE = new BlockDeskCabinet(Material.ROCK).setUnlocalizedName("desk_cabinet_stone").setRegistryName("desk_cabinet_stone");
        DESK_CABINET_GRANITE = new BlockDeskCabinet(Material.ROCK).setUnlocalizedName("desk_cabinet_granite").setRegistryName("desk_cabinet_granite");
        DESK_CABINET_DIORITE = new BlockDeskCabinet(Material.ROCK).setUnlocalizedName("desk_cabinet_diorite").setRegistryName("desk_cabinet_diorite");
        DESK_CABINET_ANDESITE = new BlockDeskCabinet(Material.ROCK).setUnlocalizedName("desk_cabinet_andesite").setRegistryName("desk_cabinet_andesite");
        CABINET_STONE = new BlockCabinet(Material.ROCK).setUnlocalizedName("cabinet_stone").setRegistryName("cabinet_stone");
        CABINET_GRANITE = new BlockCabinet(Material.ROCK).setUnlocalizedName("cabinet_granite").setRegistryName("cabinet_granite");
        CABINET_DIORITE = new BlockCabinet(Material.ROCK).setUnlocalizedName("cabinet_diorite").setRegistryName("cabinet_diorite");
        CABINET_ANDESITE = new BlockCabinet(Material.ROCK).setUnlocalizedName("cabinet_andesite").setRegistryName("cabinet_andesite");
        DIGITAL_CLOCK = new BlockDigitalClock();
        MODERN_TV = new BlockModernTV();
    }

    public static void register()
    {
        registerBlock(COFFEE_TABLE_OAK);
        registerBlock(COFFEE_TABLE_SPRUCE);
        registerBlock(COFFEE_TABLE_BIRCH);
        registerBlock(COFFEE_TABLE_JUNGLE);
        registerBlock(COFFEE_TABLE_ACACIA);
        registerBlock(COFFEE_TABLE_DARK_OAK);
        registerBlock(COFFEE_TABLE_STONE);
        registerBlock(COFFEE_TABLE_GRANITE);
        registerBlock(COFFEE_TABLE_DIORITE);
        registerBlock(COFFEE_TABLE_ANDESITE);
        registerBlock(TABLE_OAK);
        registerBlock(TABLE_SPRUCE);
        registerBlock(TABLE_BIRCH);
        registerBlock(TABLE_JUNGLE);
        registerBlock(TABLE_ACACIA);
        registerBlock(TABLE_DARK_OAK);
        registerBlock(TABLE_STONE);
        registerBlock(TABLE_GRANITE);
        registerBlock(TABLE_DIORITE);
        registerBlock(TABLE_ANDESITE);
        registerBlock(CHAIR_OAK);
        registerBlock(CHAIR_SPRUCE);
        registerBlock(CHAIR_BIRCH);
        registerBlock(CHAIR_JUNGLE);
        registerBlock(CHAIR_ACACIA);
        registerBlock(CHAIR_DARK_OAK);
        registerBlock(CHAIR_STONE);
        registerBlock(CHAIR_GRANITE);
        registerBlock(CHAIR_DIORITE);
        registerBlock(CHAIR_ANDESITE);
        registerBlock(FREEZER);
        registerBlock(FRIDGE, null);
        registerBlock(CABINET_OAK);
        registerBlock(CABINET_SPRUCE);
        registerBlock(CABINET_BIRCH);
        registerBlock(CABINET_JUNGLE);
        registerBlock(CABINET_ACACIA);
        registerBlock(CABINET_DARK_OAK);
        registerBlock(CABINET_STONE);
        registerBlock(CABINET_GRANITE);
        registerBlock(CABINET_DIORITE);
        registerBlock(CABINET_ANDESITE);
        registerBlock(COUCH, new ItemBlockColored(COUCH));
        registerBlock(COUCH_JEB);
        registerBlock(LAMP_ON, null);
        registerBlock(LAMP_OFF, new ItemBlockColored(LAMP_OFF));
        registerBlock(BLINDS);
        registerBlock(BLINDS_CLOSED, null);
        registerBlock(CURTAINS);
        registerBlock(CURTAINS_CLOSED, null);
        registerBlock(BEDSIDE_CABINET_OAK);
        registerBlock(BEDSIDE_CABINET_SPRUCE);
        registerBlock(BEDSIDE_CABINET_BIRCH);
        registerBlock(BEDSIDE_CABINET_JUNGLE);
        registerBlock(BEDSIDE_CABINET_ACACIA);
        registerBlock(BEDSIDE_CABINET_DARK_OAK);
        registerBlock(BEDSIDE_CABINET_STONE);
        registerBlock(BEDSIDE_CABINET_GRANITE);
        registerBlock(BEDSIDE_CABINET_DIORITE);
        registerBlock(BEDSIDE_CABINET_ANDESITE);
        registerBlock(OVEN);
        registerBlock(RANGE_HOOD);
        registerBlock(HEDGE_OAK, new ItemHedge(HEDGE_OAK));
        registerBlock(HEDGE_SPRUCE, new ItemHedge(HEDGE_SPRUCE));
        registerBlock(HEDGE_BIRCH, new ItemHedge(HEDGE_BIRCH));
        registerBlock(HEDGE_JUNGLE, new ItemHedge(HEDGE_JUNGLE));
        registerBlock(HEDGE_ACACIA, new ItemHedge(HEDGE_ACACIA));
        registerBlock(HEDGE_DARK_OAK, new ItemHedge(HEDGE_DARK_OAK));
        registerBlock(BIRD_BATH);
        registerBlock(STONE_PATH);
        registerBlock(WHITE_FENCE);
        registerBlock(TAP);
        registerBlock(MAIL_BOX);
        registerBlock(TV);
        registerBlock(COMPUTER);
        registerBlock(PRINTER);
        registerBlock(ELECTRIC_FENCE);
        registerBlock(DOOR_BELL);
        registerBlock(FIRE_ALARM_OFF);
        registerBlock(FIRE_ALARM_ON);
        registerBlock(CEILING_LIGHT_OFF);
        registerBlock(CEILING_LIGHT_ON, null);
        registerBlock(STEREO);
        registerBlock(TOILET);
        registerBlock(BASIN);
        registerBlock(WALL_CABINET);
        registerBlock(BATH_1, new ItemBath(BATH_1));
        registerBlock(BATH_2, null);
        registerBlock(SHOWER_BOTTOM);
        registerBlock(SHOWER_TOP, null);
        registerBlock(SHOWER_HEAD_OFF);
        registerBlock(SHOWER_HEAD_ON, null);
        registerBlock(BIN);
        registerBlock(PRESENT, new ItemPresent(PRESENT));
        registerBlock(TREE_TOP, null);
        registerBlock(TREE_BOTTOM, new ItemWreath(TREE_BOTTOM));
        registerBlock(TOASTER);
        registerBlock(MICROWAVE);
        registerBlock(WASHING_MACHINE);
        registerBlock(COOKIE_JAR);
        registerBlock(BLENDER);
        registerBlock(CUP, null);
        registerBlock(PLATE);
        registerBlock(COUNTER);
        registerBlock(COUNTER_SINK);
        registerBlock(DISHWASHER);
        registerBlock(KITCHEN_CABINET);
        registerBlock(CHOPPING_BOARD);
        registerBlock(BAR_STOOL, new ItemBlockColored(BAR_STOOL));
        registerBlock(MIRROR);
        registerBlock(MANTEL_PIECE);
        registerBlock(GRAND_CHAIR_TOP, null);
        registerBlock(GRAND_CHAIR_BOTTOM);
        registerBlock(CANDLE);
        registerBlock(CHIMNEY);
        registerBlock(WREATH, new ItemWreath(WREATH));
        registerBlock(FAIRY_LIGHT);
        registerBlock(FIRE_PIT_OFF, null);
        registerBlock(FIRE_PIT_ON);
        registerBlock(TRAMPOLINE);
        registerBlock(CRATE, new ItemCrate(CRATE));
        registerBlock(BENCH);
        registerBlock(TABLE_OUTDOOR);
        registerBlock(GRILL);
        registerBlock(DIVING_BOARD_BASE, new ItemBath(DIVING_BOARD_BASE));
        registerBlock(DIVING_BOARD_PLANK, null);
        registerBlock(DOOR_MAT);
        registerBlock(COOLER);
        registerBlock(MODERN_WINDOW);
        registerBlock(MODERN_SLIDING_DOOR, new ItemModernSlidingDoor(MODERN_SLIDING_DOOR));
        registerBlock(LIGHT_SWITCH_OFF, new ItemLightSwitch(LIGHT_SWITCH_OFF));
        registerBlock(LIGHT_SWITCH_ON, null);
        registerBlock(CEILING_FAN);
        registerBlock(DESK_OAK);
        registerBlock(DESK_SPRUCE);
        registerBlock(DESK_BIRCH);
        registerBlock(DESK_JUNGLE);
        registerBlock(DESK_ACACIA);
        registerBlock(DESK_DARK_OAK);
        registerBlock(DESK_STONE);
        registerBlock(DESK_GRANITE);
        registerBlock(DESK_DIORITE);
        registerBlock(DESK_ANDESITE);
        registerBlock(DESK_CABINET_OAK);
        registerBlock(DESK_CABINET_SPRUCE);
        registerBlock(DESK_CABINET_BIRCH);
        registerBlock(DESK_CABINET_JUNGLE);
        registerBlock(DESK_CABINET_ACACIA);
        registerBlock(DESK_CABINET_DARK_OAK);
        registerBlock(DESK_CABINET_STONE);
        registerBlock(DESK_CABINET_GRANITE);
        registerBlock(DESK_CABINET_DIORITE);
        registerBlock(DESK_CABINET_ANDESITE);
        registerBlock(DIGITAL_CLOCK, new ItemColored(DIGITAL_CLOCK));
        registerBlock(MODERN_TV);
    }

    private static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item)
    {
        if(block.getRegistryName() == null)
            throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

        RegistrationHandler.Blocks.add(block);

        if(item != null)
        {
            item.setRegistryName(block.getRegistryName());
            RegistrationHandler.Items.add(item);
        }
    }
}
