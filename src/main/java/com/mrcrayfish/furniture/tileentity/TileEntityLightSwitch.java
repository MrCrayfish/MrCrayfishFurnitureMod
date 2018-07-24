package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.blocks.BlockCeilingLight;
import com.mrcrayfish.furniture.blocks.BlockFurniture;
import com.mrcrayfish.furniture.blocks.BlockLightSwitch;
import com.mrcrayfish.furniture.blocks.IPowered;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class TileEntityLightSwitch extends TileEntity
{
    private final List<BlockPos> lights = NonNullList.create();

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if(compound.hasKey("lights", Constants.NBT.TAG_LIST))
        {
            lights.clear();
            NBTTagList tagList = compound.getTagList("lights", Constants.NBT.TAG_LONG);
            tagList.forEach(nbtBase -> addLight(((NBTTagLong)nbtBase).getLong()));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList tagList = new NBTTagList();
        lights.forEach(blockPos -> tagList.appendTag(new NBTTagLong(blockPos.toLong())));
        compound.setTag("lights", tagList);

        return compound;
    }

    private void addLight(long pos)
    {
        BlockPos lightPos = BlockPos.fromLong(pos);
        if(!lights.contains(lightPos))
        {
            lights.add(lightPos);
        }
    }

    public void addLight(BlockPos lightPos)
    {
        if(!lights.contains(lightPos))
        {
            lights.add(lightPos);
        }
    }

    public void setState(boolean powered)
    {
        lights.removeIf(lightPos ->
        {
            IBlockState state = world.getBlockState(lightPos);
            return !(state.getBlock() instanceof IPowered);
        });

        lights.forEach(lightPos ->
        {
            IBlockState state = world.getBlockState(lightPos);
            ((IPowered) state.getBlock()).setPowered(world, lightPos, powered);

        });

        IBlockState state = world.getBlockState(pos);
        if(powered)
        {
            world.setBlockState(pos, FurnitureBlocks.LIGHT_SWITCH_ON.getDefaultState().withProperty(BlockFurniture.FACING, state.getValue(BlockFurniture.FACING)));
        }
        else
        {
            world.setBlockState(pos, FurnitureBlocks.LIGHT_SWITCH_OFF.getDefaultState().withProperty(BlockFurniture.FACING, state.getValue(BlockFurniture.FACING)));
        }

        this.validate();
        world.setTileEntity(pos, this);
    }
}
