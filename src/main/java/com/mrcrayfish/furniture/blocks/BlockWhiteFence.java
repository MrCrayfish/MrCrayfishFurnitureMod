package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class BlockWhiteFence extends BlockFence
{
    public static final PropertyBool POST = PropertyBool.create("post");

    private static final AxisAlignedBB COLLISION_BOX_CENTER = new Bounds(7, 0, 7, 9, 24, 9).toAABB();
    private static final AxisAlignedBB[] COLLISION_BOXES = new Bounds(9, 0, 7, 16, 24, 9).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);

    public BlockWhiteFence(Material material)
    {
        super(material, material.getMaterialMapColor());
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        boolean south = state.getValue(SOUTH);
        AxisAlignedBB box = new Bounds(state.getValue(WEST) ? 0 : 6.5, 0, state.getValue(NORTH) ? 0 : 6.5, state.getValue(EAST) ? 16 : 9.5, 16, south ? 16 : 9.5).toAABB();
        if (!state.getValue(POST))
        {
            if (south)
                box = box.grow(-0.05, 0, 0);
            else
                box = box.grow(0, 0, -0.05);
        }
        return box;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = getActualState(state, world, pos);

        if (state.getValue(SOUTH))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOXES[0]);

        if (state.getValue(WEST))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOXES[1]);

        if (state.getValue(NORTH))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOXES[2]);

        if (state.getValue(EAST))
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOXES[3]);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_CENTER);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_OUTDOOR_FURNITURE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        boolean north = canFenceConnectTo(worldIn, pos, EnumFacing.NORTH);
        boolean east = canFenceConnectTo(worldIn, pos, EnumFacing.EAST);
        boolean south = canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH);
        boolean west = canFenceConnectTo(worldIn, pos, EnumFacing.WEST);
        boolean post = north && !east && south && !west || !north && east && !south && west;
        return state.withProperty(POST, !post).withProperty(NORTH, north).withProperty(EAST, east).withProperty(SOUTH, south).withProperty(WEST, west);
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, POST, NORTH, EAST, WEST, SOUTH);
    }
}
