package com.mrcrayfish.furniture.blocks;

import java.util.Random;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChimney extends Block
{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", ChimneyType.class);

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

    public BlockChimney(Material material)
    {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ChimneyType.TOP));
        this.setTickRandomly(true);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(world.getBlockState(pos.up()).getBlock() == this)
        {
            return state.withProperty(TYPE, ChimneyType.BOTTOM);
        }
        else
        {
            return state.withProperty(TYPE, ChimneyType.TOP);
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        IBlockState actualState = getActualState(state, worldIn, pos);
        if(actualState.getValue(TYPE) != ChimneyType.TOP) return;

        for(int i = 0; i < 2; i++)
        {
            double posX = 0.25 + (0.5 * rand.nextDouble());
            double posZ = 0.25 + (0.5 * rand.nextDouble());
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + posX, pos.getY() + 0.9, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D, new int[0]);
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + posX, pos.getY() + 0.9, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((ChimneyType) state.getValue(TYPE)).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, ChimneyType.values()[meta % ChimneyType.values().length]);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{TYPE});
    }

    public enum ChimneyType implements IStringSerializable
    {
        TOP,
        BOTTOM;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }
    }

}
