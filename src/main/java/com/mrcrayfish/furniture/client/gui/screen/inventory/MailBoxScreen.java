package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.screen.MailBoxSettingsScreen;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.inventory.container.MailBoxContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Author: MrCrayfish
 */
public class MailBoxScreen extends ContainerScreen<MailBoxContainer>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/mail_box.png");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");

    public MailBoxScreen(MailBoxContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
        this.ySize = 132;
    }

    @Override
    protected void init()
    {
        super.init();
        this.addButton(new IconButton(this.guiLeft + this.xSize + 2, this.guiTop + 17, I18n.format("gui.button.cfm.lock"), button -> {
            this.minecraft.displayGuiScreen(new MailBoxSettingsScreen(this.container.getMailBoxTileEntity()));
        }, ICONS_TEXTURE, 48, 0));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int startX = (this.width - this.xSize) / 2;
        int startY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, startX, startY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 6.0F, 0x404040);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
}
