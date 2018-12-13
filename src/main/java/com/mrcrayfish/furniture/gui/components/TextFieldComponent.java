package com.mrcrayfish.furniture.gui.components;

import com.mrcrayfish.furniture.gui.GuiEditValueContainer;
import com.mrcrayfish.furniture.tileentity.IValueContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

/**
 * Author: MrCrayfish
 */
public class TextFieldComponent extends ValueComponent
{
    private GuiTextField textFieldLootTable;

    public TextFieldComponent(IValueContainer.Entry entry)
    {
        super(entry.getId(), entry.getName());
        textFieldLootTable = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer, 0, 0, GuiEditValueContainer.WIDTH - GuiEditValueContainer.PADDING * 2, 20);
        textFieldLootTable.setMaxStringLength(256);
        if(entry.getValue() != null)
        {
            textFieldLootTable.setText(entry.getValue());
        }
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY)
    {
        super.render(x, y, mouseX, mouseY);
        textFieldLootTable.x = x;
        textFieldLootTable.y = y + 10;
        textFieldLootTable.drawTextBox();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        textFieldLootTable.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        textFieldLootTable.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public String getValue()
    {
        return textFieldLootTable.getText();
    }

    @Override
    public IValueContainer.Entry toEntry()
    {
        return new IValueContainer.Entry(id, getValue());
    }
}