/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.network;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.network.message.*;
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
        INSTANCE.registerMessage(MessageMicrowave.class, MessageMicrowave.class, 13, Side.SERVER);
        INSTANCE.registerMessage(MessageWashingMachine.class, MessageWashingMachine.class, 14, Side.SERVER);
        INSTANCE.registerMessage(MessageDishwasher.class, MessageDishwasher.class, 15, Side.SERVER);
        INSTANCE.registerMessage(MessageFreezer.class, MessageFreezer.class, 16, Side.SERVER);
        INSTANCE.registerMessage(MessageUpdateFields.class, MessageUpdateFields.class, 17, Side.CLIENT);
        INSTANCE.registerMessage(MessageDoorMat.class, MessageDoorMat.class, 18, Side.SERVER);
        INSTANCE.registerMessage(MessageSealCrate.class, MessageSealCrate.class, 19, Side.SERVER);
        INSTANCE.registerMessage(MessageTVPlaySound.class, MessageTVPlaySound.class, 20, Side.CLIENT);
        INSTANCE.registerMessage(MessageTVStopSound.class, MessageTVStopSound.class, 21, Side.CLIENT);
    }
}
