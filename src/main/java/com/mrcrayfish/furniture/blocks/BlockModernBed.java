package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityColoured;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.EnumDyeColor;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockModernBed extends BlockFurnitureTile
{
    public static final PropertyEnum<Type> BED_TYPE = PropertyEnum.create("type", Type.class);
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");

    public static final AxisAlignedBB BOUNDING_BOX = new Bounds(0, 0, 0, 16, 9, 16).toAABB();
    public static final AxisAlignedBB BASE = new Bounds(0, 4, 0, 16, 8, 16).toAABB();
    public static final AxisAlignedBB[] TOP = new Bounds(14, 0, 0, 16, 16, 16).getRotatedBounds();
    public static final AxisAlignedBB[] BOTTOM = new Bounds(0, 0, 0, 2, 8, 16).getRotatedBounds();

    public BlockModernBed(String id)
    {
        super(Material.WOOD);
        this.setUnlocalizedName(id);
        this.setRegistryName(id);
        this.setHardness(0.5F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState)
    {
        List<AxisAlignedBB> list = getCollisionBoxList(state, worldIn, pos);
        for(AxisAlignedBB box : list)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    protected List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        boxes.add(BASE);
        if(this == FurnitureBlocks.MODERN_BED_TOP)
        {
            boxes.add(TOP[state.getValue(FACING).getHorizontalIndex()]);
        }
        else
        {
            boxes.add(BOTTOM[state.getValue(FACING).getHorizontalIndex()]);
        }
        return boxes;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            if (this == FurnitureBlocks.MODERN_BED_BOTTOM)
            {
                pos = pos.offset(state.getValue(FACING));
                state = worldIn.getBlockState(pos);
                if (state.getBlock() != FurnitureBlocks.MODERN_BED_TOP)
                {
                    return true;
                }
            }

            if (worldIn.provider.canRespawnHere() && worldIn.getBiome(pos) != Biomes.HELL)
            {
                if (state.getValue(OCCUPIED))
                {
                    EntityPlayer entityplayer = this.getPlayerInBed(worldIn, pos);

                    if (entityplayer != null)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.occupied"), true);
                        return true;
                    }

                    TileEntity tileEntity = worldIn.getTileEntity(pos);
                    state = state.withProperty(OCCUPIED, Boolean.FALSE);
                    worldIn.setBlockState(pos, state, 3);
                    if(tileEntity != null)
                    {
                        tileEntity.validate();
                        worldIn.setTileEntity(pos, tileEntity);
                    }
                }

                EntityPlayer.SleepResult sleepResult = playerIn.trySleep(pos);
                if (sleepResult == EntityPlayer.SleepResult.OK)
                {
                    TileEntity tileEntity = worldIn.getTileEntity(pos);
                    state = state.withProperty(OCCUPIED, Boolean.TRUE);
                    worldIn.setBlockState(pos, state, 3);
                    if(tileEntity != null)
                    {
                        tileEntity.validate();
                        worldIn.setTileEntity(pos, tileEntity);
                    }
                    return true;
                }
                else
                {
                    if (sleepResult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.noSleep", new Object[0]), true);
                    }
                    else if (sleepResult == EntityPlayer.SleepResult.NOT_SAFE)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.notSafe", new Object[0]), true);
                    }
                    else if (sleepResult == EntityPlayer.SleepResult.TOO_FAR_AWAY)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.tooFarAway", new Object[0]), true);
                    }
                    return true;
                }
            }
            else
            {
                worldIn.setBlockToAir(pos);
                EnumFacing facing1 = state.getValue(FACING);
                pos = pos.offset(this == FurnitureBlocks.MODERN_BED_TOP ? facing1.getOpposite() : facing1);
                if (worldIn.getBlockState(pos).getBlock() == (this == FurnitureBlocks.MODERN_BED_TOP ? FurnitureBlocks.MODERN_BED_BOTTOM : FurnitureBlocks.MODERN_BED_TOP))
                {
                    worldIn.setBlockToAir(pos);
                }
                worldIn.newExplosion(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, true);
                return true;
            }
        }
    }

    @Nullable
    private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos)
    {
        for (EntityPlayer entityplayer : worldIn.playerEntities)
        {
            if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos))
            {
                return entityplayer;
            }
        }

        return null;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityColoured();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if(this == FurnitureBlocks.MODERN_BED_BOTTOM)
        {
            IBlockState otherState = worldIn.getBlockState(pos.offset(state.getValue(FACING)));
            if(otherState.getBlock() == FurnitureBlocks.MODERN_BED_TOP)
            {
                state = state.withProperty(OCCUPIED, otherState.getValue(OCCUPIED));
            }
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityColoured)
        {
            int colour = ((TileEntityColoured) tileEntity).getColour();
            state = state.withProperty(COLOUR, colour);
        }

        EnumFacing facing = state.getValue(FACING);
        boolean left = false;
        boolean right = false;

        IBlockState rightState = worldIn.getBlockState(pos.offset(facing.rotateY()));
        if(rightState.getBlock() == this && rightState.getValue(FACING) == facing)
        {
            right = true;
        }
        IBlockState leftState = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
        if(leftState.getBlock() == this && leftState.getValue(FACING) == facing)
        {
            left = true;
        }

        if(left && right)
        {
            state = state.withProperty(BED_TYPE, Type.BOTH);
        }
        else if(left)
        {
            state = state.withProperty(BED_TYPE, Type.RIGHT);
        }
        else if(right)
        {
            state = state.withProperty(BED_TYPE, Type.LEFT);
        }
        else
        {
            state = state.withProperty(BED_TYPE, Type.NONE);
        }
        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(OCCUPIED, meta > 3);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if(state.getValue(OCCUPIED))
        {
            meta += 4;
        }
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, BED_TYPE, COLOUR, OCCUPIED);
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
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        EnumFacing facing = state.getValue(FACING);
        if(this == FurnitureBlocks.MODERN_BED_TOP)
        {
            if(worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() == FurnitureBlocks.MODERN_BED_BOTTOM)
            {
                worldIn.destroyBlock(pos.offset(facing.getOpposite()), false);
            }
        }
        else if(this == FurnitureBlocks.MODERN_BED_BOTTOM)
        {
            if(worldIn.getBlockState(pos.offset(facing)).getBlock() == FurnitureBlocks.MODERN_BED_TOP)
            {
                worldIn.destroyBlock(pos.offset(facing), false);
            }
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if (tileEntity instanceof TileEntityColoured)
        {
            TileEntityColoured couch = (TileEntityColoured) tileEntity;
            ItemStack itemstack = new ItemStack(FurnitureBlocks.MODERN_BED_BOTTOM, 1, couch.getColour());
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

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player)
    {
        return true;
    }

    @Override
    public void setBedOccupied(IBlockAccess blockAccess, BlockPos pos, EntityPlayer player, boolean occupied)
    {
        if (blockAccess instanceof World)
        {
            World world = (World) blockAccess;
            TileEntity tileEntity = world.getTileEntity(pos);
            IBlockState state = world.getBlockState(pos);
            state = state.getBlock().getActualState(state, world, pos);
            state = state.withProperty(OCCUPIED, occupied);
            world.setBlockState(pos, state, 4);
            if(tileEntity != null)
            {
                tileEntity.validate();
                world.setTileEntity(pos, tileEntity);
            }
        }
    }

    @Override
    public EnumFacing getBedDirection(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return getActualState(state, world, pos).getValue(FACING);
    }

    @Override
    public boolean isBedFoot(IBlockAccess world, BlockPos pos)
    {
        return this == FurnitureBlocks.MODERN_BED_BOTTOM;
    }

    public enum Type implements IStringSerializable
    {
        LEFT, RIGHT, BOTH, NONE;

        @Override
        public String getName()
        {
            return name().toLowerCase(Locale.US);
        }
    }
}
