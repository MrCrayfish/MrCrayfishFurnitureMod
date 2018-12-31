package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChimney extends BlockCollisionRaytrace
{
    public static final PropertyEnum<ChimneyType> TYPE = PropertyEnum.create("type", ChimneyType.class);

    private static final AxisAlignedBB WALL_LEFT = new Bounds(3, 0, 4, 5, 12, 12).toAABB();
    private static final AxisAlignedBB WALL_RIGHT = new Bounds(11, 0, 4, 13, 12, 12).toAABB();
    private static final AxisAlignedBB WALL_FRONT = new Bounds(4, 0, 11, 12, 12, 13).toAABB();
    private static final AxisAlignedBB WALL_BACK = new Bounds(4, 0, 3, 12, 12, 5).toAABB();
    private static final AxisAlignedBB LIP_RIGHT = new Bounds(12, 12, 2, 14, 14, 14).toAABB();
    private static final AxisAlignedBB LIP_LEFT = new Bounds(2, 12, 2, 4, 14, 14).toAABB();
    private static final AxisAlignedBB LIP_BACK = new Bounds(4, 12, 2, 12, 14, 4).toAABB();
    private static final AxisAlignedBB LIP_FRONT = new Bounds(4, 12, 12, 12, 14, 14).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(WALL_LEFT, WALL_RIGHT, WALL_FRONT, WALL_BACK, LIP_RIGHT, LIP_LEFT, LIP_BACK, LIP_FRONT);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

    private static final AxisAlignedBB WALL_LEFT_BOTTOM = new Bounds(3, 0, 4, 5, 16, 12).toAABB();
    private static final AxisAlignedBB WALL_RIGHT_BOTTOM = new Bounds(11, 0, 4, 13, 16, 12).toAABB();
    private static final AxisAlignedBB WALL_FRONT_BOTTOM = new Bounds(4, 0, 11, 12, 16, 13).toAABB();
    private static final AxisAlignedBB WALL_BACK_BOTTOM = new Bounds(4, 0, 3, 12, 16, 5).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES_BOTTOM = Lists.newArrayList(WALL_LEFT_BOTTOM, WALL_RIGHT_BOTTOM, WALL_FRONT_BOTTOM, WALL_BACK_BOTTOM);
    private static final AxisAlignedBB BOUNDING_BOX_BOTTOM = Bounds.getBoundingBox(COLLISION_BOXES_BOTTOM);

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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return getActualState(state, source, pos).getValue(TYPE) == ChimneyType.TOP ? BOUNDING_BOX : BOUNDING_BOX_BOTTOM;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return getActualState(state, world, pos).getValue(TYPE) == ChimneyType.TOP ? COLLISION_BOXES : COLLISION_BOXES_BOTTOM;
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
        if(getActualState(state, worldIn, pos).getValue(TYPE) != ChimneyType.TOP) return;

        for(int i = 0; i < 2; i++)
        {
            double posX = 0.25 + (0.5 * rand.nextDouble());
            double posZ = 0.25 + (0.5 * rand.nextDouble());
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + posX, pos.getY() + 0.9, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + posX, pos.getY() + 0.9, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D);
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
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    public enum ChimneyType implements IStringSerializable
    {
        TOP,
        BOTTOM;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }
    }

}
