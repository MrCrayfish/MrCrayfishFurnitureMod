package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.blocks.BlockMirror;
import com.mrcrayfish.furniture.entity.EntityMirror;
import com.mrcrayfish.furniture.render.tileentity.MirrorRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMirror extends TileEntity
{
    private EntityMirror bindedMirror = null;

    @SideOnly(Side.CLIENT)
    public EntityMirror getMirror()
    {
        if(bindedMirror == null)
        {
            if(getBlockType() instanceof BlockMirror)
            {
                EnumFacing facing = (EnumFacing) world.getBlockState(pos).getValue(BlockMirror.FACING);
                bindedMirror = new EntityMirror(world, pos.getX(), pos.getY(), pos.getZ(), facing);
                world.spawnEntity(bindedMirror);
            }
        }
        return bindedMirror;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onChunkUnload()
    {
        if(bindedMirror != null)
        {
            MirrorRenderer.removeRegisteredMirror(bindedMirror);
            bindedMirror.setDead();
            bindedMirror = null;
        }
    }
}
