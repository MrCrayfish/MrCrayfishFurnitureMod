package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityGrill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GrillRenderer extends TileEntitySpecialRenderer<TileEntityGrill>
{
    private ItemStack coal = new ItemStack(Items.COAL, 1, 1);
    private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D, coal);

    private final float MAX_ANIM_TIME = 100F;
    private final float FLIP_HEIGHT = 0.5F;

    @Override
    public void render(TileEntityGrill tileEntityGrill, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        entityItem.hoverStart = 0;

        int rotation = tileEntityGrill.getBlockMetadata();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0.85, 0.5);
            GlStateManager.rotate(rotation * -90F, 0, 1, 0);
            GlStateManager.translate(0.18, 0, -0.5);
            GlStateManager.rotate(90F, 1, 0, 0);
            for(int i = 0; i < tileEntityGrill.getCoal(); i++)
            {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.rotate(15F, 0, 1, 0);
                    entityItem.setItem(coal);
                    Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
                }
                GlStateManager.popMatrix();
                GlStateManager.translate(-0.2, 0, 0);
            }
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 1, 0.5);
            GlStateManager.rotate(rotation * -90F, 0, 1, 0);
            GlStateManager.translate(0.2, 0, -0.45);
            GlStateManager.rotate(90F, 1, 0, 0);

            /* Left */
            if(tileEntityGrill.getItem(0) != null)
            {
                GlStateManager.pushMatrix();
                {
                    if(tileEntityGrill.flippedLeft)
                    {
                        float percent = (tileEntityGrill.leftFlipCount + partialTicks) / (float) tileEntityGrill.FLIP_DURATION;

                        if(tileEntityGrill.leftFlipCount < tileEntityGrill.FLIP_DURATION / 2)
                        {
                            tileEntityGrill.leftCurrentHeight = (FLIP_HEIGHT / (tileEntityGrill.FLIP_DURATION / 2)) * (tileEntityGrill.leftFlipCount + partialTicks);
                        }
                        else if(tileEntityGrill.leftCurrentHeight > 0F)
                        {
                            tileEntityGrill.leftCurrentHeight = (FLIP_HEIGHT * 2) - (FLIP_HEIGHT / (tileEntityGrill.FLIP_DURATION / 2)) * (tileEntityGrill.leftFlipCount + partialTicks);
                        }

                        if(tileEntityGrill.leftCurrentHeight >= 0F)
                        {
                            GlStateManager.translate(0, 0, -Math.sqrt(tileEntityGrill.leftCurrentHeight));
                        }

                        if(tileEntityGrill.leftFlipCount < tileEntityGrill.FLIP_DURATION)
                        {
                            GlStateManager.rotate(180F * percent, 0, 1, 0);
                        }
                        else
                        {
                            GlStateManager.rotate(180F, 0, 1, 0);
                            tileEntityGrill.leftCurrentHeight = 0F;
                        }
                    }

                    entityItem.setItem(tileEntityGrill.getItem(0));
                    Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
                }
                GlStateManager.popMatrix();
            }
            else
            {
                tileEntityGrill.leftFlipCount = 0;
            }

            GlStateManager.translate(-0.4, 0, 0);

            /* Right */
            if(tileEntityGrill.getItem(1) != null)
            {
                GlStateManager.pushMatrix();
                {
                    if(tileEntityGrill.flippedRight)
                    {
                        float percent = (tileEntityGrill.rightFlipCount + partialTicks) / (float) tileEntityGrill.FLIP_DURATION;

                        if(tileEntityGrill.rightFlipCount < tileEntityGrill.FLIP_DURATION / 2)
                        {
                            tileEntityGrill.rightCurrentHeight = (FLIP_HEIGHT / (tileEntityGrill.FLIP_DURATION / 2)) * (tileEntityGrill.rightFlipCount + partialTicks);
                        }
                        else if(tileEntityGrill.rightCurrentHeight > 0F)
                        {
                            tileEntityGrill.rightCurrentHeight = (FLIP_HEIGHT * 2) - (FLIP_HEIGHT / (tileEntityGrill.FLIP_DURATION / 2)) * (tileEntityGrill.rightFlipCount + partialTicks);
                        }

                        if(tileEntityGrill.rightCurrentHeight >= 0F)
                        {
                            GlStateManager.translate(0, 0, -Math.sqrt(tileEntityGrill.rightCurrentHeight));
                        }

                        if(tileEntityGrill.rightFlipCount < tileEntityGrill.FLIP_DURATION)
                        {
                            GlStateManager.rotate(180F * percent, 0, 1, 0);
                        }
                        else
                        {
                            GlStateManager.rotate(180F, 0, 1, 0);
                            tileEntityGrill.rightCurrentHeight = 0F;
                        }
                    }

                    entityItem.setItem(tileEntityGrill.getItem(1));
                    Minecraft.getMinecraft().getRenderManager().renderEntity(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
                }
                GlStateManager.popMatrix();
            }
            else
            {
                tileEntityGrill.rightFlipCount = 0;
            }
        }
        GlStateManager.popMatrix();
    }
}
