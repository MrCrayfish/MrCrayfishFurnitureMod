package com.mrcrayfish.furniture.core;

import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.block.BlockTable;
import com.mrcrayfish.furniture.util.BlockNames;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks
{
    private static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<Item> ITEMS = new ArrayList<>();

    @ObjectHolder(value = BlockNames.TABLE_OAK)
    public static final Block TABLE_OAK = null;

    @ObjectHolder(value = BlockNames.TABLE_SPRUCE)
    public static final Block TABLE_SPRUCE = null;

    @ObjectHolder(value = BlockNames.TABLE_BIRCH)
    public static final Block TABLE_BIRCH = null;

    @ObjectHolder(value = BlockNames.TABLE_JUNGLE)
    public static final Block TABLE_JUNGLE = null;

    @ObjectHolder(value = BlockNames.TABLE_ACACIA)
    public static final Block TABLE_ACACIA = null;

    @ObjectHolder(value = BlockNames.TABLE_DARK_OAK)
    public static final Block TABLE_DARK_OAK = null;

    @ObjectHolder(value = BlockNames.TABLE_STONE)
    public static final Block TABLE_STONE = null;

    @ObjectHolder(value = BlockNames.TABLE_GRANITE)
    public static final Block TABLE_GRANITE = null;

    @ObjectHolder(value = BlockNames.TABLE_DIORITE)
    public static final Block TABLE_DIORITE = null;

    @ObjectHolder(value = BlockNames.TABLE_ANDESITE)
    public static final Block TABLE_ANDESITE = null;

    static
    {
        register(BlockNames.TABLE_OAK, new BlockTable(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
        register(BlockNames.TABLE_SPRUCE, new BlockTable(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
        register(BlockNames.TABLE_BIRCH, new BlockTable(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
        register(BlockNames.TABLE_JUNGLE, new BlockTable(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
        register(BlockNames.TABLE_ACACIA, new BlockTable(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
        register(BlockNames.TABLE_DARK_OAK, new BlockTable(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
        register(BlockNames.TABLE_STONE, new BlockTable(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
        register(BlockNames.TABLE_GRANITE, new BlockTable(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
        register(BlockNames.TABLE_DIORITE, new BlockTable(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
        register(BlockNames.TABLE_ANDESITE, new BlockTable(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)));
    }

    private static void register(String name, Block block)
    {
        register(name, block, new Item.Properties());
    }

    private static void register(String name, Block block, Item.Properties properties)
    {
        register(name, block, new ItemBlock(block, properties.group(FurnitureMod.GROUP)));
    }

    private static void register(String name, Block block, ItemBlock item)
    {
        block.setRegistryName(name);
        BLOCKS.add(block);
        if(block.getRegistryName() != null)
        {
            item.setRegistryName(name);
            ITEMS.add(item);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        ITEMS.forEach(item -> event.getRegistry().register(item));
    }
}
