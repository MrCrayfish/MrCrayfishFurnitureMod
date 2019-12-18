package com.mrcrayfish.furniture.client.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageSetDoorMatMessage;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Author: MrCrayfish
 */
public class DoorMatScreen extends Screen
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/mail_box_settings.png");

    private int xSize = 176;
    private int ySize = 69;

    private DoorMatTileEntity doorMatTileEntity;
    private TextFieldWidget nameField;
    private Button btnSave;

    public DoorMatScreen(DoorMatTileEntity doorMatTileEntity)
    {
        super(new TranslationTextComponent("gui.cfm.door_mat_message"));
        this.doorMatTileEntity = doorMatTileEntity;
    }

    @Override
    protected void init()
    {
        int guiLeft = (this.width - this.xSize) / 2;
        int guiTop = (this.height - this.ySize) / 2;

        this.nameField = new TextFieldWidget(this.font, guiLeft + 8, guiTop + 18, 160, 18, "")
        {
            @Override
            public void writeText(String textToWrite)
            {
                int lines = DoorMatScreen.this.font.listFormattedStringToWidth(this.getText() + textToWrite, 60).size();
                if(lines <= 2)
                {
                    super.writeText(textToWrite);
                }
            }
        };
        if(this.doorMatTileEntity.getMessage() != null)
        {
            this.nameField.setText(this.doorMatTileEntity.getMessage());
        }
        this.children.add(this.nameField);

        this.btnSave = this.addButton(new Button(guiLeft + 7, guiTop + 42, 79, 20, I18n.format("gui.button.cfm.save"), button ->
        {
            if(this.isValidName())
            {
                PacketHandler.instance.sendToServer(new MessageSetDoorMatMessage(this.doorMatTileEntity.getPos(), this.nameField.getText()));
                this.minecraft.player.closeScreen();
            }
        }));
        this.btnSave.active = false;

        this.addButton(new Button(guiLeft + 91, guiTop + 42, 79, 20, I18n.format("gui.button.cfm.cancel"), button ->
        {
            this.minecraft.player.closeScreen();
        }));
    }

    @Override
    public void tick()
    {
        super.tick();
        this.nameField.tick();
        this.btnSave.active = this.isValidName();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground();

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int startX = (this.width - this.xSize) / 2;
        int startY = (this.height - this.ySize) / 2;
        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);

        super.render(mouseX, mouseY, partialTicks);

        this.font.drawString(this.title.getFormattedText(), startX + 8.0F, startY + 6.0F, 0x404040);

        this.nameField.render(mouseX, mouseY, partialTicks);
    }

    private boolean isValidName()
    {
        int lines = this.font.listFormattedStringToWidth(doorMatTileEntity.getMessage(), 45).size();
        return !this.nameField.getText().equals(doorMatTileEntity.getMessage()) && !this.nameField.getText().trim().isEmpty() && lines < 2;
    }
}
