package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageDoorMat;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiDoorMat extends GuiScreen
{
    private int x, y, z;
    private GuiTextField messageTextField;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;

    public GuiDoorMat(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        messageTextField = new GuiDoorMatTextField(2, fontRenderer, this.width / 2 - 150, this.height / 2 - 20, 300, 20, 50, 4);
        messageTextField.setFocused(true);
        messageTextField.setMaxStringLength(100);
        doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done"));
        cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel"));
        buttonList.add(doneBtn);
        buttonList.add(cancelBtn);
        doneBtn.enabled = false;
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(fontRenderer, I18n.format("cfm.gui.doormat"), this.width / 2, this.height / 2 - 40, 16777215);
        messageTextField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        messageTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        messageTextField.textboxKeyTyped(typedChar, keyCode);
        doneBtn.enabled = messageTextField.getText().length() > 0;
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(!button.enabled)
        {
            return;
        }

        if(button.id == 0)
        {
            PacketHandler.INSTANCE.sendToServer(new MessageDoorMat(x, y, z, messageTextField.getText()));
            closeGui();
        }
        else if(button.id == 1)
        {
            closeGui();
        }
    }

    private void closeGui()
    {
        this.mc.displayGuiScreen(null);

        if(this.mc.currentScreen == null)
        {
            this.mc.setIngameFocus();
        }
    }
}