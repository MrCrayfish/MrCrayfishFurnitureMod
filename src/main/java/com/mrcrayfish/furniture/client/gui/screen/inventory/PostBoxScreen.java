package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.inventory.container.PostBoxContainer;
import com.mrcrayfish.furniture.util.RenderUtil;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

/**
 * Author: MrCrayfish
 */
public class PostBoxScreen extends ContainerScreen<PostBoxContainer>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/post_box.png");

    private TextFieldWidget searchField;

    public PostBoxScreen(PostBoxContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
        this.ySize = 187;
    }

    @Override
    protected void init()
    {
        super.init();
        this.searchField = new TextFieldWidget(this.font, this.guiLeft + 22, this.guiTop + 19, 101, 9, I18n.format("gui.cfm.post_box.search"));
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setMaxStringLength(32);
        this.searchField.setTextColor(16777215);
        this.children.add(this.searchField);
    }

    @Override
    public void tick()
    {
        super.tick();
        this.searchField.tick();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int startX = (this.width - this.xSize) / 2;
        int startY = (this.height - this.ySize) / 2;
        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);
        this.searchField.render(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        {
            RenderUtil.scissor(8, 32, 116, 57);
            //TODO render mail box entries
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
