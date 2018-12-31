package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

public class BlockTrampoline extends BlockCollisionRaytrace
{
    public static final PropertyBool SOUTH = PropertyBool.create("back");
    public static final PropertyBool NORTH = PropertyBool.create("forward");
    public static final PropertyBool WEST = PropertyBool.create("left");
    public static final PropertyBool EAST = PropertyBool.create("right");

    private static final AxisAlignedBB[] LEGS = new Bounds(13.2, 0, 1.2, 14.7, 12.5, 2.7).getRotatedBounds();
    private static final AxisAlignedBB BASE_WEST = new Bounds(1.2, 0, 1.2, 2.7, 1.5, 14.7).toAABB();
    private static final AxisAlignedBB BASE_EAST = new Bounds(13.2, 0, 1.2, 14.7, 1.5, 14.7).toAABB();
    private static final AxisAlignedBB TOP = new Bounds(0, 12, 0, 16, 13, 16).toAABB();

    private static final AxisAlignedBB BASE_SOUTH_1 = new Bounds(13.2, 0, 14, 14.7, 1.5, 16).toAABB();
    private static final AxisAlignedBB BASE_SOUTH_2 = new Bounds(1.2, 0, 14, 2.7, 1.5, 16).toAABB();
    private static final List<AxisAlignedBB> BASE_SOUTH = Lists.newArrayList(BASE_SOUTH_1, BASE_SOUTH_2);
    private static final List<AxisAlignedBB> BASE_NORTH = Bounds.transformBoxListsHorizontal(EnumFacing.Axis.X, -14, BASE_SOUTH)[0];

    private static final AxisAlignedBB BOUNDING_BOX_CENTER = Bounds.getBoundingBox(TOP);
    private static final AxisAlignedBB BOUNDING_BOX_NORMAL = Bounds.getBoundingBox(TOP, BASE_WEST);

    public BlockTrampoline(Material materialIn)
    {
        super(materialIn);
        this.setHardness(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SOUTH, false).withProperty(NORTH, false).withProperty(WEST, false).withProperty(EAST, false));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        return state.getValue(WEST) && state.getValue(EAST) ? BOUNDING_BOX_CENTER : BOUNDING_BOX_NORMAL;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = getActualState(state, world, pos);

        List<AxisAlignedBB> boxes = Lists.newArrayList(TOP);
        boolean south = state.getValue(SOUTH);
        boolean west = state.getValue(WEST);
        boolean north = state.getValue(NORTH);
        boolean east = state.getValue(EAST);
        if (!west)
        {
            boxes.add(BASE_WEST);
            if (south)
                boxes.add(BASE_SOUTH.get(1));

            if (north)
                boxes.add(BASE_NORTH.get(1));
        }
        if (!east)
        {
            boxes.add(BASE_EAST);
            if (south)
                boxes.add(BASE_SOUTH.get(0));

            if (north)
                boxes.add(BASE_NORTH.get(0));
        }
        boolean[] sides = new boolean[] {south, west, north, east};
        for (int i = 0; i < 4; i++)
        {
            boolean clear = !sides[i];
            if (clear && !sides[(i + 1) % 4])
                boxes.add(LEGS[i]);
        }
        return boxes;
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
    public void onLanded(World worldIn, Entity entityIn)
    {
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(entityIn instanceof EntityLivingBase)
        {
            float height = entityIn.fallDistance;
            if(height > 0 && !entityIn.isSneaking())
            {
                if(height > 9) height = 9;
                entityIn.motionY = 0;
                entityIn.addVelocity(0, getRequiredVelocity(height + 1), 0);
                worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.boing, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
                if(worldIn.isRemote)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, entityIn.posX, entityIn.posY, entityIn.posZ, 1, 1, 1, 0);
                    }
                }
            }
            entityIn.fallDistance = 0;
        }
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles)
    {
        return true;
    }

    public double getRequiredVelocity(float height)
    {
        return Math.sqrt(0.22 * height);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        boolean forward = world.getBlockState(pos.north()).getBlock() == this;
        boolean back = world.getBlockState(pos.south()).getBlock() == this;
        boolean left = world.getBlockState(pos.west()).getBlock() == this;
        boolean right = world.getBlockState(pos.east()).getBlock() == this;
        return state.withProperty(SOUTH, back).withProperty(NORTH, forward).withProperty(WEST, left).withProperty(EAST, right);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SOUTH, NORTH, WEST, EAST);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
