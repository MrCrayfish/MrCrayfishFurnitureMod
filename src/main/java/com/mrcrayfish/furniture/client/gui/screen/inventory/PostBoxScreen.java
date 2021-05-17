package com.mrcrayfish.furniture.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.MailBoxEntry;
import com.mrcrayfish.furniture.client.gui.widget.button.IconButton;
import com.mrcrayfish.furniture.inventory.container.PostBoxContainer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageRequestMailBoxes;
import com.mrcrayfish.furniture.network.message.MessageSendMail;
import com.mrcrayfish.furniture.util.RenderUtil;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.opengl.GL11;

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
public class PostBoxScreen extends ContainerScreen<PostBoxContainer>
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

    private TextFieldWidget searchField;
    private int scroll;
    private int pressedMouseY = -1;
    private MailBoxEntry selected;
    private Button btnSend;

    private List<MailBoxEntry> mailBoxList = new ArrayList<>();
    private List<MailBoxEntry> filteredMailBoxList = new ArrayList<>();

    public PostBoxScreen(PostBoxContainer container, PlayerInventory playerInventory, ITextComponent title)
    {
        super(container, playerInventory, title);
        this.ySize = 187;
    }

    @Override
    protected void init()
    {
        super.init();
        this.searchField = new TextFieldWidget(this.font, this.guiLeft + 22, this.guiTop + 19, 101, 9, new TranslationTextComponent("gui.cfm.post_box.search"));
        this.searchField.setEnableBackgroundDrawing(false);
        this.searchField.setMaxStringLength(32);
        this.searchField.setTextColor(16777215);
        this.children.add(this.searchField);
        this.btnSend = this.addButton(new IconButton(this.guiLeft + 147, this.guiTop + 53, new TranslationTextComponent("gui.button.cfm.send_mail"), this::sendMail, ICONS_TEXTURE, 32, 0));
        this.btnSend.active = false;
        PacketHandler.instance.sendToServer(new MessageRequestMailBoxes());
    }

    private void sendMail(Button button)
    {
        if(this.selected != null && !this.container.getMail().isEmpty())
        {
            PacketHandler.instance.sendToServer(new MessageSendMail(this.selected.getOwnerId(), this.selected.getMailBoxId()));
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        this.searchField.tick();
        this.btnSend.active = this.selected != null && !this.container.getMail().isEmpty();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int startX = (this.width - this.xSize) / 2;
        int startY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, startX, startY, 0, 0, this.xSize, this.ySize);

        if(this.container.getMail().isEmpty())
        {
            this.blit(matrixStack, startX + 149, startY + 33, 116, 202, 16, 16);
        }

        this.searchField.render(matrixStack,mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 6.0F, 0x404040);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);

        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int scrollBarY = this.getScrollBarY(mouseY);
        int scrollBarUOffset = this.getMaxScroll() <= 0 ? SCROLL_BAR_WIDTH : 0;
        this.blit(matrixStack, 128, 32 + scrollBarY, 116 + scrollBarUOffset, 187, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        {
            RenderUtil.scissor(this.guiLeft + 8, this.guiTop + 32, 116, 57);

            int scroll = this.scroll;
            if(this.pressedMouseY != -1)
            {
                scroll = (int) (this.getMaxScroll() * (scrollBarY / (double) (LIST_HEIGHT - SCROLL_BAR_HEIGHT)) + 0.5);
            }
            int startIndex = scroll / ITEM_HEIGHT;
            for(int i = startIndex; i < Math.min(startIndex + MAX_VISIBLE_ITEMS, this.filteredMailBoxList.size()); i++)
            {
                RenderSystem.pushMatrix();
                RenderSystem.translatef(8, 32, 0);
                RenderSystem.translatef(0, -scroll, 0);
                RenderSystem.translatef(0, i * ITEM_HEIGHT, 0);

                MailBoxEntry entry = this.filteredMailBoxList.get(i);
                this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);

                boolean isSelected = entry == selected;
                this.blit(matrixStack, 0, 0, 0, 211 - (isSelected ? ITEM_HEIGHT : 0), ITEM_WIDTH, ITEM_HEIGHT);

                if(isSelected)
                {
                    this.blit(matrixStack, ITEM_WIDTH - 20, 5, 140, 187, 14, 12);
                    this.font.drawString(matrixStack, TextFormatting.BOLD + entry.getName(), 3, 3, 16777045);
                    this.font.drawString(matrixStack, entry.getOwnerName(), 3, 13, 0xFFFFFF);
                }
                else
                {
                    this.font.drawString(matrixStack, entry.getName(), 3, 3, 0xFFFFFF);
                    this.font.drawString(matrixStack, entry.getOwnerName(), 3, 13, 0x777777);
                }

                RenderSystem.popMatrix();
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if(RenderUtil.isMouseInArea((int) mouseX, (int) mouseY, this.guiLeft + 8, this.guiTop + 32, 116, 57))
        {
            int clickedIndex = (int) ((mouseY - this.guiTop - 32 + scroll) / ITEM_HEIGHT);
            if(clickedIndex >= 0 && clickedIndex < this.filteredMailBoxList.size())
            {
                MailBoxEntry entry = this.filteredMailBoxList.get(clickedIndex);
                this.selected = this.selected == entry ? null : entry;
                return true;
            }
        }
        int scrollBarY = (int) ((LIST_HEIGHT - SCROLL_BAR_HEIGHT) * (scroll / (double) this.getMaxScroll()));
        if(this.getMaxScroll() > 0 && RenderUtil.isMouseInArea((int) mouseX, (int) mouseY, this.guiLeft + 128, this.guiTop + 32 + scrollBarY, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT))
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
        if(RenderUtil.isMouseInArea((int) mouseX, (int) mouseY, this.guiLeft + 8, this.guiTop + 32, 116, 57))
        {
            this.scroll = (int) Math.max(0, Math.min(this.getMaxScroll(), this.scroll - (speed * 10)));
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(char c, int code)
    {
        String s = this.searchField.getText();
        if(this.searchField.charTyped(c, code))
        {
            if(!Objects.equals(s, this.searchField.getText()))
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
        String s = this.searchField.getText();
        if(this.searchField.keyPressed(key, scanCode, mods))
        {
            if(!Objects.equals(s, this.searchField.getText()))
            {
                this.updateMailBoxList();
            }
            return true;
        }
        return this.searchField.isFocused() && this.searchField.getVisible() && key != GLFW_KEY_ESCAPE || super.keyPressed(key, scanCode, mods);
    }

    private void updateMailBoxList()
    {
        if(this.searchField.getText().isEmpty())
        {
            this.filteredMailBoxList = this.mailBoxList;
        }
        else
        {
            Stream<MailBoxEntry> stream = this.mailBoxList.stream().filter(entry ->
            {
                String searchText = this.searchField.getText().toLowerCase(Locale.ENGLISH).trim();
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
        return MathHelper.clamp(scrollBarY + scrollOffset, 0, LIST_HEIGHT - SCROLL_BAR_HEIGHT);
    }

    private int getMaxScroll()
    {
        return Math.max(0, ITEM_HEIGHT * this.filteredMailBoxList.size() - LIST_HEIGHT);
    }

    public void updateMailBoxes(List<MailBoxEntry> entries)
    {
        this.mailBoxList = entries;
        this.filteredMailBoxList = entries;
    }
}
