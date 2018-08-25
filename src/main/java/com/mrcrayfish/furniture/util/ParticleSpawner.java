package com.mrcrayfish.furniture.util;

import com.mrcrayfish.furniture.entity.EnittySteamFX;
import com.mrcrayfish.furniture.entity.EntityShowerFX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;

public class ParticleSpawner
{
    private static Minecraft mc = Minecraft.getMinecraft();

    public static Particle spawnParticle(String particleName, double par2, double par4, double par6)
    {
        if(mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null)
        {
            int var14 = mc.gameSettings.particleSetting;

            if(var14 == 1 && mc.world.rand.nextInt(3) == 0)
            {
                var14 = 2;
            }

            double var15 = mc.getRenderViewEntity().posX - par2;
            double var17 = mc.getRenderViewEntity().posY - par4;
            double var19 = mc.getRenderViewEntity().posZ - par6;
            Particle var21 = null;
            double var22 = 16.0D;

            if(var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
            {
                return null;
            }
            else if(var14 > 1)
            {
                return null;
            }
            else
            {
                if(particleName.equals("shower"))
                {
                    var21 = new EntityShowerFX(mc.world, par2, par4, par6);
                }
                else if(particleName.equals("smoke"))
                {
                    var21 = new EnittySteamFX(mc.world, par2, par4, par6, 0.01, 0.0000001, 0.01);
                }

                mc.effectRenderer.addEffect(var21);
                return var21;
            }
        }
        return null;
    }
}