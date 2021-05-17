package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageLockCrate;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

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
        this.button = this.addButton(new IconButton(this.guiLeft + this.xSize + 2, this.guiTop + 17, new TranslationTextComponent("gui.button.cfm.lock"), button -> PacketHandler.instance.sendToServer(new MessageLockCrate()), ICONS_TEXTURE, 0, 0));
        this.updateLockButton();
    }

    @Override
    public void tick()
    {
        super.tick();
        if(this.locked != this.container.getCrateTileEntity().isLocked())
        {
            this.locked = this.container.getCrateTileEntity().isLocked();
            this.updateLockButton();
        }
    }

    private void updateLockButton()
    {
        this.locked = this.container.getCrateTileEntity().isLocked();
        this.button.setIcon(ICONS_TEXTURE, this.locked ? 0 : 16, 0);
        this.button.setMessage(new TranslationTextComponent(this.locked ? "gui.button.cfm.locked" : "gui.button.cfm.unlocked"));
        UUID ownerUuid = this.container.getCrateTileEntity().getOwner();
        this.button.visible = ownerUuid == null || this.playerInventory.player.getUniqueID().equals(ownerUuid);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        if(this.button.isMouseOver(mouseX, mouseY))
        {
            this.renderTooltip(matrixStack, new TranslationTextComponent(this.locked ? "gui.button.cfm.locked" : "gui.button.cfm.unlocked"), mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 6.0F, 0x404040);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
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
}
