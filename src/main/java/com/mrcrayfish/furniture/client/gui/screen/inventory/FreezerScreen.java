package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.inventory.container.FreezerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;

/**
 * Author: MrCrayfish
 */
public class FreezerScreen extends ContainerScreen<FreezerContainer>
{
    private static final ResourceLocation FREEZER_GUI_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/freezer.png");

    public FreezerScreen(FreezerContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        ITextProperties title = this.title;
        this.font.drawString(matrixStack, title.getString(), (float) (this.xSize / 2 - this.font.getStringWidth(title.getString()) / 2), 6.0F, 4210752);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixstack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(FREEZER_GUI_TEXTURES);
        this.blit(matrixstack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(this.container.isFueling())
        {
            int fuelLeft = this.container.getFuelLeftScaled();
            this.blit(matrixstack, this.guiLeft + 57, this.guiTop + 37 + 12 - fuelLeft, 176, 12 - fuelLeft, 14, fuelLeft + 1);
        }
        int progress = this.container.getSolidifyProgressionScaled();
        this.blit(matrixstack, this.guiLeft + 79, this.guiTop + 34, 176, 14, progress + 1, 16);
    }
}
