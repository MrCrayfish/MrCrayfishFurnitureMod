package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.DamageSourceFence;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockElectricFence extends BlockWhiteFence
{
    public static final DamageSource ELECTRIC_FENCE = new DamageSourceFence("electricFence");

    public BlockElectricFence(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.ANVIL);
        this.setLightLevel(0.2F);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityLivingBase && !entity.isDead)
        {
            if(entity instanceof EntityCreeper)
            {
                EntityCreeper creeper = (EntityCreeper) entity;
                EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false);
                if(!creeper.getPowered())
                {
                    creeper.setFire(1);
                    creeper.onStruckByLightning(lightning);
                }
            }
            else if(entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;
                if(!player.isCreative())
                {
                    entity.attackEntityFrom(ELECTRIC_FENCE, (int) 2.0F);
                    world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.zap, SoundCategory.BLOCKS, 0.2F, 1.0F);
                    Triggers.trigger(Triggers.PLAYER_ZAPPED, player);
                    this.sparkle(world, pos);
                }
            }
            else
            {
                entity.attackEntityFrom(ELECTRIC_FENCE, (int) 2.0F);
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.zap, SoundCategory.BLOCKS, 0.2F, 1.0F);
                this.sparkle(world, pos);
            }
        }
    }

    private void sparkle(World worldIn, BlockPos pos)
    {
        double posX = (pos.getX() + RANDOM.nextFloat());
        double posY = (pos.getY() + RANDOM.nextFloat());
        double posZ = (pos.getZ() + RANDOM.nextFloat());
        worldIn.spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
    }
}
