package com.mrcrayfish.furniture.handler;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.init.FurnitureTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

import java.net.URI;
import java.net.URISyntaxException;

public class GuiDrawHandler
{
    private static final ResourceLocation ICONS = new ResourceLocation("cfm:textures/gui/icons.png");

    private GuiLinkImageButton buttonWebsite;
    private GuiLinkImageButton buttonYouTube;
    private GuiLinkImageButton buttonTwitter;
    private GuiLinkImageButton buttonPatreon;

    private GuiCategoryButton categoryGeneral;
    private GuiCategoryButton categoryKitchen;
    private GuiCategoryButton categoryBathroom;
    private GuiCategoryButton categoryOutdoor;
    private GuiCategoryButton categoryElectronics;
    private GuiCategoryButton categoryEvent;

    private int guiCenterX = 0;
    private int guiCenterY = 0;

    @SubscribeEvent
    public void onDrawGui(InitGuiEvent.Post event)
    {
        if(event.getGui() instanceof GuiContainerCreative)
        {
            this.guiCenterX = ((GuiContainerCreative) event.getGui()).getGuiLeft();
            this.guiCenterY = ((GuiContainerCreative) event.getGui()).getGuiTop();

            event.getButtonList().add(buttonWebsite = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY, ICONS, 48, 0, "https://mrcrayfish.com", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.website")));
            event.getButtonList().add(buttonYouTube = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY + 22, ICONS, 32, 0, "https://www.youtube.com/channel/UCSwwxl2lWJcbGOGQ_d04v2Q", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.youtube")));
            event.getButtonList().add(buttonTwitter = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY + 44, ICONS, 16, 0, "https://twitter.com/MrCraayfish", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.twitter")));
            event.getButtonList().add(buttonPatreon = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY, ICONS, 0, 0, "https://www.patreon.com/mrcrayfish", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.patreon")));

            event.getButtonList().add(categoryGeneral = new GuiCategoryButton(guiCenterX - 28, guiCenterY, new ItemStack(FurnitureBlocks.CHAIR_OAK)));
            event.getButtonList().add(categoryKitchen = new GuiCategoryButton(guiCenterX - 28, guiCenterY + 29, new ItemStack(FurnitureBlocks.FREEZER)));
            event.getButtonList().add(categoryBathroom = new GuiCategoryButton(guiCenterX - 28, guiCenterY + 58, new ItemStack(FurnitureBlocks.TOILET)));
            event.getButtonList().add(categoryOutdoor = new GuiCategoryButton(guiCenterX - 28, guiCenterY + 87, new ItemStack(FurnitureBlocks.FIRE_PIT_ON)));
            event.getButtonList().add(categoryElectronics = new GuiCategoryButton(guiCenterX - 28, guiCenterY + 116, new ItemStack(FurnitureBlocks.COMPUTER)));
            event.getButtonList().add(categoryEvent = new GuiCategoryButton(guiCenterX - 28, guiCenterY + 145, new ItemStack(FurnitureBlocks.TREE_BOTTOM)));
        }
    }

    @SubscribeEvent
    public void onDrawGui(DrawScreenEvent.Post event)
    {
        if(event.getGui() instanceof GuiContainerCreative)
        {
            GuiContainerCreative creative = (GuiContainerCreative) event.getGui();

            this.guiCenterX = event.getGui().width / 2;
            this.guiCenterY = event.getGui().height / 2;

            if(creative.getSelectedTabIndex() == MrCrayfishFurnitureMod.tabFurniture.getTabIndex())
            {
                buttonWebsite.visible = true;
                buttonYouTube.visible = true;
                buttonTwitter.visible = true;
                buttonPatreon.visible = true;
            }
            else
            {
                buttonWebsite.visible = false;
                buttonYouTube.visible = false;
                buttonTwitter.visible = false;
                buttonPatreon.visible = false;
            }
        }
    }

    @SubscribeEvent
    public void onButtonClick(ActionPerformedEvent.Post event)
    {
        if(event.getButton() instanceof GuiLinkImageButton)
        {
            GuiLinkImageButton button = (GuiLinkImageButton) event.getButton();
            try
            {
                openWebLink(new URI(button.link));
            }
            catch(URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
        else if(event.getButton() instanceof GuiCategoryButton)
        {
            ((GuiCategoryButton) event.getButton()).onClick();
        }
    }

    private void openWebLink(URI url)
    {
        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
            oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private class GuiLinkImageButton extends GuiButton
    {
        private final ResourceLocation image;
        private final int u;
        private final int v;
        private final String link;
        private final String toolTip;

        public GuiLinkImageButton(int buttonId, int x, int y, ResourceLocation image, int u, int v, String link, String toolTip)
        {
            super(buttonId, x, y, 20, 20, "");
            this.image = image;
            this.u = u;
            this.v = v;
            this.link = link;
            this.toolTip = toolTip;
            this.visible = false;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if(this.visible)
            {
                if(this.hovered && !mousePressed(mc, mouseX, mouseY))
                {
                    ((FurnitureTab) MrCrayfishFurnitureMod.tabFurniture).setHoveringButton(false);
                }

                FontRenderer fontrenderer = mc.fontRenderer;
                mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int i = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
                this.mouseDragged(mc, mouseX, mouseY);

                if(this.hovered)
                {
                    ((FurnitureTab) MrCrayfishFurnitureMod.tabFurniture).setTitle(toolTip);
                    ((FurnitureTab) MrCrayfishFurnitureMod.tabFurniture).setHoveringButton(true);
                }

                mc.getTextureManager().bindTexture(GuiDrawHandler.ICONS);
                this.drawTexturedModalRect(this.x + 2, this.y + 2, u, v, 16, 16);
            }
        }
    }

    private static class GuiCategoryButton extends GuiButton
    {
        private static final ResourceLocation TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

        private int x, y;
        private boolean toggled;
        private ItemStack stack;

        public GuiCategoryButton(int x, int y, ItemStack stack)
        {
            super(0, x, y, 32, 28, "");
            this.x = x;
            this.y = y;
            this.stack = stack;
        }

        public void onClick()
        {
            if(!this.visible || !this.enabled)
                return;

            toggled = !toggled;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if(!this.visible || !this.enabled)
                return;

            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            mc.getTextureManager().bindTexture(TABS);
            GlStateManager.disableLighting();
            GlStateManager.color(1F, 1F, 1F);
            GlStateManager.enableBlend();

            int width = toggled ? 32 : 28;
            int textureX = 28;
            int textureY = toggled ? 32 : 0;
            this.drawRotatedTexture(x, y, textureX, textureY, width, 28, 28, width);

            this.zLevel = 100.0F;
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            renderItem.zLevel = 100.0F;
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableRescaleNormal();
            renderItem.renderItemAndEffectIntoGUI(stack, x + 8, y + 6);
            renderItem.renderItemOverlays(mc.fontRenderer, stack, x + 8, y + 6);
            GlStateManager.disableDepth();
            renderItem.zLevel = 0.0F;
            this.zLevel = 0.0F;
        }

        public void drawHoveringText(GuiScreen screen, int mouseX, int mouseY)
        {
            if(!this.visible || !this.enabled)
                return;

            if(this.hovered)
            {
                screen.drawHoveringText("Category", mouseX, mouseY);
            }
        }

        private void drawRotatedTexture(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight)
        {
            float scaleX = 0.00390625F;
            float scaleY = 0.00390625F;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + textureWidth) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
            bufferbuilder.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + textureWidth) * 0.00390625F), (double)((float)(textureY + textureHeight) * 0.00390625F)).endVertex();
            bufferbuilder.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + textureHeight) * 0.00390625F)).endVertex();
            bufferbuilder.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
            tessellator.draw();
        }
    }
}
