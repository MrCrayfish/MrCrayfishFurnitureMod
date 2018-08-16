package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.DamageSourceFence;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.CollisionHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockElectricFence extends Block
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    protected static final AxisAlignedBB[] BOUNDING_BOX = new AxisAlignedBB[]{new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.4375, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.4375, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.4375, 0.0, 0.0, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.0, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 0.5625, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.0, 0.5625, 1.0, 1.0), new AxisAlignedBB(0.4375, 0.0, 0.4375, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.4375, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.4375, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.4375, 1.0, 1.0, 1.0), new AxisAlignedBB(0.4375, 0.0, 0.0, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.4375, 0.0, 0.0, 1.0, 1.0, 1.0), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5625), new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)};

    private static final AxisAlignedBB COLLISION_BOX_CENTER = new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625);
    private static final AxisAlignedBB COLLISION_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.5625, 0.0, 0.4375, 1.0, 1.0, 0.5625);
    private static final AxisAlignedBB COLLISION_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.5625, 0.0, 0.4375, 1.0, 1.0, 0.5625);
    private static final AxisAlignedBB COLLISION_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.5625, 0.0, 0.4375, 1.0, 1.0, 0.5625);
    private static final AxisAlignedBB COLLISION_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.5625, 0.0, 0.4375, 1.0, 1.0, 0.5625);

    public DamageSource electricFence = new DamageSourceFence("electricFence");

    public BlockElectricFence(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.ANVIL);
        this.setLightLevel(0.2F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
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
        state = state.getActualState(source, pos);
        return BOUNDING_BOX[getBoundingBoxId(state)];
    }

    private static int getBoundingBoxId(IBlockState state)
    {
        int i = 0;

        if(state.getValue(NORTH))
        {
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if(state.getValue(EAST))
        {
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if(state.getValue(SOUTH))
        {
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if(state.getValue(WEST))
        {
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return i;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        state = state.getActualState(worldIn, pos);

        if(state.getValue(NORTH))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_NORTH);
        }

        if(state.getValue(EAST))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_EAST);
        }

        if(state.getValue(SOUTH))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_SOUTH);
        }

        if(state.getValue(WEST))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_WEST);
        }

        addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_CENTER);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityLivingBase && !entity.isDead)
        {
            if(entity instanceof EntityCreeper)
            {
                EntityCreeper creeper = (EntityCreeper) entity;
                EntityLightningBolt lightning = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false);
                if(!creeper.getPowered())
                {
                    creeper.setFire(1);
                    creeper.onStruckByLightning(lightning);
                }
            }
            else if(entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;
                if(!player.isCreative())
                {
                    entity.attackEntityFrom(this.electricFence, (int) 2.0F);
                    world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.zap, SoundCategory.BLOCKS, 0.2F, 1.0F);
                    Triggers.trigger(Triggers.PLAYER_ZAPPED, player);

                    this.sparkle(world, pos);
                }
            }
            else
            {
                entity.attackEntityFrom(this.electricFence, (int) 2.0F);
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.zap, SoundCategory.BLOCKS, 0.2F, 1.0F);
                this.sparkle(world, pos);
            }
        }
    }

    private void sparkle(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        double d0 = 0.0625D;

        for(int l = 0; l < 6; ++l)
        {
            double d1 = (pos.getX() + RANDOM.nextFloat());
            double d2 = (pos.getY() + RANDOM.nextFloat());
            double d3 = (pos.getZ() + RANDOM.nextFloat());

            if(l == 0 && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube(state))
            {
                d2 = (pos.getY() + 1) + d0;
            }

            if(l == 1 && !worldIn.getBlockState(pos.down()).getBlock().isOpaqueCube(state))
            {
                d2 = (pos.getY()) - d0;
            }

            if(l == 2 && !worldIn.getBlockState(pos.south()).getBlock().isOpaqueCube(state))
            {
                d3 = (pos.getZ() + 1) + d0;
            }

            if(l == 3 && !worldIn.getBlockState(pos.north()).getBlock().isOpaqueCube(state))
            {
                d3 = (pos.getZ()) - d0;
            }

            if(l == 4 && !worldIn.getBlockState(pos.east()).getBlock().isOpaqueCube(state))
            {
                d1 = (pos.getX() + 1) + d0;
            }

            if(l == 5 && !worldIn.getBlockState(pos.west()).getBlock().isOpaqueCube(state))
            {
                d1 = (pos.getX()) - d0;
            }

            if(d1 < pos.getX() || d1 > (pos.getX() + 1) || d2 < 0.0D || d2 > (pos.getY() + 1) || d3 < pos.getZ() || d3 > (pos.getZ() + 1))
            {
                worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(NORTH, isFence(world, pos.north())).withProperty(EAST, isFence(world, pos.east())).withProperty(SOUTH, isFence(world, pos.south())).withProperty(WEST, isFence(world, pos.west()));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
    }

    public boolean isFence(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == this;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
