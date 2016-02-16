/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
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
package com.mrcrayfish.furniture.network;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.message.MessageConfig;
import com.mrcrayfish.furniture.network.message.MessageDishwasher;
import com.mrcrayfish.furniture.network.message.MessageDoorMat;
import com.mrcrayfish.furniture.network.message.MessageEmptyBin;
import com.mrcrayfish.furniture.network.message.MessageEnvelope;
import com.mrcrayfish.furniture.network.message.MessageFart;
import com.mrcrayfish.furniture.network.message.MessageFillBasin;
import com.mrcrayfish.furniture.network.message.MessageFillBath;
import com.mrcrayfish.furniture.network.message.MessageFreezer;
import com.mrcrayfish.furniture.network.message.MessageMicrowave;
import com.mrcrayfish.furniture.network.message.MessageMineBayBrowse;
import com.mrcrayfish.furniture.network.message.MessageMineBayBuy;
import com.mrcrayfish.furniture.network.message.MessageMineBayClosed;
import com.mrcrayfish.furniture.network.message.MessagePackage;
import com.mrcrayfish.furniture.network.message.MessagePresent;
import com.mrcrayfish.furniture.network.message.MessagePresentContents;
import com.mrcrayfish.furniture.network.message.MessageTVClient;
import com.mrcrayfish.furniture.network.message.MessageTakeWater;
import com.mrcrayfish.furniture.network.message.MessageUpdateFields;
import com.mrcrayfish.furniture.network.message.MessageWashingMachine;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

	public static void init()
	{
		INSTANCE.registerMessage(MessageConfig.class, MessageConfig.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageEmptyBin.class, MessageEmptyBin.class, 1, Side.SERVER);
		INSTANCE.registerMessage(MessageEnvelope.class, MessageEnvelope.class, 2, Side.SERVER);
		INSTANCE.registerMessage(MessageFart.class, MessageFart.class, 3, Side.SERVER);
		INSTANCE.registerMessage(MessageFillBasin.class, MessageFillBasin.class, 4, Side.CLIENT);
		INSTANCE.registerMessage(MessageFillBath.class, MessageFillBath.class, 5, Side.CLIENT);
		INSTANCE.registerMessage(MessageMineBayBrowse.class, MessageMineBayBrowse.class, 6, Side.SERVER);
		INSTANCE.registerMessage(MessageMineBayBuy.class, MessageMineBayBuy.class, 7, Side.SERVER);
		INSTANCE.registerMessage(MessageMineBayClosed.class, MessageMineBayClosed.class, 8, Side.SERVER);
		INSTANCE.registerMessage(MessagePackage.class, MessagePackage.class, 9, Side.SERVER);
		INSTANCE.registerMessage(MessagePresent.class, MessagePresent.class, 10, Side.SERVER);
		INSTANCE.registerMessage(MessagePresentContents.class, MessagePresentContents.class, 11, Side.SERVER);
		INSTANCE.registerMessage(MessageTakeWater.class, MessageTakeWater.class, 12, Side.SERVER);
		INSTANCE.registerMessage(MessageTVClient.class, MessageTVClient.class, 13, Side.CLIENT);
		INSTANCE.registerMessage(MessageMicrowave.class, MessageMicrowave.class, 14, Side.SERVER);
		INSTANCE.registerMessage(MessageWashingMachine.class, MessageWashingMachine.class, 15, Side.SERVER);
		INSTANCE.registerMessage(MessageDishwasher.class, MessageDishwasher.class, 16, Side.SERVER);
		INSTANCE.registerMessage(MessageFreezer.class, MessageFreezer.class, 17, Side.SERVER);
		INSTANCE.registerMessage(MessageUpdateFields.class, MessageUpdateFields.class, 18, Side.CLIENT);
		INSTANCE.registerMessage(MessageDoorMat.class, MessageDoorMat.class, 19, Side.SERVER);
	}
}
