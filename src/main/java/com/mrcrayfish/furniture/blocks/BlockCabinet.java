package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCabinet;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCabinet extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] DOOR = new Bounds(2, 1, 1, 3, 15, 15).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_FRONT = new Bounds(0, 4.5, 3, 1, 11.5, 4).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_TOP = new Bounds(1, 10.5, 3, 2, 11.5, 4).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_BOTTOM = new Bounds(1, 4.5, 3, 2, 5.5, 4).getRotatedBounds();
    private static final AxisAlignedBB[] BODY = new Bounds(3, 0, 0, 16, 16, 16).getRotatedBounds();

    public static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, DOOR, HANDLE_FRONT, HANDLE_TOP, HANDLE_BOTTOM, BODY);
    public static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY));

    public BlockCabinet(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if(tile_entity instanceof TileEntityCabinet)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityCabinet();
    }
}
