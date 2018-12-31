package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityFreezer;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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

public class BlockFreezer extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(3, 0, 1, 15, 16, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_OUTER = new Bounds(1, 0, 1, 2.4, 15.8, 15).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_INNER = new Bounds(1.2, 0.2, 1.2, 3, 16, 14.8).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_FRONT = new Bounds(-0.4, 8, 2, 0.4, 15, 3).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_BOTTOM = new Bounds(0.4, 8, 2, 1, 9, 3).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_TOP = new Bounds(0.4, 14, 2, 1, 15, 3).getRotatedBounds();
    private static final AxisAlignedBB[] COOLER = new Bounds(15, 1, 3, 16, 5, 13).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_1 = new Bounds(15, 15, 12, 15.5, 16, 13).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY, DOOR_OUTER, DOOR_INNER, HANDLE_FRONT, HANDLE_BOTTOM, HANDLE_TOP, COOLER, PIPE_1);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, BlockFridge.COLLISION_BOXES_PIPING);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(BlockFridge.BOUNDING_BOX_BODY, BlockFridge.BOUNDING_BOX_BODY.offset(0, 1, 0));

    public BlockFreezer(Material material)
    {
        super(material);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.METAL);
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
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, net.minecraft.entity.EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), FurnitureBlocks.FRIDGE.getDefaultState().withProperty(FACING, state.getValue(FACING)));
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_APPLIANCE, (EntityPlayer) placer);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isAirBlock(pos.up());
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(worldIn.getBlockState(pos.up()).getBlock() == FurnitureBlocks.FRIDGE)
        {
            worldIn.destroyBlock(pos.up(), false);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if(tile_entity instanceof TileEntityFreezer)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityFreezer();
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityFreezer freezer = (TileEntityFreezer) world.getTileEntity(pos);
        return freezer.isFreezing() ? 1 : 0;
    }
}
