package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.Random;

public class BlockDivingboard extends BlockFurniture
{
    public final int MAX_JUMP_HEIGHT = 4;

    private static final AxisAlignedBB BOUNDING_BOX = new Bounds(0, 0, 0, 16, 13, 16).toAABB();

    private static final AxisAlignedBB[] HANDLE_LEFT = new Bounds(0, 0, 0, 14, 16, 1.5).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_RIGHT = new Bounds(0, 0, 14.5, 14, 16, 16).getRotatedBounds();
    private static final AxisAlignedBB BOARD[] = new Bounds(0, 4, 2, 16, 6, 14).getRotatedBounds();

    public BlockDivingboard(Material material, boolean plank)
    {
        super(material);
        this.setHardness(1.0F);
        if(plank)
        {
            this.setCreativeTab(null);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(this == FurnitureBlocks.DIVING_BOARD_BASE)
        {
            world.setBlockState(pos.offset(placer.getHorizontalFacing()), FurnitureBlocks.DIVING_BOARD_PLANK.getDefaultState().withProperty(FACING, state.getValue(FACING)));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        EnumFacing facing = state.getValue(FACING);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOARD[facing.getHorizontalIndex() % 2]);
        if(this == FurnitureBlocks.DIVING_BOARD_BASE)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, HANDLE_LEFT[facing.getHorizontalIndex()]);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, HANDLE_RIGHT[facing.getHorizontalIndex()]);
        }
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        if(this == FurnitureBlocks.DIVING_BOARD_BASE) super.onLanded(worldIn, entityIn);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(this == FurnitureBlocks.DIVING_BOARD_PLANK && entityIn instanceof EntityLivingBase)
        {
            float height = entityIn.fallDistance;
            if(height > 0 && !entityIn.isSneaking())
            {
                if(height > MAX_JUMP_HEIGHT) height = MAX_JUMP_HEIGHT;
                entityIn.motionY = 0;
                entityIn.addVelocity(0, getRequiredVelocity(height + 1), 0);
                worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.boing, SoundCategory.BLOCKS, 0.75F, 0.75F, false);
                if(worldIn.isRemote)
                {
                    for(int i = 0; i < 3; i++)
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
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(this == FurnitureBlocks.DIVING_BOARD_BASE)
        {
            worldIn.destroyBlock(pos.offset(state.getValue(FACING)), false);
        }
        else
        {
            worldIn.destroyBlock(pos.offset(state.getValue(FACING).getOpposite()), false);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.DIVING_BOARD_BASE).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.DIVING_BOARD_BASE);
    }
}
