package com.mrcrayfish.furniture.blocks.tv;

import com.google.common.collect.Maps;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Channels
{
	public static final Map<BlockPos, ISound> SOUND_POSITIONS = Maps.newHashMap();

	private static List<Channel> channels = new ArrayList<>();

	public static int getChannelCount()
	{
		return channels.size();
	}

	public static Channel getChannel(int channel)
	{
		return channels.get(channel);
	}

	public static Channel getChannel(String name)
	{
		return channels.stream().filter(channel -> channel.getChannelName().equals(name)).findFirst().orElse(null);
	}

	private static void registerChannel(String name, SoundEvent event)
	{
		channels.add(new Channel("channel_" + name, event));
	}

	public static void registerChannels()
	{
		registerChannel("news", FurnitureSounds.channel_news);
		registerChannel("cooking", FurnitureSounds.channel_cooking);
		registerChannel("sam_tabor", FurnitureSounds.channel_sam_tabor);
		registerChannel("heman", FurnitureSounds.channel_heman);
		registerChannel("switch", FurnitureSounds.channel_switch);
	}
}
