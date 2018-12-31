package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCookieJar;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCookieJar extends BlockCollisionRaytrace implements ITileEntityProvider
{
    public static final PropertyInteger COOKIE_COUNT = PropertyInteger.create("cookie_count", 0, 6);

    private static final AxisAlignedBB LID = new Bounds(5.5, 9, 5.5, 10.5, 10, 10.5).toAABB();
    private static final AxisAlignedBB BODY = new Bounds(4.5, 0.01, 4.5, 11.5, 9, 11.5).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(LID, BODY);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

    public BlockCookieJar(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GLASS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COOKIE_COUNT, 0));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES;
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
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
    {
        if(!world.isRemote)
        {
            for(int i = 0; i < getMetaFromState(state); i++)
            {
                EntityItem cookie = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, new ItemStack(Items.COOKIE));
                world.spawnEntity(cookie);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        int metadata = getMetaFromState(state);
        if(!heldItem.isEmpty())
        {
            if(heldItem.getItem() == Items.COOKIE && metadata < 6)
            {
                worldIn.setBlockState(pos, state.withProperty(COOKIE_COUNT, metadata + 1), 2);
                heldItem.shrink(1);
                worldIn.updateComparatorOutputLevel(pos, this);
                return true;
            }
        }
        if(metadata > 0)
        {
            worldIn.setBlockState(pos, state.withProperty(COOKIE_COUNT, metadata - 1), 2);
            if(!worldIn.isRemote)
            {
                EntityItem var14 = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, new ItemStack(Items.COOKIE));
                worldIn.spawnEntity(var14);
            }
            TileEntityUtil.markBlockForUpdate(worldIn, pos);
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityCookieJar();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(world.getTileEntity(pos) instanceof TileEntityCookieJar)
        {
            return state.withProperty(COOKIE_COUNT, ((TileEntityCookieJar) world.getTileEntity(pos)).getSize());
        }
        return state.withProperty(COOKIE_COUNT, 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COOKIE_COUNT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(COOKIE_COUNT, meta);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COOKIE_COUNT);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityCookieJar jar = (TileEntityCookieJar) world.getTileEntity(pos);
        return jar.getSize();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
