package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockInflatableCastle extends BlockColored
{
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockInflatableCastle()
    {
        super(Material.CLOTH);
        this.setSoundType(new SoundType(0.0F, 1.0F, SoundEvents.BLOCK_CLOTH_BREAK, SoundEvents.BLOCK_CLOTH_STEP, SoundEvents.BLOCK_CLOTH_PLACE, SoundEvents.BLOCK_CLOTH_HIT, SoundEvents.BLOCK_CLOTH_FALL));
        this.setHardness(0.25F);
        this.setUnlocalizedName("inflatable_castle");
        this.setRegistryName("inflatable_castle");
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE).withProperty(TYPE, Type.NONE));
    }

    @Override
    public void onLanded(World worldIn, Entity entityIn) {}

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(entityIn instanceof EntityLivingBase)
        {
            float height = entityIn.fallDistance;
            if(height > 0 && !entityIn.isSneaking())
            {
                if(height > 4) height = 4;
                entityIn.motionY = 0;
                entityIn.addVelocity(0, getRequiredVelocity(height + 1), 0);
                worldIn.playSound(null, pos, FurnitureSounds.bounce, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, entityIn.posX, entityIn.posY, entityIn.posZ, 1, 1, 1, 0);
            }
            entityIn.fallDistance = 0;
        }
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles)
    {
        return true;
    }

    public double getRequiredVelocity(float height)
    {
        return Math.sqrt(0.22 * height);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOR, TYPE);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {

        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        boolean north = worldIn.getBlockState(pos.north()).getBlock() instanceof BlockInflatableCastle;
        boolean east = worldIn.getBlockState(pos.east()).getBlock() instanceof BlockInflatableCastle;
        boolean south = worldIn.getBlockState(pos.south()).getBlock() instanceof BlockInflatableCastle;
        boolean west = worldIn.getBlockState(pos.west()).getBlock() instanceof BlockInflatableCastle;

        if(!north && !east && !south && !west)
        {
            return state.withProperty(TYPE, Type.NONE);
        }
        if(!north && !east && south && !west)
        {
            return state.withProperty(TYPE, Type.NORTH);
        }
        if(north && !east && !south && !west)
        {
            return state.withProperty(TYPE, Type.SOUTH);
        }
        if(!north && !east && !south && west)
        {
            return state.withProperty(TYPE, Type.EAST);
        }
        if(!north && east && !south && !west)
        {
            return state.withProperty(TYPE, Type.WEST);
        }
        if(north && !east && south && !west)
        {
            return state.withProperty(TYPE, Type.HORIZONTAL_NORTH_SOUTH);
        }
        if(!north && east && !south && west)
        {
            return state.withProperty(TYPE, Type.HORIZONTAL_EAST_WEST);
        }
        if(!north && east && south && !west)
        {
            return state.withProperty(TYPE, Type.CORNER_EAST_SOUTH);
        }
        if(!north && !east && south && west)
        {
            return state.withProperty(TYPE, Type.CORNER_WEST_SOUTH);
        }
        if(north && east && !south && !west)
        {
            return state.withProperty(TYPE, Type.CORNER_NORTH_EAST);
        }
        if(north && !east && !south && west)
        {
            return state.withProperty(TYPE, Type.CORNER_NORTH_WEST);
        }
        if(!north && east && south && west)
        {
            return state.withProperty(TYPE, Type.THREE_NORTH);
        }
        if(north && !east && south && west)
        {
            return state.withProperty(TYPE, Type.THREE_EAST);
        }
        if(north && east && !south && west)
        {
            return state.withProperty(TYPE, Type.THREE_SOUTH);
        }
        if(north && east && south && !west)
        {
            return state.withProperty(TYPE, Type.THREE_WEST);
        }
        return state.withProperty(TYPE, Type.CENTER);
    }

    public enum Type implements IStringSerializable
    {
        NONE,
        NORTH,
        EAST,
        SOUTH,
        WEST,
        HORIZONTAL_EAST_WEST,
        HORIZONTAL_NORTH_SOUTH,
        CORNER_EAST_SOUTH,
        CORNER_WEST_SOUTH,
        CORNER_NORTH_EAST,
        CORNER_NORTH_WEST,
        THREE_NORTH,
        THREE_EAST,
        THREE_SOUTH,
        THREE_WEST,
        CENTER;

        @Override
        public String getName()
        {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
