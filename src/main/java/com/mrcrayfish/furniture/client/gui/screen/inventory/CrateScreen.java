package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageLockCrate;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ForgeI18n;

/**
 * Author: MrCrayfish
 */
@OnlyIn(Dist.CLIENT)
public class CrateScreen extends ContainerScreen<CrateContainer>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/crate.png");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");

    private IconButton button;
    private boolean locked;

    public CrateScreen(CrateContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
    }

    @Override
    protected void init()
    {
        super.init();
        this.button = this.addButton(new IconButton(this.guiLeft + this.xSize + 2, this.guiTop + 17, I18n.format("gui.cfm.lock"), button -> PacketHandler.instance.sendToServer(new MessageLockCrate()), ICONS_TEXTURE, 0, 0));
        this.updateLockButton();
    }

    @Override
    public void tick()
    {
        super.tick();
        if(this.locked != this.container.isLocked())
        {
            this.locked = this.container.isLocked();
            this.updateLockButton();
        }
    }

    private void updateLockButton()
    {
        this.button.setIcon(ICONS_TEXTURE, this.container.isLocked() ? 0 : 16, 0);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        if(this.button.isMouseOver(mouseX, mouseY))
        {
            this.renderTooltip(locked ? I18n.format("gui.button.cfm.locked") : I18n.format("gui.button.cfm.unlocked"), mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int startX = (this.width - this.xSize) / 2;
        int startY = (this.height - this.ySize) / 2;
        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);
    }
}
