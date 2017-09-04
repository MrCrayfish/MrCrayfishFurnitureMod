package com.mrcrayfish.furniture;

import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;

public class MirrorRenderGlobal extends RenderGlobal
{
	public MirrorRenderGlobal(Minecraft mcIn)
	{
		super(mcIn);
	}
	
	@Override
	public void renderClouds(float partialTicks, int pass, double p_180447_3_, double p_180447_5_, double p_180447_7_) 
	{
		if(ConfigurationHandler.mirrorClouds)
			super.renderClouds(partialTicks, pass, p_180447_3_, p_180447_5_, p_180447_7_);
	}
}
