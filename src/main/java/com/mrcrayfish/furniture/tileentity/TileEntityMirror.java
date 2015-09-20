package com.mrcrayfish.furniture.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mrcrayfish.furniture.blocks.BlockMirror;
import com.mrcrayfish.furniture.entity.EntityMirror;
import com.mrcrayfish.furniture.render.tileentity.MirrorRenderer;

public class TileEntityMirror extends TileEntity
{
	private Entity bindedMirror = null;

	@SideOnly(Side.CLIENT)
	public Entity getMirror()
	{
		if (bindedMirror == null)
		{
			if (getBlockType() instanceof BlockMirror)
			{
				EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getValue(BlockMirror.FACING);
				bindedMirror = new EntityMirror(worldObj, pos.getX(), pos.getY(), pos.getZ(), facing);
				worldObj.spawnEntityInWorld(bindedMirror);
			}
		}
		return bindedMirror;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onChunkUnload()
	{
		if (bindedMirror != null)
		{
			MirrorRenderer.removeRegisteredMirror(bindedMirror);
			bindedMirror.setDead();
			bindedMirror = null;
		}
	}
}
