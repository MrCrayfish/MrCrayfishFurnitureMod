package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockBasin extends BlockFurniture
{
    private static final AxisAlignedBB[] BASE = new Bounds(8, 0, 3.2, 16, 11, 12.8).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_BASE = new Bounds(0, 11, 0, 16, 12, 16).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_BACK = new Bounds(12, 12, 0, 16, 16, 16).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_LEFT = new Bounds(0, 12, 0, 12, 16, 2).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_RIGHT = new Bounds(0, 12, 14, 12, 16, 16).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_FRONT = new Bounds(0, 12, 2, 2, 16, 14).getRotatedBounds();
    private static final AxisAlignedBB[] FAUCET_BASE = new Bounds(13, 16, 7, 15, 20, 9).getRotatedBounds();
    private static final AxisAlignedBB[] FAUCET_TOP = new Bounds(9, 20, 7, 15, 22, 9).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_LEFT = new Bounds(13, 16, 2, 15, 18, 4).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_RIGHT = new Bounds(13, 16, 12, 15, 18, 14).getRotatedBounds();
    private static final AxisAlignedBB[] FAUCET_NOZZLE = new Bounds(9, 19.68, 7, 11, 20, 9).getRotatedBounds();

    public static final List<AxisAlignedBB>[] COLLISION_BOXES_FAUCET = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, FAUCET_BASE, FAUCET_TOP, HANDLE_LEFT, HANDLE_RIGHT, FAUCET_NOZZLE);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BASE, BOWL_BASE, BOWL_BACK, BOWL_LEFT, BOWL_RIGHT, BOWL_FRONT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_FAUCET);

    public static final PropertyBool FILLED = PropertyBool.create("filled");

    public BlockBasin(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(FILLED, Boolean.FALSE));
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BATHTROOM_FURNITURE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        world.updateComparatorOutputLevel(pos, this);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
    {
        world.updateComparatorOutputLevel(pos, this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!heldItem.isEmpty())
        {
            if(heldItem.getItem() == Items.BUCKET)
            {
                if(hasWater(state))
                {
                    if(!worldIn.isRemote)
                    {
                        if(!playerIn.isCreative())
                        {
                            if(heldItem.getCount() > 1)
                            {
                                if(playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
                                {
                                    heldItem.shrink(1);
                                }
                            }
                            else
                            {
                                playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                            }
                        }
                        worldIn.setBlockState(pos, state.withProperty(FILLED, false));
                        worldIn.updateComparatorOutputLevel(pos, this);
                    }
                    else
                    {
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                    }
                }
            }
            else if(heldItem.getItem() == Items.WATER_BUCKET)
            {
                if(!hasWater(state))
                {
                    if(!worldIn.isRemote)
                    {
                        if(!playerIn.isCreative())
                        {
                            playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                        }
                        worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
                        worldIn.updateComparatorOutputLevel(pos, this);
                    }
                    else
                    {
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
                        createSplashParticle(worldIn, pos);
                    }
                }
            }
            else if(heldItem.getItem() == Items.GLASS_BOTTLE)
            {
                if(hasWater(state))
                {
                    if(!worldIn.isRemote)
                    {
                        if(!playerIn.isCreative())
                        {
                            if(heldItem.getCount() > 1)
                            {
                                if(playerIn.inventory.addItemStackToInventory(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER)))
                                {
                                    heldItem.shrink(1);
                                }
                            }
                            else
                            {
                                playerIn.setHeldItem(hand, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER));
                            }
                        }
                        worldIn.setBlockState(pos, state.withProperty(FILLED, false), 2);
                        worldIn.updateComparatorOutputLevel(pos, this);
                    }
                    else
                    {
                        playerIn.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
                        createSplashParticle(worldIn, pos);
                    }
                }
            }
            else if(PotionUtils.getPotionFromItem(heldItem) == PotionTypes.WATER)
            {
                if(!hasWater(state))
                {
                    if(!worldIn.isRemote)
                    {
                        if(!playerIn.isCreative())
                        {
                            if(heldItem.getItem() == Items.POTIONITEM)
                                playerIn.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                            else playerIn.setHeldItem(hand, ItemStack.EMPTY);
                        }
                        worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
                        worldIn.updateComparatorOutputLevel(pos, this);
                    }
                    else
                    {
                        playerIn.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 1.0F, 1.0F);
                        createSplashParticle(worldIn, pos);
                    }
                }
            }
            else
            {
                if(!hasWater(state))
                {
                    if(hasWaterSource(worldIn, pos))
                    {
                        if(!worldIn.isRemote)
                        {
                            worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
                            worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 1.0F);
                            worldIn.setBlockToAir(pos.add(0, -2, 0));
                            worldIn.updateComparatorOutputLevel(pos, this);
                        }
                        else
                        {
                            createSplashParticle(worldIn, pos);
                        }
                    }
                    else if(!worldIn.isRemote)
                    {
                        playerIn.sendMessage(new TextComponentTranslation("cfm.message.basin"));
                    }
                }
            }
        }
        else
        {
            if(!hasWater(state))
            {
                if(hasWaterSource(worldIn, pos))
                {
                    if(!worldIn.isRemote)
                    {
                        worldIn.setBlockState(pos, state.withProperty(FILLED, true), 2);
                        worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 1.0F);
                        worldIn.setBlockToAir(pos.add(0, -2, 0));
                        worldIn.updateComparatorOutputLevel(pos, this);
                    }
                    else
                    {
                        createSplashParticle(worldIn, pos);
                    }
                }
                else if(!worldIn.isRemote)
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.basin"));
                }
            }
        }
        return true;
    }

    private void createSplashParticle(World world, BlockPos pos)
    {
        for(int i = 0; i < 5; i++)
        {
            world.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.3 + 0.4 * RANDOM.nextDouble(), pos.getY() + 1.05, pos.getZ() + 0.3 + 0.4 * RANDOM.nextDouble(), 0, 0, 0);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(FILLED, meta / 4 >= 1);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(FILLED) ? 4 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, FILLED);
    }

    private boolean hasWater(IBlockState state)
    {
        return state.getValue(FILLED);
    }

    private boolean hasWaterSource(World world, BlockPos pos)
    {
        return world.getBlockState(pos.add(0, -2, 0)) == Blocks.WATER.getDefaultState();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return world.getBlockState(pos).getValue(FILLED) ? 1 : 0;
    }
}
