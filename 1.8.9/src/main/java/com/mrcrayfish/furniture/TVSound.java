package com.mrcrayfish.furniture;

import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

public class TVSound extends PositionedSound
{
	public TVSound(ResourceLocation soundResource)
	{
		super(soundResource);
	}
	
	public void setPosition(BlockPos pos)
	{
		this.xPosF = pos.getX();
		this.yPosF = pos.getY();
		this.zPosF = pos.getZ();
	}
}
