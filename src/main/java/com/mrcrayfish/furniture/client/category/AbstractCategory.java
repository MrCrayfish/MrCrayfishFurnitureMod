package com.mrcrayfish.furniture.client.category;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public abstract class AbstractCategory
{
    private String title;
    private ItemStack icon;
    private boolean enabled = true;
    private List<Item> items = Lists.newArrayList();

    public AbstractCategory(String title, ItemStack icon)
    {
        this.title = title;
        this.icon = icon;
        this.init();
    }

    public abstract void init();

    public String getTitle()
    {
        return title;
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
