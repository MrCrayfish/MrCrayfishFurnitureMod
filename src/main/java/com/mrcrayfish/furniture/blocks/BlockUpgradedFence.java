package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Author: MrCrayfish
 */
public class BlockUpgradedFence extends BlockFence
{
    public static final PropertyBool POST = PropertyBool.create("post");
    public static final PropertyBool POST_TOP = PropertyBool.create("post_top");

    public BlockUpgradedFence(String id)
    {
        super(Material.WOOD, MapColor.WOOD);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setCreativeTab(null);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POST, false).withProperty(POST_TOP, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        boolean north = canWallConnectTo(worldIn, pos, EnumFacing.NORTH);
        boolean east = canWallConnectTo(worldIn, pos, EnumFacing.EAST);
        boolean south = canWallConnectTo(worldIn, pos, EnumFacing.SOUTH);
        boolean west = canWallConnectTo(worldIn, pos, EnumFacing.WEST);
        boolean post = north && !east && south && !west || !north && east && !south && west;
        boolean postTop = !post && worldIn.isAirBlock(pos.up());
        return state.withProperty(POST, !post).withProperty(POST_TOP, postTop).withProperty(NORTH, north).withProperty(EAST, east).withProperty(SOUTH, south).withProperty(WEST, west);
    }

    private boolean isWallAboveAndConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        IBlockState state = world.getBlockState(pos.up());
        return state.getBlock() == this && canWallConnectTo(world, pos.up(), face);
    }

    private boolean canWallConnectTo(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        BlockPos other = pos.offset(face);
        Block block = world.getBlockState(other).getBlock();
        return block.canBeConnectedTo(world, other, face.getOpposite()) || canConnectTo(world, other, face.getOpposite());
    }

    @Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing face)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, face);
        boolean flag = blockfaceshape == BlockFaceShape.MIDDLE_POLE_THICK || blockfaceshape == BlockFaceShape.MIDDLE_POLE && block instanceof BlockFenceGate;
        return !isExcepBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID || flag;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, POST, POST_TOP, NORTH, EAST, WEST, SOUTH);
    }
}
