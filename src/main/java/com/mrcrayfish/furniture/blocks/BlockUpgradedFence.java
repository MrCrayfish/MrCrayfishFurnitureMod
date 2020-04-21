package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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
        this.setHardness(2.0F);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POST, false).withProperty(POST_TOP, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
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
