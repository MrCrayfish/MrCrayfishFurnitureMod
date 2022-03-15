package com.mrcrayfish.furniture.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class BlockSupplierItem extends BlockItem
{
    private Block block;
    private Supplier<Block> supplier;

    public BlockSupplierItem(Item.Properties properties, Block block, Supplier<Block> supplier)
    {
        super(block, properties);
        this.block = block;
        this.supplier = supplier;
    }

    @Override
    public String getDescriptionId()
    {
        return this.block.getDescriptionId();
    }

    @Override
    public Block getBlock()
    {
        return this.supplier.get();
    }

    @Override
    public void registerBlocks(Map<Block, Item> map, Item item)
    {
        map.put(this.block, item);
    }
}
