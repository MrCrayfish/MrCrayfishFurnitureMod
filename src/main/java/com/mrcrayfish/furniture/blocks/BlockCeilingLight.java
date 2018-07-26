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

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public class BlockCeilingLight extends Block implements IPowered
{
    public static final PropertyEnum MODE = PropertyEnum.create("mode", Mode.class);

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(5 * 0.0625, 0.4, 5 * 0.0625, 11 * 0.0625, 1.0, 11F * 0.0625);

    public BlockCeilingLight(Material material, boolean on)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GLASS);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        if(on)
        {
            this.setCreativeTab(null);
            this.setLightLevel(1.0F);
        }
        this.setDefaultState(this.blockState.getBaseState().withProperty(MODE, Mode.RIGHT_CLICK));

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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            if(playerIn.isSneaking())
            {
                Mode mode = (Mode) state.getValue(MODE);
                if(mode == Mode.RIGHT_CLICK)
                {
                    worldIn.setBlockState(pos, state.withProperty(MODE, Mode.REDSTONE));
                    playerIn.sendMessage(new TextComponentString(I18n.format("cfm.message.ceiling_light.mode1")));
                }
                else
                {
                    worldIn.setBlockState(pos, state.withProperty(MODE, Mode.RIGHT_CLICK));
                    playerIn.sendMessage(new TextComponentString(I18n.format("cfm.message.ceiling_light.mode2")));
                }
            }
            else if(((Mode) state.getValue(MODE)) == Mode.RIGHT_CLICK)
            {
                if(this == FurnitureBlocks.CEILING_LIGHT_ON)
                {
                    worldIn.setBlockState(pos, FurnitureBlocks.CEILING_LIGHT_OFF.getDefaultState(), 2);
                }
                else
                {
                    worldIn.setBlockState(pos, FurnitureBlocks.CEILING_LIGHT_ON.getDefaultState(), 2);
                }
            }
        }
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!canBlockStay(worldIn, pos))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return;
        }

        if(((Mode) state.getValue(MODE)) == Mode.REDSTONE)
        {
            if(worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, FurnitureBlocks.CEILING_LIGHT_ON.getDefaultState().withProperty(MODE, state.getValue(MODE)), 2);
            }
            else
            {
                worldIn.setBlockState(pos, FurnitureBlocks.CEILING_LIGHT_OFF.getDefaultState().withProperty(MODE, state.getValue(MODE)), 2);
            }
        }
    }

    public boolean canBlockStay(IBlockAccess worldIn, BlockPos pos)
    {
        return !worldIn.isAirBlock(pos.up());
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.DOWN;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (Mode) state.getValue(MODE) == Mode.REDSTONE ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(MODE, meta == 1 ? Mode.REDSTONE : Mode.RIGHT_CLICK);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{MODE});
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void setPowered(World world, BlockPos pos, boolean powered)
    {
        if(powered)
        {
            world.setBlockState(pos, FurnitureBlocks.CEILING_LIGHT_ON.getDefaultState());
        }
        else
        {
            world.setBlockState(pos, FurnitureBlocks.CEILING_LIGHT_OFF.getDefaultState());
        }
    }

    public enum Mode implements IStringSerializable
    {
        RIGHT_CLICK,
        REDSTONE;

        @Override
        public String getName()
        {
            return toString().toLowerCase(Locale.US);
        }
    }
}
