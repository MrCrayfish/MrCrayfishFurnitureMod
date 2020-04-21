package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Author: MrCrayfish
 */
public class BlockModernWindow extends BlockPane
{
    public static final PropertyBool PANE_NORTH = PropertyBool.create("pane_north");
    public static final PropertyBool PANE_EAST = PropertyBool.create("pane_east");
    public static final PropertyBool PANE_SOUTH = PropertyBool.create("pane_south");
    public static final PropertyBool PANE_WEST = PropertyBool.create("pane_west");
    public static final PropertyBool PANE_UP = PropertyBool.create("pane_up");
    public static final PropertyBool PANE_DOWN = PropertyBool.create("pane_down");

    public BlockModernWindow()
    {
        super(Material.WOOD, true);
        this.setHardness(1.0F);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE)
                .withProperty(PANE_NORTH, Boolean.FALSE)
                .withProperty(PANE_EAST, Boolean.FALSE)
                .withProperty(PANE_SOUTH, Boolean.FALSE)
                .withProperty(PANE_WEST, Boolean.FALSE)
                .withProperty(PANE_UP, Boolean.FALSE)
                .withProperty(PANE_DOWN, Boolean.FALSE));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        state = state.withProperty(NORTH, this.attachesTo(worldIn, worldIn.getBlockState(pos.north()), pos.north(), EnumFacing.SOUTH));
        state = state.withProperty(EAST, this.attachesTo(worldIn, worldIn.getBlockState(pos.east()), pos.east(), EnumFacing.WEST));
        state = state.withProperty(SOUTH, this.attachesTo(worldIn, worldIn.getBlockState(pos.south()), pos.south(), EnumFacing.NORTH));
        state = state.withProperty(WEST, this.attachesTo(worldIn, worldIn.getBlockState(pos.west()), pos.west(), EnumFacing.EAST));
        state = state.withProperty(PANE_NORTH, this.attachesToBlock(worldIn, pos.north()));
        state = state.withProperty(PANE_EAST, this.attachesToBlock(worldIn, pos.east()));
        state = state.withProperty(PANE_SOUTH, this.attachesToBlock(worldIn, pos.south()));
        state = state.withProperty(PANE_WEST, this.attachesToBlock(worldIn, pos.west()));
        state = state.withProperty(PANE_UP, this.attachesToBlock(worldIn, pos.up()));
        state = state.withProperty(PANE_DOWN, this.attachesToBlock(worldIn, pos.down()));
        return state;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, PANE_NORTH, PANE_EAST, PANE_SOUTH, PANE_WEST, PANE_UP, PANE_DOWN);
    }

    private boolean attachesToBlock(IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        return block instanceof BlockPane || block instanceof BlockModernSlidingDoor;
    }
}
