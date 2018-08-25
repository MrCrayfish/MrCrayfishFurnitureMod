package com.mrcrayfish.furniture.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public class TileEntityModernSlidingDoor extends TileEntitySyncClient implements ITickable
{
    private boolean powered = false;
    private int maxProgress = 10;
    private int slideProgress;
    private int prevSlideProgress;

    @Override
    public void update()
    {
        prevSlideProgress = slideProgress;
        if(powered)
        {
            if(slideProgress < Math.max(0, maxProgress))
            {
                slideProgress++;
            }
        }
        else if(slideProgress > 0)
        {
            slideProgress--;
        }
    }

    public float getSlideProgress(float partialTicks)
    {
        float partialSlideProgress = prevSlideProgress + (slideProgress - prevSlideProgress) * partialTicks;
        float normalProgress = partialSlideProgress / (float) maxProgress;
        return 0.815F * (1.0F - ((float) Math.sin(Math.toRadians(90.0 + 180.0 * normalProgress)) / 2.0F + 0.5F));
    }

    public void setPowered(boolean powered)
    {
        this.powered = powered;
    }

    public boolean isPowered()
    {
        return powered;
    }

    @Override
    public double getMaxRenderDistanceSquared()
    {
        return 16384;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("powered", Constants.NBT.TAG_BYTE))
        {
            powered = compound.getBoolean("powered");
        }
        if(compound.hasKey("maxProgress", Constants.NBT.TAG_INT))
        {
            maxProgress = compound.getInteger("maxProgress");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("powered", powered);
        compound.setInteger("maxProgress", maxProgress);
        return compound;
    }
}
