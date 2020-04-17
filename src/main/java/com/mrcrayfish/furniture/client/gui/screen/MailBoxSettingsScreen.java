package com.mrcrayfish.furniture.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageOpenMailBox;
import com.mrcrayfish.furniture.network.message.MessageSetMailBoxName;
import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Author: MrCrayfish
 */
public class MailBoxSettingsScreen extends Screen
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/mail_box_settings.png");

    private int xSize = 176;
    private int ySize = 69;

    private MailBoxTileEntity mailBoxTileEntity;
    private TextFieldWidget nameField;
    private Button btnSave;

    public MailBoxSettingsScreen(MailBoxTileEntity mailBoxTileEntity)
    {
        super(new TranslationTextComponent("gui.cfm.mail_box_settings"));
        this.mailBoxTileEntity = mailBoxTileEntity;
    }

    @Override
    protected void init()
    {
        int guiLeft = (this.width - this.xSize) / 2;
        int guiTop = (this.height - this.ySize) / 2;

        this.nameField = new TextFieldWidget(this.font, guiLeft + 8, guiTop + 18, 160, 18, "");
        if(this.mailBoxTileEntity.getMailBoxName() != null)
        {
            this.nameField.setText(this.mailBoxTileEntity.getMailBoxName());
        }
        this.children.add(this.nameField);

        this.btnSave = this.addButton(new Button(guiLeft + 7, guiTop + 42, 79, 20, I18n.format("gui.button.cfm.save"), button ->
        {
            if(this.isValidName())
            {
                PacketHandler.instance.sendToServer(new MessageSetMailBoxName(mailBoxTileEntity.getId(), this.nameField.getText(), mailBoxTileEntity.getPos()));
            }
        }));
        this.btnSave.active = false;

        this.addButton(new Button(guiLeft + 91, guiTop + 42, 79, 20, I18n.format("gui.button.cfm.back"), button ->
        {
            PacketHandler.instance.sendToServer(new MessageOpenMailBox(mailBoxTileEntity.getPos()));
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
        return !this.nameField.getText().equals(mailBoxTileEntity.getMailBoxName()) && !this.nameField.getText().trim().isEmpty();
    }
}
