package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.blocks.BlockBarStool;
import com.mrcrayfish.furniture.blocks.BlockBasin;
import com.mrcrayfish.furniture.blocks.BlockBath;
import com.mrcrayfish.furniture.blocks.BlockBedsideCabinet;
import com.mrcrayfish.furniture.blocks.BlockBench;
import com.mrcrayfish.furniture.blocks.BlockBin;
import com.mrcrayfish.furniture.blocks.BlockBirdBath;
import com.mrcrayfish.furniture.blocks.BlockBlender;
import com.mrcrayfish.furniture.blocks.BlockBlinds;
import com.mrcrayfish.furniture.blocks.BlockCabinet;
import com.mrcrayfish.furniture.blocks.BlockCandle;
import com.mrcrayfish.furniture.blocks.BlockCeilingFan;
import com.mrcrayfish.furniture.blocks.BlockCeilingLight;
import com.mrcrayfish.furniture.blocks.BlockChair;
import com.mrcrayfish.furniture.blocks.BlockChimney;
import com.mrcrayfish.furniture.blocks.BlockChoppingBoard;
import com.mrcrayfish.furniture.blocks.BlockCoffeeTable;
import com.mrcrayfish.furniture.blocks.BlockComputer;
import com.mrcrayfish.furniture.blocks.BlockCookieJar;
import com.mrcrayfish.furniture.blocks.BlockCouchJeb;
import com.mrcrayfish.furniture.blocks.BlockCouchNormal;
import com.mrcrayfish.furniture.blocks.BlockCounter;
import com.mrcrayfish.furniture.blocks.BlockCounterDrawer;
import com.mrcrayfish.furniture.blocks.BlockCounterSink;
import com.mrcrayfish.furniture.blocks.BlockCrate;
import com.mrcrayfish.furniture.blocks.BlockCup;
import com.mrcrayfish.furniture.blocks.BlockCurtainsClosed;
import com.mrcrayfish.furniture.blocks.BlockCurtainsOpen;
import com.mrcrayfish.furniture.blocks.BlockDesk;
import com.mrcrayfish.furniture.blocks.BlockDeskCabinet;
import com.mrcrayfish.furniture.blocks.BlockDigitalClock;
import com.mrcrayfish.furniture.blocks.BlockDishwasher;
import com.mrcrayfish.furniture.blocks.BlockDivingboard;
import com.mrcrayfish.furniture.blocks.BlockDoorBell;
import com.mrcrayfish.furniture.blocks.BlockDoorMat;
import com.mrcrayfish.furniture.blocks.BlockElectricFence;
import com.mrcrayfish.furniture.blocks.BlockEsky;
import com.mrcrayfish.furniture.blocks.BlockFairyLight;
import com.mrcrayfish.furniture.blocks.BlockFireAlarm;
import com.mrcrayfish.furniture.blocks.BlockFirePitOff;
import com.mrcrayfish.furniture.blocks.BlockFirePitOn;
import com.mrcrayfish.furniture.blocks.BlockFreezer;
import com.mrcrayfish.furniture.blocks.BlockFridge;
import com.mrcrayfish.furniture.blocks.BlockGrandChair;
import com.mrcrayfish.furniture.blocks.BlockGrill;
import com.mrcrayfish.furniture.blocks.BlockHedge;
import com.mrcrayfish.furniture.blocks.BlockLampOff;
import com.mrcrayfish.furniture.blocks.BlockLampOn;
import com.mrcrayfish.furniture.blocks.BlockLightSwitch;
import com.mrcrayfish.furniture.blocks.BlockMailBox;
import com.mrcrayfish.furniture.blocks.BlockMantelPiece;
import com.mrcrayfish.furniture.blocks.BlockMicrowave;
import com.mrcrayfish.furniture.blocks.BlockMirror;
import com.mrcrayfish.furniture.blocks.BlockModernSlidingDoor;
import com.mrcrayfish.furniture.blocks.BlockModernTV;
import com.mrcrayfish.furniture.blocks.BlockModernWindow;
import com.mrcrayfish.furniture.blocks.BlockOutdoorTable;
import com.mrcrayfish.furniture.blocks.BlockOven;
import com.mrcrayfish.furniture.blocks.BlockPlate;
import com.mrcrayfish.furniture.blocks.BlockPresent;
import com.mrcrayfish.furniture.blocks.BlockPrinter;
import com.mrcrayfish.furniture.blocks.BlockRangeHood;
import com.mrcrayfish.furniture.blocks.BlockShower;
import com.mrcrayfish.furniture.blocks.BlockShowerHeadOff;
import com.mrcrayfish.furniture.blocks.BlockShowerHeadOn;
import com.mrcrayfish.furniture.blocks.BlockStereo;
import com.mrcrayfish.furniture.blocks.BlockStonePath;
import com.mrcrayfish.furniture.blocks.BlockTV;
import com.mrcrayfish.furniture.blocks.BlockTable;
import com.mrcrayfish.furniture.blocks.BlockTap;
import com.mrcrayfish.furniture.blocks.BlockToaster;
import com.mrcrayfish.furniture.blocks.BlockToilet;
import com.mrcrayfish.furniture.blocks.BlockTrampoline;
import com.mrcrayfish.furniture.blocks.BlockTree;
import com.mrcrayfish.furniture.blocks.BlockWallCabinet;
import com.mrcrayfish.furniture.blocks.BlockWashingMachine;
import com.mrcrayfish.furniture.blocks.BlockWhiteFence;
import com.mrcrayfish.furniture.blocks.BlockWreath;
import com.mrcrayfish.furniture.items.ItemBath;
import com.mrcrayfish.furniture.items.ItemBlockColored;
import com.mrcrayfish.furniture.items.ItemColored;
import com.mrcrayfish.furniture.items.ItemCrate;
import com.mrcrayfish.furniture.items.ItemHedge;
import com.mrcrayfish.furniture.items.ItemLightSwitch;
import com.mrcrayfish.furniture.items.ItemModernSlidingDoor;
import com.mrcrayfish.furniture.items.ItemPresent;
import com.mrcrayfish.furniture.items.ItemWreath;

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
	public static final Block BIRD_BATH, STONE_PATH, WHITE_FENCE, TAP, MAIL_BOX_OAK;

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
	public static final Block COUNTER_DRAWER;
	public static final Block MAIL_BOX_SPRUCE, MAIL_BOX_BIRCH, MAIL_BOX_JUNGLE, MAIL_BOX_ACACIA, MAIL_BOX_DARK_OAK;

	/* Special */
	public static final Block MIRROR;

	static
	{
		COFFEE_TABLE_OAK = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_oak").setHardness(2.0f).setResistance(5.0f);
		COFFEE_TABLE_SPRUCE = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_spruce").setHardness(2.0f).setResistance(5.0f);
		COFFEE_TABLE_BIRCH = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_birch").setHardness(2.0f).setResistance(5.0f);
		COFFEE_TABLE_JUNGLE = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_jungle").setHardness(2.0f).setResistance(5.0f);
		COFFEE_TABLE_ACACIA = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_acacia").setHardness(2.0f).setResistance(5.0f);
		COFFEE_TABLE_DARK_OAK = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_dark_oak").setHardness(2.0f).setResistance(5.0f);
		COFFEE_TABLE_STONE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_stone").setHardness(1.5f).setResistance(10.0f);
		COFFEE_TABLE_GRANITE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_granite").setHardness(1.5f).setResistance(10.0f);
		COFFEE_TABLE_DIORITE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_diorite").setHardness(1.5f).setResistance(10.0f);
		COFFEE_TABLE_ANDESITE = new BlockCoffeeTable(Material.ROCK, SoundType.STONE, "coffee_table_andesite").setHardness(1.5f).setResistance(10.0f);

		TABLE_OAK = new BlockTable(Material.WOOD, SoundType.WOOD, "table_oak").setHardness(2.0f).setResistance(5.0f);
		TABLE_SPRUCE = new BlockTable(Material.WOOD, SoundType.WOOD, "table_spruce").setHardness(2.0f).setResistance(5.0f);
		TABLE_BIRCH = new BlockTable(Material.WOOD, SoundType.WOOD, "table_birch").setHardness(2.0f).setResistance(5.0f);
		TABLE_JUNGLE = new BlockTable(Material.WOOD, SoundType.WOOD, "table_jungle").setHardness(2.0f).setResistance(5.0f);
		TABLE_ACACIA = new BlockTable(Material.WOOD, SoundType.WOOD, "table_acacia").setHardness(2.0f).setResistance(5.0f);
		TABLE_DARK_OAK = new BlockTable(Material.WOOD, SoundType.WOOD, "table_dark_oak").setHardness(2.0f).setResistance(5.0f);
		TABLE_STONE = new BlockTable(Material.ROCK, SoundType.STONE, "table_stone").setHardness(1.5f).setResistance(10.0f);
		TABLE_GRANITE = new BlockTable(Material.ROCK, SoundType.STONE, "table_granite").setHardness(1.5f).setResistance(10.0f);
		TABLE_DIORITE = new BlockTable(Material.ROCK, SoundType.STONE, "table_diorite").setHardness(1.5f).setResistance(10.0f);
		TABLE_ANDESITE = new BlockTable(Material.ROCK, SoundType.STONE, "table_andesite").setHardness(1.5f).setResistance(10.0f);

		CHAIR_OAK = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_oak").setHardness(2.0f).setResistance(5.0f);
		CHAIR_SPRUCE = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_spruce").setHardness(2.0f).setResistance(5.0f);
		CHAIR_BIRCH = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_birch").setHardness(2.0f).setResistance(5.0f);
		CHAIR_JUNGLE = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_jungle").setHardness(2.0f).setResistance(5.0f);
		CHAIR_ACACIA = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_acacia").setHardness(2.0f).setResistance(5.0f);
		CHAIR_DARK_OAK = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_dark_oak").setHardness(2.0f).setResistance(5.0f);
		CHAIR_STONE = new BlockChair(Material.ROCK, SoundType.STONE, "chair_stone").setHardness(1.5f).setResistance(10.0f);
		CHAIR_GRANITE = new BlockChair(Material.ROCK, SoundType.STONE, "chair_granite").setHardness(1.5f).setResistance(10.0f);
		CHAIR_DIORITE = new BlockChair(Material.ROCK, SoundType.STONE, "chair_diorite").setHardness(1.5f).setResistance(10.0f);
		CHAIR_ANDESITE = new BlockChair(Material.ROCK, SoundType.STONE, "chair_andesite").setHardness(1.5f).setResistance(10.0f);

		FREEZER = new BlockFreezer();
		FRIDGE = new BlockFridge();

		CABINET_OAK = new BlockCabinet(Material.WOOD, SoundType.WOOD, "cabinet_oak").setHardness(2.0f).setResistance(5.0f);
		CABINET_SPRUCE = new BlockCabinet(Material.WOOD, SoundType.WOOD, "cabinet_spruce").setHardness(2.0f).setResistance(5.0f);
		CABINET_BIRCH = new BlockCabinet(Material.WOOD, SoundType.WOOD, "cabinet_birch").setHardness(2.0f).setResistance(5.0f);
		CABINET_JUNGLE = new BlockCabinet(Material.WOOD, SoundType.WOOD, "cabinet_jungle").setHardness(2.0f).setResistance(5.0f);
		CABINET_ACACIA = new BlockCabinet(Material.WOOD, SoundType.WOOD, "cabinet_acacia").setHardness(2.0f).setResistance(5.0f);
		CABINET_DARK_OAK = new BlockCabinet(Material.WOOD, SoundType.WOOD, "cabinet_dark_oak").setHardness(2.0f).setResistance(5.0f);
		CABINET_STONE = new BlockCabinet(Material.ROCK, SoundType.STONE, "cabinet_stone").setHardness(1.5f).setResistance(10.0f);
		CABINET_GRANITE = new BlockCabinet(Material.ROCK, SoundType.STONE, "cabinet_granite").setHardness(1.5f).setResistance(10.0f);
		CABINET_DIORITE = new BlockCabinet(Material.ROCK, SoundType.STONE, "cabinet_diorite").setHardness(1.5f).setResistance(10.0f);
		CABINET_ANDESITE = new BlockCabinet(Material.ROCK, SoundType.STONE, "cabinet_andesite").setHardness(1.5f).setResistance(10.0f);

		COUCH = new BlockCouchNormal();
		COUCH_JEB = new BlockCouchJeb();
		LAMP_ON = new BlockLampOn();
		LAMP_OFF = new BlockLampOff();
		BLINDS = new BlockBlinds(Material.WOOD, "blinds_open", true);
		BLINDS_CLOSED = new BlockBlinds(Material.WOOD, "blinds_closed", false);
		CURTAINS = new BlockCurtainsOpen();
		CURTAINS_CLOSED = new BlockCurtainsClosed();

		BEDSIDE_CABINET_OAK = new BlockBedsideCabinet(Material.WOOD, SoundType.WOOD, "bedside_cabinet_oak").setHardness(2.0f).setResistance(5.0f);
		BEDSIDE_CABINET_SPRUCE = new BlockBedsideCabinet(Material.WOOD, SoundType.WOOD, "bedside_cabinet_spruce").setHardness(2.0f).setResistance(5.0f);
		BEDSIDE_CABINET_BIRCH = new BlockBedsideCabinet(Material.WOOD, SoundType.WOOD, "bedside_cabinet_birch").setHardness(2.0f).setResistance(5.0f);
		BEDSIDE_CABINET_JUNGLE = new BlockBedsideCabinet(Material.WOOD, SoundType.WOOD, "bedside_cabinet_jungle").setHardness(2.0f).setResistance(5.0f);
		BEDSIDE_CABINET_ACACIA = new BlockBedsideCabinet(Material.WOOD, SoundType.WOOD, "bedside_cabinet_acacia").setHardness(2.0f).setResistance(5.0f);
		BEDSIDE_CABINET_DARK_OAK = new BlockBedsideCabinet(Material.WOOD, SoundType.WOOD, "bedside_cabinet_dark_oak").setHardness(2.0f).setResistance(5.0f);
		BEDSIDE_CABINET_STONE = new BlockBedsideCabinet(Material.ROCK, SoundType.STONE, "bedside_cabinet_stone").setHardness(1.5f).setResistance(10.0f);
		BEDSIDE_CABINET_GRANITE = new BlockBedsideCabinet(Material.ROCK, SoundType.STONE, "bedside_cabinet_granite").setHardness(1.5f).setResistance(10.0f);
		BEDSIDE_CABINET_DIORITE = new BlockBedsideCabinet(Material.ROCK, SoundType.STONE, "bedside_cabinet_diorite").setHardness(1.5f).setResistance(10.0f);
		BEDSIDE_CABINET_ANDESITE = new BlockBedsideCabinet(Material.ROCK, SoundType.STONE, "bedside_cabinet_andesite").setHardness(1.5f).setResistance(10.0f);

		OVEN = new BlockOven();
		RANGE_HOOD = new BlockRangeHood();

		HEDGE_OAK = new BlockHedge("hedge_oak");
		HEDGE_SPRUCE = new BlockHedge("hedge_spruce");
		HEDGE_BIRCH = new BlockHedge("hedge_birch");
		HEDGE_JUNGLE = new BlockHedge("hedge_jungle");
		HEDGE_ACACIA = new BlockHedge("hedge_acacia");
		HEDGE_DARK_OAK = new BlockHedge("hedge_dark_oak");

		BIRD_BATH = new BlockBirdBath();
		STONE_PATH = new BlockStonePath("stone_path");
		WHITE_FENCE = new BlockWhiteFence();
		TAP = new BlockTap();

		MAIL_BOX_OAK = new BlockMailBox(Material.WOOD, SoundType.WOOD, "mail_box").setHardness(2.0f).setResistance(5.0f);
		MAIL_BOX_SPRUCE = new BlockMailBox(Material.WOOD, SoundType.WOOD, "mail_box_spruce").setHardness(2.0f).setResistance(5.0f);
		MAIL_BOX_BIRCH = new BlockMailBox(Material.WOOD, SoundType.WOOD, "mail_box_birch").setHardness(2.0f).setResistance(5.0f);
		MAIL_BOX_JUNGLE = new BlockMailBox(Material.WOOD, SoundType.WOOD, "mail_box_jungle").setHardness(2.0f).setResistance(5.0f);
		MAIL_BOX_ACACIA = new BlockMailBox(Material.WOOD, SoundType.WOOD, "mail_box_acacia").setHardness(2.0f).setResistance(5.0f);
		MAIL_BOX_DARK_OAK = new BlockMailBox(Material.WOOD, SoundType.WOOD, "mail_box_dark_oak").setHardness(2.0f).setResistance(5.0f);

		TV = new BlockTV();
		COMPUTER = new BlockComputer();
		PRINTER = new BlockPrinter();
		ELECTRIC_FENCE = new BlockElectricFence(Material.ROCK).setUnlocalizedName("electric_fence").setRegistryName("electric_fence");

		DOOR_BELL = new BlockDoorBell(Material.WOOD, SoundType.WOOD, "door_bell").setHardness(2.0f).setResistance(5.0f);

		FIRE_ALARM_ON = new BlockFireAlarm(Material.ROCK, "fire_alarm_on", true);
		FIRE_ALARM_OFF = new BlockFireAlarm(Material.ROCK, "fire_alarm_off", false);
		CEILING_LIGHT_ON = new BlockCeilingLight(Material.GLASS, "ceiling_light_on", true);
		CEILING_LIGHT_OFF = new BlockCeilingLight(Material.GLASS, "ceiling_light_off", false);
		STEREO = new BlockStereo();
		TOILET = new BlockToilet();
		BASIN = new BlockBasin();
		WALL_CABINET = new BlockWallCabinet();
		BATH_1 = new BlockBath(Material.ROCK, "bath_bottom", false);
		BATH_2 = new BlockBath(Material.ROCK, "bath_top", true);
		SHOWER_BOTTOM = new BlockShower("shower_bottom", false);
		SHOWER_TOP = new BlockShower("shower_top", true);
		SHOWER_HEAD_OFF = new BlockShowerHeadOff();
		SHOWER_HEAD_ON = new BlockShowerHeadOn();
		BIN = new BlockBin();

		TOASTER = new BlockToaster();
		MICROWAVE = new BlockMicrowave();
		WASHING_MACHINE = new BlockWashingMachine();
		COOKIE_JAR = new BlockCookieJar();
		BLENDER = new BlockBlender();
		CUP = new BlockCup();
		PLATE = new BlockPlate();
		COUNTER = new BlockCounter();
		COUNTER_SINK = new BlockCounterSink();
		COUNTER_DRAWER = new BlockCounterDrawer();
		DISHWASHER = new BlockDishwasher();
		KITCHEN_CABINET = new BlockCabinet(Material.ROCK, SoundType.METAL, "cabinet_kitchen").setHardness(5.0f).setResistance(10.0F);

		CHOPPING_BOARD = new BlockChoppingBoard(Material.WOOD, SoundType.WOOD, "chopping_board");

		BAR_STOOL = new BlockBarStool();
		MIRROR = new BlockMirror();
		MANTEL_PIECE = new BlockMantelPiece();
		GRAND_CHAIR_TOP = new BlockGrandChair("grand_chair_top", true);
		GRAND_CHAIR_BOTTOM = new BlockGrandChair("grand_chair_bottom", false);
		CANDLE = new BlockCandle();
		CHIMNEY = new BlockChimney();

		WREATH = new BlockWreath();
		FAIRY_LIGHT = new BlockFairyLight();
		TREE_TOP = new BlockTree("tree_top", true);
		TREE_BOTTOM = new BlockTree("tree_bottom", false);
		PRESENT = new BlockPresent();

		FIRE_PIT_OFF = new BlockFirePitOff();
		FIRE_PIT_ON = new BlockFirePitOn();
		TRAMPOLINE = new BlockTrampoline();
		CRATE = new BlockCrate();
		BENCH = new BlockBench();
		TABLE_OUTDOOR = new BlockOutdoorTable();
		GRILL = new BlockGrill();
		DIVING_BOARD_BASE = new BlockDivingboard(Material.ROCK, "divingboard_base", false);
		DIVING_BOARD_PLANK = new BlockDivingboard(Material.ROCK, "divingboard_plank", true);
		DOOR_MAT = new BlockDoorMat();

		COOLER = new BlockEsky();
		MODERN_WINDOW = new BlockModernWindow();
		MODERN_SLIDING_DOOR = new BlockModernSlidingDoor();
		LIGHT_SWITCH_OFF = new BlockLightSwitch("light_switch_off");
		LIGHT_SWITCH_ON = new BlockLightSwitch("light_switch_on");
		CEILING_FAN = new BlockCeilingFan();

		DESK_OAK = new BlockDesk(Material.WOOD, SoundType.WOOD, "desk_oak").setHardness(2.0f).setResistance(5.0f);
		DESK_SPRUCE = new BlockDesk(Material.WOOD, SoundType.WOOD, "desk_spruce").setHardness(2.0f).setResistance(5.0f);
		DESK_BIRCH = new BlockDesk(Material.WOOD, SoundType.WOOD, "desk_birch").setHardness(2.0f).setResistance(5.0f);
		DESK_JUNGLE = new BlockDesk(Material.WOOD, SoundType.WOOD, "desk_jungle").setHardness(2.0f).setResistance(5.0f);
		DESK_ACACIA = new BlockDesk(Material.WOOD, SoundType.WOOD, "desk_acacia").setHardness(2.0f).setResistance(5.0f);
		DESK_DARK_OAK = new BlockDesk(Material.WOOD, SoundType.WOOD, "desk_dark_oak").setHardness(2.0f).setResistance(5.0f);
		DESK_STONE = new BlockDesk(Material.ROCK, SoundType.STONE, "desk_stone").setHardness(1.5f).setResistance(10.0f);
		DESK_GRANITE = new BlockDesk(Material.ROCK, SoundType.STONE, "desk_granite").setHardness(1.5f).setResistance(10.0f);
		DESK_DIORITE = new BlockDesk(Material.ROCK, SoundType.STONE, "desk_diorite").setHardness(1.5f).setResistance(10.0f);
		DESK_ANDESITE = new BlockDesk(Material.ROCK, SoundType.STONE, "desk_andesite").setHardness(1.5f).setResistance(10.0f);

		DESK_CABINET_OAK = new BlockDeskCabinet(Material.WOOD, SoundType.WOOD, "desk_cabinet_oak").setHardness(2.0f).setResistance(5.0f);
		DESK_CABINET_SPRUCE = new BlockDeskCabinet(Material.WOOD, SoundType.WOOD, "desk_cabinet_spruce").setHardness(2.0f).setResistance(5.0f);
		DESK_CABINET_BIRCH = new BlockDeskCabinet(Material.WOOD, SoundType.WOOD, "desk_cabinet_birch").setHardness(2.0f).setResistance(5.0f);
		DESK_CABINET_JUNGLE = new BlockDeskCabinet(Material.WOOD, SoundType.WOOD, "desk_cabinet_jungle").setHardness(2.0f).setResistance(5.0f);
		DESK_CABINET_ACACIA = new BlockDeskCabinet(Material.WOOD, SoundType.WOOD, "desk_cabinet_acacia").setHardness(2.0f).setResistance(5.0f);
		DESK_CABINET_DARK_OAK = new BlockDeskCabinet(Material.WOOD, SoundType.WOOD, "desk_cabinet_dark_oak").setHardness(2.0f).setResistance(5.0f);
		DESK_CABINET_STONE = new BlockDeskCabinet(Material.ROCK, SoundType.STONE, "desk_cabinet_stone").setHardness(1.5f).setResistance(10.0f);
		DESK_CABINET_GRANITE = new BlockDeskCabinet(Material.ROCK, SoundType.STONE, "desk_cabinet_granite").setHardness(1.5f).setResistance(10.0f);
		DESK_CABINET_DIORITE = new BlockDeskCabinet(Material.ROCK, SoundType.STONE, "desk_cabinet_diorite").setHardness(1.5f).setResistance(10.0f);
		DESK_CABINET_ANDESITE = new BlockDeskCabinet(Material.ROCK, SoundType.STONE, "desk_cabinet_andesite").setHardness(1.5f).setResistance(10.0f);

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
		registerBlock(MAIL_BOX_OAK);
		registerBlock(MAIL_BOX_SPRUCE);
		registerBlock(MAIL_BOX_BIRCH);
		registerBlock(MAIL_BOX_JUNGLE);
		registerBlock(MAIL_BOX_ACACIA);
		registerBlock(MAIL_BOX_DARK_OAK);
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
		registerBlock(COUNTER, new ItemBlockColored(COUNTER));
		registerBlock(COUNTER_SINK, new ItemBlockColored(COUNTER_SINK));
		registerBlock(COUNTER_DRAWER, new ItemBlockColored(COUNTER_DRAWER));
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
		registerBlock(DOOR_MAT, new ItemBlockColored(DOOR_MAT));
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
		if (block.getRegistryName() == null)
			throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

		RegistrationHandler.Blocks.add(block);

		if (item != null)
		{
			item.setRegistryName(block.getRegistryName());
			RegistrationHandler.Items.add(item);
		}
	}
}