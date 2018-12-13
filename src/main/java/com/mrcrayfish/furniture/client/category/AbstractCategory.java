package com.mrcrayfish.furniture.client.category;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public abstract class AbstractCategory
{
    private String titleKey;
    private ItemStack icon;
    private boolean enabled = true;
    private List<Item> items = Lists.newArrayList();

    public AbstractCategory(String titleKey, ItemStack icon)
    {
        this.titleKey = titleKey;
        this.icon = icon;
        this.init();
    }

    public abstract void init();

    public String getTitleKey()
    {
        return titleKey;
    }

    public ItemStack getIcon()
    {
        return icon;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    protected void add(Item item)
    {
        items.add(item);
    }

    protected void add(Block block)
    {
        items.add(Item.getItemFromBlock(block));
    }

    public List<Item> getItems()
    {
        return items;
    }
}