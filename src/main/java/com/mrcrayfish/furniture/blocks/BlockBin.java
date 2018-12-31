package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.tileentity.TileEntityBin;
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

import java.util.List;

import javax.annotation.Nullable;

public class BlockBin extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(3.2, 0, 3.2, 12.8, 12.8, 12.8).getRotatedBounds();
    private static final AxisAlignedBB[] LID = new Bounds(1.6, 12.16, 1.6, 14.4, 14.4, 14.4).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_RIGHT = new Bounds(7.2, 14.4, 4, 8.8, 16, 5.6).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_LEFT = new Bounds(7.2, 14.4, 10.4, 8.8, 16, 12).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_TOP = new Bounds(7.2, 15.2, 5.6, 8.8, 16, 10.4).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.CLOCKWISE_90, BODY, LID, HANDLE_RIGHT, HANDLE_LEFT, HANDLE_TOP);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(BODY[0], LID[0]);

    public BlockBin(Material material)
    {
        super(material);
        this.setSoundType(SoundType.ANVIL);
        this.setHardness(0.5F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return entity instanceof EntitySeat ? EMPTY : COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
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

            if(tile_entity instanceof TileEntityBin)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBin();
    }
}
