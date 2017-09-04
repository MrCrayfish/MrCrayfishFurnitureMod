package com.mrcrayfish.furniture.blocks.tv;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

public class Channel 
{
	private String channelName;
	private TextureAtlasSprite atlas;
	private SoundEvent sound;
	private Field counterField;
	private boolean init;

	public Channel(String channelName, SoundEvent event)
	{
		this.channelName = channelName;
		this.sound = event;
	}

	@SideOnly(Side.CLIENT)
	private void initReflection()
	{
		if(init) return;
		this.atlas = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("cfm:blocks/" + channelName);
		this.counterField = ReflectionHelper.findField(TextureAtlasSprite.class, ObfuscationReflectionHelper.remapFieldNames(TextureAtlasSprite.class.getName(), "field_110973_g"));
		init = true;
	}

	@SideOnly(Side.CLIENT)
	public void resetAnimation() {
		initReflection();
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

	public String getChannelName()
	{
		return channelName;
	}

	public SoundEvent getSound()
	{
		return sound;
	}
}
