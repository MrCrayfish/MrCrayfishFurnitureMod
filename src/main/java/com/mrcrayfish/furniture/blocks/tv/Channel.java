package com.mrcrayfish.furniture.blocks.tv;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.furniture.TVSound;
import com.mrcrayfish.furniture.blocks.BlockTV;
import com.mrcrayfish.furniture.util.ReflectionUtil;

@SideOnly(Side.CLIENT)
public class Channel
{
	public static int uniqueId = 0;
	
	private int id;
	private TextureAtlasSprite atlas = null;
	private TVSound sound = null;
	private SoundHandler soundHandler = null;
	private Field counterField = null;

	public Channel(String channelName)
	{
		this.atlas = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry("cfm:blocks/" + channelName);
		this.sound = new TVSound(new ResourceLocation("cfm:" + channelName));
		this.soundHandler = Minecraft.getMinecraft().getSoundHandler();
		this.id = uniqueId++;

		initRefleciton();
	}

	private void initRefleciton()
	{
		try
		{
			this.counterField = ReflectionUtil.getField(atlas.getClass(), "frameCounter");
			ReflectionUtil.makeAccessible(this.counterField);
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}

	}

	public IBlockState getTVState(IBlockState state)
	{
		return state.withProperty(BlockTV.CHANNEL, id);
	}

	public void play(World world, BlockPos pos)
	{
		resetAnimation();
		playSound(pos);
	}

	private void resetAnimation()
	{
		if (atlas != null && counterField != null)
		{
			try
			{
				counterField.setInt(atlas, 0);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void playSound(BlockPos pos)
	{
		if (sound != null)
		{
			if (!soundHandler.isSoundPlaying(sound))
			{
				sound.setPosition(pos);
				soundHandler.playSound(sound);
			}
			else
			{
				soundHandler.stopSound(sound);
			}
		}
	}
}
