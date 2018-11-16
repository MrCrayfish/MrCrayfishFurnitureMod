package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.gui.containers.ContainerComputer;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageMineBayBuy;
import com.mrcrayfish.furniture.network.message.MessageMineBayClosed;
import com.mrcrayfish.furniture.tileentity.TileEntityComputer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiComputer extends GuiContainer
{
    private static final ResourceLocation gui = new ResourceLocation("cfm:textures/gui/computer.png");

    private GuiButton left, right;
    private GuiButton button_buy;

    private int itemNum;
    private ItemStack buySlot;
    private TileEntityComputer tileEntityComputer;
    private RecipeData[] itemdata;

    public GuiComputer(InventoryPlayer inventoryplayer, TileEntityComputer tileEntityComputer)
    {
        super(new ContainerComputer(inventoryplayer, tileEntityComputer));
        this.tileEntityComputer = tileEntityComputer;
        this.xSize = 176;
        this.ySize = 187;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(false);

        buttonList.clear();

        int posX = width / 2;
        int posY = height / 2;

        left = new GuiButton(0, posX - 48, posY - 80, 15, 20, "<");
        right = new GuiButton(1, posX + 34, posY - 80, 15, 20, ">");
        button_buy = new GuiButton(2, posX - 48, posY - 57, 29, 20, I18n.format("cfm.button.buy"));

        buttonList.add(left);
        buttonList.add(right);
        buttonList.add(button_buy);

        this.itemNum = tileEntityComputer.getBrowsingInfo();
        itemdata = Recipes.getMineBayItems();
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 0)
        {
            itemNum--;
            if(itemNum < 0)
            {
                itemNum = 0;
            }
            this.tileEntityComputer.setBrowsingInfo(itemNum);
        }
        if(guibutton.id == 1)
        {
            itemNum++;
            if(itemNum > itemdata.length - 1)
            {
                itemNum = itemdata.length - 1;
            }
            this.tileEntityComputer.setBrowsingInfo(itemNum);
        }
        if(guibutton.id == 2)
        {
            this.buySlot = this.tileEntityComputer.getStackInSlot(0);
            if(!buySlot.isEmpty())
            {
                ItemStack money = itemdata[itemNum].getCurrency();
                if(buySlot.getItem() == money.getItem())
                {
                    if(buySlot.getItemDamage() == money.getItemDamage())
                    {
                        PacketHandler.INSTANCE.sendToServer(new MessageMineBayBuy(this.itemNum, this.tileEntityComputer.getPos().getX(), this.tileEntityComputer.getPos().getY(), this.tileEntityComputer.getPos().getZ()));
                    }
                }
            }
        }
    }

    @Override
    public void onGuiClosed()
    {
        PacketHandler.INSTANCE.sendToServer(new MessageMineBayClosed(tileEntityComputer.getPos().getX(), tileEntityComputer.getPos().getY(), tileEntityComputer.getPos().getZ()));
        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, (ySize - 103), 4210752);

        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        itemRender.zLevel = 100.0F;

        if((itemNum - 1) >= 0)
        {
            ItemStack pre = itemdata[itemNum - 1].getInput();
            itemRender.renderItemAndEffectIntoGUI(pre, 57, 16);
            itemRender.renderItemOverlays(this.fontRenderer, pre, 57, 16);
        }

        ItemStack stock = itemdata[itemNum].getInput();
        itemRender.renderItemAndEffectIntoGUI(stock, 80, 16);
        itemRender.renderItemOverlays(this.fontRenderer, stock, 80, 16);

        if((itemNum + 1) < itemdata.length)
        {
            ItemStack post = itemdata[itemNum + 1].getInput();
            itemRender.renderItemAndEffectIntoGUI(post, 103, 16);
            itemRender.renderItemOverlays(this.fontRenderer, post, 103, 16);
        }

        ItemStack currency = itemdata[itemNum].getCurrency();
        itemRender.renderItemAndEffectIntoGUI(currency, 73, 40);
        itemRender.renderItemOverlays(this.fontRenderer, currency, 73, 40);
        itemRender.zLevel = 0.0F;
        GL11.glDisable(GL11.GL_LIGHTING);

        int price = itemdata[itemNum].getPrice();
        this.fontRenderer.drawString("x" + Integer.toString(price), 90, 44, 0);

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        ItemStack stock = itemdata[itemNum].getInput();
        if(this.isPointInRegion(80, 16, 16, 16, mouseX, mouseY))
        {
            this.renderToolTip(stock, mouseX, mouseY);
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(gui);
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
    }
}
