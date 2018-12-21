package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityColoured;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
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
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Locale;

public abstract class BlockCouch extends BlockFurnitureTile
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyEnum<CouchType> TYPE = PropertyEnum.create("type", CouchType.class);

    private static final AxisAlignedBB COUCH_BASE = new Bounds(0, 0, 0, 16, 9.6, 16).toAABB();

    private static final AxisAlignedBB[] COUCH_BACKREST = new Bounds(12.8, 9.6, 0, 16, 19.36, 16).getRotatedBounds();
    private static final AxisAlignedBB[] COUCH_ARMREST_LEFT = new Bounds(0, 8, 14.4, 16, 14.4, 16).getRotatedBounds();
    private static final AxisAlignedBB[] COUCH_ARMREST_RIGHT = new Bounds(0, 8, 0, 16, 14.4, 1.6).getRotatedBounds();

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
        return FULL_BLOCK_AABB;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(!isSpecial())
        {
            int colour = ((TileEntityColoured) world.getTileEntity(pos)).getColour();
            state = state.withProperty(COLOUR, colour);
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

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);

        IBlockState actualState = this.getActualState(state, world, pos);

        list.add(COUCH_BACKREST[facing.getHorizontalIndex()]);
        list.add(COUCH_BASE);

        if(actualState.getValue(TYPE) == CouchType.CORNER_LEFT)
        {
            if(facing.getAxis() == Axis.X)
            {
                list.add(COUCH_BACKREST[(facing.getHorizontalIndex() - 1) < 0 ? 3 : facing.getHorizontalIndex() - 1]);
            }
            else
            {
                list.add(COUCH_BACKREST[(facing.getHorizontalIndex() + 1) % 4]);
            }
        }
        else if(actualState.getValue(TYPE) == CouchType.CORNER_RIGHT)
        {
            if(facing.getAxis() == Axis.X)
            {
                list.add(COUCH_BACKREST[(facing.getHorizontalIndex() + 1) % 4]);
            }
            else
            {
                list.add(COUCH_BACKREST[(facing.getHorizontalIndex() - 1) < 0 ? 3 : facing.getHorizontalIndex() - 1]);
            }
        }
        else
        {
            if(StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT))
            {
                list.add(COUCH_ARMREST_LEFT[facing.getHorizontalIndex()]);
            }
            if(StateHelper.isAirBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT))
            {
                list.add(COUCH_ARMREST_RIGHT[facing.getHorizontalIndex()]);
            }
        }

        return list;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean p_185477_7_)
    {
        if(!(entity instanceof EntitySeat))
        {
            List<AxisAlignedBB> boxes = this.getCollisionBoxList(this.getActualState(state, worldIn, pos), worldIn, pos);
            for(AxisAlignedBB box : boxes)
            {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
            }
        }
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for(AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, world, pos), world, pos))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for(RayTraceResult raytraceresult : list)
        {
            if(raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if(d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
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
