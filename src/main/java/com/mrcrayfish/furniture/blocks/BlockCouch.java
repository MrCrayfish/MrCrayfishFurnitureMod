/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySittableBlock;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityCouch;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SittableUtil;
import com.mrcrayfish.furniture.util.StateHelper;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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

public abstract class BlockCouch extends BlockFurnitureTile
{
    public static final PropertyInteger COLOUR = PropertyInteger.create("colour", 0, 15);
    public static final PropertyEnum<CouchType> TYPE = PropertyEnum.<CouchType>create("type", CouchType.class);

    private static final AxisAlignedBB COUCH_BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.6, 1.0);

    private static final AxisAlignedBB COUCH_BACKREST_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
    private static final AxisAlignedBB COUCH_BACKREST_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
    private static final AxisAlignedBB COUCH_BACKREST_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
    private static final AxisAlignedBB COUCH_BACKREST_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.80, 0.6, 0.0, 1.0, 1.21, 1.0);
    private static final AxisAlignedBB[] COUCH_BACKREST = {COUCH_BACKREST_SOUTH, COUCH_BACKREST_WEST, COUCH_BACKREST_NORTH, COUCH_BACKREST_EAST};
    private static final AxisAlignedBB[] COUCH_BACKREST_LEFT = {COUCH_BACKREST_WEST, COUCH_BACKREST_NORTH, COUCH_BACKREST_EAST, COUCH_BACKREST_SOUTH};
    private static final AxisAlignedBB[] COUCH_BACKREST_RIGHT = {COUCH_BACKREST_EAST, COUCH_BACKREST_SOUTH, COUCH_BACKREST_WEST, COUCH_BACKREST_NORTH};

    private static final AxisAlignedBB COUCH_ARMREST_LEFT_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
    private static final AxisAlignedBB COUCH_ARMREST_LEFT_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
    private static final AxisAlignedBB COUCH_ARMREST_LEFT_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
    private static final AxisAlignedBB COUCH_ARMREST_LEFT_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.0, 0.5, 0.9, 1.0, 0.9, 1.0);
    private static final AxisAlignedBB[] COUCH_ARMREST_LEFT = {COUCH_ARMREST_LEFT_SOUTH, COUCH_ARMREST_LEFT_WEST, COUCH_ARMREST_LEFT_NORTH, COUCH_ARMREST_LEFT_EAST};

    private static final AxisAlignedBB COUCH_ARMREST_RIGHT_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
    private static final AxisAlignedBB COUCH_ARMREST_RIGHT_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
    private static final AxisAlignedBB COUCH_ARMREST_RIGHT_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
    private static final AxisAlignedBB COUCH_ARMREST_RIGHT_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.0, 0.5, 0.0, 1.0, 0.9, 0.1);
    private static final AxisAlignedBB[] COUCH_ARMREST_RIGHT = {COUCH_ARMREST_RIGHT_SOUTH, COUCH_ARMREST_RIGHT_WEST, COUCH_ARMREST_RIGHT_NORTH, COUCH_ARMREST_RIGHT_EAST};

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
            this.setDefaultState(baseState.withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, CouchType.BOTH).withProperty(COLOUR, Integer.valueOf(0)));
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
            int colour = ((TileEntityCouch) world.getTileEntity(pos)).getColour();
            state = state.withProperty(COLOUR, Integer.valueOf(colour));
        }

        if(StateHelper.getBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.DOWN) instanceof BlockCouch)
        {
            if(StateHelper.getRotation(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.RIGHT)
            {
                return state.withProperty(TYPE, CouchType.CORNER_RIGHT);
            }
            else if(StateHelper.getRotation(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.LEFT)
            {
                return state.withProperty(TYPE, CouchType.CORNER_LEFT);
            }
        }

        boolean left = false;
        boolean right = false;

        if(!StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.LEFT))
        {
            left = true;
        }
        if(!StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.RIGHT))
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
            state = state.withProperty(COLOUR, Integer.valueOf(0));
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
                        worldIn.setBlockState(pos, FurnitureBlocks.couch_jeb.getDefaultState().withProperty(FACING, state.getValue(FACING)));
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
            if(tileEntity instanceof TileEntityCouch)
            {
                TileEntityCouch tileEntityCouch = (TileEntityCouch) tileEntity;
                if(!heldItem.isEmpty())
                {
                    if(heldItem.getItem() instanceof ItemDye)
                    {
                        tileEntityCouch.setColour(heldItem.getItemDamage());
                        if(!playerIn.isCreative())
                        {
                            heldItem.shrink(1);
                        }
                        TileEntityUtil.markBlockForUpdate(worldIn, pos);
                        return true;
                    }
                }
            }
        }
        return SittableUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0.45);
    }

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
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
            if(StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.LEFT))
            {
                list.add(COUCH_ARMREST_LEFT[facing.getHorizontalIndex()]);
            }
            if(StateHelper.isAirBlock(world, pos, (EnumFacing) state.getValue(FACING), StateHelper.Direction.RIGHT))
            {
                list.add(COUCH_ARMREST_RIGHT[facing.getHorizontalIndex()]);
            }
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
                super.addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
            }
        }
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

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
        return new TileEntityCouch();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return isSpecial() ? new BlockStateContainer(this, new IProperty[]{FACING, TYPE}) : new BlockStateContainer(this, new IProperty[]{FACING, COLOUR, TYPE});
    }

    public abstract boolean isSpecial();

    public static enum CouchType implements IStringSerializable
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
            return this.toString().toLowerCase();
        }
    }
}
