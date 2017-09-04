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
package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.blocks.BlockBedsideCabinet;
import com.mrcrayfish.furniture.blocks.BlockCabinet;
import com.mrcrayfish.furniture.gui.containers.*;
import com.mrcrayfish.furniture.items.*;
import com.mrcrayfish.furniture.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile_entity = world.getTileEntity(new BlockPos(x, y, z));
		if (tile_entity instanceof TileEntityFridge)
		{
			return new ContainerFridge(player.inventory, (TileEntityFridge) tile_entity);
		}
		if (tile_entity instanceof TileEntityFreezer)
		{
			return new ContainerFreezer(player.inventory, (TileEntityFreezer) tile_entity);
		}
		if (tile_entity instanceof TileEntityCabinet)
		{
			return new ContainerCabinet(player.inventory, (TileEntityCabinet) tile_entity);
		}
		if (tile_entity instanceof TileEntityBedsideCabinet)
		{
			return new ContainerBedsideCabinet(player.inventory, (TileEntityBedsideCabinet) tile_entity);
		}
		if (tile_entity instanceof TileEntityOven)
		{
			return new ContainerOven(player.inventory, (TileEntityOven) tile_entity);
		}
		if (tile_entity instanceof TileEntityMailBox)
		{
			return new ContainerMailBox(player.inventory, (TileEntityMailBox) tile_entity);
		}
		if (tile_entity instanceof TileEntityComputer)
		{
			return new ContainerComputer(player.inventory, (TileEntityComputer) tile_entity);
		}
		if (tile_entity instanceof TileEntityPrinter)
		{
			return new ContainerPrinter(player.inventory, (TileEntityPrinter) tile_entity);
		}
		if (tile_entity instanceof TileEntityBin)
		{
			return new ContainerBin(player.inventory, (TileEntityBin) tile_entity);
		}
		if (tile_entity instanceof TileEntityWallCabinet)
		{
			return new ContainerWallCabinet(player.inventory, (TileEntityWallCabinet) tile_entity);
		}
		if (tile_entity instanceof TileEntityMicrowave)
		{
			return new ContainerMicrowave(player.inventory, (TileEntityMicrowave) tile_entity);
		}
		if (tile_entity instanceof TileEntityWashingMachine)
		{
			return new ContainerWashingMachine(player.inventory, (TileEntityWashingMachine) tile_entity);
		}
		if (tile_entity instanceof TileEntityDishwasher)
		{
			return new ContainerDishwasher(player.inventory, (TileEntityDishwasher) tile_entity);
		}
		if (tile_entity instanceof TileEntityCabinetKitchen)
		{
			return new ContainerCabinet(player.inventory, (TileEntityCabinetKitchen) tile_entity);
		}
		if (tile_entity instanceof TileEntityEsky)
		{
			return new ContainerEski(player.inventory, (TileEntityEsky) tile_entity);
		}
		if (tile_entity instanceof TileEntityCrate)
		{
			return new ContainerCrate(player.inventory, (TileEntityCrate) tile_entity);
		}
		if (id == 5)
		{
			return new ContainerEnvelope(player.inventory, ItemEnvelope.getInv(player));
		}
		if (id == 6)
		{
			return new ContainerEnvelope(player.inventory, ItemEnvelopeSigned.getInv(player));
		}
		if (id == 7)
		{
			return new ContainerPackage(player.inventory, ItemPackage.getInv(player));
		}
		if (id == 8)
		{
			return new ContainerPackage(player.inventory, ItemPackageSigned.getInv(player));
		}
		if (id == 9)
		{
			return new ContainerPresent(player.inventory, ItemPresent.getInv(player, player.getActiveHand()));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile_entity = world.getTileEntity(new BlockPos(x, y, z));
		if (tile_entity instanceof TileEntityFridge)
		{
			return new GuiFridge(player.inventory, (TileEntityFridge) tile_entity);
		}
		if (tile_entity instanceof TileEntityFreezer)
		{
			return new GuiFreezer(player.inventory, (TileEntityFreezer) tile_entity);
		}
		if (tile_entity instanceof TileEntityCabinet)
		{
			return new GuiCabinet(player.inventory, (TileEntityCabinet) tile_entity, (BlockCabinet) tile_entity.getBlockType());
		}
		if (tile_entity instanceof TileEntityBedsideCabinet)
		{
			return new GuiBedsideCabinet(player.inventory, (TileEntityBedsideCabinet) tile_entity, (BlockBedsideCabinet) tile_entity.getBlockType());
		}
		if (tile_entity instanceof TileEntityOven)
		{
			return new GuiOven(player.inventory, (TileEntityOven) tile_entity);
		}
		if (tile_entity instanceof TileEntityMailBox && id == 0)
		{
			return new GuiMailBox(player.inventory, (TileEntityMailBox) tile_entity);
		}
		if (tile_entity instanceof TileEntityComputer)
		{
			return new GuiComputer(player.inventory, (TileEntityComputer) tile_entity);
		}
		if (tile_entity instanceof TileEntityPrinter)
		{
			return new GuiPrinter(player.inventory, (TileEntityPrinter) tile_entity);
		}
		if (tile_entity instanceof TileEntityBin)
		{
			return new GuiBin(player.inventory, (TileEntityBin) tile_entity, x, y, z);
		}
		if (tile_entity instanceof TileEntityWallCabinet)
		{
			return new GuiWallCabinet(player.inventory, (TileEntityWallCabinet) tile_entity);
		}
		if (tile_entity instanceof TileEntityMicrowave)
		{
			return new GuiMicrowave(player.inventory, (TileEntityMicrowave) tile_entity);
		}
		if (tile_entity instanceof TileEntityWashingMachine)
		{
			return new GuiWashingMachine(player.inventory, (TileEntityWashingMachine) tile_entity);
		}
		if (tile_entity instanceof TileEntityDishwasher)
		{
			return new GuiDishwasher(player.inventory, (TileEntityDishwasher) tile_entity);
		}
		if (tile_entity instanceof TileEntityCabinetKitchen)
		{
			return new GuiKitchenCabinet(player.inventory, (TileEntityCabinetKitchen) tile_entity);
		}
		if (tile_entity instanceof TileEntityEsky)
		{
			return new GuiEski(player.inventory, (TileEntityEsky) tile_entity);
		}
		if (tile_entity instanceof TileEntityDoorMat)
		{
			return new GuiDoorMat(x, y, z);
		}
		if (tile_entity instanceof TileEntityCrate)
		{
			return new GuiCrate(player.inventory, (TileEntityCrate) tile_entity, x, y, z);
		}
		ItemStack mail = null;
		if (player.inventory.getCurrentItem() != null)
		{
			mail = player.inventory.getCurrentItem();
		}
		if (id == 5)
		{
			return new GuiEnvelope(player.inventory, ItemEnvelope.getInv(player), player, mail);
		}
		if (id == 6)
		{
			return new GuiEnvelope(player.inventory, ItemEnvelopeSigned.getInv(player), player, mail);
		}
		if (id == 7)
		{
			return new GuiPackage(player.inventory, ItemPackage.getInv(player), player, mail);
		}
		if (id == 8)
		{
			return new GuiPackage(player.inventory, ItemPackageSigned.getInv(player), player, mail);
		}
		if (id == 9)
		{
			return new GuiPresent(player.inventory, ItemPresent.getInv(player, player.getActiveHand()), player, mail);
		}
		if (id == 10)
		{
			return new GuiRecipeBook();
		}
		return null;
	}
}