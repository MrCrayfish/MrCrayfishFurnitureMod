package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.screen.MailBoxSettingsScreen;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.inventory.container.MailBoxMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class MailBoxScreen extends AbstractContainerScreen<MailBoxMenu>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/mail_box.png");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");
    private Button settingsButton;
    private final UUID playerId;

    public MailBoxScreen(MailBoxMenu container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);
        this.imageHeight = 132;
        this.playerId = playerInventory.player.getUUID();
        this.inventoryLabelY = 39;
    }

    @Override
    protected void init()
    {
        super.init();
        this.settingsButton = this.addRenderableWidget(new IconButton(this.leftPos + this.imageWidth + 2, this.topPos + 17, button -> {
            this.minecraft.setScreen(new MailBoxSettingsScreen(this.menu.getMailBoxBlockEntity()));
        }, ICONS_TEXTURE, 48, 0));
        this.settingsButton.setTooltip(Tooltip.create(Component.translatable("gui.button.cfm.lock")));
        this.settingsButton.visible = this.playerId.equals(this.menu.getMailBoxBlockEntity().getOwnerId());
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int startX = (this.width - this.imageWidth) / 2;
        int startY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI_TEXTURE, startX, startY, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
