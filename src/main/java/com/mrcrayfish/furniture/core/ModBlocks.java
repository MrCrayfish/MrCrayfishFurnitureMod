package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.block.*;
import com.mrcrayfish.furniture.util.BlockNames;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks
{
    public static final Material WOOD = new Material(MaterialColor.WOOD, false, false, false, false, true, true, false, PushReaction.NORMAL);
    public static final Material STONE = new Material(MaterialColor.STONE, false, false, false, false, false, true, false, PushReaction.NORMAL);
    public static final Material WOOL = new Material(MaterialColor.WOOL, false, false, false, false, false, true, false, PushReaction.NORMAL);

    private static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<Item> ITEMS = new ArrayList<>();

    public static final Block TABLE_OAK = register(BlockNames.TABLE_OAK, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_SPRUCE = register(BlockNames.TABLE_SPRUCE, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_BIRCH = register(BlockNames.TABLE_BIRCH, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_JUNGLE = register(BlockNames.TABLE_JUNGLE, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_ACACIA = register(BlockNames.TABLE_ACACIA, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_DARK_OAK = register(BlockNames.TABLE_DARK_OAK, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_STONE = register(BlockNames.TABLE_STONE, new TableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block TABLE_GRANITE = register(BlockNames.TABLE_GRANITE, new TableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block TABLE_DIORITE = register(BlockNames.TABLE_DIORITE, new TableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block TABLE_ANDESITE = register(BlockNames.TABLE_ANDESITE, new TableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block TABLE_STRIPPED_OAK = register(BlockNames.TABLE_STRIPPED_OAK, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_STRIPPED_SPRUCE = register(BlockNames.TABLE_STRIPPED_SPRUCE, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_STRIPPED_BIRCH = register(BlockNames.TABLE_STRIPPED_BIRCH, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_STRIPPED_JUNGLE = register(BlockNames.TABLE_STRIPPED_JUNGLE, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_STRIPPED_ACACIA = register(BlockNames.TABLE_STRIPPED_ACACIA, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block TABLE_STRIPPED_DARK_OAK = register(BlockNames.TABLE_STRIPPED_DARK_OAK, new TableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_OAK = register(BlockNames.CHAIR_OAK, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_SPRUCE = register(BlockNames.CHAIR_SPRUCE, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_BIRCH = register(BlockNames.CHAIR_BIRCH, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_JUNGLE = register(BlockNames.CHAIR_JUNGLE, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_ACACIA = register(BlockNames.CHAIR_ACACIA, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_DARK_OAK = register(BlockNames.CHAIR_DARK_OAK, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_STONE = register(BlockNames.CHAIR_STONE, new ChairBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CHAIR_GRANITE = register(BlockNames.CHAIR_GRANITE, new ChairBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CHAIR_DIORITE = register(BlockNames.CHAIR_DIORITE, new ChairBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CHAIR_ANDESITE = register(BlockNames.CHAIR_ANDESITE, new ChairBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CHAIR_STRIPPED_OAK = register(BlockNames.CHAIR_STRIPPED_OAK, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_STRIPPED_SPRUCE = register(BlockNames.CHAIR_STRIPPED_SPRUCE, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_STRIPPED_BIRCH = register(BlockNames.CHAIR_STRIPPED_BIRCH, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_STRIPPED_JUNGLE = register(BlockNames.CHAIR_STRIPPED_JUNGLE, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_STRIPPED_ACACIA = register(BlockNames.CHAIR_STRIPPED_ACACIA, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CHAIR_STRIPPED_DARK_OAK = register(BlockNames.CHAIR_STRIPPED_DARK_OAK, new ChairBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_OAK = register(BlockNames.COFFEE_TABLE_OAK, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_SPRUCE = register(BlockNames.COFFEE_TABLE_SPRUCE, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_BIRCH = register(BlockNames.COFFEE_TABLE_BIRCH, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_JUNGLE = register(BlockNames.COFFEE_TABLE_JUNGLE, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_ACACIA = register(BlockNames.COFFEE_TABLE_ACACIA, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_DARK_OAK = register(BlockNames.COFFEE_TABLE_DARK_OAK, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_STONE = register(BlockNames.COFFEE_TABLE_STONE, new CoffeeTableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block COFFEE_TABLE_GRANITE = register(BlockNames.COFFEE_TABLE_GRANITE, new CoffeeTableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block COFFEE_TABLE_DIORITE = register(BlockNames.COFFEE_TABLE_DIORITE, new CoffeeTableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block COFFEE_TABLE_ANDESITE = register(BlockNames.COFFEE_TABLE_ANDESITE, new CoffeeTableBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block COFFEE_TABLE_STRIPPED_OAK = register(BlockNames.COFFEE_TABLE_STRIPPED_OAK, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_STRIPPED_SPRUCE = register(BlockNames.COFFEE_TABLE_STRIPPED_SPRUCE, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_STRIPPED_BIRCH = register(BlockNames.COFFEE_TABLE_STRIPPED_BIRCH, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_STRIPPED_JUNGLE = register(BlockNames.COFFEE_TABLE_STRIPPED_JUNGLE, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_STRIPPED_ACACIA = register(BlockNames.COFFEE_TABLE_STRIPPED_ACACIA, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block COFFEE_TABLE_STRIPPED_DARK_OAK = register(BlockNames.COFFEE_TABLE_STRIPPED_DARK_OAK, new CoffeeTableBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block CABINET_OAK = register(BlockNames.CABINET_OAK, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_SPRUCE = register(BlockNames.CABINET_SPRUCE, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_BIRCH = register(BlockNames.CABINET_BIRCH, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_JUNGLE = register(BlockNames.CABINET_JUNGLE, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_ACACIA = register(BlockNames.CABINET_ACACIA, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_DARK_OAK = register(BlockNames.CABINET_DARK_OAK, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_STONE = register(BlockNames.CABINET_STONE, new CabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CABINET_GRANITE = register(BlockNames.CABINET_GRANITE, new CabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CABINET_DIORITE = register(BlockNames.CABINET_DIORITE, new CabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CABINET_ANDESITE = register(BlockNames.CABINET_ANDESITE, new CabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block CABINET_STRIPPED_OAK = register(BlockNames.CABINET_STRIPPED_OAK, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_STRIPPED_SPRUCE = register(BlockNames.CABINET_STRIPPED_SPRUCE, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_STRIPPED_BIRCH = register(BlockNames.CABINET_STRIPPED_BIRCH, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_STRIPPED_JUNGLE = register(BlockNames.CABINET_STRIPPED_JUNGLE, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_STRIPPED_ACACIA = register(BlockNames.CABINET_STRIPPED_ACACIA, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block CABINET_STRIPPED_DARK_OAK = register(BlockNames.CABINET_STRIPPED_DARK_OAK, new CabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_OAK = register(BlockNames.BEDSIDE_CABINET_OAK, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_SPRUCE = register(BlockNames.BEDSIDE_CABINET_SPRUCE, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_BIRCH = register(BlockNames.BEDSIDE_CABINET_BIRCH, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_JUNGLE = register(BlockNames.BEDSIDE_CABINET_JUNGLE, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_ACACIA = register(BlockNames.BEDSIDE_CABINET_ACACIA, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_DARK_OAK = register(BlockNames.BEDSIDE_CABINET_DARK_OAK, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_STONE = register(BlockNames.BEDSIDE_CABINET_STONE, new BedsideCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block BEDSIDE_CABINET_GRANITE = register(BlockNames.BEDSIDE_CABINET_GRANITE, new BedsideCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block BEDSIDE_CABINET_DIORITE = register(BlockNames.BEDSIDE_CABINET_DIORITE, new BedsideCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block BEDSIDE_CABINET_ANDESITE = register(BlockNames.BEDSIDE_CABINET_ANDESITE, new BedsideCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE)));
    public static final Block BEDSIDE_CABINET_STRIPPED_OAK = register(BlockNames.BEDSIDE_CABINET_STRIPPED_OAK, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_STRIPPED_SPRUCE = register(BlockNames.BEDSIDE_CABINET_STRIPPED_SPRUCE, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_STRIPPED_BIRCH = register(BlockNames.BEDSIDE_CABINET_STRIPPED_BIRCH, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_STRIPPED_JUNGLE = register(BlockNames.BEDSIDE_CABINET_STRIPPED_JUNGLE, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_STRIPPED_ACACIA = register(BlockNames.BEDSIDE_CABINET_STRIPPED_ACACIA, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block BEDSIDE_CABINET_STRIPPED_DARK_OAK = register(BlockNames.BEDSIDE_CABINET_STRIPPED_DARK_OAK, new BedsideCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final Block DESK_OAK = register(BlockNames.DESK_OAK, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.OAK));
    public static final Block DESK_SPRUCE = register(BlockNames.DESK_SPRUCE, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.BIRCH));
    public static final Block DESK_BIRCH = register(BlockNames.DESK_BIRCH, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.SPRUCE));
    public static final Block DESK_JUNGLE = register(BlockNames.DESK_JUNGLE, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.JUNGLE));
    public static final Block DESK_ACACIA = register(BlockNames.DESK_ACACIA, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.ACACIA));
    public static final Block DESK_DARK_OAK = register(BlockNames.DESK_DARK_OAK, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.DARK_OAK));
    public static final Block DESK_STONE = register(BlockNames.DESK_STONE, new DeskBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.STONE));
    public static final Block DESK_GRANITE = register(BlockNames.DESK_GRANITE, new DeskBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.GRANITE));
    public static final Block DESK_DIORITE = register(BlockNames.DESK_DIORITE, new DeskBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.DIORITE));
    public static final Block DESK_ANDESITE = register(BlockNames.DESK_ANDESITE, new DeskBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.ANDESITE));
    public static final Block DESK_STRIPPED_OAK = register(BlockNames.DESK_STRIPPED_OAK, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_OAK));
    public static final Block DESK_STRIPPED_SPRUCE = register(BlockNames.DESK_STRIPPED_SPRUCE, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_BIRCH));
    public static final Block DESK_STRIPPED_BIRCH = register(BlockNames.DESK_STRIPPED_BIRCH, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_SPRUCE));
    public static final Block DESK_STRIPPED_JUNGLE = register(BlockNames.DESK_STRIPPED_JUNGLE, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_JUNGLE));
    public static final Block DESK_STRIPPED_ACACIA = register(BlockNames.DESK_STRIPPED_ACACIA, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_ACACIA));
    public static final Block DESK_STRIPPED_DARK_OAK = register(BlockNames.DESK_STRIPPED_DARK_OAK, new DeskBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_DARK_OAK));
    public static final Block DESK_CABINET_OAK = register(BlockNames.DESK_CABINET_OAK, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.OAK));
    public static final Block DESK_CABINET_SPRUCE = register(BlockNames.DESK_CABINET_SPRUCE, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.BIRCH));
    public static final Block DESK_CABINET_BIRCH = register(BlockNames.DESK_CABINET_BIRCH, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.SPRUCE));
    public static final Block DESK_CABINET_JUNGLE = register(BlockNames.DESK_CABINET_JUNGLE, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.JUNGLE));
    public static final Block DESK_CABINET_ACACIA = register(BlockNames.DESK_CABINET_ACACIA, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.ACACIA));
    public static final Block DESK_CABINET_DARK_OAK = register(BlockNames.DESK_CABINET_DARK_OAK, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.DARK_OAK));
    public static final Block DESK_CABINET_STONE = register(BlockNames.DESK_CABINET_STONE, new DeskCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.STONE));
    public static final Block DESK_CABINET_GRANITE = register(BlockNames.DESK_CABINET_GRANITE, new DeskCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.GRANITE));
    public static final Block DESK_CABINET_DIORITE = register(BlockNames.DESK_CABINET_DIORITE, new DeskCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.DIORITE));
    public static final Block DESK_CABINET_ANDESITE = register(BlockNames.DESK_CABINET_ANDESITE, new DeskCabinetBlock(Block.Properties.create(STONE).hardnessAndResistance(1.0F).sound(SoundType.STONE), DeskBlock.MaterialType.ANDESITE));
    public static final Block DESK_CABINET_STRIPPED_OAK = register(BlockNames.DESK_CABINET_STRIPPED_OAK, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_OAK));
    public static final Block DESK_CABINET_STRIPPED_SPRUCE = register(BlockNames.DESK_CABINET_STRIPPED_SPRUCE, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_BIRCH));
    public static final Block DESK_CABINET_STRIPPED_BIRCH = register(BlockNames.DESK_CABINET_STRIPPED_BIRCH, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_SPRUCE));
    public static final Block DESK_CABINET_STRIPPED_JUNGLE = register(BlockNames.DESK_CABINET_STRIPPED_JUNGLE, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_JUNGLE));
    public static final Block DESK_CABINET_STRIPPED_ACACIA = register(BlockNames.DESK_CABINET_STRIPPED_ACACIA, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_ACACIA));
    public static final Block DESK_CABINET_STRIPPED_DARK_OAK = register(BlockNames.DESK_CABINET_STRIPPED_DARK_OAK, new DeskCabinetBlock(Block.Properties.create(WOOD).hardnessAndResistance(1.0F).sound(SoundType.WOOD), DeskBlock.MaterialType.STRIPPED_DARK_OAK));
    public static final Block SOFA_WHITE = register(BlockNames.SOFA_WHITE, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.SNOW).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_ORANGE = register(BlockNames.SOFA_ORANGE, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.ADOBE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_MAGENTA = register(BlockNames.SOFA_MAGENTA, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.MAGENTA).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_LIGHT_BLUE = register(BlockNames.SOFA_LIGHT_BLUE, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.LIGHT_BLUE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_YELLOW = register(BlockNames.SOFA_YELLOW, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_LIME = register(BlockNames.SOFA_LIME, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.LIME).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_PINK = register(BlockNames.SOFA_PINK, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.PINK).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_GRAY = register(BlockNames.SOFA_GRAY, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.GRAY).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_LIGHT_GRAY = register(BlockNames.SOFA_LIGHT_GRAY, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_CYAN = register(BlockNames.SOFA_CYAN, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.CYAN).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_PURPLE = register(BlockNames.SOFA_PURPLE, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.PURPLE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_BLUE = register(BlockNames.SOFA_BLUE, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.BLUE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_BROWN = register(BlockNames.SOFA_BROWN, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_GREEN = register(BlockNames.SOFA_GREEN, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.GREEN).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_RED = register(BlockNames.SOFA_RED, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.RED).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_BLACK = register(BlockNames.SOFA_BLACK, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.BLACK).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block SOFA_RAINBOW = register(BlockNames.SOFA_RAINBOW, new SofaBlock(Block.Properties.create(WOOL, MaterialColor.SNOW).hardnessAndResistance(0.5F).sound(SoundType.WOOD)), new Item.Properties());
    public static final Block BLINDS_OAK = register(BlockNames.BLINDS_OAK, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_SPRUCE = register(BlockNames.BLINDS_SPRUCE, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_BIRCH = register(BlockNames.BLINDS_BIRCH, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_JUNGLE = register(BlockNames.BLINDS_JUNGLE, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_ACACIA = register(BlockNames.BLINDS_ACACIA, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_DARK_OAK = register(BlockNames.BLINDS_DARK_OAK, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_STRIPPED_OAK = register(BlockNames.BLINDS_STRIPPED_OAK, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_STRIPPED_SPRUCE = register(BlockNames.BLINDS_STRIPPED_SPRUCE, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_STRIPPED_BIRCH = register(BlockNames.BLINDS_STRIPPED_BIRCH, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_STRIPPED_JUNGLE = register(BlockNames.BLINDS_STRIPPED_JUNGLE, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_STRIPPED_ACACIA = register(BlockNames.BLINDS_STRIPPED_ACACIA, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block BLINDS_STRIPPED_DARK_OAK = register(BlockNames.BLINDS_STRIPPED_DARK_OAK, new BlindsBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_OAK = register(BlockNames.UPGRADED_FENCE_OAK, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_SPRUCE = register(BlockNames.UPGRADED_FENCE_SPRUCE, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_BIRCH = register(BlockNames.UPGRADED_FENCE_BIRCH, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_JUNGLE = register(BlockNames.UPGRADED_FENCE_JUNGLE, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_ACACIA = register(BlockNames.UPGRADED_FENCE_ACACIA, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_DARK_OAK = register(BlockNames.UPGRADED_FENCE_DARK_OAK, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_STRIPPED_OAK = register(BlockNames.UPGRADED_FENCE_STRIPPED_OAK, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_STRIPPED_SPRUCE = register(BlockNames.UPGRADED_FENCE_STRIPPED_SPRUCE, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_STRIPPED_BIRCH = register(BlockNames.UPGRADED_FENCE_STRIPPED_BIRCH, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_STRIPPED_JUNGLE = register(BlockNames.UPGRADED_FENCE_STRIPPED_JUNGLE, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_STRIPPED_ACACIA = register(BlockNames.UPGRADED_FENCE_STRIPPED_ACACIA, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_FENCE_STRIPPED_DARK_OAK = register(BlockNames.UPGRADED_FENCE_STRIPPED_DARK_OAK, new UpgradedFenceBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_OAK = register(BlockNames.UPGRADED_GATE_OAK, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_SPRUCE = register(BlockNames.UPGRADED_GATE_SPRUCE, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_BIRCH = register(BlockNames.UPGRADED_GATE_BIRCH, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_JUNGLE = register(BlockNames.UPGRADED_GATE_JUNGLE, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_ACACIA = register(BlockNames.UPGRADED_GATE_ACACIA, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_DARK_OAK = register(BlockNames.UPGRADED_GATE_DARK_OAK, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_STRIPPED_OAK = register(BlockNames.UPGRADED_GATE_STRIPPED_OAK, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_STRIPPED_SPRUCE = register(BlockNames.UPGRADED_GATE_STRIPPED_SPRUCE, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_STRIPPED_BIRCH = register(BlockNames.UPGRADED_GATE_STRIPPED_BIRCH, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_STRIPPED_JUNGLE = register(BlockNames.UPGRADED_GATE_STRIPPED_JUNGLE, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_STRIPPED_ACACIA = register(BlockNames.UPGRADED_GATE_STRIPPED_ACACIA, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block UPGRADED_GATE_STRIPPED_DARK_OAK = register(BlockNames.UPGRADED_GATE_STRIPPED_DARK_OAK, new UpgradedGateBlock(Block.Properties.create(WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_WHITE = register(BlockNames.PICKET_FENCE_WHITE, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.SNOW).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_ORANGE = register(BlockNames.PICKET_FENCE_ORANGE, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.ADOBE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_MAGENTA = register(BlockNames.PICKET_FENCE_MAGENTA, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.MAGENTA).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_LIGHT_BLUE = register(BlockNames.PICKET_FENCE_LIGHT_BLUE, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.LIGHT_BLUE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_YELLOW = register(BlockNames.PICKET_FENCE_YELLOW, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_LIME = register(BlockNames.PICKET_FENCE_LIME, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.LIME).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_PINK = register(BlockNames.PICKET_FENCE_PINK, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.PINK).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_GRAY = register(BlockNames.PICKET_FENCE_GRAY, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.GRAY).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_LIGHT_GRAY = register(BlockNames.PICKET_FENCE_LIGHT_GRAY, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_CYAN = register(BlockNames.PICKET_FENCE_CYAN, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.CYAN).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_PURPLE = register(BlockNames.PICKET_FENCE_PURPLE, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.PURPLE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_BLUE = register(BlockNames.PICKET_FENCE_BLUE, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.BLUE).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_BROWN = register(BlockNames.PICKET_FENCE_BROWN, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_GREEN = register(BlockNames.PICKET_FENCE_GREEN, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.GREEN).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_RED = register(BlockNames.PICKET_FENCE_RED, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.RED).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final Block PICKET_FENCE_BLACK = register(BlockNames.PICKET_FENCE_BLACK, new UpgradedFenceBlock(Block.Properties.create(WOOD, MaterialColor.BLACK).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));

    private static Block register(String name, Block block)
    {
        return register(name, block, new Item.Properties().group(FurnitureMod.GROUP));
    }

    private static Block register(String name, Block block, Item.Properties properties)
    {
        return register(name, block, new BlockItem(block, properties));
    }

    private static Block register(String name, Block block, BlockItem item)
    {
        block.setRegistryName(name);
        BLOCKS.add(block);
        if(block.getRegistryName() != null)
        {
            item.setRegistryName(name);
            ITEMS.add(item);
        }
        return block;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
        BLOCKS.clear();
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        ITEMS.forEach(item -> event.getRegistry().register(item));
        ITEMS.clear();
    }
}
