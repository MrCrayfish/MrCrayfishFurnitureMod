package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.SittableUtil;
import com.mrcrayfish.furniture.util.StateHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockParkBench extends BlockFurniture
{
    public static final PropertyEnum<BlockModernCouch.CouchType> TYPE = PropertyEnum.create("type", BlockModernCouch.CouchType.class);

    public BlockParkBench(String id)
    {
        super(Material.WOOD);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, BlockModernCouch.CouchType.BOTH));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        boolean left = false;
        boolean right = false;

        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) instanceof BlockParkBench)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) == StateHelper.Direction.DOWN)
            {
                left = true;
            }
        }
        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) instanceof BlockParkBench)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) == StateHelper.Direction.DOWN)
            {
                right = true;
            }
        }
        if(left && !right)
        {
            return state.withProperty(TYPE, BlockModernCouch.CouchType.LEFT);
        }
        else if(!left && right)
        {
            return state.withProperty(TYPE, BlockModernCouch.CouchType.RIGHT);
        }
        else if(!left && !right)
        {
            return state.withProperty(TYPE, BlockModernCouch.CouchType.BOTH);
        }
        return state.withProperty(TYPE, BlockModernCouch.CouchType.NONE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 6 * 0.0625F);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }
}
