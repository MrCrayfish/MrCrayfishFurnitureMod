package com.mrcrayfish.furniture;

import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class TVSound extends PositionedSound
{
	public TVSound(ResourceLocation soundResource)
	{
		super(soundResource, SoundCategory.BLOCKS);
	}
	
	public void setPosition(BlockPos pos)
	{
		this.xPosF = pos.getX();
		this.yPosF = pos.getY();
		this.zPosF = pos.getZ();
	}
}
