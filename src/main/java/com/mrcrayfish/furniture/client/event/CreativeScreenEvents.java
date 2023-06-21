package com.mrcrayfish.furniture.client.event;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.ClientHandler;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.client.gui.widget.button.TagButton;
import com.mrcrayfish.furniture.common.ModTags;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * Author: MrCrayfish
 */
public class CreativeScreenEvents
{
    private static final ResourceLocation ICONS = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");
    private static int startIndex;
    private static CreativeModeTab lastTab = CreativeModeTabs.getDefaultTab();

    private List<TagFilter> filters;
    private List<TagButton> buttons;
    private Button btnScrollUp;
    private Button btnScrollDown;
    private Button btnEnableAll;
    private Button btnDisableAll;
    private int guiCenterX = 0;
    private int guiCenterY = 0;

    @SubscribeEvent
    public void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event)
    {
        this.filters = null;
    }

    @SubscribeEvent
    public void onScreenInit(ScreenEvent.Init.Post event)
    {
        if(event.getScreen() instanceof CreativeModeInventoryScreen creativeScreen)
        {
            if(this.filters == null)
            {
                this.compileItems();
            }

            this.guiCenterX = creativeScreen.getGuiLeft();
            this.guiCenterY = creativeScreen.getGuiTop();
            this.buttons = this.createTagButtons();
            this.buttons.forEach(event::addListener);

            event.addListener(this.btnScrollUp = new IconButton(this.guiCenterX - 22, this.guiCenterY - 12, button -> {
                if(startIndex > 0) startIndex--;
                this.updateTagButtons();
            }, ICONS, 64, 0));

            event.addListener(this.btnScrollDown = new IconButton(this.guiCenterX - 22, this.guiCenterY + 127, button -> {
                if(startIndex <= filters.size() - 4 - 1) startIndex++;
                this.updateTagButtons();
            }, ICONS, 80, 0));

            event.addListener(this.btnEnableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 10, button -> {
                this.filters.forEach(filters -> filters.setEnabled(true));
                this.buttons.forEach(TagButton::updateState);
                Screen screen = Minecraft.getInstance().screen;
                if(screen instanceof CreativeModeInventoryScreen)
                {
                    this.updateItems((CreativeModeInventoryScreen) screen);
                }
            }, ICONS, 96, 0));
            this.btnEnableAll.setTooltip(Tooltip.create(Component.translatable("gui.button.cfm.enable_filters")));

            event.addListener(this.btnDisableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 32, button -> {
                this.filters.forEach(filters -> filters.setEnabled(false));
                this.buttons.forEach(TagButton::updateState);
                Screen screen = Minecraft.getInstance().screen;
                if(screen instanceof CreativeModeInventoryScreen)
                {
                    this.updateItems((CreativeModeInventoryScreen) screen);
                }
            }, ICONS, 112, 0));
            this.btnDisableAll.setTooltip(Tooltip.create(Component.translatable("gui.button.cfm.disable_filters")));

            this.onSwitchCreativeTab(CreativeModeInventoryScreen.selectedTab, creativeScreen);
        }
    }

    @SubscribeEvent
    public void onScreenDrawPost(ScreenEvent.Render.Post event)
    {
        if(event.getScreen() instanceof CreativeModeInventoryScreen creativeScreen)
        {
            this.guiCenterX = creativeScreen.getGuiLeft();
            this.guiCenterY = creativeScreen.getGuiTop();

            CreativeModeTab tab = CreativeModeInventoryScreen.selectedTab;
            if(lastTab != tab)
            {
                this.onSwitchCreativeTab(tab, creativeScreen);
                lastTab = tab;
            }
        }
    }

    private void onSwitchCreativeTab(CreativeModeTab tab, CreativeModeInventoryScreen screen)
    {
        if(tab == ClientHandler.creativeTab)
        {
            this.btnScrollUp.visible = true;
            this.btnScrollDown.visible = true;
            this.btnEnableAll.visible = true;
            this.btnDisableAll.visible = true;
            this.updateTagButtons();
            this.updateItems(screen);
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

    private List<TagButton> createTagButtons()
    {
        List<TagButton> tagButtons = new ArrayList<>();
        for(TagFilter filter : this.filters)
        {
            TagButton tagButton = new TagButton(this.guiCenterX - 28, this.guiCenterY, filter, button -> {
                Screen screen = Minecraft.getInstance().screen;
                if(screen instanceof CreativeModeInventoryScreen creativeScreen)
                {
                    this.updateItems(creativeScreen);
                }
            });
            tagButton.visible = false;
            tagButtons.add(tagButton);
        }
        return tagButtons;
    }

    private void updateTagButtons()
    {
        this.buttons.forEach(button -> button.visible = false);
        for(int i = startIndex; i < startIndex + 4 && i < this.buttons.size(); i++)
        {
            TagButton button = this.buttons.get(i);
            button.setY(this.guiCenterY + 29 * (i - startIndex) + 11);
            button.visible = true;
        }
        this.btnScrollUp.active = startIndex > 0;
        this.btnScrollDown.active = startIndex <= this.filters.size() - 4 - 1;
    }

    private void updateItems(CreativeModeInventoryScreen screen)
    {
        CreativeModeInventoryScreen.ItemPickerMenu menu = screen.getMenu();
        LinkedHashSet<Item> categorisedItems = new LinkedHashSet<>();
        for(TagFilter filter : this.filters)
        {
            if(filter.isEnabled())
            {
                categorisedItems.addAll(filter.getItems());
            }
        }

        NonNullList<ItemStack> newItems = NonNullList.create();
        for(Item item : categorisedItems)
        {
            newItems.add(new ItemStack(item));
        }

        menu.items.clear();
        menu.items.addAll(newItems);
        menu.items.sort(Comparator.comparingInt(o -> Item.getId(o.getItem())));
        menu.scrollTo(0);
    }

    private void compileItems()
    {
        TagFilter[] filters = new TagFilter[] {
            new TagFilter(ModTags.Items.GENERAL, new ItemStack(ModBlocks.CHAIR_OAK.get())),
            new TagFilter(ModTags.Items.STORAGE, new ItemStack(ModBlocks.CABINET_OAK.get())),
            new TagFilter(ModTags.Items.BEDROOM, new ItemStack(ModBlocks.DESK_OAK.get())),
            new TagFilter(ModTags.Items.OUTDOORS, new ItemStack(ModBlocks.MAIL_BOX_OAK.get())),
            new TagFilter(ModTags.Items.KITCHEN, new ItemStack(ModBlocks.KITCHEN_COUNTER_CYAN.get())),
            new TagFilter(ModTags.Items.ITEMS, new ItemStack(ModItems.SPATULA.get()))
        };

        ForgeRegistries.ITEMS.getValues().stream()
            .filter(item -> Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getNamespace().equals(Reference.MOD_ID))
            .forEach(item -> {
                ForgeRegistries.ITEMS.getHolder(item).ifPresent(holder -> {
                    holder.tags().forEach(tagKey -> {
                        for(TagFilter filter : filters) {
                            if(tagKey == filter.getTag()) {
                                filter.add(item);
                            }
                        }
                    });
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
        private final List<Item> items = Lists.newArrayList();
        private final TagKey<Item> tag;
        private final Component name;
        private final ItemStack icon;
        private boolean enabled = true;

        public TagFilter(TagKey<Item> tag, ItemStack icon)
        {
            this.tag = tag;
            this.name = Component.translatable(String.format("gui.tag_filter.%s.%s", tag.location().getNamespace(), tag.location().getPath().replace("/", ".")));
            this.icon = icon;
        }

        public TagKey<Item> getTag()
        {
            return tag;
        }

        public ItemStack getIcon()
        {
            return this.icon;
        }

        public Component getName()
        {
            return this.name;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean isEnabled()
        {
            return this.enabled;
        }

        public void add(Item item)
        {
            this.items.add(item);
        }

        public void add(Block block)
        {
            this.items.add(Item.byBlock(block));
        }

        public List<Item> getItems()
        {
            return this.items;
        }
    }
}
