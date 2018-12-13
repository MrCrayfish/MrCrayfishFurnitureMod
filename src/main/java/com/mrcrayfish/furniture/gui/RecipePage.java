package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public abstract class RecipePage
{
    protected ArrayList<RecipeData> recipes = new ArrayList<>();
    public String type;

    protected int spacing = 30;

    public RecipePage(String type)
    {
        this.type = type;
    }

    public void init(List<GuiButton> buttonList)
    {
    }

    public abstract void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks);

    public abstract void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks);

    public void onShown()
    {
    }

    public void onClose()
    {
    }

    public void handleButtonClick(GuiButton button)
    {
    }

    public abstract String getTitle();

    public boolean shouldDrawTitle()
    {
        return true;
    }

    public void addRecipe(RecipeData data)
    {
        this.recipes.add(data);
    }

    public String fixName(String name)
    {
        if(name.length() > 18)
        {
            name = name.substring(0, 18) + "...";
        }
        return name;
    }

    private ItemStack drink = new ItemStack(FurnitureItems.DRINK);

    public ItemStack getDrink(String name, int red, int green, int blue)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setIntArray("Colour", new int[]{red, green, blue});
        nbt.setString("Name", name);
        drink.setTagCompound(nbt);
        return drink;
    }

    public void drawToolTip(GuiRecipeBook gui, Minecraft mc, int x, int y, int mouseX, int mouseY, ItemStack stack)
    {
        if(gui.isMouseWithin(x, y, 16, 16, mouseX, mouseY))
        {
            gui.renderToolTip(stack, mouseX, mouseY);
        }
    }
}