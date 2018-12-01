package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCoffeeTable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCoffeeTable extends Block
{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
    public static final AxisAlignedBB TABLE_TOP = new AxisAlignedBB(0, 6.5 * 0.0625, 0, 1, 8 * 0.0625, 1);
    public static final AxisAlignedBB SOUTH_WEST_LEG = new AxisAlignedBB(0, 0, 14.4 * 0.0625, 1.6 * 0.0625, 6.5 * 0.0625, 1);
    public static final AxisAlignedBB NORTH_WEST_LEG = new AxisAlignedBB(0, 0, 0, 1.6 * 0.0625, 6.5 * 0.0625, 1.6 * 0.0625);
    public static final AxisAlignedBB SOUTH_EAST_LEG = new AxisAlignedBB(14.4 * 0.0625, 0, 14.4 * 0.0625, 1, 6.5 * 0.0625, 1);
    public static final AxisAlignedBB NORTH_EAST_LEG = new AxisAlignedBB(14.4 * 0.0625, 0, 0, 1, 6.5 * 0.0625, 1.6 * 0.0625);

    public BlockCoffeeTable(Material material, SoundType sound, String unlocalizedName)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(sound);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        List<AxisAlignedBB> list = getCollisionBoxList(this.getActualState(state, worldIn, pos));
        for(AxisAlignedBB box : list)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.withProperty(NORTH, isCoffeeTable(world, pos.north())).withProperty(EAST, isCoffeeTable(world, pos.east())).withProperty(SOUTH, isCoffeeTable(world, pos.south())).withProperty(WEST, isCoffeeTable(world, pos.west()));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
    }

    public boolean isCoffeeTable(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == this;
    }

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        boolean north = state.getValue(NORTH);
        boolean south = state.getValue(SOUTH);
        boolean east = state.getValue(EAST);
        boolean west = state.getValue(WEST);
        list.add(TABLE_TOP);
        if(!north)
        {
            if(!west) list.add(NORTH_WEST_LEG);
            if(!east) list.add(NORTH_EAST_LEG);
        }
        if(!south)
        {
            if(!west) list.add(SOUTH_WEST_LEG);
            if(!east) list.add(SOUTH_EAST_LEG);
        }
        return list;
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for(AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityCoffeeTable)
        {
            TileEntityCoffeeTable tileEntityCoffeeTable = (TileEntityCoffeeTable) tileEntity;
            if(!heldItem.isEmpty() && tileEntityCoffeeTable.getFood().isEmpty())
            {
                tileEntityCoffeeTable.setFood(new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
                tileEntityCoffeeTable.setRotation(playerIn.getHorizontalFacing().getHorizontalIndex());
                tileEntityCoffeeTable.sync();
                heldItem.shrink(1);
                return true;
            }
            if(!tileEntityCoffeeTable.getFood().isEmpty())
            {
                if(!worldIn.isRemote)
                {
                    EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityCoffeeTable.getFood());
                    worldIn.spawnEntity(entityFood);
                }
                tileEntityCoffeeTable.setFood(ItemStack.EMPTY);
                tileEntityCoffeeTable.sync();
            }
        }
        return true;
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
        return new TileEntityCoffeeTable();
    }
}
