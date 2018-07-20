package com.mrcrayfish.furniture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class MirrorRenderGlobal extends RenderGlobal
{
    public MirrorRenderGlobal(Minecraft mcIn)
    {
        super(mcIn);
    }

    @Override
    public void renderClouds(float partialTicks, int pass, double p_180447_3_, double p_180447_5_, double p_180447_7_)
    {
        if (FurnitureConfig.CLIENT.mirror.clouds)
        {
            super.renderClouds(partialTicks, pass, p_180447_3_, p_180447_5_, p_180447_7_);
        }
    }

    @Override
    public void playRecord(SoundEvent soundIn, BlockPos pos)
    {
    }

    @Override
    public void playSoundToAllNearExcept(EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch)
    {
    }

    @Override
    public void broadcastSound(int soundID, BlockPos pos, int data)
    {
    }

    @Override
    public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data)
    {
    }
}
