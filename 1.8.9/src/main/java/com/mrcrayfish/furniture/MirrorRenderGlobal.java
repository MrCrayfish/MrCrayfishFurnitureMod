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
	public void renderClouds(float p_180447_1_, int p_180447_2_)
	{
		if(ConfigurationHandler.mirrorClouds)
			super.renderClouds(p_180447_1_, p_180447_2_);
	}
}
