package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

public class BlockPresent extends BlockCollisionRaytrace implements ITileEntityProvider
{
    public static final PropertyEnum<EnumDyeColor> COLOUR = PropertyEnum.create("colour", EnumDyeColor.class);

    private static final AxisAlignedBB BASE = new Bounds(4, 0, 4, 12, 6, 12).toAABB();
    private static final AxisAlignedBB RIBBON_1 = new Bounds(3.9, -0.1, 7, 12.1, 6.1, 9).toAABB();
    private static final AxisAlignedBB RIBBON_2 = new Bounds(7, -0.1, 3.9, 9, 6.1, 12.1).toAABB();

    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(BASE, RIBBON_1, RIBBON_2);
    private static final AxisAlignedBB BOUNDING_BOX = Bounds.getBoundingBox(BASE);

    public BlockPresent(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.CLOTH);
        this.hasTileEntity = true;
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOUR, EnumDyeColor.WHITE));
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
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        TileEntityPresent present = (TileEntityPresent) world.getTileEntity(pos);
        if(present != null)
        {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
            if(world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation("cfm.message.present_christmas", TextFormatting.RED + present.ownerName));
            }
            Triggers.trigger(Triggers.UNWRAP_PRESENT, player);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof IInventory)
        {
            IInventory inv = (IInventory) tileEntity;
            InventoryHelper.dropInventoryItems(world, pos, inv);
        }
        if(tileEntity instanceof ISimpleInventory)
        {
            ISimpleInventory inv = (ISimpleInventory) tileEntity;
            InventoryUtil.dropInventoryItems(world, pos, inv);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPresent();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(COLOUR).getMetadata();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOUR).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOUR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOUR);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
    {
        for(int i = 0; i < EnumDyeColor.values().length; ++i)
        {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
