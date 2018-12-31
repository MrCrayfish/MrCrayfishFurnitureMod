package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockBlinds extends BlockFurniture
{
    public static final AxisAlignedBB[] TOP = new Bounds(14, 14, 0, 16, 16, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
    public static final AxisAlignedBB[] BLINDS = new Bounds(15, 0, 0, 16, 14, 16).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);
    public static final AxisAlignedBB[] ROD = new Bounds(14.5, 8, 0.75, 15, 14, 1.25).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(TOP, BLINDS, ROD);
    public static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    public static final PropertyBool LEFT = PropertyBool.create("left");
    private Block replacementBlock;

    public BlockBlinds(Material material, boolean open)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEFT, false));
        if(!open)
        {
            this.setLightOpacity(255);
            this.setCreativeTab(null);
        }
        else
        {
            this.setLightOpacity(0);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    public void setReplacementBlock(Block replacementBlock)
    {
        this.replacementBlock = replacementBlock;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BLINDS_OR_CURTAINS, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public boolean isTranslucent(IBlockState state)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        playerIn.playSound(SoundEvents.BLOCK_WOOD_PLACE, 1.0F, 1.0F);
        return worldIn.setBlockState(pos, replacementBlock.getDefaultState().withProperty(FACING, state.getValue(FACING)));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.BLINDS_OAK).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.BLINDS_OAK);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return state.withProperty(LEFT, worldIn.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() instanceof BlockBlinds);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LEFT);
    }
}
