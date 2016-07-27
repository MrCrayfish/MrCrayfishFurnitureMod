package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.Reference;

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
		door_bell = registerSound("door_bell");
		fire_alarm = registerSound("fire_alarm");
		white_noise = registerSound("white_noise");
		zap = registerSound("zap");
		flush = registerSound("flush");
		shower = registerSound("shower");
		bin_open = registerSound("bin_open");
		bin_close = registerSound("bin_close");
		tap = registerSound("tap");
		cabinet_open = registerSound("cabinet_open");
		cabinet_close = registerSound("cabinet_close");
		fart_1 = registerSound("fart_1");
		fart_2 = registerSound("fart_2");
		fart_3 = registerSound("fart_3");
		blender = registerSound("blender");
		dishwasher = registerSound("dishwasher");
		knife_chop = registerSound("knife_chop");
		microwave_running = registerSound("microwave_running");
		microwave_finish = registerSound("microwave_finish");
		toaster_down = registerSound("toaster_down");
		toaster_up = registerSound("toaster_up");
		washing_machine = registerSound("washing_machine");
		channel_news = registerSound("channel_news");
		channel_cooking = registerSound("channel_cooking");
		channel_sam_tabor = registerSound("channel_sam_tabor");
		channel_heman = registerSound("channel_heman");
		channel_switch = registerSound("channel_switch");
		boing = registerSound("boing");
		sizzle = registerSound("sizzle");
	}
	
	private static SoundEvent registerSound(String soundNameIn)
    {
        ResourceLocation resource = new ResourceLocation(Reference.MOD_ID, soundNameIn);
        GameRegistry.register(new SoundEvent(resource).setRegistryName(soundNameIn));
        return SoundEvent.soundEventRegistry.getObject(resource);
    }
}
