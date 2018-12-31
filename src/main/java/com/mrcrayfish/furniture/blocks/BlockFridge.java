package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityFridge;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFridge extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(3, 0, 1, 15, 16, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_OUTER = new Bounds(1, 0.2, 1, 2.4, 16, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_INNER = new Bounds(1.2, 0, 1.2, 3, 15.8, 14.8).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_FRONT = new Bounds(-0.4, 1, 2, 0.4, 11, 3).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_BOTTOM = new Bounds(0.4, 1, 2, 1, 2, 3).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_TOP = new Bounds(0.4, 10, 2, 1, 11, 3).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_11 = new Bounds(15, 4, 3, 15.5, 5, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_12 = new Bounds(15, 3, 12, 15.5, 4, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_13 = new Bounds(15, 2, 3, 15.5, 3, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_14 = new Bounds(15, 1, 3, 15.5, 2, 4).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_15 = new Bounds(15, 0, 3, 15.5, 1, 13).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY, DOOR_OUTER, DOOR_INNER, HANDLE_FRONT, HANDLE_BOTTOM, HANDLE_TOP, PIPE_11, PIPE_12, PIPE_13, PIPE_14, PIPE_15);

    private static final AxisAlignedBB[] PIPE_1 = new Bounds(15, 14, 3, 15.5, 15, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_2 = new Bounds(15, 13, 3, 15.5, 14, 4).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_3 = new Bounds(15, 12, 3, 15.5, 13, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_4 = new Bounds(15, 11, 12, 15.5, 12, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_5 = new Bounds(15, 10, 3, 15.5, 11, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_6 = new Bounds(15, 9, 3, 15.5, 10, 4).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_7 = new Bounds(15, 8, 3, 15.5, 9, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_8 = new Bounds(15, 7, 12, 15.5, 8, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_9 = new Bounds(15, 6, 3, 15.5, 7, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_10 = new Bounds(15, 5, 3, 15.5, 6, 4).getRotatedBounds();
    public static final List<AxisAlignedBB>[] COLLISION_BOXES_PIPING = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, PIPE_1, PIPE_2, PIPE_3, PIPE_4, PIPE_5, PIPE_6, PIPE_7, PIPE_8, PIPE_9, PIPE_10);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_PIPING);
    public static final AxisAlignedBB BOUNDING_BOX_BODY = Bounds.getBoundingBox(Bounds.getRotatedBoundLists(BODY, DOOR_OUTER));
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(BOUNDING_BOX_BODY, BOUNDING_BOX_BODY.offset(0, -1, 0));

    public BlockFridge(Material material)
    {
        super(material);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(null);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
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
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(worldIn.getBlockState(pos.down()).getBlock() == FurnitureBlocks.FREEZER)
        {
            worldIn.destroyBlock(pos.down(), false);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileEntityFridge)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityFridge();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.FREEZER).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.FREEZER);
    }
}
