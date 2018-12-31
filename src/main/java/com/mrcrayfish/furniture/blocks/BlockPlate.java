package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlate extends BlockCollisionRaytrace implements ITileEntityProvider
{
    private static final AxisAlignedBB BASE = new Bounds(5.5, 0, 5.5, 10.5, 1, 10.5).toAABB();
    private static final AxisAlignedBB INNER_LEFT = new Bounds(4.5, 0.5, 3.5, 5.5, 1.5, 12.5).toAABB();
    private static final AxisAlignedBB INNER_RIGHT = new Bounds(10.5, 0.5, 3.5, 11.5, 1.5, 12.5).toAABB();
    private static final AxisAlignedBB OUTER_RIGHT = new Bounds(11.5, 0.5, 4.5, 12.5, 1.5, 11.5).toAABB();
    private static final AxisAlignedBB OUTER_LEFT = new Bounds(3.5, 0.5, 4.5, 4.5, 1.5, 11.5).toAABB();
    private static final AxisAlignedBB BACK = new Bounds(5.5, 0.5, 3.5, 10.5, 1.5, 5.5).toAABB();
    private static final AxisAlignedBB FRONT = new Bounds(5.5, 0.5, 10.5, 10.5, 1.5, 12.5).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(BASE, INNER_LEFT, INNER_RIGHT, OUTER_RIGHT, OUTER_LEFT, BACK, FRONT);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

    public BlockPlate(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityPlate)
        {
            TileEntityPlate tileEntityPlate = (TileEntityPlate) tileEntity;
            if(!heldItem.isEmpty() && tileEntityPlate.getFood().isEmpty())
            {
                if(heldItem.getItem() instanceof ItemFood)
                {
                    tileEntityPlate.setFood(new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
                    tileEntityPlate.setRotation(playerIn.getHorizontalFacing().getHorizontalIndex());
                    TileEntityUtil.markBlockForUpdate(worldIn, pos);
                    heldItem.shrink(1);
                    return true;
                }
            }
            if(!tileEntityPlate.getFood().isEmpty())
            {
                if(!worldIn.isRemote)
                {
                    EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityPlate.getFood());
                    worldIn.spawnEntity(entityFood);
                }
                tileEntityPlate.setFood(ItemStack.EMPTY);
                TileEntityUtil.markBlockForUpdate(worldIn, pos);
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPlate();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
