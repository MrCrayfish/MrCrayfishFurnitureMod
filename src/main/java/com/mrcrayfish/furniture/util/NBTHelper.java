/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2014  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * NBTHelper
 * 
 * @author pahimar
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class NBTHelper
{
	/**
	 * Initializes the NBT Tag Compound for the given ItemStack if it is null
	 * 
	 * @param itemStack
	 *            The ItemStack for which its NBT Tag Compound is being checked
	 *            for initialization
	 */
	private static void initNBTTagCompound(ItemStack itemStack)
	{

		if (itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
	}

	public static boolean hasTag(ItemStack itemStack, String keyName)
	{

		if (itemStack.getTagCompound() != null)
			return itemStack.getTagCompound().hasKey(keyName);

		return false;
	}

	public static void removeTag(ItemStack itemStack, String keyName)
	{

		if (itemStack.getTagCompound() != null)
		{
			itemStack.getTagCompound().removeTag(keyName);
		}
	}

	// String
	public static String getString(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setString(itemStack, keyName, "");
		}

		return itemStack.getTagCompound().getString(keyName);
	}

	public static void setString(ItemStack itemStack, String keyName, String keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setString(keyName, keyValue);
	}

	// boolean
	public static boolean getBoolean(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setBoolean(itemStack, keyName, false);
		}

		return itemStack.getTagCompound().getBoolean(keyName);
	}

	public static void setBoolean(ItemStack itemStack, String keyName, boolean keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setBoolean(keyName, keyValue);
	}

	// byte
	public static byte getByte(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setByte(itemStack, keyName, (byte) 0);
		}

		return itemStack.getTagCompound().getByte(keyName);
	}

	public static void setByte(ItemStack itemStack, String keyName, byte keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setByte(keyName, keyValue);
	}

	// short
	public static short getShort(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setShort(itemStack, keyName, (short) 0);
		}

		return itemStack.getTagCompound().getShort(keyName);
	}

	public static void setShort(ItemStack itemStack, String keyName, short keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setShort(keyName, keyValue);
	}

	// int
	public static int getInt(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setInteger(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getInteger(keyName);
	}

	public static void setInteger(ItemStack itemStack, String keyName, int keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setInteger(keyName, keyValue);
	}

	// long
	public static long getLong(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setLong(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getLong(keyName);
	}

	public static void setLong(ItemStack itemStack, String keyName, long keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setLong(keyName, keyValue);
	}

	// float
	public static float getFloat(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setFloat(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getFloat(keyName);
	}

	public static void setFloat(ItemStack itemStack, String keyName, float keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setFloat(keyName, keyValue);
	}

	// double
	public static double getDouble(ItemStack itemStack, String keyName)
	{

		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setDouble(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getDouble(keyName);
	}

	public static void setDouble(ItemStack itemStack, String keyName, double keyValue)
	{

		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setDouble(keyName, keyValue);
	}

	public static NBTTagCompound getCompoundTag(ItemStack itemStack, String tagName)
	{
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(tagName))
		{
			itemStack.getTagCompound().setTag(tagName, new NBTTagCompound());
		}
		return itemStack.getTagCompound().getCompoundTag(tagName);
	}

	public static void setCompoundTag(ItemStack itemStack, String tagName, NBTTagCompound tagValue)
	{
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setTag(tagName, tagValue);
	}

}