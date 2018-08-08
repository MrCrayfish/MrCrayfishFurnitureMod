package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockTap extends BlockFurniture
{
    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.125, 0.0, 0.4, 0.5625, 1.0, 0.6);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.125, 0.0, 0.4, 0.5625, 1.0, 0.6);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.125, 0.0, 0.4, 0.5625, 1.0, 0.6);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.125, 0.0, 0.4, 0.5625, 1.0, 0.6);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    public BlockTap(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            EnumFacing side = state.getValue(FACING);
            if(hasWaterSource(worldIn, pos))
            {
                if(worldIn.isAirBlock(pos.offset(side.getOpposite())))
                {
                    worldIn.setBlockState(pos.offset(side.getOpposite()), Blocks.WATER.getDefaultState());
                    worldIn.setBlockToAir(pos.down(2));
                }
                else
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.tap_blocked"));
                }
            }
            else
            {
                playerIn.sendMessage(new TextComponentTranslation("cfm.message.tap_nowater"));
            }
        }
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        EnumFacing facing = state.getValue(FACING);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX[facing.getHorizontalIndex()]);
    }

    public boolean hasWaterSource(World world, BlockPos pos)
    {
        if(world.getBlockState(pos.down(2)).getBlock() == Blocks.WATER)
        {
            if(world.getBlockState(pos.down(2)).getValue(BlockLiquid.LEVEL) == 0)
            {
                return true;
            }
        }
        return false;
    }
}
