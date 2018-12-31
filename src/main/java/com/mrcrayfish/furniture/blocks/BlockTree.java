package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityTree;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

public class BlockTree extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] STEM_TOP = new Bounds(7, 0, 7, 9, 8, 9).getRotatedBounds();
    private static final AxisAlignedBB[] LEAVES_4 = new Bounds(4, 0, 4, 12, 3, 12).getRotatedBounds();
    private static final AxisAlignedBB[] LEAVES_5 = new Bounds(5, 3, 5, 11, 6, 11).getRotatedBounds();
    private static final AxisAlignedBB[] LEAVES_6 = new Bounds(6, 6, 6, 10, 9, 10).getRotatedBounds();
    private static final AxisAlignedBB[] LEAVES_7 = new Bounds(7, 9, 7, 9, 12, 9).getRotatedBounds();
    private static final AxisAlignedBB[] STAR_3 = new Bounds(4.9, 11.2, 7.52, 11.1, 16, 8.48).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_TOP = Bounds.getRotatedBoundLists(STEM_TOP, LEAVES_4, LEAVES_5, LEAVES_6, LEAVES_7, STAR_3);

    private static final AxisAlignedBB POT_BASE = new Bounds(4.5, 0, 4.5, 11.5, 1, 11.5).toAABB();
    private static final AxisAlignedBB POT_SIDE_1 = new Bounds(11, 0.5, 4.5, 12, 5.5, 11.5).toAABB();
    private static final AxisAlignedBB POT_SIDE_2 = new Bounds(4, 0.5, 4.5, 5, 5.5, 11.5).toAABB();
    private static final AxisAlignedBB POT_SIDE_3 = new Bounds(4.5, 0.5, 4, 11.5, 5.5, 5).toAABB();
    private static final AxisAlignedBB POT_SIDE_4 = new Bounds(4.5, 0.5, 11, 11.5, 5.5, 12).toAABB();
    private static final AxisAlignedBB POT_DIRT = new Bounds(5, 4, 5, 11, 5, 11).toAABB();
    private static final AxisAlignedBB STEM_BOTTOM = new Bounds(7, 5, 7, 9, 16, 9).toAABB();
    private static final AxisAlignedBB LEAVES_1 = new Bounds(1, 7, 1, 15, 10, 15).toAABB();
    private static final AxisAlignedBB LEAVES_2 = new Bounds(2, 10, 2, 14, 13, 14).toAABB();
    private static final AxisAlignedBB LEAVES_3 = new Bounds(3, 13, 3, 13, 16, 13).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES_BOTTOM = Lists.newArrayList(POT_BASE, POT_SIDE_1, POT_SIDE_2, POT_SIDE_3, POT_SIDE_4, POT_DIRT, STEM_BOTTOM, LEAVES_1, LEAVES_2, LEAVES_3);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_TOP_TRANSLATED = Bounds.transformBoxListsVertical(1, COLLISION_BOXES_TOP);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(COLLISION_BOXES_BOTTOM, COLLISION_BOXES_TOP_TRANSLATED[0]);

    public BlockTree(Material material, boolean top)
    {
        super(material);
        this.setSoundType(SoundType.WOOD);
        this.setLightLevel(0.3F);
        this.setHardness(0.5F);
        if(top) this.setCreativeTab(null);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this == FurnitureBlocks.TREE_TOP ? BOUNDING_BOX.offset(0, -1, 0)
                : (source.getBlockState(pos.up()).getBlock() == FurnitureBlocks.TREE_TOP ? BOUNDING_BOX : FULL_BLOCK_AABB);
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return this == FurnitureBlocks.TREE_BOTTOM ? COLLISION_BOXES_BOTTOM : COLLISION_BOXES_TOP[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if(this == FurnitureBlocks.TREE_BOTTOM)
            world.setBlockState(pos.up(), FurnitureBlocks.TREE_TOP.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()));
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityTree)
        {
            TileEntityTree tileEntityTree = (TileEntityTree) tileEntity;
            tileEntityTree.addOrnament(playerIn.getHorizontalFacing(), heldItem);
            if(!heldItem.isEmpty()) heldItem.shrink(1);
            TileEntityUtil.markBlockForUpdate(worldIn, pos);
        }
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(this == FurnitureBlocks.TREE_TOP)
        {
            if(player.getHeldItemMainhand() != null)
            {
                if(player.getHeldItemMainhand().getItem() != Items.SHEARS)
                {
                    worldIn.destroyBlock(pos.down(), false);
                }
                else
                {
                    player.getHeldItemMainhand().damageItem(1, player);
                }
            }
            else
            {
                worldIn.destroyBlock(pos.down(), false);
            }
        }
        else
        {
            worldIn.destroyBlock(pos.up(), false);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.TREE_BOTTOM).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.TREE_BOTTOM);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTree();
    }
}
