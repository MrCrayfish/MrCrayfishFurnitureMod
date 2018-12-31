package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityColoured;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.SeatUtil;
import com.mrcrayfish.furniture.util.StateHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class BlockModernCouch extends BlockFurnitureTile
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyEnum<CouchType> TYPE = PropertyEnum.create("type", CouchType.class);

    private static final AxisAlignedBB[] BASE = new Bounds(2, 3, 1, 14, 9, 15).getRotatedBounds();
    private static final AxisAlignedBB[] BACKREST = new Bounds(2, 9, 1, 14, 19, 5).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(BASE, BACKREST);

    private static final AxisAlignedBB[] BASE_LEFT = new Bounds(0, 3, 1, 2, 9, 15).getRotatedBounds();
    private static final AxisAlignedBB[] BACKREST_LEFT = new Bounds(0, 9, 1, 2, 19, 5).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_SOLID_LEFT = Bounds.getRotatedBoundLists(BASE_LEFT, BACKREST_LEFT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_SOLID_RIGHT = Bounds.transformBoxListsHorizontal(EnumFacing.Axis.Z, 14, COLLISION_BOXES_SOLID_LEFT);

    private static final Bounds BOUNDS_ARMREST_TOP = new Bounds(0, 11, 1, 2, 13, 15);
    private static final AxisAlignedBB[] ARMREST_TOP = BOUNDS_ARMREST_TOP.getRotatedBounds();
    private static final AxisAlignedBB[] ARMREST_BOTTOM = new Bounds(0, 0, 1, 2, 2, 15).getRotatedBounds();
    private static final AxisAlignedBB[] ARMREST_FRONT = new Bounds(0, 2, 13, 2, 11, 15).getRotatedBounds();
    private static final AxisAlignedBB[] ARMREST_BACK = new Bounds(0, 2, 1, 2, 11, 3).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_ARMREST_LEFT = Bounds.getRotatedBoundLists(ARMREST_TOP, ARMREST_BOTTOM, ARMREST_FRONT, ARMREST_BACK);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_ARMREST_RIGHT = Bounds.transformBoxListsHorizontal(EnumFacing.Axis.Z, 14, COLLISION_BOXES_ARMREST_LEFT);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTH = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_ARMREST_LEFT, COLLISION_BOXES_ARMREST_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_NONE = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_SOLID_LEFT, COLLISION_BOXES_SOLID_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_RIGHT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_SOLID_LEFT, COLLISION_BOXES_ARMREST_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_LEFT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_ARMREST_LEFT, COLLISION_BOXES_SOLID_RIGHT);

    private static final AxisAlignedBB[] BOUNDING_BOX_BOTH = Bounds.getBoundingBoxes(COLLISION_BOXES_ARMREST_LEFT, COLLISION_BOXES_ARMREST_RIGHT);
    private static final AxisAlignedBB[] BOUNDING_BOX_NONE = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(BASE, ARMREST_TOP, BOUNDS_ARMREST_TOP.rotateY(Rotation.CLOCKWISE_180).getRotatedBounds()));

    public BlockModernCouch()
    {
        super(Material.CLOTH);
        this.setUnlocalizedName("modern_couch");
        this.setRegistryName("modern_couch");
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, CouchType.BOTH).withProperty(COLOUR, 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(TYPE) == CouchType.NONE ? BOUNDING_BOX_NONE[i] : BOUNDING_BOX_BOTH[i];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (entity instanceof EntitySeat)
            return EMPTY;

        if (!isActualState)
            state = getActualState(state, world, pos);

        int i = state.getValue(FACING).getHorizontalIndex();
        switch (state.getValue(TYPE))
        {
            case LEFT: return COLLISION_BOXES_LEFT[i];
            case RIGHT: return COLLISION_BOXES_RIGHT[i];
            case NONE: return COLLISION_BOXES_NONE[i];
            default: return COLLISION_BOXES_BOTH[i];
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            int colour = ((TileEntityColoured) tileEntity).getColour();
            state = state.withProperty(COLOUR, colour);
        }

        boolean left = false;
        boolean right = false;

        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) == this)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) == StateHelper.Direction.DOWN)
            {
                left = true;
            }
        }
        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) == this)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) == StateHelper.Direction.DOWN)
            {
                right = true;
            }
        }
        if(left && !right)
        {
            return state.withProperty(TYPE, CouchType.LEFT);
        }
        else if(!left && right)
        {
            return state.withProperty(TYPE, CouchType.RIGHT);
        }
        else if(!left && !right)
        {
            return state.withProperty(TYPE, CouchType.BOTH);
        }
        return state.withProperty(TYPE, CouchType.NONE);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(COLOUR, meta);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!heldItem.isEmpty() && heldItem.getItem() == Items.NAME_TAG)
        {
            if(heldItem.hasDisplayName())
            {
                if(heldItem.getDisplayName().equals("jeb_"))
                {
                    worldIn.setBlockState(pos, FurnitureBlocks.COUCH_JEB.getDefaultState().withProperty(FACING, state.getValue(FACING)));
                    if(!playerIn.isCreative())
                    {
                        heldItem.shrink(1);
                    }
                    Triggers.trigger(Triggers.CREATE_COUCH_JEB, playerIn);
                    return true;
                }
            }
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            TileEntityColoured tileEntityColoured = (TileEntityColoured) tileEntity;
            if(!heldItem.isEmpty())
            {
                if(heldItem.getItem() instanceof ItemDye)
                {
                    if(tileEntityColoured.getColour() != (15 - heldItem.getItemDamage()))
                    {
                        tileEntityColoured.setColour(heldItem.getItemDamage());
                        if(!playerIn.isCreative())
                        {
                            heldItem.shrink(1);
                        }
                        TileEntityUtil.markBlockForUpdate(worldIn, pos);
                    }
                    return true;
                }
            }
        }
        return SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 5 * 0.0625F);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityColoured();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, COLOUR, TYPE);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            ((TileEntityColoured) tileEntity).setColour(15 - stack.getMetadata());
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if (tileEntity instanceof TileEntityColoured)
        {
            TileEntityColoured couch = (TileEntityColoured) tileEntity;
            ItemStack itemstack = new ItemStack(this, 1, couch.getColour());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, tileEntity, stack);
        }
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
    {
        for(int i = 0; i < EnumDyeColor.values().length; i++)
        {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        int metadata = 0;
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            metadata = ((TileEntityColoured) tileEntity).getColour();
        }
        return new ItemStack(this, 1, metadata);
    }

    public enum CouchType implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
