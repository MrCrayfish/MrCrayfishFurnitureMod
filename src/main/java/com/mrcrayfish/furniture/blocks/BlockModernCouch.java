package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityColoured;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.SittableUtil;
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

    private static final AxisAlignedBB[] COUCH_BASE = new Bounds(1, 3, 0, 15, 9, 16).getRotatedBounds();
    private static final AxisAlignedBB[] COUCH_ARMREST_LEFT = new Bounds(1, 0, 0, 15, 13, 2).getRotatedBounds();
    private static final AxisAlignedBB[] COUCH_ARMREST_RIGHT = new Bounds(1, 0, 14, 15, 13, 16).getRotatedBounds();
    private static final AxisAlignedBB[] COUCH_BACKREST = new Bounds(11, 9, 0, 15, 19, 16).getRotatedBounds();

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
        return FULL_BLOCK_AABB;
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
        return SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 5 * 0.0625F);
    }

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);

        list.add(COUCH_BACKREST[facing.getHorizontalIndex()]);
        list.add(COUCH_BASE[facing.getHorizontalIndex()]);

        if(state.getValue(TYPE) == CouchType.LEFT || state.getValue(TYPE) == CouchType.BOTH)
        {
            list.add(COUCH_ARMREST_LEFT[facing.getHorizontalIndex()]);
        }
        if(state.getValue(TYPE) == CouchType.RIGHT || state.getValue(TYPE) == CouchType.BOTH)
        {
            list.add(COUCH_ARMREST_RIGHT[facing.getHorizontalIndex()]);
        }
        return list;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean p_185477_7_)
    {
        if(!(entity instanceof EntitySittableBlock))
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
