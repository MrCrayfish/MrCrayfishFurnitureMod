package com.mrcrayfish.furniture.client.event;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.client.gui.widget.button.TagButton;
import com.mrcrayfish.furniture.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.*;

/**
 * Author: MrCrayfish
 */
public class CreativeScreenEvents
{
    private static final ResourceLocation ICONS = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");
    private static int startIndex;

    private List<TagFilter> filters;
    private List<TagButton> buttons;
    private Button btnScrollUp;
    private Button btnScrollDown;
    private Button btnEnableAll;
    private Button btnDisableAll;
    private boolean viewingFurnitureTab;
    private int guiCenterX = 0;
    private int guiCenterY = 0;

    @SubscribeEvent
    public void onPlayerLogin(ClientPlayerNetworkEvent.LoggedInEvent event)
    {
        this.compileItems();
    }

    @SubscribeEvent
    public void onPlayerLogout(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        this.filters = null;
    }

    @SubscribeEvent
    public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if(event.getGui() instanceof CreativeScreen)
        {
            this.viewingFurnitureTab = false;
            this.guiCenterX = ((CreativeScreen) event.getGui()).getGuiLeft();
            this.guiCenterY = ((CreativeScreen) event.getGui()).getGuiTop();
            this.buttons = new ArrayList<>();

            event.addWidget(this.btnScrollUp = new IconButton(this.guiCenterX - 22, this.guiCenterY - 12, I18n.format("gui.button.cfm.scroll_filters_up"), button -> {
                if(startIndex > 0) startIndex--;
            }, ICONS, 64, 0));

            event.addWidget(this.btnScrollDown = new IconButton(this.guiCenterX - 22, this.guiCenterY + 127, I18n.format("gui.button.cfm.scroll_filters_down"), button -> {
                if(startIndex <= filters.size() - 4 - 1) startIndex++;
            }, ICONS, 80, 0));

            event.addWidget(this.btnEnableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 10, I18n.format("gui.button.cfm.enable_filters"), button -> {
                this.filters.forEach(filters -> filters.setEnabled(true));
                this.buttons.forEach(TagButton::updateState);
                Screen screen = Minecraft.getInstance().currentScreen;
                if(screen instanceof CreativeScreen)
                {
                    this.updateItems((CreativeScreen) screen);
                }
            }, ICONS, 96, 0));

