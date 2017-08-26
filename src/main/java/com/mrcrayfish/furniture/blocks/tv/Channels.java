package com.mrcrayfish.furniture.blocks.tv;

import java.util.ArrayList;
import java.util.List;

public class Channels
{
	private static List<Channel> channels = new ArrayList<Channel>();

	public static int getChannelCount()
	{
		return channels.size();
	}

	public static Channel getChannel(int channel)
	{
		return channels.get(channel);
	}

	public static void registerChannels(int count)
	{
		for(int i = 0; i < count; i++)
		{
			channels.add(new Channel("channel_" + (i + 1)));
		}
	}
}
