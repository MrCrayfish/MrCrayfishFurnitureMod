package com.mrcrayfish.furniture.entity;

import com.mrcrayfish.furniture.core.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class EntitySeat extends Entity
{
    private BlockPos source;

    public EntitySeat(World world)
    {
        super(ModEntities.SEAT, world);
        this.noClip = true;
        this.height = 0.01F;
        this.width = 0.01F;
    }

    public EntitySeat(World world, BlockPos source, double yOffset)
    {
        this(world);
        this.source = source;
        this.setPosition(source.getX() + 0.5, source.getY() + yOffset, source.getZ() + 0.5);
    }

    @Override
    protected void registerData() {}

    @Override
    public void baseTick()
    {
        super.baseTick();
        if(source == null)
        {
            source = this.getPosition();
        }
        if(!this.world.isRemote)
        {
            if(this.getPassengers().isEmpty() || this.world.isAirBlock(source))
            {
                world.removeEntity(this);
                world.updateComparatorOutputLevel(getPosition(), world.getBlockState(getPosition()).getBlock());
            }
        }
    }

    @Override
    protected void readAdditional(NBTTagCompound compound) {}

    @Override
    protected void writeAdditional(NBTTagCompound compound) {}

    @Override
    public double getMountedYOffset()
    {
        return 0.0;
    }

    public BlockPos getSource()
    {
        return source;
    }

    @Override
    protected boolean canBeRidden(Entity entity)
    {
        return true;
    }

    public static boolean create(World world, BlockPos pos, double yOffset, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            List<EntitySeat> seats = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if(seats.isEmpty())
            {
                EntitySeat seat = new EntitySeat(world, pos, yOffset);
                world.spawnEntity(seat);
                player.startRiding(seat, true);
            }
        }
        return true;
    }
}
