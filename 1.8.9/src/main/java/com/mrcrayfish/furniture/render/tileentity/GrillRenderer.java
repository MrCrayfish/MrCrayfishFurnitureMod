package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.blocks.BlockGrill;
import com.mrcrayfish.furniture.tileentity.TileEntityGrill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class GrillRenderer extends TileEntitySpecialRenderer 
{
	private ItemStack coal = new ItemStack(Items.coal);
	private EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, coal);
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) 
	{
		entityItem.hoverStart = 0;
		
		if(!(te.getBlockType() instanceof BlockGrill))
			return;
		
		BlockGrill grill = (BlockGrill) te.getBlockType();
		TileEntityGrill tileEntityGrill = (TileEntityGrill) te;
		
		int rotation = grill.getMetaFromState(te.getWorld().getBlockState(te.getPos()));
		
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x, y, z);
			GlStateManager.translate(0.5, 0.85, 0.5);
			GlStateManager.rotate(rotation * -90F, 0, 1, 0);
			GlStateManager.translate(0.2, 0, -0.32);
			GlStateManager.rotate(90F, 1, 0, 0);
			for(int i = 0; i < tileEntityGrill.getCoal(); i++)
			{
				GlStateManager.pushMatrix();
				{
					GlStateManager.rotate(15F, 0, 1, 0);
					Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				}
				GlStateManager.popMatrix();
				GlStateManager.translate(-0.2, 0, 0);
			}
		}
		GlStateManager.popMatrix();
	}
}
