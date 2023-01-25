package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.MailBoxEntry;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.inventory.container.PostBoxMenu;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.C2SMessageSendMail;
import com.mrcrayfish.furniture.util.RenderUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

/**
 * Author: MrCrayfish
 */
public class PostBoxScreen extends AbstractContainerScreen<PostBoxMenu>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/post_box.png");
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");
    private static final int LIST_WIDTH = 116;
    private static final int LIST_HEIGHT = 57;
    private static final int SCROLL_BAR_WIDTH = 12;
    private static final int SCROLL_BAR_HEIGHT = 15;
    private static final int ITEM_WIDTH = 116;
    private static final int ITEM_HEIGHT = 24;
    private static final int MAX_VISIBLE_ITEMS = (int) Math.ceil((double) LIST_HEIGHT / (double) ITEM_HEIGHT) + 1;

    private EditBox searchField;
    private int scroll;
    private int pressedMouseY = -1;
    private MailBoxEntry selected;
    private Button btnSend;

    private final List<MailBoxEntry> mailBoxList;
    private List<MailBoxEntry> filteredMailBoxList = new ArrayList<>();

    public PostBoxScreen(PostBoxMenu container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);
        this.imageHeight = 187;
        this.mailBoxList = container.getMailBoxes();
        this.filteredMailBoxList.addAll(this.mailBoxList);
    }

    @Override
    protected void init()
    {
        super.init();
        this.searchField = new EditBox(this.font, this.leftPos + 22, this.topPos + 19, 101, 9, Component.translatable("gui.cfm.post_box.search"));
        this.searchField.setBordered(false);
        this.searchField.setMaxLength(32);
        this.searchField.setTextColor(16777215);
        this.addWidget(this.searchField);
        this.btnSend = this.addRenderableWidget(new IconButton(this.leftPos + 147, this.topPos + 53, Component.translatable("gui.button.cfm.send_mail"), this::sendMail, ICONS_TEXTURE, 32, 0));
        this.btnSend.active = false;
    }

    private void sendMail(Button button)
    {
        if(this.selected != null && !this.menu.getMail().isEmpty())
        {
            PacketHandler.getPlayChannel().sendToServer(new C2SMessageSendMail(this.selected.getOwnerId(), this.selected.getMailBoxId()));
        }
    }

    @Override
    public void containerTick()
    {
        this.searchField.tick();
        this.btnSend.active = this.selected != null && !this.menu.getMail().isEmpty();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int startX = (this.width - this.imageWidth) / 2;
        int startY = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, startX, startY, 0, 0, this.imageWidth, this.imageHeight);

        if(this.menu.getMail().isEmpty())
        {
            this.blit(poseStack, startX + 149, startY + 33, 116, 202, 16, 16);
        }

        this.searchField.render(poseStack,mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY)
    {
        this.font.draw(poseStack, this.title.getString(), 8.0F, 6.0F, 0x404040);
        this.font.draw(poseStack, this.playerInventoryTitle, 8.0F, (float) (this.imageHeight - 96 + 2), 0x404040);

        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int scrollBarY = this.getScrollBarY(mouseY);
        int scrollBarUOffset = this.getMaxScroll() <= 0 ? SCROLL_BAR_WIDTH : 0;
        this.blit(poseStack, 128, 32 + scrollBarY, 116 + scrollBarUOffset, 187, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);

        RenderUtil.scissor(this.leftPos + 8, this.topPos + 32, 116, 57);
        {
            int scroll = this.scroll;
            if(this.pressedMouseY != -1)
            {
                scroll = (int) (this.getMaxScroll() * (scrollBarY / (double) (LIST_HEIGHT - SCROLL_BAR_HEIGHT)) + 0.5);
            }
            int startIndex = scroll / ITEM_HEIGHT;
            for(int i = startIndex; i < Math.min(startIndex + MAX_VISIBLE_ITEMS, this.filteredMailBoxList.size()); i++)
            {
                poseStack.pushPose();
                poseStack.translate(8, 32, 0);
                poseStack.translate(0, -scroll, 0);
                poseStack.translate(0, i * ITEM_HEIGHT, 0);

                MailBoxEntry entry = this.filteredMailBoxList.get(i);

                RenderSystem.setShaderTexture(0, GUI_TEXTURE);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                boolean isSelected = entry == selected;
                this.blit(poseStack, 0, 0, 0, 211 - (isSelected ? ITEM_HEIGHT : 0), ITEM_WIDTH, ITEM_HEIGHT);

                if(isSelected)
                {
                    this.blit(poseStack, ITEM_WIDTH - 20, 5, 140, 187, 14, 12);
                    this.font.draw(poseStack, ChatFormatting.BOLD + entry.getName(), 3, 3, 16777045);
                    this.font.draw(poseStack, entry.getOwnerName(), 3, 13, 0xFFFFFF);
                }
                else
                {
                    this.font.draw(poseStack, entry.getName(), 3, 3, 0xFFFFFF);
                    this.font.draw(poseStack, entry.getOwnerName(), 3, 13, 0x777777);
                }

                poseStack.popPose();
            }
        }
        RenderUtil.endScissor();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        super.render(poseStack,mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(RenderUtil.isMouseInArea((int) mouseX, (int) mouseY, this.leftPos + 8, this.topPos + 32, 116, 57))
        {
            int clickedIndex = (int) ((mouseY - this.topPos - 32 + scroll) / ITEM_HEIGHT);
            if(clickedIndex >= 0 && clickedIndex < this.filteredMailBoxList.size())
            {
                MailBoxEntry entry = this.filteredMailBoxList.get(clickedIndex);
                this.selected = this.selected == entry ? null : entry;
                return true;
            }
        }
        int scrollBarY = (int) ((LIST_HEIGHT - SCROLL_BAR_HEIGHT) * (scroll / (double) this.getMaxScroll()));
        if(this.getMaxScroll() > 0 && RenderUtil.isMouseInArea((int) mouseX, (int) mouseY, this.leftPos + 128, this.topPos + 32 + scrollBarY, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT))
        {
            this.pressedMouseY = (int) mouseY;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if(this.pressedMouseY != -1 && button == GLFW_MOUSE_BUTTON_LEFT)
        {
            this.scroll = (int) (this.getMaxScroll() * (this.getScrollBarY((int) mouseY) / (double) (LIST_HEIGHT - SCROLL_BAR_HEIGHT)) + 0.5);
            this.pressedMouseY = -1;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double speed)
    {
        if(RenderUtil.isMouseInArea((int) mouseX, (int) mouseY, this.leftPos + 8, this.topPos + 32, 116, 57))
        {
            this.scroll = (int) Math.max(0, Math.min(this.getMaxScroll(), this.scroll - (speed * 10)));
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(char c, int code)
    {
        String s = this.searchField.getValue();
        if(this.searchField.charTyped(c, code))
        {
            if(!Objects.equals(s, this.searchField.getValue()))
            {
                this.updateMailBoxList();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int mods)
    {
        String s = this.searchField.getValue();
        if(this.searchField.keyPressed(key, scanCode, mods))
        {
            if(!Objects.equals(s, this.searchField.getValue()))
            {
                this.updateMailBoxList();
            }
            return true;
        }
        return this.searchField.isFocused() && this.searchField.isVisible() && key != GLFW_KEY_ESCAPE || super.keyPressed(key, scanCode, mods);
    }

    private void updateMailBoxList()
    {
        if(this.searchField.getValue().isEmpty())
        {
            this.filteredMailBoxList = this.mailBoxList;
        }
        else
        {
            Stream<MailBoxEntry> stream = this.mailBoxList.stream().filter(entry ->
            {
                String searchText = this.searchField.getValue().toLowerCase(Locale.ENGLISH).trim();
                if(entry.getName().toLowerCase().contains(searchText))
                {
                    return true;
                }
                return entry.getOwnerName().toLowerCase().contains(searchText);
            });
            this.filteredMailBoxList = stream.collect(Collectors.toList());
        }
    }

    private int getScrollBarY(int mouseY)
    {
        int scrollOffset = 0;
        if(this.pressedMouseY != -1)
        {
            scrollOffset = (mouseY - pressedMouseY);
        }
        int scrollBarY = (int) ((LIST_HEIGHT - SCROLL_BAR_HEIGHT) * (scroll / (double) this.getMaxScroll()));
        return Mth.clamp(scrollBarY + scrollOffset, 0, LIST_HEIGHT - SCROLL_BAR_HEIGHT);
    }

    private int getMaxScroll()
    {
        return Math.max(0, ITEM_HEIGHT * this.filteredMailBoxList.size() - LIST_HEIGHT);
    }
}
