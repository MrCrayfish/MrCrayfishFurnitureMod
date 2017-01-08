package com.mrcrayfish.furniture.init;

import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnitureSounds {
	
	public static SoundEvent door_bell;
	public static SoundEvent fire_alarm;
	public static SoundEvent white_noise;
	public static SoundEvent zap;
	public static SoundEvent flush;
	public static SoundEvent shower;
	public static SoundEvent bin_open;
	public static SoundEvent bin_close;
	public static SoundEvent tap;
	public static SoundEvent cabinet_open;
	public static SoundEvent cabinet_close;
	public static SoundEvent fart_1;
	public static SoundEvent fart_2;
	public static SoundEvent fart_3;
	public static SoundEvent blender;
	public static SoundEvent dishwasher;
	public static SoundEvent knife_chop;
	public static SoundEvent microwave_running;
	public static SoundEvent microwave_finish;
	public static SoundEvent toaster_down;
	public static SoundEvent toaster_up;
	public static SoundEvent washing_machine;
	public static SoundEvent channel_news;
	public static SoundEvent channel_cooking;
	public static SoundEvent channel_sam_tabor;
	public static SoundEvent channel_heman;
	public static SoundEvent channel_switch;
	public static SoundEvent boing;
	public static SoundEvent sizzle;
	
	public static void register()
	{
		door_bell = registerSound("cfm:door_bell");
		fire_alarm = registerSound("cfm:fire_alarm");
		white_noise = registerSound("cfm:white_noise");
		zap = registerSound("cfm:zap");
		flush = registerSound("cfm:flush");
		shower = registerSound("cfm:shower");
		bin_open = registerSound("cfm:bin_open");
		bin_close = registerSound("cfm:bin_close");
		tap = registerSound("cfm:tap");
		cabinet_open = registerSound("cfm:cabinet_open");
		cabinet_close = registerSound("cfm:cabinet_close");
		fart_1 = registerSound("cfm:fart_1");
		fart_2 = registerSound("cfm:fart_2");
		fart_3 = registerSound("cfm:fart_3");
		blender = registerSound("cfm:blender");
		dishwasher = registerSound("cfm:dishwasher");
		knife_chop = registerSound("cfm:knife_chop");
		microwave_running = registerSound("cfm:microwave_running");
		microwave_finish = registerSound("cfm:microwave_finish");
		toaster_down = registerSound("cfm:toaster_down");
		toaster_up = registerSound("cfm:toaster_up");
		washing_machine = registerSound("cfm:washing_machine");
		channel_news = registerSound("cfm:channel_news");
		channel_cooking = registerSound("cfm:channel_cooking");
		channel_sam_tabor = registerSound("cfm:channel_sam_tabor");
		channel_heman = registerSound("cfm:channel_heman");
		channel_switch = registerSound("cfm:channel_switch");
		boing = registerSound("cfm:boing");
		sizzle = registerSound("cfm:sizzle");
	}
	
	public static SoundEvent getRandomFart(Random rand) 
	{
		int num = rand.nextInt(3);
		switch(num)
		{
		case 1:
			return fart_2;
		case 2:
			return fart_3;
		default:
			return fart_1;
		}
	}
	
	private static SoundEvent registerSound(String soundNameIn)
    {
		ResourceLocation sound = new ResourceLocation(soundNameIn);
        return GameRegistry.register(new SoundEvent(sound).setRegistryName(soundNameIn));
    }
}
