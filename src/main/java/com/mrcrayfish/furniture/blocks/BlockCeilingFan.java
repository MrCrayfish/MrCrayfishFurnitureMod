package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCeilingFan;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockCeilingFan extends Block implements IPowered {
    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing");
    private static final List<AxisAlignedBB> SELECTION_BOX = Arrays.asList(
            new Bounds(4, 5, 4, 12, 16, 12).toAABB(),
            new Bounds(4, 0, 4, 12, 11, 12).toAABB(),
            new Bounds(4, 4, 5, 12, 12, 16).toAABB(),
            new Bounds(4, 4, 0, 12, 12, 11).toAABB(),
            new Bounds(5, 4, 4, 16, 12, 12).toAABB(),
            new Bounds(0, 4, 4, 11, 12, 12).toAABB()
    );
    private static final List<AxisAlignedBB> COLLISION_BOX = Arrays.asList(
            new Bounds(5, 6, 5, 11, 16, 11).toAABB(),
            new Bounds(5, 0, 5, 11, 10, 11).toAABB(),
            new Bounds(5, 5, 6, 11, 11, 16).toAABB(),
            new Bounds(5, 5, 0, 11, 11, 10).toAABB(),
            new Bounds(6, 5, 5, 16, 11, 11).toAABB(),
            new Bounds(0, 5, 5, 10, 11, 11).toAABB()
    );

    public BlockCeilingFan()
    {
        super(Material.WOOD);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SELECTION_BOX.get(state.getValue(FACING).getIndex());
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX.get(state.getValue(FACING).getIndex()));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityCeilingFan)
            {
                TileEntityCeilingFan tileEntityCeilingFan = (TileEntityCeilingFan) tileEntity;
                tileEntityCeilingFan.setPowered(!tileEntityCeilingFan.isPowered());
            }
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityCeilingFan();
    }

    @Override
    public void setPowered(World world, BlockPos pos, boolean powered)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityCeilingFan)
        {
            TileEntityCeilingFan tileEntityCeilingFan = (TileEntityCeilingFan) tileEntity;
            tileEntityCeilingFan.setPowered(powered);
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }
}
