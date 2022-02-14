package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockCeilingFan;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityCeilingFan;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Author: MrCrayfish
 */
public class CeilingFanRenderer extends TileEntitySpecialRenderer<TileEntityCeilingFan>
{
    @Override
    public void render(TileEntityCeilingFan te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        EnumFacing enumfacing = EnumFacing.DOWN;

        if (te.hasWorld()) {
            IBlockState iblockstate = this.getWorld().getBlockState(te.getPos());

            if (iblockstate.getBlock() instanceof BlockCeilingFan) {
                enumfacing = iblockstate.getValue(BlockCeilingFan.FACING);
            }
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5, 0.5, 0.5);

        GlStateManager.pushMatrix();
        switch (enumfacing) {
            case UP:
                GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            case DOWN:
            default:
                break;
            case NORTH:
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                break;
            case SOUTH:
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                break;
            case WEST:
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                break;
            case EAST:
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        }
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.35, 1.0, 1.35);
        float rotation = te.prevFanRotation + (te.fanRotation - te.prevFanRotation) * partialTicks;
        GlStateManager.rotate(-rotation, 0, 1, 0);

        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FurnitureItems.CEILING_FAN_FANS), ItemCameraTransforms.TransformType.NONE);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
}
