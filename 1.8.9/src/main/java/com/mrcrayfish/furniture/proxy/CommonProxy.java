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
package com.mrcrayfish.furniture.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommonProxy implements ProxyInterface
{
	public void registerRenders()
	{

	}

	public World getClientWorld()
	{
		return null;
	}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}

	@Override
	public boolean isSinglePlayer()
	{
		return false;
	}

	@Override
	public boolean isDedicatedServer()
	{
		return MinecraftServer.getServer().isDedicatedServer();
	}

	@Override
	public void preInit()
	{
		
	}
}
