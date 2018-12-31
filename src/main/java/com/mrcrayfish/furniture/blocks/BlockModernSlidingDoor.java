package com.mrcrayfish.furniture.blocks;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.tileentity.TileEntityModernSlidingDoor;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author: MrCrayfish
 */
public class BlockModernSlidingDoor extends BlockFurniture implements IPowered
{
    public static final PropertyBool TOP = PropertyBool.create("top");

    private static final Bounds FRAME_TOP_CLOSED = new Bounds(0, 14, 6, 16, 16, 10);
    private static final Bounds FRAME_LEFT_CLOSED = new Bounds(0, 0, 6, 2, 14, 10);
    private static final Bounds FRAME_RIGHT_CLOSED = new Bounds(14, 0, 6, 16, 14, 10);
    private static final Bounds GLASS_CLOSED = new Bounds(2, 0, 6.5, 14, 15, 9.5);
    private static final Bounds HANDLE_1_CLOSED = new Bounds(14, 0, 5, 15, 2, 11);
    private static final Bounds HANDLE_2_CLOSED = new Bounds(13, 0, 6, 14, 3, 10);
    private static final Bounds[] BOUNDS_CLOSED = new Bounds[] {FRAME_TOP_CLOSED, FRAME_LEFT_CLOSED, FRAME_RIGHT_CLOSED, GLASS_CLOSED, HANDLE_1_CLOSED, HANDLE_2_CLOSED};

    private static final AxisAlignedBB[][] BOXES_CLOSED_TOP = Arrays.stream(BOUNDS_CLOSED).map(bounds -> bounds.getRotatedBounds()).toArray(AxisAlignedBB[][]::new);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_CLOSED_TOP = Bounds.getRotatedBoundLists(BOXES_CLOSED_TOP);

    private static final AxisAlignedBB[][] BOXES_CLOSED_BOTTOM = Arrays.stream(BOUNDS_CLOSED).map(bounds -> bounds.rotateX(Rotation.CLOCKWISE_180).getRotatedBounds()).toArray(AxisAlignedBB[][]::new);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_CLOSED_BOTTOM = Bounds.getRotatedBoundLists(BOXES_CLOSED_BOTTOM);

    private static final List<AxisAlignedBB>[] BOUNDING_BOX_CLOSED_FRAME = Bounds.getRotatedBoundLists(FRAME_TOP_CLOSED.getRotatedBounds(), FRAME_RIGHT_CLOSED.getRotatedBounds());
    private static final List<AxisAlignedBB>[] BOUNDING_BOX_CLOSED_FRAME_TRANSLATED = Bounds.transformBoxListsVertical(1, BOUNDING_BOX_CLOSED_FRAME);
    private static final AxisAlignedBB[] BOUNDING_BOX_CLOSED = Bounds.getBoundingBoxes(BOUNDING_BOX_CLOSED_FRAME, BOUNDING_BOX_CLOSED_FRAME_TRANSLATED);

    private static final Bounds FRAME_TOP_OPEN = new Bounds(0, 14, 6, 3, 16, 10);
    private static final Bounds FRAME_RIGHT_OPEN = new Bounds(1, 0, 6, 3, 14, 10);
    private static final Bounds GLASS_OPEN = new Bounds(0, 0, 6.5, 1, 14, 9.5);
    private static final Bounds HANDLE_1_OPEN = new Bounds(1, 0, 5, 2, 2, 11);
    private static final Bounds HANDLE_2_OPEN = new Bounds(0, 0, 6, 1, 3, 10);
    private static final Bounds[] BOUNDS_OPEN = new Bounds[] {FRAME_TOP_OPEN, FRAME_RIGHT_OPEN, GLASS_OPEN, HANDLE_1_OPEN, HANDLE_2_OPEN};

    private static final AxisAlignedBB[][] BOXES_OPEN_TOP = Arrays.stream(BOUNDS_OPEN).map(bounds -> bounds.getRotatedBounds()).toArray(AxisAlignedBB[][]::new);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_OPEN_TOP = Bounds.getRotatedBoundLists(BOXES_OPEN_TOP);

    private static final AxisAlignedBB[][] BOXES_OPEN_BOTTOM = Arrays.stream(BOUNDS_OPEN).map(bounds -> bounds.rotateX(Rotation.CLOCKWISE_180).getRotatedBounds()).toArray(AxisAlignedBB[][]::new);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_OPEN_BOTTOM = Bounds.getRotatedBoundLists(BOXES_OPEN_BOTTOM);

