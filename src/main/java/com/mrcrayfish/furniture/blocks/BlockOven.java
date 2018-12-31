package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityOven;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOven extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(2, 0, 1, 16, 16, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DIAL_2 = new Bounds(1, 14, 13, 2, 15, 14).getRotatedBounds();
    private static final AxisAlignedBB[] DIAL_1 = new Bounds(1, 14, 11, 2, 15, 12).getRotatedBounds();
    private static final AxisAlignedBB[] ELEMENT = new Bounds(1, 14, 2, 2, 15, 3).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_TOP = new Bounds(1, 11, 2, 2, 13, 14).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_BOTTOM = new Bounds(1, 1, 2, 2, 3, 14).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_LEFT = new Bounds(1, 3, 2, 2, 11, 4).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_RIGHT = new Bounds(1, 3, 12, 2, 11, 14).getRotatedBounds();
    private static final AxisAlignedBB[] GLASS = new Bounds(1.5, 3, 4, 2.5, 11, 12).getRotatedBounds();
    private static final AxisAlignedBB[] BURNER_1 = new Bounds(3, 15.5, 3, 7, 16.5, 7).getRotatedBounds();
    private static final AxisAlignedBB[] BURNER_2 = new Bounds(3, 15.5, 9, 7, 16.5, 13).getRotatedBounds();
    private static final AxisAlignedBB[] BURNER_3 = new Bounds(9, 15.5, 9, 13, 16.5, 13).getRotatedBounds();
    private static final AxisAlignedBB[] BURNER_4 = new Bounds(9, 15.5, 3, 13, 16.5, 7).getRotatedBounds();
    private static final AxisAlignedBB[] BACKING_TOP = new Bounds(14, 16, 1, 16, 18, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DIAL_3 = new Bounds(1, 14, 9, 2, 15, 10).getRotatedBounds();
    private static final AxisAlignedBB[] DIAL_4 = new Bounds(1, 14, 7, 2, 15, 8).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE = new Bounds(0.5, 11.5, 6, 1.5, 12.5, 10).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY, DIAL_2, DIAL_1, ELEMENT, DOOR_TOP, DOOR_BOTTOM, DOOR_LEFT, DOOR_RIGHT, GLASS, BURNER_1, BURNER_2, BURNER_3, BURNER_4, BACKING_TOP, DIAL_3, DIAL_4, HANDLE);

    public BlockOven(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BlockDishwasher.BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_APPLIANCE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
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
        if(!worldIn.isRemote)
        {
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if(tile_entity instanceof TileEntityOven)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityOven();
    }


    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityOven oven = (TileEntityOven) world.getTileEntity(pos);
        return oven.isCooking() ? 1 : 0;
    }
}
