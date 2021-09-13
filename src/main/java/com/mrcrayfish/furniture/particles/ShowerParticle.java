package com.mrcrayfish.furniture.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class ShowerParticle extends SpriteTexturedParticle
{

    protected ShowerParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        float f = this.rand.nextFloat() * 1.0f;
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;

        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 1.1f;
        this.motionX *= 0.02f;
        this.motionY *= -1.0f;
        this.motionZ *= 0.02f;
        this.maxAge = (int)(20.0D / (Math.random() * 1.0D));

    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if(this.maxAge-- <= 0)
        {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>
    {

        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite)
        {
            this.spriteSet = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ShowerParticle showerParticle = new ShowerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            showerParticle.setColor(1.0F, 1.0F, 1.0F);
            showerParticle.selectSpriteRandomly(this.spriteSet);
            return showerParticle;
        }
    }
}
