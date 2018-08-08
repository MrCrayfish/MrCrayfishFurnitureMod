package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityWallCabinet;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockWallCabinet extends BlockFurnitureTile
{
    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.75, 0.1, 0.125, 1.0, 0.9, 0.875);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.75, 0.1, 0.125, 1.0, 0.9, 0.875);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.75, 0.1, 0.125, 1.0, 0.9, 0.875);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.75, 0.1, 0.125, 1.0, 0.9, 0.875);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    public BlockWallCabinet(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BATHTROOM_FURNITURE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(this.canPlaceCheck(worldIn, pos, state))
        {
            EnumFacing enumfacing = state.getValue(FACING);

            if(!worldIn.getBlockState(pos.offset(enumfacing)).isNormalCube())
            {
                this.breakBlock(worldIn, pos, state);
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if(tile_entity instanceof TileEntityWallCabinet)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return side.getHorizontalIndex() != -1 && world.isSideSolid(pos.offset(side.getOpposite()), side, true);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        EnumFacing[] aenumfacing = EnumFacing.values();
        int i = aenumfacing.length;

        for(int j = 2; j < i; ++j)
        {
            EnumFacing enumfacing = aenumfacing[j];

            if(worldIn.isSideSolid(pos.offset(enumfacing), enumfacing.getOpposite(), true))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
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

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityWallCabinet();
    }

    private boolean canPlaceCheck(World world, BlockPos pos, IBlockState state)
    {
        if(!this.canPlaceBlockAt(world, pos))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return false;
        }
        else
        {
            return true;
        }
    }
}
