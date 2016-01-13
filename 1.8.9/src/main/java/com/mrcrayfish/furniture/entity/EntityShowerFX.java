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
package com.mrcrayfish.furniture.entity;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityShowerFX extends EntityFX
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
	public int getBrightnessForRender(float par1)
	{
		return super.getBrightnessForRender(par1);
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(float par1)
	{
		return super.getBrightness(par1);
	}

	/**
	 * e Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.particleRed = 0.2F;
		this.particleGreen = 0.3F;
		this.particleBlue = 1.0F;

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionY = -0.2D;

		if (this.particleMaxAge-- <= 0)
		{
			this.setDead();
		}

		if (this.onGround)
		{
			this.particleScale -= 0.1F;
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderParticle(WorldRenderer renderer, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
	{
		super.renderParticle(renderer, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
	}
}
