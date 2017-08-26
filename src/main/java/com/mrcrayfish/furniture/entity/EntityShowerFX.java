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
package com.mrcrayfish.furniture.entity;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityShowerFX extends Particle
{
	private Material materialType;

	public EntityShowerFX(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		this.motionY = -0.2D;
		this.motionX *= 0.30000001192092896D;
		this.motionZ *= 0.30000001192092896D;

		this.particleRed = 0.0F;
		this.particleGreen = 0.0F;
		this.particleBlue = 1.0F;

		this.setParticleTextureIndex(113);
		this.setSize(0.1F, 0.1F);
		this.particleMaxAge = (int) 15.0D;
		this.particleScale = 1.5F;
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.particleRed = 0.2F;
		this.particleGreen = 0.3F;
		this.particleBlue = 1.0F;

		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionY = -0.2D;

		if (this.particleMaxAge-- <= 0)
		{
			this.setExpired();
		}

		if (this.onGround)
		{
			this.particleScale -= 0.1F;
			this.motionX *= 0.699999988079071D;
			this.motionY *= 0.699999988079071D;
		}
	}
}
