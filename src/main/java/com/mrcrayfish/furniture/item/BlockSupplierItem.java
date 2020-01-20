package com.mrcrayfish.furniture.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class BlockSupplierItem extends BlockItem
{
    private Supplier<Block> supplier;

    public BlockSupplierItem(Properties properties, Supplier<Block> supplier)
    {
        super(null, properties);
        this.supplier = supplier;
    }

    @Override
    public Block getBlock()
    {
        return this.supplier.get();
    }
}
