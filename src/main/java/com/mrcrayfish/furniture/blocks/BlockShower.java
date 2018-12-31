package com.mrcrayfish.furniture.blocks;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockShower extends BlockFurniture
{
    private static final Bounds TOP_LEFT = new Bounds(0, 15, 0, 16, 16, 1);
    private static final Bounds TOP_RIGHT = new Bounds(0, 15, 15, 16, 16, 16);
    private static final Bounds TOP_FRONT = new Bounds(0, 15, 1, 1, 16, 15);
    private static final Bounds TOP_BACK = new Bounds(15, 15, 1, 16, 16, 15);
    private static final Bounds FRONT_LEFT = new Bounds(0, 0, 0, 1, 15, 1);
    private static final Bounds BACK_LEFT = new Bounds(15, 0, 0, 16, 15, 1);
    private static final Bounds BACK_RIGHT = new Bounds(15, 0, 15, 16, 15, 16);
    private static final Bounds FRONT_RIGHT = new Bounds(0, 0, 15, 1, 15, 16);
    private static final Bounds LEFT = new Bounds(0.5, 0, 0.5, 15.5, 15, 0.5);
    private static final Bounds RIGHT = new Bounds(0.5, 0, 15.5, 15.5, 15, 15.5);
    private static final Bounds BACK = new Bounds(15.5, 0, 0.5, 15.5, 15, 15.5);
    private static final Bounds[] BOUNDS_TOP = new Bounds[] {TOP_LEFT, TOP_RIGHT, TOP_FRONT, TOP_BACK, FRONT_LEFT, BACK_LEFT, BACK_RIGHT, FRONT_RIGHT, LEFT, RIGHT, BACK};

    private static final AxisAlignedBB[][] BOXES_TOP = Arrays.stream(BOUNDS_TOP).map(bounds -> bounds.getRotatedBounds()).toArray(AxisAlignedBB[][]::new);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_TOP = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BOXES_TOP);

    private static final Bounds BOTTOM = new Bounds(0, 0, 0, 16, 1, 16).rotateX(Rotation.CLOCKWISE_180);
    private static final Bounds[] BOUNDS_BOTTOM = Stream.of(BOUNDS_TOP, new Bounds[] {BOTTOM}).flatMap(Stream::of).toArray(Bounds[]::new);

    private static final AxisAlignedBB[][] BOXES_BOTTOM = Arrays.stream(BOUNDS_BOTTOM).map(bounds -> bounds.rotateX(Rotation.CLOCKWISE_180).getRotatedBounds()).toArray(AxisAlignedBB[][]::new);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTTOM = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BOXES_BOTTOM);

    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(FULL_BLOCK_AABB, FULL_BLOCK_AABB.offset(0, 1, 0));

    public BlockShower(Material material, boolean top)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
        if(top) this.setCreativeTab(null);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this == FurnitureBlocks.SHOWER_TOP ? BOUNDING_BOX.offset(0, -1, 0) : BOUNDING_BOX;
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        int i = state.getValue(FACING).getHorizontalIndex();
        return this == FurnitureBlocks.SHOWER_TOP ? COLLISION_BOXES_TOP[i] : COLLISION_BOXES_BOTTOM[i];
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        world.setBlockState(pos.up(), FurnitureBlocks.SHOWER_TOP.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BATHTROOM_FURNITURE, (EntityPlayer) placer);
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        world.destroyBlock(this == FurnitureBlocks.SHOWER_BOTTOM ? pos.up() : pos.down(), false);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            boolean top = this == FurnitureBlocks.SHOWER_TOP;

            IBlockState head = world.getBlockState(pos.up(top ? 1 : 2));
            if(head.getBlock() == FurnitureBlocks.SHOWER_HEAD_ON)
            {
                Triggers.trigger(Triggers.PLAYER_SHOWER, player);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        // IBlockState aboveState = worldIn.getBlockState(pos.up());
        // Block block = aboveState.getBlock();
        // int metadata = getMetaFromState(state);
        // if (block == FurnitureBlocks.shower_head_off) {
        // worldIn.setBlockState(pos.up(), FurnitureBlocks.shower_head_on.getDefaultState().withProperty(FACING, aboveState.getValue(FACING)), 2);
        // worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.6F, false);
        // } else if (block == FurnitureBlocks.shower_head_on) {
        // worldIn.setBlockState(pos.up(), FurnitureBlocks.shower_head_off.getDefaultState().withProperty(FACING, aboveState.getValue(FACING)), 2);
        // worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, 0.5F, false);
        // }
        // return true;
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.SHOWER_BOTTOM).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.SHOWER_BOTTOM);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
