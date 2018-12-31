package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

public class BlockWashingMachine extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] DOOR_BOTTOM = new Bounds(1, 3, 3, 2, 5, 13).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_TOP = new Bounds(1, 11, 3, 2, 13, 13).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_LEFT = new Bounds(1, 5, 3, 2, 11, 5).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_RIGHT = new Bounds(1, 5, 10.96, 2, 11, 13).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE = new Bounds(0, 6, 11.5, 1, 10, 12.5).getRotatedBounds();
    private static final AxisAlignedBB[] DOOR_GLASS = new Bounds(1.5, 5, 5, 1.5, 11, 11).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_DOOR = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, DOOR_BOTTOM, DOOR_TOP, DOOR_LEFT, DOOR_RIGHT, HANDLE, DOOR_GLASS);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_HANDLE = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, HANDLE);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(BlockDishwasher.COLLISION_BOXES_BODY, COLLISION_BOXES_DOOR, COLLISION_BOXES_HANDLE);

    public BlockWashingMachine(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.ANVIL);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if(tile_entity instanceof TileEntityWashingMachine)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityWashingMachine();
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityWashingMachine washing_machine = (TileEntityWashingMachine) world.getTileEntity(pos);
        return washing_machine.isWashing() ? 1 : 0;
    }
}
