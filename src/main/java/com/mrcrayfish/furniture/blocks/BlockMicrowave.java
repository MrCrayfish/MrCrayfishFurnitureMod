package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

public class BlockMicrowave extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(4, 0, 1, 13, 8, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_BOTTOM = new Bounds(3, 0, 1, 4, 2, 12).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_TOP = new Bounds(3, 6, 1, 4, 8, 12).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_LEFT = new Bounds(3, 1, 1, 4, 7, 3).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_RIGHT = new Bounds(3, 1, 10, 4, 7, 12).getRotatedBounds();
    private static final AxisAlignedBB[] PANEL = new Bounds(3, 0, 12.5, 4, 8, 15).getRotatedBounds();
    private static final AxisAlignedBB[] WINDOW = new Bounds(3.5, 2, 3, 3.5, 6, 10).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY, DOOR_BOTTOM, DOOR_TOP, DOOR_LEFT, DOOR_RIGHT, PANEL, WINDOW);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    public BlockMicrowave(Material material)
    {
        super(material);
        this.setHardness(0.5F);
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

            if(tile_entity instanceof TileEntityMicrowave)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMicrowave();
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityMicrowave microwave = (TileEntityMicrowave) world.getTileEntity(pos);
        return microwave.isCooking() ? 1 : 0;
    }
}
