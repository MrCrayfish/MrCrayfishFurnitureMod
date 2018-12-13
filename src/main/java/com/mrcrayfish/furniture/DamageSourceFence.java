package com.mrcrayfish.furniture;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourceFence extends DamageSource
{
    public DamageSourceFence(String damageTypeIn)
    {
        super(damageTypeIn);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        return new TextComponentTranslation("death.block.electric_fence", entityLivingBaseIn.getDisplayName());
    }
}