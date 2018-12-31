package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public class BlockFairyLight extends BlockFurniture
{

    public static final PropertyEnum<FairyLightType> TYPE = PropertyEnum.create("type", FairyLightType.class);

    private static final AxisAlignedBB[] BOUNDING_BOX = new Bounds(6, 11, 0, 11, 16, 16).getRotatedBounds(Rotation.CLOCKWISE_90);

    public BlockFairyLight(Material materialIn)
    {
        super(materialIn);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, FairyLightType.EVEN));
        this.setLightLevel(0.5F);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GLASS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.up(), EnumFacing.DOWN);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!canBlockStay(world, pos))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    public boolean canBlockStay(IBlockAccess world, BlockPos pos)
    {
        return !world.isAirBlock(pos.up());
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if((pos.getX() % 2) == (pos.getZ() % 2))
        {
            return state.withProperty(TYPE, FairyLightType.EVEN);
        }
        return state.withProperty(TYPE, FairyLightType.ODD);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public enum FairyLightType implements IStringSerializable
    {
        EVEN,
        ODD;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }
    }

}
