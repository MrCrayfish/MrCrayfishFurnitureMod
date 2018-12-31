package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityPrinter;
import com.mrcrayfish.furniture.util.Bounds;

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
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPrinter extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] TOP = new Bounds(4.8, 3, 1, 12.8, 6, 15).getRotatedBounds();
    private static final AxisAlignedBB[] BACK = new Bounds(14.8, 1, 3, 15.8, 9.6, 13).getRotatedBounds();
    private static final AxisAlignedBB[] TRAY_BACK = new Bounds(0, 0.5, 3, 4.8, 1.5, 13).getRotatedBounds();
    private static final AxisAlignedBB[] TRAY_LEFT = new Bounds(12.8, 1, 3, 14.8, 5.4, 4).getRotatedBounds();
    private static final AxisAlignedBB[] TRAY_RIGHT = new Bounds(12.8, 1, 12, 14.8, 5.4, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PAPER = new Bounds(13.4, 2, 4.5, 14.4, 12.2, 11.5).getRotatedBounds();
    private static final AxisAlignedBB[] BASE = new Bounds(4.8, 0, 1, 12.8, 1.5, 15).getRotatedBounds();
    private static final AxisAlignedBB[] CENTER = new Bounds(5.8, 1.5, 1, 12.8, 3, 15).getRotatedBounds();
    private static final AxisAlignedBB[] FRONT_LEFT = new Bounds(4.8, 1.5, 1, 5.8, 3, 3).getRotatedBounds();
    private static final AxisAlignedBB[] FRONT_RIGHT = new Bounds(4.8, 1.5, 13, 5.8, 3, 15).getRotatedBounds();
    private static final AxisAlignedBB[] TRAY_BOTTOM = new Bounds(12.8, 1, 4, 14.8, 2, 12).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_1 = new Bounds(4.4, 4, 2, 5.4, 5, 3).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_2 = new Bounds(4.4, 4, 3.5, 5.4, 5, 4.5).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_3 = new Bounds(4.4, 4, 5, 5.4, 5, 6).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, TOP, BACK, TRAY_BACK, TRAY_LEFT, TRAY_RIGHT, PAPER, BASE, CENTER, FRONT_LEFT, FRONT_RIGHT, TRAY_BOTTOM, BUTTON_1, BUTTON_2, BUTTON_3);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BASE, TOP));

    public BlockPrinter(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.ANVIL);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
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

            if(tile_entity instanceof TileEntityPrinter)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPrinter();
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityPrinter printer = (TileEntityPrinter) world.getTileEntity(pos);
        return printer.isPrinting() ? 1 : 0;
    }
}
