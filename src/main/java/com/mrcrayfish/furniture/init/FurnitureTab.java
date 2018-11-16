package com.mrcrayfish.furniture.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FurnitureTab extends CreativeTabs
{
    private String title = "";
    private boolean hoveringButton = false;

    public FurnitureTab(String label)
    {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(FurnitureBlocks.CHAIR_OAK);
    }

    @Override
    public String getTranslatedTabLabel()
    {
        return hoveringButton ? title : "itemGroup." + this.getTabLabel();
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setHoveringButton(boolean hoveringButton)
    {
        this.hoveringButton = hoveringButton;
    }

    //TODO create toggle buttons for categories of furniture
    /*@Override
    public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_)
    {
        //p_78018_1_.clear();
    }*/
}
