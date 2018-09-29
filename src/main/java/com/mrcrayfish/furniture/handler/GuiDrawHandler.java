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
import java.util.*;

public class GuiDrawHandler
{
    private static final ResourceLocation ICONS = new ResourceLocation("cfm:textures/gui/icons.png");
    private static final ResourceLocation BEACON = new ResourceLocation("textures/gui/container/beacon.png");

    private GuiLinkImageButton buttonWebsite;
    private GuiLinkImageButton buttonYouTube;
    private GuiLinkImageButton buttonTwitter;
    private GuiLinkImageButton buttonPatreon;

    private AbstractCategory[] categories;
    private List<GuiCategoryButton> categoryButtons;
    private GuiButton categoryUp;
    private GuiButton categoryDown;
    private GuiButton categoryEnableAll;
    private GuiButton categoryDisableAll;
    private static int startIndex;
    private List<GuiButton> buttonList;

    private boolean viewingFurnitureTab;

    private int guiCenterX = 0;
    private int guiCenterY = 0;

    @SubscribeEvent
    public void onDrawGui(InitGuiEvent.Post event)
    {
        viewingFurnitureTab = false;
        if(event.getGui() instanceof GuiContainerCreative)
        {
            this.guiCenterX = ((GuiContainerCreative) event.getGui()).getGuiLeft();
            this.guiCenterY = ((GuiContainerCreative) event.getGui()).getGuiTop();
            this.categories = new AbstractCategory[] { Categories.GENERAL, Categories.BEDROOM, Categories.KITCHEN, Categories.BATHROOM, Categories.OUTDOOR, Categories.ELECTRONICS, Categories.EVENT, Categories.STORAGE, Categories.LIGHTING };
            this.categoryButtons = Lists.newArrayList();
            this.buttonList = event.getButtonList();

            event.getButtonList().add(buttonWebsite = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY, ICONS, 48, 0, "https://mrcrayfish.com", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.website")));
            event.getButtonList().add(buttonYouTube = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY + 22, ICONS, 32, 0, "https://www.youtube.com/channel/UCSwwxl2lWJcbGOGQ_d04v2Q", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.youtube")));
            event.getButtonList().add(buttonTwitter = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY + 44, ICONS, 16, 0, "https://twitter.com/MrCraayfish", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.twitter")));
            event.getButtonList().add(buttonPatreon = new GuiLinkImageButton(10, guiCenterX - 50, guiCenterY, ICONS, 0, 0, "https://www.patreon.com/mrcrayfish", TextFormatting.WHITE + "> " + TextFormatting.DARK_GRAY + I18n.format("cfm.button.patreon")));

            event.getButtonList().add(categoryUp = new GuiArrowButton(11, guiCenterX - 22, guiCenterY - 12, 20, 20, true));
            event.getButtonList().add(categoryDown = new GuiArrowButton(11, guiCenterX - 22, guiCenterY + 127, 20, 20, false));
            event.getButtonList().add(categoryEnableAll = new GuiImageButton(guiCenterX - 50, guiCenterY + 88, 91, 223, 14, 14, BEACON));
            event.getButtonList().add(categoryDisableAll = new GuiImageButton(guiCenterX - 50, guiCenterY + 110, 114, 223, 14, 14, BEACON));
            updateCategories();

            GuiContainerCreative creative = (GuiContainerCreative) event.getGui();
            if(creative.getSelectedTabIndex() == MrCrayfishFurnitureMod.tabFurniture.getTabIndex())
            {
                viewingFurnitureTab = true;
                categoryButtons.forEach(guiCategoryButton -> guiCategoryButton.visible = true);
                updateItems(creative);
            }
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
            GuiCategoryButton button = new GuiCategoryButton(guiCenterX - 28, guiCenterY + 29 * (i - startIndex) + 10, categories[i]);
            categoryButtons.add(button);
            buttonList.add(button);
        }
        updateCategoryButtons();
    }

    @SubscribeEvent
    public void onDrawGui(DrawScreenEvent.Pre event)
    {
        if(event.getGui() instanceof GuiContainerCreative)
        {
            GuiContainerCreative creative = (GuiContainerCreative) event.getGui();
            if(creative.getSelectedTabIndex() == MrCrayfishFurnitureMod.tabFurniture.getTabIndex())
            {
                if(!viewingFurnitureTab)
                {
                    updateItems(creative);
                    viewingFurnitureTab = true;
                }
            }
            else
            {
                viewingFurnitureTab = false;
            }
        }
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
                categoryUp.visible = true;
                categoryDown.visible = true;
                categoryEnableAll.visible = true;
                categoryDisableAll.visible = true;
                categoryButtons.forEach(guiButton -> guiButton.visible = true);
                categoryButtons.forEach(guiButton ->
                {
                    if(guiButton.isMouseOver())
                    {
                        guiButton.drawHoveringText(event.getGui(), event.getMouseX(), event.getMouseY());
                    }
                });
                if(categoryEnableAll.isMouseOver())
                {
                    event.getGui().drawHoveringText("Enable All Filters", event.getMouseX(), event.getMouseY());
                }
                else if(categoryDisableAll.isMouseOver())
                {
                    event.getGui().drawHoveringText("Disable All Filters", event.getMouseX(), event.getMouseY());
                }
            }
            else
            {
                buttonWebsite.visible = false;
                buttonYouTube.visible = false;
                buttonTwitter.visible = false;
                buttonPatreon.visible = false;
                categoryUp.visible = false;
                categoryDown.visible = false;
                categoryEnableAll.visible = false;
                categoryDisableAll.visible = false;
                categoryButtons.forEach(guiButton -> guiButton.visible = false);
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
                updateItems(creative);
            }
        }
        else if(categories != null && event.getGui() instanceof GuiContainerCreative)
        {
            if(event.getButton() == categoryUp)
            {
                if(startIndex > 0)
                {
                    startIndex--;
                }
            }
            else if(event.getButton() == categoryDown)
            {
                if(startIndex <= categories.length - 4 - 1)
                {
                    startIndex++;
                }
            }
            else if(event.getButton() == categoryEnableAll)
            {
                for(AbstractCategory category : categories)
                {
                    category.setEnabled(true);
                }
                updateItems((GuiContainerCreative) event.getGui());
            }
            else if(event.getButton() == categoryDisableAll)
            {
                for(AbstractCategory category : categories)
                {
                    category.setEnabled(false);
                }
                updateItems((GuiContainerCreative) event.getGui());
            }
            updateCategories();
            GuiContainerCreative creative = (GuiContainerCreative) event.getGui();
            if(creative.getSelectedTabIndex() == MrCrayfishFurnitureMod.tabFurniture.getTabIndex())
            {
                categoryButtons.forEach(guiCategoryButton -> guiCategoryButton.visible = true);
            }
        }
    }

    private void updateItems(GuiContainerCreative creative)
    {
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
        container.itemList.sort(Comparator.comparingInt(o -> Item.getIdFromItem(o.getItem())));
        container.scrollTo(0);
    }

    private void updateCategoryButtons()
    {
        categoryUp.enabled = startIndex > 0;
        categoryDown.enabled = startIndex <= categories.length - 4 - 1;
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

                this.zLevel = 100.0F;
                GlStateManager.enableDepth();
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
                this.zLevel = 0.0F;
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
            this.visible = false;
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

            this.zLevel = 100.0F;
            GlStateManager.enableDepth();
            this.drawRotatedTexture(x, y, textureX, textureY, width, 28, 28, width);
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            renderItem.zLevel = 100.0F;
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableRescaleNormal();
            renderItem.renderItemAndEffectIntoGUI(stack, x + 8, y + 6);
            renderItem.renderItemOverlays(mc.fontRenderer, stack, x + 8, y + 6);
            renderItem.zLevel = 0.0F;
            this.zLevel = 0.0F;
        }

        public void drawHoveringText(GuiScreen screen, int mouseX, int mouseY)
        {
            if(!this.visible || !this.enabled)
                return;

            if(this.hovered)
            {
                screen.drawHoveringText(Arrays.asList(TextFormatting.BOLD + category.getTitle(), category.isEnabled() ? TextFormatting.BLUE + "Enabled" : TextFormatting.DARK_GRAY + "Disabled"), mouseX, mouseY);
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

    private static class GuiArrowButton extends GuiButton
    {
        private static final ResourceLocation ARROWS = new ResourceLocation("textures/gui/resource_packs.png");

        private boolean up = false;

        public GuiArrowButton(int buttonId, int x, int y, int widthIn, int heightIn, boolean up)
        {
            super(buttonId, x, y, widthIn, heightIn, "");
            this.up = up;
            this.visible = false;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if(!visible)
                return;

            this.zLevel = 100.0F;
            super.drawButton(mc, mouseX, mouseY, partialTicks);
            mc.getTextureManager().bindTexture(ARROWS);
            if(up)
            {
                this.drawTexturedModalRect(x + 4, y + 7, 114, 37, 11, 7);
            }
            else
            {
                this.drawTexturedModalRect(x + 4, y + 7, 82, 52, 11, 7);
            }
            this.zLevel = 0.0F;
        }
    }

    private static class GuiImageButton extends GuiButton
    {
        private ResourceLocation resource;
        private int textureU, textureV;
        private int textureWidth, textureHeight;

        public GuiImageButton(int x, int y, int textureU, int textureV, int textureWidth, int textureHeight, ResourceLocation resource)
        {
            super(-1, x, y, textureWidth + 6, textureHeight + 6, "");
            this.resource = resource;
            this.textureU = textureU;
            this.textureV = textureV;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
            this.visible = false;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if(!this.visible)
                return;
            this.zLevel = 100.0F;
            super.drawButton(mc, mouseX, mouseY, partialTicks);
            mc.getTextureManager().bindTexture(resource);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(x + 3, y + 3, textureU, textureV, textureWidth, textureHeight);
            this.zLevel = 0.0F;
        }
    }
}
