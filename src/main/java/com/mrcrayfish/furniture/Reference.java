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
package com.mrcrayfish.furniture;

public class Reference
{
	public static final String MOD_ID = "cfm";
	public static final String NAME = "MrCrayfish's Furniture Mod";
	public static final String VERSION = "4.1.5";
	public static final String ACCEPTED_MC_VERSIONS = "[1.12]";
	public static final String CLIENT_PROXY_CLASS = "com.mrcrayfish.furniture.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.mrcrayfish.furniture.proxy.CommonProxy";
	public static final String GUI_FACTORY_CLASS = "com.mrcrayfish.furniture.gui.GuiFactory";
	
	/*public static enum FurnitureItems implements IRegistry 
	{
		;
		
		private String unlocalizedName, registryName;
		
		FurnitureItems(String unlocalizedName, String registryName) {
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
		}

		@Override
		public String getUnocalizedName() 
		{
			return null;
		}

		@Override
		public String getRegistryName() 
		{
			return null;
		}
		
	}
	
	public static enum FurnitureBlocks implements IRegistry 
	{
		;
		
		private String unlocalizedName, registryName;
		
		FurnitureBlocks(String unlocalizedName, String registryName) {
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
		}

		@Override
		public String getUnocalizedName() 
		{
			return null;
		}

		@Override
		public String getRegistryName() 
		{
			return null;
		}
		
	}
	
	public interface IRegistry 
	{
		public String getUnocalizedName();
		public String getRegistryName();
	}*/
}
