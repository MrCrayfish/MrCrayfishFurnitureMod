package com.mrcrayfish.furniture.client.event;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.client.gui.widget.button.TagButton;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

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
    public void onPlayerLogout(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        this.filters = null;
    }

    @SubscribeEvent
    public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if(event.getGui() instanceof CreativeModeInventoryScreen creativeScreen)
        {
            if(this.filters == null)
            {
                this.compileItems();
            }

            this.viewingFurnitureTab = false;
            this.guiCenterX = creativeScreen.getGuiLeft();
            this.guiCenterY = creativeScreen.getGuiTop();
            this.buttons = new ArrayList<>();

            event.addWidget(this.btnScrollUp = new IconButton(this.guiCenterX - 22, this.guiCenterY - 12, new TranslatableComponent("gui.button.cfm.scroll_filters_up"), button -> {
                if(startIndex > 0) startIndex--;
                this.updateTagButtons();
            }, ICONS, 64, 0));

            event.addWidget(this.btnScrollDown = new IconButton(this.guiCenterX - 22, this.guiCenterY + 127, new TranslatableComponent("gui.button.cfm.scroll_filters_down"), button -> {
                if(startIndex <= filters.size() - 4 - 1) startIndex++;
                this.updateTagButtons();
            }, ICONS, 80, 0));

            event.addWidget(this.btnEnableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 10, new TranslatableComponent("gui.button.cfm.enable_filters"), button -> {
                this.filters.forEach(filters -> filters.setEnabled(true));
                this.buttons.forEach(TagButton::updateState);
                Screen screen = Minecraft.getInstance().screen;
                if(screen instanceof CreativeModeInventoryScreen)
                {
                    this.updateItems((CreativeModeInventoryScreen) screen);
                }
            }, ICONS, 96, 0));

            event.addWidget(this.btnDisableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 32, new TranslatableComponent("gui.button.cfm.disable_filters"), button -> {
                this.filters.forEach(filters -> filters.setEnabled(false));
                this.buttons.forEach(TagButton::updateState);
                Screen screen = Minecraft.getInstance().screen;
                if(screen instanceof CreativeModeInventoryScreen)
                {
                    this.updateItems((CreativeModeInventoryScreen) screen);
                }
            }, ICONS, 112, 0));

            this.btnScrollUp.visible = false;
            this.btnScrollDown.visible = false;
            this.btnEnableAll.visible = false;
            this.btnDisableAll.visible = false;

            this.updateTagButtons();

            if(creativeScreen.getSelectedTab() == FurnitureMod.GROUP.getId())
            {
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.btnEnableAll.visible = true;
                this.btnDisableAll.visible = true;
                this.viewingFurnitureTab = true;
                this.buttons.forEach(button -> button.visible = true);
                this.updateItems(creativeScreen);
            }
        }
    }

    @SubscribeEvent
    public void onScreenClick(GuiScreenEvent.MouseClickedEvent.Pre event)
    {
        if(event.getButton() != GLFW.GLFW_MOUSE_BUTTON_LEFT)
            return;

        if(event.getGui() instanceof CreativeModeInventoryScreen)
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
        if(event.getGui() instanceof CreativeModeInventoryScreen creativeScreen)
        {
            if(creativeScreen.getSelectedTab() == FurnitureMod.GROUP.getId())
            {
                if(!this.viewingFurnitureTab)
                {
                    this.updateItems(creativeScreen);
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
        if(event.getGui() instanceof CreativeModeInventoryScreen creativeScreen)
        {
            this.guiCenterX = creativeScreen.getGuiLeft();
            this.guiCenterY = creativeScreen.getGuiTop();

            if(creativeScreen.getSelectedTab() == FurnitureMod.GROUP.getId())
            {
                this.btnScrollUp.visible = true;
                this.btnScrollDown.visible = true;
                this.btnEnableAll.visible = true;
                this.btnDisableAll.visible = true;
                this.buttons.forEach(button -> button.visible = true);

                /* Render buttons */
                this.buttons.forEach(button ->
                {
                    button.render(event.getMatrixStack(), event.getMouseX(), event.getMouseY(), event.getRenderPartialTicks());
                });

                /* Render tooltips after so it renders above buttons */
                this.buttons.forEach(button ->
                {
                    if(button.isMouseOver(event.getMouseX(), event.getMouseY()))
                    {
                        creativeScreen.renderTooltip(event.getMatrixStack(), button.getCategory().getName(), event.getMouseX(), event.getMouseY());
                    }
                });

                if(this.btnEnableAll.isMouseOver(event.getMouseX(), event.getMouseY()))
                {
                    creativeScreen.renderTooltip(event.getMatrixStack(), this.btnEnableAll.getMessage(), event.getMouseX(), event.getMouseY());
                }

                if(this.btnDisableAll.isMouseOver(event.getMouseX(), event.getMouseY()))
                {
                    creativeScreen.renderTooltip(event.getMatrixStack(), this.btnDisableAll.getMessage(), event.getMouseX(), event.getMouseY());
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
        final Button.OnPress onPress = button ->
        {
            Screen screen = Minecraft.getInstance().screen;
            if(screen instanceof CreativeModeInventoryScreen creativeScreen)
            {
                this.updateItems(creativeScreen);
            }
        };
        this.buttons.clear();
        for(int i = startIndex; i < startIndex + 4 && i < this.filters.size(); i++)
        {
            TagButton button = new TagButton(this.guiCenterX - 28, this.guiCenterY + 29 * (i - startIndex) + 10, this.filters.get(i), onPress);
            this.buttons.add(button);
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
            item.fillItemCategory(FurnitureMod.GROUP, newItems);
        }

        menu.items.clear();
        menu.items.addAll(newItems);
        menu.items.sort(Comparator.comparingInt(o -> Item.getId(o.getItem())));
        menu.scrollTo(0);
    }

    private void compileItems()
    {
        final TagFilter GENERAL = new TagFilter(new ResourceLocation(Reference.MOD_ID, "general"), new ItemStack(ModBlocks.CHAIR_OAK.get()));
        final TagFilter STORAGE = new TagFilter(new ResourceLocation(Reference.MOD_ID, "storage"), new ItemStack(ModBlocks.CABINET_OAK.get()));
        final TagFilter BEDROOM = new TagFilter(new ResourceLocation(Reference.MOD_ID, "bedroom"), new ItemStack(ModBlocks.DESK_OAK.get()));
        final TagFilter OUTDOORS = new TagFilter(new ResourceLocation(Reference.MOD_ID, "outdoors"), new ItemStack(ModBlocks.MAIL_BOX_OAK.get()));
        final TagFilter KITCHEN = new TagFilter(new ResourceLocation(Reference.MOD_ID, "kitchen"), new ItemStack(ModBlocks.KITCHEN_COUNTER_CYAN.get()));
        final TagFilter ITEMS = new TagFilter(new ResourceLocation(Reference.MOD_ID, "items"), new ItemStack(ModItems.SPATULA.get()));
        TagFilter[] filters = new TagFilter[] {GENERAL, STORAGE, BEDROOM, OUTDOORS, KITCHEN, ITEMS};

        ForgeRegistries.ITEMS.getValues().stream()
            .filter(item -> item.getItemCategory() == FurnitureMod.GROUP)
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
        private final List<Item> items = Lists.newArrayList();
        private final ResourceLocation tag;
        private final TranslatableComponent name;
        private final ItemStack icon;
        private boolean enabled = true;

        public TagFilter(ResourceLocation tag, ItemStack icon)
        {
            this.tag = tag;
            this.name = new TranslatableComponent(String.format("gui.tag_filter.%s.%s", tag.getNamespace(), tag.getPath().replace("/", ".")));
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

        public TranslatableComponent getName()
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
