package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockUpgradedGate extends BlockFurniture
{
    public static final PropertyEnum<BlockDoor.EnumHingePosition> HINGE = PropertyEnum.create("hinge", BlockDoor.EnumHingePosition.class);
    public static final PropertyBool OPENED = PropertyBool.create("opened");

    private static final AxisAlignedBB[] BOUNDING_BOXES = new Bounds(6, 0, -1, 10, 17, 17).getRotatedBounds();
    private static final AxisAlignedBB[] COLLISION_BOXES = new Bounds(6, 0, -1, 10, 24, 17).getRotatedBounds();

    public BlockUpgradedGate(String id)
    {
        super(Material.WOOD);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(OPENED, false);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(OPENED))
        {
            return NULL_AABB;
        }
        else
        {
            return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        worldIn.setBlockState(pos, state.withProperty(OPENED, !state.getValue(OPENED)));
        worldIn.playEvent(playerIn, state.getValue(OPENED) ? 1008 : 1014, pos, 0);
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(OPENED, (meta & 4) != 0).withProperty(HINGE, (meta & 8) == 0 ? BlockDoor.EnumHingePosition.LEFT : BlockDoor.EnumHingePosition.RIGHT);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getHorizontalIndex();

        if(state.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT)
        {
            i |= 8;
        }

        if(state.getValue(OPENED))
        {
            i |= 4;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, HINGE, OPENED);
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        Block connector = world.getBlockState(pos.offset(facing)).getBlock();
        return connector instanceof BlockFence || connector instanceof BlockWall;
    }
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.MIDDLE_POLE;
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return !state.getValue(OPENED) ? PathNodeType.FENCE : PathNodeType.OPEN;
    }
}