    private static final List<AxisAlignedBB>[] BOUNDING_BOX_OPEN_FRAME = Bounds.getRotatedBoundLists(FRAME_TOP_OPEN.getRotatedBounds(), FRAME_RIGHT_OPEN.getRotatedBounds());
    private static final List<AxisAlignedBB>[] BOUNDING_BOX_OPEN_FRAME_TRANSLATED = Bounds.transformBoxListsVertical(1, BOUNDING_BOX_OPEN_FRAME);
    private static final AxisAlignedBB[] BOUNDING_BOX_OPEN = Bounds.getBoundingBoxes(BOUNDING_BOX_OPEN_FRAME, BOUNDING_BOX_OPEN_FRAME_TRANSLATED);

    public BlockModernSlidingDoor()
    {
        super(Material.WOOD);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TOP, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        int i = state.getValue(FACING).getHorizontalIndex();
        AxisAlignedBB box = isOpen(source, pos) ? BOUNDING_BOX_OPEN[i] : BOUNDING_BOX_CLOSED[i];
        return state.getValue(TOP) ? box.offset(0, -1, 0) : box;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        boolean open = isOpen(world, pos);
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(TOP) ? (open ? COLLISION_BOXES_OPEN_TOP[i] : COLLISION_BOXES_CLOSED_TOP[i])
                : (open ? COLLISION_BOXES_OPEN_BOTTOM[i] : COLLISION_BOXES_CLOSED_BOTTOM[i]);
    }

    private boolean isOpen(IBlockAccess source, BlockPos pos)
    {
        TileEntity tileEntity = source.getTileEntity(pos);
        return tileEntity instanceof TileEntityModernSlidingDoor ? ((TileEntityModernSlidingDoor) tileEntity).isPowered() : false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(!state.getValue(TOP))
        {
            worldIn.setBlockState(pos.up(), FurnitureBlocks.MODERN_SLIDING_DOOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(TOP, true));
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(state.getValue(TOP))
        {
            worldIn.destroyBlock(pos.down(), false);
        }
        else
        {
            worldIn.destroyBlock(pos.up(), false);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityModernSlidingDoor)
        {
            TileEntityModernSlidingDoor slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
            boolean powered = slidingDoor.isPowered();
            setPowered(world, pos, state, !powered);
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!worldIn.isRemote && blockIn instanceof BlockModernSlidingDoor)
        {
            boolean hasPower = worldIn.isBlockPowered(pos);
            if(state.getValue(TOP))
            {
                hasPower |= worldIn.isBlockPowered(pos.down());
            }
            else
            {
                hasPower |= worldIn.isBlockPowered(pos.up());
            }
            setPowered(worldIn, pos, state, hasPower);
        }
    }

    private void setPowered(World world, BlockPos pos, IBlockState state, boolean powered)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityModernSlidingDoor)
        {
            TileEntityModernSlidingDoor slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
            if(slidingDoor.isPowered() == powered)
                return;

            slidingDoor.setPowered(powered);
            slidingDoor.sync();

            if(powered)
            {
                world.playSound(null, pos, FurnitureSounds.sliding_door_open, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            else
            {
                world.playSound(null, pos, FurnitureSounds.sliding_door_close, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            if(state.getValue(TOP))
            {
                pos = pos.offset(EnumFacing.DOWN);
            }
            else
            {
                pos = pos.offset(EnumFacing.UP);
            }

            tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof TileEntityModernSlidingDoor)
            {
                slidingDoor = (TileEntityModernSlidingDoor) tileEntity;
                slidingDoor.setPowered(powered);
                slidingDoor.sync();
            }

            EnumFacing slidingFacing = state.getValue(FACING).rotateYCCW();
            BlockPos otherDoor = pos.offset(slidingFacing.getOpposite());
            IBlockState state1 = world.getBlockState(otherDoor);
            if(state1.getBlock() instanceof BlockModernSlidingDoor)
            {
                ((BlockModernSlidingDoor) state1.getBlock()).setPowered(world, otherDoor, state1, powered);
            }
        }
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
        return new TileEntityModernSlidingDoor();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(TOP, meta / 4 > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(TOP) ? 4 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TOP);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void setPowered(World world, BlockPos pos, boolean powered)
    {
        setPowered(world, pos, world.getBlockState(pos), powered);
    }
}