            event.addWidget(this.btnDisableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 32, I18n.format("gui.button.cfm.disable_filters"), button -> {
                this.filters.forEach(filters -> filters.setEnabled(false));
                this.buttons.forEach(TagButton::updateState);
                Screen screen = Minecraft.getInstance().currentScreen;
                if(screen instanceof CreativeScreen)
                {
                    this.updateItems((CreativeScreen) screen);
                }
            }, ICONS, 112, 0));

            this.btnScrollUp.visible = false;
            this.btnScrollDown.visible = false;
            this.btnEnableAll.visible = false;
            this.btnDisableAll.visible = false;

            this.updateTagButtons();

            CreativeScreen screen = (CreativeScreen) event.getGui();
            if(screen.getSelectedTabIndex() == FurnitureMod.GROUP.getIndex())
            {
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.btnEnableAll.visible = true;
                this.btnDisableAll.visible = true;
                this.viewingFurnitureTab = true;
                this.buttons.forEach(button -> button.visible = true);
                this.updateItems(screen);
            }
        }
    }

    @SubscribeEvent
    public void onScreenClick(GuiScreenEvent.MouseClickedEvent.Pre event)
    {
        if(event.getButton() != GLFW.GLFW_MOUSE_BUTTON_LEFT)
            return;

        if(event.getGui() instanceof CreativeScreen)
        {
            for(Button button : this.buttons)
            {
                if(button.isMouseOver(event.getMouseX(), event.getMouseY()))
                {
                    if(button.mouseClicked(event.getMouseX(), event.getMouseY(), event.getButton()))
                    {
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawPre(GuiScreenEvent.DrawScreenEvent.Pre event)
    {
        if(event.getGui() instanceof CreativeScreen)
        {
            CreativeScreen screen = (CreativeScreen) event.getGui();
            if(screen.getSelectedTabIndex() == FurnitureMod.GROUP.getIndex())
            {
                if(!this.viewingFurnitureTab)
                {
                    this.updateItems(screen);
                    this.viewingFurnitureTab = true;
                }
            }
            else
            {
                this.viewingFurnitureTab = false;
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawPost(GuiScreenEvent.DrawScreenEvent.Post event)
    {
        if(event.getGui() instanceof CreativeScreen)
        {
            CreativeScreen screen = (CreativeScreen) event.getGui();
            this.guiCenterX = screen.getGuiLeft();
            this.guiCenterY = screen.getGuiTop();

            if(screen.getSelectedTabIndex() == FurnitureMod.GROUP.getIndex())
            {
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.btnEnableAll.visible = true;
                this.btnDisableAll.visible = true;
                this.buttons.forEach(button -> button.visible = true);

                /* Render buttons */
                this.buttons.forEach(button ->
                {
                    button.render(event.getMouseX(), event.getMouseY(), event.getRenderPartialTicks());
                });

                /* Render tooltips after so it renders above buttons */
                this.buttons.forEach(button ->
                {
                    if(button.isMouseOver(event.getMouseX(), event.getMouseY()))
                    {
                        screen.renderTooltip(button.getCategory().getName(), event.getMouseX(), event.getMouseY());
                    }
                });

                if(this.btnEnableAll.isMouseOver(event.getMouseX(), event.getMouseY()))
                {
                    screen.renderTooltip(this.btnEnableAll.getMessage(), event.getMouseX(), event.getMouseY());
                }

                if(this.btnDisableAll.isMouseOver(event.getMouseX(), event.getMouseY()))
                {
                    screen.renderTooltip(this.btnDisableAll.getMessage(), event.getMouseX(), event.getMouseY());
                }
            }
            else
            {
                this.btnScrollUp.visible = false;
                this.btnScrollDown.visible = false;
                this.btnEnableAll.visible = false;
                this.btnDisableAll.visible = false;
                this.buttons.forEach(button -> button.visible = false);
            }
        }
    }

    private void updateTagButtons()
    {
        final Button.IPressable pressable = button ->
        {
            Screen screen = Minecraft.getInstance().currentScreen;
            if(screen instanceof CreativeScreen)
            {
                this.updateItems((CreativeScreen) screen);
            }
        };
        this.buttons.clear();
        for(int i = startIndex; i < startIndex + 4 && i < this.filters.size(); i++)
        {
            TagButton button = new TagButton(this.guiCenterX - 28, this.guiCenterY + 29 * (i - startIndex) + 10, this.filters.get(i), pressable);
            this.buttons.add(button);
        }
        this.btnScrollUp.active = startIndex > 0;
        this.btnScrollDown.active = startIndex <= this.filters.size() - 4 - 1;
    }

    private void updateItems(CreativeScreen screen)
    {
        CreativeScreen.CreativeContainer container = screen.getContainer();
        Set<Item> categorisedItems = new LinkedHashSet<>();
        for(TagFilter filter : this.filters)
        {
            if(filter.isEnabled())
            {
                categorisedItems.addAll(filter.getItems());
            }
        }
        container.itemList.clear();
        categorisedItems.forEach(item -> container.itemList.add(new ItemStack(item)));
        container.itemList.sort(Comparator.comparingInt(o -> Item.getIdFromItem(o.getItem())));
        container.scrollTo(0);
    }

    private void compileItems()
    {
        final TagFilter GENERAL = new TagFilter(new ResourceLocation(Reference.MOD_ID, "general"), new ItemStack(ModBlocks.CHAIR_OAK));
        final TagFilter STORAGE = new TagFilter(new ResourceLocation(Reference.MOD_ID, "storage"), new ItemStack(ModBlocks.CABINET_OAK));
        final TagFilter BEDROOM = new TagFilter(new ResourceLocation(Reference.MOD_ID, "bedroom"), new ItemStack(ModBlocks.DESK_OAK));
        final TagFilter OUTDOORS = new TagFilter(new ResourceLocation(Reference.MOD_ID, "outdoors"), new ItemStack(ModBlocks.MAIL_BOX_OAK));
        TagFilter[] filters = new TagFilter[] {GENERAL, STORAGE, BEDROOM, OUTDOORS};

        ForgeRegistries.ITEMS.getValues().stream()
            .filter(item -> item.getGroup() == FurnitureMod.GROUP)
            .filter(item -> item.getRegistryName().getNamespace().equals(Reference.MOD_ID))
            .forEach(item ->
            {
                item.getTags().forEach(location ->
                {
                    for(TagFilter filter : filters)
                    {
                        if(location.equals(filter.getTag()))
                        {
                            filter.add(item);
                        }
                    }
                });
            });

        this.filters = new ArrayList<>();
        this.filters.addAll(Arrays.asList(filters));
    }

    /**
     * Author: MrCrayfish
     */
    public static class TagFilter
    {
        private ResourceLocation tag;
        private String translationKey;
        private ItemStack icon;
        private boolean enabled = true;
        private List<Item> items = Lists.newArrayList();

        public TagFilter(ResourceLocation tag, ItemStack icon)
        {
            this.tag = tag;
            this.translationKey = String.format("gui.tag_filter.%s.%s", tag.getNamespace(), tag.getPath().replace("/", "."));
            this.icon = icon;
        }

        public ResourceLocation getTag()
        {
            return tag;
        }

        public ItemStack getIcon()
        {
            return this.icon;
        }

        public String getName()
        {
            return I18n.format(this.translationKey);
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean isEnabled()
        {
            return enabled;
        }

        public void add(Item item)
        {
            items.add(item);
        }

        public void add(Block block)
        {
            items.add(Item.getItemFromBlock(block));
        }

        public List<Item> getItems()
        {
            return items;
        }
    }
}
