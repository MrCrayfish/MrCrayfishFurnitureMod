package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageFillBath;
import com.mrcrayfish.furniture.tileentity.TileEntityBath;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.SeatUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class BlockBath extends BlockFurnitureTile
{
    public static final PropertyInteger WATER_LEVEL = PropertyInteger.create("level", 0, 16);

    private static final AxisAlignedBB[] BOWL_BASE = new Bounds(0, 0, 0, 16, 2, 16).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_BACK = new Bounds(12, 2, 2, 16, 14, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_LEFT = new Bounds(0, 2, 0, 16, 14, 2).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_RIGHT = new Bounds(0, 2, 14, 16, 14, 16).getRotatedBounds();
    private static final AxisAlignedBB[] FAUCET_BASE = new Bounds(13, 14, 7, 15, 18, 9).getRotatedBounds();
    private static final AxisAlignedBB[] FAUCET_TOP = new Bounds(9, 18, 7, 15, 20, 9).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_LEFT = new Bounds(13, 14, 2, 15, 16, 4).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_RIGHT = new Bounds(13, 14, 12, 15, 16, 14).getRotatedBounds();
    private static final AxisAlignedBB[] FAUCET_NOZZLE = new Bounds(9, 17.68, 7, 11, 18, 9).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BACK = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BOWL_BASE, BOWL_BACK, BOWL_LEFT, BOWL_RIGHT, FAUCET_BASE, FAUCET_TOP, HANDLE_LEFT, HANDLE_RIGHT, FAUCET_NOZZLE);
    
    private static final AxisAlignedBB[] BOWL_FRONT = new Bounds(0, 2, 2, 2, 14, 14).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_FRONT = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BOWL_FRONT, BOWL_BASE, BOWL_LEFT, BOWL_RIGHT);

    private static final AxisAlignedBB[] BOUNDING_BOX = new Bounds(0, 0, -16, 16, 14, 16).getRotatedBounds();

    public BlockBath(Material material, boolean top)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
        if(top) this.setCreativeTab(null);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        AxisAlignedBB bounds = BOUNDING_BOX[facing.getHorizontalIndex()];
        return this == FurnitureBlocks.BATH_1 ? bounds : bounds.offset(-facing.getFrontOffsetX(), 0, -facing.getFrontOffsetZ());
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        int i = state.getValue(FACING).getHorizontalIndex();
        return this == FurnitureBlocks.BATH_1 ? COLLISION_BOXES_FRONT[i] : COLLISION_BOXES_BACK[i];
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
        return state.withProperty(WATER_LEVEL, 0);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        world.setBlockState(pos.offset(placer.getHorizontalFacing()), FurnitureBlocks.BATH_2.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(WATER_LEVEL, state.getValue(WATER_LEVEL)));
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BATHTROOM_FURNITURE, (EntityPlayer) placer);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        BlockPos otherBathPos;
        if(this == FurnitureBlocks.BATH_1)
        {
            otherBathPos = pos.offset(state.getValue(FACING));
        }
        else
        {
            otherBathPos = pos.offset(state.getValue(FACING).getOpposite());
        }

        TileEntity tile_entity_1 = worldIn.getTileEntity(pos);
        TileEntity tile_entity_2 = worldIn.getTileEntity(otherBathPos);
        if(tile_entity_1 instanceof TileEntityBath && tile_entity_2 instanceof TileEntityBath)
        {
            TileEntityBath tileEntityBath = (TileEntityBath) tile_entity_1;
            TileEntityBath tileEntityBath2 = (TileEntityBath) tile_entity_2;

            if(!heldItem.isEmpty())
            {
                if(heldItem.getItem() == Items.BUCKET)
                {
                    if(tileEntityBath.hasWater())
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
                            tileEntityBath.removeWaterLevel();
                            tileEntityBath2.removeWaterLevel();
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
                    if(!tileEntityBath.isFull())
                    {
                        if(!worldIn.isRemote)
                        {
                            tileEntityBath.addWaterLevel();
                            tileEntityBath2.addWaterLevel();
                            if(!playerIn.isCreative())
                            {
                                playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                            }
                            worldIn.updateComparatorOutputLevel(pos, this);
                        }
                        else
                        {
                            playerIn.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
                            worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5, pos.getY() + 0.75 + tileEntityBath.getWaterLevel() * 0.0265, pos.getZ() + 0.5, 0, 0.1, 0);
                        }
                    }
                }
                else if(heldItem.getItem() == Items.GLASS_BOTTLE)
                {
                    if(tileEntityBath.hasWater())
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
                            tileEntityBath.removeWaterLevel();
                            tileEntityBath2.removeWaterLevel();
                            worldIn.updateComparatorOutputLevel(pos, this);
                        }
                        else
                        {
                            playerIn.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
                        }
                    }
                }
                else if(PotionUtils.getPotionFromItem(heldItem) == PotionTypes.WATER)
                {
                    if(!tileEntityBath.isFull())
                    {
                        if(!worldIn.isRemote)
                        {
                            tileEntityBath.addWaterLevel();
                            tileEntityBath2.addWaterLevel();
                            if(!playerIn.isCreative())
                            {
                                if(heldItem.getItem() == Items.POTIONITEM)
                                    playerIn.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                                else playerIn.setHeldItem(hand, ItemStack.EMPTY);
                            }
                            worldIn.updateComparatorOutputLevel(pos, this);
                        }
                        else
                        {
                            playerIn.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 1.0F, 1.0F);
                            worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5, pos.getY() + 0.75 + tileEntityBath.getWaterLevel() * 0.0265, pos.getZ() + 0.5, 0, 0.1, 0);
                        }
                    }
                }
                else
                {
                    if(!tileEntityBath.isFull())
                    {
                        if(hasWaterSource(worldIn, pos))
                        {
                            if(this == FurnitureBlocks.BATH_2)
                            {
                                if(!worldIn.isRemote)
                                {
                                    tileEntityBath.addWaterLevel();
                                    tileEntityBath2.addWaterLevel();
                                    worldIn.setBlockToAir(pos.add(0, -2, 0));
                                    worldIn.updateComparatorOutputLevel(pos, this);
                                }
                                else
                                {
                                    worldIn.playSound(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 0.8F, true);
                                }
                            }
                        }
                        else if(!worldIn.isRemote)
                        {
                            playerIn.sendMessage(new TextComponentTranslation("cfm.message.bath"));
                        }
                    }
                }
            }
            else
            {
                if(playerIn.isSneaking())
                {
                    if(!tileEntityBath.isFull())
                    {
                        if(hasWaterSource(worldIn, pos))
                        {
                            if(this == FurnitureBlocks.BATH_2)
                            {
                                if(!worldIn.isRemote)
                                {
                                    tileEntityBath.addWaterLevel();
                                    tileEntityBath2.addWaterLevel();
                                    worldIn.setBlockToAir(pos.add(0, -2, 0));
                                    worldIn.updateComparatorOutputLevel(pos, this);
                                }
                                else
                                {
                                    worldIn.playSound(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, FurnitureSounds.tap, SoundCategory.BLOCKS, 0.75F, 0.8F, true);
                                }
                            }
                        }
                        else if(!worldIn.isRemote)
                        {
                            playerIn.sendMessage(new TextComponentTranslation("cfm.message.bath"));
                        }
                    }
                    return true;
                }
                else
                {
                    return SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0);
                }
            }
            PacketHandler.INSTANCE.sendToAllAround(new MessageFillBath(tileEntityBath.getWaterLevel(), pos.getX(), pos.getY(), pos.getZ(), otherBathPos.getX(), otherBathPos.getY(), otherBathPos.getZ()), new TargetPoint(playerIn.dimension, pos.getX(), pos.getY(), pos.getZ(), 128D));
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            worldIn.markBlockRangeForRenderUpdate(otherBathPos, otherBathPos);
        }
        return true;

    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(this == FurnitureBlocks.BATH_1)
        {
            worldIn.destroyBlock(pos.offset(state.getValue(FACING)), false);
        }
        else
        {
            worldIn.destroyBlock(pos.offset(state.getValue(FACING).getOpposite()), false);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBath();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.BATH_1).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.BATH_1);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(world.getTileEntity(pos) instanceof TileEntityBath)
        {
            TileEntityBath bath = (TileEntityBath) world.getTileEntity(pos);
            return state.withProperty(WATER_LEVEL, bath.getWaterLevel());
        }
        return state.withProperty(WATER_LEVEL, 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, WATER_LEVEL);
    }

    public static boolean canPlaceBath(World world, BlockPos pos, EnumFacing facing)
    {
        return (world.isAirBlock(pos) && world.isAirBlock(pos.offset(facing, 1)));
    }

    public boolean hasWaterSource(World world, BlockPos pos)
    {
        return world.getBlockState(pos.add(0, -2, 0)) == Blocks.WATER.getDefaultState();
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityBath bath = (TileEntityBath) world.getTileEntity(pos);
        return bath.getWaterLevel();
    }
}
