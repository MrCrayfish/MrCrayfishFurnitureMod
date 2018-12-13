package com.mrcrayfish.furniture.util;

public class TimeUtil
{
	public static String getFormattedTime(long ticks)
	{
		int hours = (int) ((Math.floor(ticks / 1000.0) + 7) % 24);
		int minutes = (int) Math.floor((ticks % 1000) / 1000.0 * 60);
		return String.format("%02d:%02d", hours, minutes);
	}
}