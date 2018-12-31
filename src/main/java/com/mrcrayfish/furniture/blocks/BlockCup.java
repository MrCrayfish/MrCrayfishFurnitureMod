package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityCup;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

public class BlockCup extends BlockCollisionRaytrace implements ITileEntityProvider
{
    private static final AxisAlignedBB LEFT = new Bounds(5.5, 0.01, 5.5, 6, 7, 10.5).toAABB();
    private static final AxisAlignedBB RIGHT = new Bounds(10, 0.01, 5.5, 10.5, 7, 10.5).toAABB();
    private static final AxisAlignedBB BACK = new Bounds(6, 0.01, 5.5, 10, 7, 6).toAABB();
    private static final AxisAlignedBB FRONT = new Bounds(6, 0.01, 10, 10, 7, 10.5).toAABB();
    private static final AxisAlignedBB BASE = new Bounds(6, 0.01, 6, 10, 0.5, 10).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(LEFT, RIGHT, BACK, FRONT, BASE);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES);

    public BlockCup(Material material)
    {
        super(material);
        this.setSoundType(SoundType.GLASS);
        this.setHardness(0.1F);
        this.setDefaultState(this.blockState.getBaseState());
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking() && !player.isCreative() && player.getFoodStats().needFood())
        {
            if(world.getTileEntity(pos) instanceof TileEntityCup)
            {
                TileEntityCup tileEntityCup = (TileEntityCup) world.getTileEntity(pos);
                ItemStack cup = tileEntityCup.getDrink();
                if(cup != null && cup.hasTagCompound())
                {
                    tileEntityCup.clear();
                    if(!world.isRemote)
                    {
                        int heal = cup.getTagCompound().getInteger("HealAmount");
                        player.getFoodStats().addStats(heal, 0.5F);
                    }
                    else
                    {
                        world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        world.playSound(player, player.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                    return true;
                }
            }
        }
        return false;
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            if(nbt.hasKey("Colour"))
            {
                int[] rgb = nbt.getIntArray("Colour");
                TileEntityCup tileEntityCup = (TileEntityCup) world.getTileEntity(pos);
                tileEntityCup.setColour(rgb);
                tileEntityCup.setItem(stack);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCup();
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        if(te instanceof TileEntityCup)
        {
            TileEntityCup tileEntityCup = (TileEntityCup) te;
            EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, tileEntityCup.getDrink() != null ? tileEntityCup.getDrink().copy() : new ItemStack(FurnitureItems.CUP));
            item.setDefaultPickupDelay();
            world.spawnEntity(item);
        }
        else
        {
            EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(FurnitureItems.CUP));
            item.setDefaultPickupDelay();
            world.spawnEntity(item);
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        if(world.getTileEntity(pos) instanceof TileEntityCup)
        {
            TileEntityCup tileEntityCup = (TileEntityCup) world.getTileEntity(pos);
            if(tileEntityCup.getDrink() != null)
            {
                return tileEntityCup.getDrink().copy();
            }
        }
        return new ItemStack(FurnitureItems.CUP);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
