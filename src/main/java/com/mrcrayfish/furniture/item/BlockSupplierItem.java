package com.mrcrayfish.furniture.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class BlockSupplierItem extends BlockItem
{
    private Block block;
    private Supplier<Block> supplier;

    public BlockSupplierItem(Properties properties, Block block, Supplier<Block> supplier)
    {
        super(block, properties);
        this.block = block;
        this.supplier = supplier;
    }

    @Override
    public String getTranslationKey()
    {
        return this.block.getTranslationKey();
    }

    @Override
    public Block getBlock()
    {
        return this.supplier.get();
    }
}
