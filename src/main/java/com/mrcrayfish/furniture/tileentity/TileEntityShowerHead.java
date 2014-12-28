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
package com.mrcrayfish.furniture.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.mrcrayfish.furniture.init.FurnitureAchievements;
import com.mrcrayfish.furniture.util.ParticleSpawner;

public class TileEntityShowerHead extends TileEntity implements IUpdatePlayerListBox
{
	private Random rand = new Random();
	private int timer = 20;

	@Override
	public void update()
	{
		if (this.worldObj.isRemote)
		{
			double posX = pos.getX() + 0.35D + (rand.nextDouble() / 3);
			double posZ = pos.getZ() + 0.35D + (rand.nextDouble() / 3);
			ParticleSpawner.spawnParticle("shower", posX, pos.getY() + 0.065D, posZ);
		}

		List<EntityPlayer> listPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX(), pos.getY() - 1, pos.getZ(), pos.getX() + 1.0D, pos.getY() - 1 + 1.0D, pos.getZ() + 1.0D));
		for (EntityPlayer player : listPlayers)
		{
			if (player != null)
			{
				player.curePotionEffects(new ItemStack(Items.milk_bucket));
				player.triggerAchievement(FurnitureAchievements.allClean);

				for (int i = 0; i < 4; i++)
				{
					ItemStack itemstack = player.getCurrentArmor(i);
					if (itemstack != null)
					{
						if (itemstack.getItem() instanceof ItemArmor)
						{
							ItemArmor armour = (ItemArmor) itemstack.getItem();
							if (armour.getArmorMaterial() == ArmorMaterial.LEATHER)
							{
								player.setCurrentItemOrArmor(i + 1, new ItemStack(itemstack.getItem(), 1, itemstack.getItemDamage()));
							}
						}
					}
				}

				if (player.isBurning())
				{
					player.extinguish();
				}
			}
		}

		if (timer >= 20)
		{
			worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "cfm:shower", 0.75F, 1.0F);
			timer = 0;
		}
		timer++;
	}
}
