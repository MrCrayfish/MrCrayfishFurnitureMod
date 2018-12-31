package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCouch extends BlockFurnitureTile
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyEnum<CouchType> TYPE = PropertyEnum.create("type", CouchType.class);

    private static final AxisAlignedBB[] ARMREST_LEFT = new Bounds(-0.016, 8, -3.2, 16.016, 14.4, 0.8).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
    private static final AxisAlignedBB[] ARMREST_RIGHT = new Bounds(-0.016, 8, 15.2, 16.016, 14.4, 19.2).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
    private static final AxisAlignedBB[] BACKREST = new Bounds(12, 9.6, 0, 16, 19.2, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
    private static final AxisAlignedBB[] BASE = new Bounds(0, 0, 0, 16, 9.6, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(BASE, BACKREST);

    private static final AxisAlignedBB BOUNDING_BOX_NONE = new Bounds(0, 0, 0, 16, 14.4, 16).toAABB();
    private static final AxisAlignedBB[] BOUNDING_BOX_RIGHT = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(BASE, ARMREST_RIGHT));
    private static final AxisAlignedBB[] BOUNDING_BOX_LEFT = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(BASE, ARMREST_LEFT));
    private static final AxisAlignedBB[] BOUNDING_BOX_BOTH = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(BASE, ARMREST_RIGHT, ARMREST_LEFT));


    public BlockCouch()
    {
        super(Material.CLOTH);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.CLOTH);

        IBlockState baseState = this.blockState.getBaseState();
        if(isSpecial())
        {
            this.setDefaultState(baseState.withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, CouchType.BOTH));
        }
        else
        {
            this.setDefaultState(baseState.withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, CouchType.BOTH).withProperty(COLOUR, 0));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        CouchType type = state.getValue(TYPE);
        if (type == CouchType.NONE || type == CouchType.CORNER_LEFT || type == CouchType.CORNER_RIGHT)
            return BOUNDING_BOX_NONE;

        int i = state.getValue(FACING).getHorizontalIndex();
        return type == CouchType.BOTH ? BOUNDING_BOX_BOTH[i] : (type == CouchType.LEFT ? BOUNDING_BOX_LEFT[i] : BOUNDING_BOX_RIGHT[i]);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (entity instanceof EntitySeat)
            return EMPTY;

        if (!isActualState)
            state = getActualState(state, world, pos);

        List<AxisAlignedBB> list = Lists.newArrayList();
        int i = state.getValue(FACING).getHorizontalIndex();
        list.addAll(COLLISION_BOXES_BODY[i]);
        CouchType type = state.getValue(TYPE);
        if(type == CouchType.CORNER_LEFT)
        {
            list.add(BACKREST[(i - 1) < 0 ? 3 : i - 1]);
        }
        else if(type == CouchType.CORNER_RIGHT)
        {
            list.add(BACKREST[(i + 1) % 4]);
        }
        else
        {
            if(type != CouchType.RIGHT)
            {
                list.add(ARMREST_LEFT[i]);
            }
            if(type != CouchType.LEFT)
            {
                list.add(ARMREST_RIGHT[i]);
            }
        }
        return list;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(!isSpecial())
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntityColoured)
            {
                int colour = ((TileEntityColoured) tileEntity).getColour();
                state = state.withProperty(COLOUR, colour);
            }
        }

        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) instanceof BlockCouch)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.RIGHT)
            {
                return state.withProperty(TYPE, CouchType.CORNER_RIGHT);
            }
            else if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.LEFT)
            {
                return state.withProperty(TYPE, CouchType.CORNER_LEFT);
            }
        }

        boolean left = false;
        boolean right = false;

        if(!StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT))
        {
            left = true;
        }
        if(!StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT))
        {
            right = true;
        }
        if(left && !right)
        {
            return state.withProperty(TYPE, CouchType.LEFT);
        }
        if(!left && right)
        {
            return state.withProperty(TYPE, CouchType.RIGHT);
        }
        if(!left && !right)
        {
            return state.withProperty(TYPE, CouchType.BOTH);
        }
        return state.withProperty(TYPE, CouchType.NONE);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        if(!isSpecial())
        {
            state = state.withProperty(COLOUR, 0);
        }
        return state;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!isSpecial())
        {
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
        }
        return SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 6 * 0.0625F);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityColoured();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return isSpecial() ? new BlockStateContainer(this, FACING, TYPE) : new BlockStateContainer(this, FACING, COLOUR, TYPE);
    }

    public abstract boolean isSpecial();

    public enum CouchType implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH,
        CORNER_LEFT,
        CORNER_RIGHT;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
