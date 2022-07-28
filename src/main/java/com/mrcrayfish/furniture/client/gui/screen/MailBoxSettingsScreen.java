package com.mrcrayfish.furniture.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.C2SMessageOpenMailBox;
import com.mrcrayfish.furniture.network.message.C2SMessageSetMailBoxName;
import com.mrcrayfish.furniture.tileentity.MailBoxBlockEntity;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class MailBoxSettingsScreen extends Screen
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/mail_box_settings.png");

    private final int xSize = 176;
    private final int ySize = 69;

    private final MailBoxBlockEntity mailBoxBlockEntity;
    private EditBox nameField;
    private Button btnSave;

    public MailBoxSettingsScreen(MailBoxBlockEntity mailBoxBlockEntity)
    {
        super(Component.translatable("gui.cfm.mail_box_settings"));
        this.mailBoxBlockEntity = mailBoxBlockEntity;
    }

    @Override
    protected void init()
    {
        int guiLeft = (this.width - this.xSize) / 2;
        int guiTop = (this.height - this.ySize) / 2;

        this.nameField = new EditBox(this.font, guiLeft + 8, guiTop + 18, 160, 18, CommonComponents.EMPTY);
        if(this.mailBoxBlockEntity.getMailBoxName() != null)
        {
            this.nameField.setValue(this.mailBoxBlockEntity.getMailBoxName());
        }
        this.addWidget(this.nameField);

        this.btnSave = this.addRenderableWidget(new Button(guiLeft + 7, guiTop + 42, 79, 20, Component.translatable("gui.button.cfm.save"), button ->
        {
            if(this.isValidName())
            {
                PacketHandler.getPlayChannel()
                        .sendToServer(new C2SMessageSetMailBoxName(this.nameField.getValue(), this.mailBoxBlockEntity.getBlockPos()));
            }
        }));
        this.btnSave.active = false;

        this.addRenderableWidget(new Button(guiLeft + 91, guiTop + 42, 79, 20, Component.translatable("gui.button.cfm.back"), button ->
        {
            PacketHandler.getPlayChannel()
                    .sendToServer(new C2SMessageOpenMailBox(this.mailBoxBlockEntity.getBlockPos()));
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
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int startX = (this.width - this.xSize) / 2;
        int startY = (this.height - this.ySize) / 2;
        this.blit(poseStack, startX, startY, 0, 0, this.xSize, this.ySize);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.font.draw(poseStack, this.title.getString(), startX + 8.0F, startY + 6.0F, 0x404040);
        this.nameField.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private boolean isValidName()
    {
        return !this.nameField.getValue().equals(this.mailBoxBlockEntity.getMailBoxName()) && !this.nameField.getValue().trim().isEmpty();
    }
}
