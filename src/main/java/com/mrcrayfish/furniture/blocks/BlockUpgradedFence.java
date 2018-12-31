package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockUpgradedFence extends BlockFence
{
    public static final PropertyBool POST = PropertyBool.create("post");
    public static final PropertyBool POST_TOP = PropertyBool.create("post_top");

    private static final AxisAlignedBB COLLISION_BOX_CENTER = new Bounds(7, 0, 7, 9, 24, 9).toAABB();
    private static final AxisAlignedBB[] COLLISION_BOXES = new Bounds(9, 0, 7, 16, 24, 9).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);

    public BlockUpgradedFence(String id)
    {
        super(Material.WOOD, MapColor.WOOD);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POST, false).withProperty(POST_TOP, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        boolean south = state.getValue(SOUTH);
        AxisAlignedBB box = new Bounds(state.getValue(WEST) ? 0 : 6, 0, state.getValue(NORTH) ? 0 : 6, state.getValue(EAST) ? 16 : 10, 16, south ? 16 : 10).toAABB();
        if (!state.getValue(POST))
        {
            if (south)
                box = box.grow(-0.0625, 0, 0);
            else
                box = box.grow(0, 0, -0.0625);
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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        boolean north = canFenceConnectTo(worldIn, pos, EnumFacing.NORTH);
        boolean east = canFenceConnectTo(worldIn, pos, EnumFacing.EAST);
        boolean south = canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH);
        boolean west = canFenceConnectTo(worldIn, pos, EnumFacing.WEST);
        boolean post = north && !east && south && !west || !north && east && !south && west;
        boolean postTop = !post && worldIn.isAirBlock(pos.up());
        return state.withProperty(POST, !post).withProperty(POST_TOP, postTop).withProperty(NORTH, north).withProperty(EAST, east).withProperty(SOUTH, south).withProperty(WEST, west);
    }

    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        BlockPos other = pos.offset(facing);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, POST, POST_TOP, NORTH, EAST, WEST, SOUTH);
    }
}
