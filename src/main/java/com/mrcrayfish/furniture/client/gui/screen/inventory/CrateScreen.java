package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.inventory.container.CrateMenu;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.C2SMessageLockCrate;
import net.minecraft.client.gui.GuiGraphics;
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
public class CrateScreen extends AbstractContainerScreen<CrateMenu>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/crate.png");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");

    private IconButton button;
    private boolean locked;
    private final UUID playerId;

    public CrateScreen(CrateMenu container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);
        this.playerId = playerInventory.player.getUUID();
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void init()
    {
        super.init();
        this.button = this.addRenderableWidget(new IconButton(this.leftPos + this.imageWidth + 2, this.topPos + 17, button -> PacketHandler.getPlayChannel()
                .sendToServer(new C2SMessageLockCrate()), ICONS_TEXTURE, 0, 0));
        this.button.setTooltip(Tooltip.create(Component.translatable("gui.button.cfm.lock")));
        this.updateLockButton();
    }

    @Override
    public void containerTick()
    {
        if(this.locked != this.menu.getBlockEntity().isLocked())
        {
            this.locked = this.menu.getBlockEntity().isLocked();
            this.updateLockButton();
        }
    }

    private void updateLockButton()
    {
        this.locked = this.menu.getBlockEntity().isLocked();
        this.button.setIcon(ICONS_TEXTURE, this.locked ? 0 : 16, 0);
        this.button.setMessage(Component.translatable(this.locked ? "gui.button.cfm.locked" : "gui.button.cfm.unlocked"));
        UUID ownerUuid = this.menu.getBlockEntity().getOwner();
        this.button.visible = ownerUuid == null || this.playerId.equals(ownerUuid);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int startX = (this.width - this.imageWidth) / 2;
        int startY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI_TEXTURE, startX, startY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
