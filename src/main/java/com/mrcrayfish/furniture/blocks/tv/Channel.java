package com.mrcrayfish.furniture.blocks.tv;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mrcrayfish.furniture.util.ReflectionUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Channel 
{
	private static Map<BlockPos, PositionedSoundRecord> currentSounds = new ConcurrentHashMap<BlockPos, PositionedSoundRecord>();
	
	private String channelName;
	private TextureAtlasSprite atlas = null;
	private ResourceLocation sound = null;
	private SoundHandler soundHandler = null;
	private Field counterField = null;

	public Channel(String channelName) 
	{
		this.channelName = channelName;
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			this.sound = new ResourceLocation("cfm:" + channelName);
			this.soundHandler = Minecraft.getMinecraft().getSoundHandler();
		}
	}

	@SideOnly(Side.CLIENT)
	private void initRefleciton() 
	{
		if (counterField != null)
			return;

		if (atlas == null)
			atlas = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry("cfm:blocks/" + channelName);

		try {
			this.counterField = ReflectionUtil.getField(atlas.getClass(), "frameCounter");
			ReflectionUtil.makeAccessible(this.counterField);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

	}

	@SideOnly(Side.CLIENT)
	public void play(BlockPos pos) {
		resetAnimation();
		playSound(pos);
	}

	@SideOnly(Side.CLIENT)
	public void resetAnimation() {
		initRefleciton();
		if (atlas != null && counterField != null) {
			try {
				counterField.setInt(atlas, 0);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void playSound(BlockPos pos)
	{
		stopSound(pos);
		PositionedSoundRecord sound = PositionedSoundRecord.create(this.sound, (float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F);
		currentSounds.put(pos, sound);
		soundHandler.playSound(sound);
	}

	@SideOnly(Side.CLIENT)
	private static PositionedSoundRecord getSound(BlockPos soundPos) 
	{
		for(BlockPos currentPos : currentSounds.keySet())
		{
			if(currentPos.equals(soundPos))
			{
				return currentSounds.get(currentPos);
			}
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public static void stopSound(BlockPos pos) 
	{
		PositionedSoundRecord sound = getSound(pos);
		if(sound != null)
		{
			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			if (handler.isSoundPlaying(sound)) 
			{
				handler.stopSound(sound);
			}
			BlockPos found = null;
			for(BlockPos currentPos : currentSounds.keySet())
			{
				if(currentPos.equals(pos))
				{
					found = currentPos;
					break;
				}
			}
			if(found != null)
			{
				currentSounds.remove(found);
			}
		}
	}
}
