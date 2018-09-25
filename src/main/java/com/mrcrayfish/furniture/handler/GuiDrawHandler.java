package com.mrcrayfish.furniture.handler;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.client.category.AbstractCategory;
import com.mrcrayfish.furniture.client.category.Categories;
import com.mrcrayfish.furniture.init.FurnitureTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GuiDrawHandler
{
    private static final ResourceLocation ICONS = new ResourceLocation("cfm:textures/gui/icons.png");

    private GuiLinkImageButton buttonWebsite;
    private GuiLinkImageButton buttonYouTube;
    private GuiLinkImageButton buttonTwitter;
    private GuiLinkImageButton buttonPatreon;

    private AbstractCategory[] categories;
    private List<GuiButton> categoryButtons;
    private GuiButton categoryUp;
    private GuiButton categoryDown;
    private static int startIndex;
    private List<GuiButton> buttonList;

    private int guiCenterX = 0;
    private int guiCenterY = 0;

    @SubscribeEvent
    public void onDrawGui(InitGuiEvent.Post event)
    {
        if(event.getGui() instanceof GuiContainerCreative)
        {
            this.guiCenterX = ((GuiContainerCreative) event.getGui()).getGuiLeft();
            this.guiCenterY = ((GuiContainerCreative) event.getGui()).getGuiTop();
            this.categories = new AbstractCategory[] { Categories.GENERAL, Categories.KITCHEN, Categories.BATHROOM, Categories.OUTDOOR, Categories.ELECTRONICS, Categories.EVENT };
            this.categoryButtons = Lists.newArrayList();
            this.buttonList = event.getButtonList();

            event.getButtonList().add(buttonWebsite = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY, ICONS, 48, 0, "https://mrcrayfish.com", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.website")));
            event.getButtonList().add(buttonYouTube = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY + 22, ICONS, 32, 0, "https://www.youtube.com/channel/UCSwwxl2lWJcbGOGQ_d04v2Q", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.youtube")));
            event.getButtonList().add(buttonTwitter = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY + 44, ICONS, 16, 0, "https://twitter.com/MrCraayfish", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.twitter")));
            event.getButtonList().add(buttonPatreon = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY, ICONS, 0, 0, "https://www.patreon.com/mrcrayfish", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.patreon")));

            event.getButtonList().add(categoryUp = new GuiButton(11, guiCenterX - 22, guiCenterY - 12, 20, 20, "▲"));
            event.getButtonList().add(categoryDown = new GuiButton(11, guiCenterX - 22, guiCenterY + 127, 20, 20, "▼"));
            updateCategories();
        }
    }

    private void updateCategories()
    {
        if(!categoryButtons.isEmpty())
        {
            buttonList.removeAll(categoryButtons);
            categoryButtons.clear();
        }
        for(int i = startIndex; i < startIndex + 4 && i < categories.length; i++)
        {
            GuiButton button = new GuiCategoryButton(guiCenterX - 28, guiCenterY + 29 * (i - startIndex) + 10, categories[i]);
            categoryButtons.add(button);
            buttonList.add(button);
        }
        updateCategoryButtons();
    }

    @SubscribeEvent
    public void onDrawGui(DrawScreenEvent.Post event)
    {
        if(event.getGui() instanceof GuiContainerCreative)
        {
            GuiContainerCreative creative = (GuiContainerCreative) event.getGui();

            this.guiCenterX = creative.getGuiLeft();
            this.guiCenterY = creative.getGuiTop();

            if(creative.getSelectedTabIndex() == MrCrayfishFurnitureMod.tabFurniture.getTabIndex())
            {
                buttonWebsite.visible = true;
                buttonYouTube.visible = true;
                buttonTwitter.visible = true;
                buttonPatreon.visible = true;
                categoryButtons.forEach(guiButton -> {
                    guiButton.visible = true;
                });
            }
            else
            {
                buttonWebsite.visible = false;
                buttonYouTube.visible = false;
                buttonTwitter.visible = false;
                buttonPatreon.visible = false;
                categoryButtons.forEach(guiButton -> {
                    guiButton.visible = false;
                });
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

            if(event.getGui() instanceof GuiContainerCreative)
            {
                GuiContainerCreative creative = (GuiContainerCreative) event.getGui();
                GuiContainerCreative.ContainerCreative container = (GuiContainerCreative.ContainerCreative) creative.inventorySlots;
                Set<Item> categorisedItems = new LinkedHashSet<>();
                for(AbstractCategory category : categories)
                {
                    if(category.isEnabled())
                    {
                        categorisedItems.addAll(category.getItems());
                    }
                }
                container.itemList.clear();
                categorisedItems.forEach(item -> item.getSubItems(CreativeTabs.SEARCH, container.itemList));
                container.scrollTo(0);
            }
        }
        else if(categories != null)
        {
            if(event.getButton() == categoryUp)
            {
                if(startIndex > 0)
                {
                    startIndex--;
                }
                updateCategories();
            }
            else if(event.getButton() == categoryDown)
            {
                if(startIndex < categories.length - 4 - 1)
                {
                    startIndex++;
                }
                updateCategories();
            }
        }
    }

    private void updateCategoryButtons()
    {
        categoryUp.enabled = startIndex > 0;
        categoryDown.enabled = startIndex < categories.length - 4 - 1;
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
        private AbstractCategory category;

        public GuiCategoryButton(int x, int y, AbstractCategory category)
        {
            super(0, x, y, 32, 28, "");
            this.x = x;
            this.y = y;
            this.stack = category.getIcon();
            this.toggled = category.isEnabled();
            this.category = category;
            this.visible = true;
        }

        public void onClick()
        {
            if(!this.visible || !this.enabled)
                return;

            toggled = !toggled;
            category.setEnabled(toggled);
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
                screen.drawHoveringText(category.getTitle(), mouseX, mouseY);
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

        @Override
        public boolean equals(Object obj)
        {
            return obj instanceof GuiCategoryButton && ((GuiCategoryButton) obj).category == category;
        }
    }
}
