package com.mrcrayfish.furniture.advancement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Triggers
{
	private static final List<BasicTrigger> TRIGGERS = Lists.<BasicTrigger>newArrayList();

	public static final BasicTrigger PLACE_CHAIR = register("get_chair");
	public static final BasicTrigger PLACE_TABLE = register("get_table");
	public static final BasicTrigger PLACE_CHAIR_OR_TABLE = register("get_chair_or_table");
	public static final BasicTrigger PLACE_BLINDS_OR_CURTAINS = register("get_blinds_or_curtains");
	public static final BasicTrigger PLACE_OUTDOOR_FURNITURE = register("get_outdoor_furniture");
	public static final BasicTrigger PLACE_BATHTROOM_FURNITURE = register("get_bathroom_furniture");
	public static final BasicTrigger PLACE_APPLIANCE = register("place_appliance");
	public static final BasicTrigger CREATE_COUCH_JEB = register("create_couch_jeb");
	
	public static final BasicTrigger UNWRAP_PRESENT = register("unwrap_present");
	public static final BasicTrigger OVEN_COOK = register("oven_cook");
	public static final BasicTrigger FREEZER_FREEZE = register("freezer_freeze");
	public static final BasicTrigger MINEBAY_PURCHASE = register("minebay_purchase");
	public static final BasicTrigger PRINTER_COPY = register("printer_copy");
	public static final BasicTrigger STEREO_ACTIVATED = register("stereo_activated");
	public static final BasicTrigger CLICKED_DOORBELL = register("clicked_doorbell");
	
	public static final BasicTrigger PLAYER_ZAPPED = register("player_zapped");
	public static final BasicTrigger PLAYER_SENT_MAIL = register("player_sent_mail");
	public static final BasicTrigger PLAYER_GOT_MAIL = register("player_got_mail");
	public static final BasicTrigger PLAYER_FART = register("player_fart");
	public static final BasicTrigger PLAYER_SHOWER = register("player_shower");

	private static BasicTrigger register(String name)
	{
		BasicTrigger trigger = new BasicTrigger(new ResourceLocation(Reference.MOD_ID, name));
		TRIGGERS.add(trigger);
		return trigger;
	}

	/**
	 * Triggers the specified custom advancement trigger.
	 * 
	 * @param trigger
	 *            The trigger to be triggered
	 * @param player
	 *            The player that is triggering the trigger
	 */
	public static void trigger(IModTrigger trigger, EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP) {
			trigger.trigger((EntityPlayerMP) player);
		}
	}

	public static void init()
	{
		Method method;
		try {
			method = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
			method.setAccessible(true);
			for (int i = 0; i < TRIGGERS.size(); i++) {
				method.invoke(null, TRIGGERS.get(i));
			}
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			MrCrayfishFurnitureMod.logger().catching(e);
		}
	}
}