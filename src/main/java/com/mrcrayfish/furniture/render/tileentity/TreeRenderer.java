package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityTree;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class TreeRenderer extends TileEntitySpecialRenderer<TileEntityTree>
{
    private EntityItem ornament = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityTree tree, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        Block block = tree.getWorld().getBlockState(tree.getPos()).getBlock();

        float yOffset = 0.0F;
        float spread = 0.3F;

        if(block == FurnitureBlocks.tree_bottom)
        {
            spread = 0.45F;
            yOffset = 0.5F;
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + yOffset, (float) z + 0.5F);
        GL11.glRotatef(90, 0, 1, 0);

        for(int i = 0; i < tree.getSize(); i++)
        {
            ItemStack item = tree.getItem(i);
            if(item != null)
            {
                this.ornament.setItem(item);
                this.ornament.hoverStart = 0.0F;

                GL11.glPushMatrix();

                GL11.glDisable(GL11.GL_LIGHTING);
                //WorldRenderer renderer = Tessellator.getInstance().getWorldRenderer();
                //renderer.setBrightness(15728880);

                GL11.glRotatef(-90 * i, 0, 1, 0);
                GL11.glTranslatef(spread, 0.0F, 0.0F);
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glRotatef(-15, 1, 0, 0);
                GL11.glScalef(0.9F, 0.9F, 0.9F);
                Minecraft.getMinecraft().getRenderManager().renderEntity(ornament, 0.0D, 0.0D, 0.0D, 180.0F, 0.0F, false);

                GL11.glEnable(GL11.GL_LIGHTING);

                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();
    }
}
