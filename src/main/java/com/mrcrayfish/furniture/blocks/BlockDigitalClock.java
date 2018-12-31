package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.tileentity.IColored;
import com.mrcrayfish.furniture.tileentity.TileEntityDigitalClock;
import com.mrcrayfish.furniture.util.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class BlockDigitalClock extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] FRAME_BOTTOM = new Bounds(4.5, 0, 9, 11.5, 0.5, 9.5).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_TOP = new Bounds(4.5, 4, 9, 11.5, 4.5, 9.5).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_BACK = new Bounds(4.5, 0, 7, 11.5, 4.3, 9).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_LEFT = new Bounds(4, 0.5, 9, 4.5, 4, 9.5).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_RIGHT = new Bounds(11.5, 0.5, 9, 12, 4, 9.5).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_BACK_2 = new Bounds(4.2, 0.3, 7, 4.5, 4, 9).getRotatedBounds();
    private static final AxisAlignedBB[] FRAME_BACK_3 = new Bounds(11.5, 0.3, 7, 11.8, 4, 9).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_1 = new Bounds(5, 4.3, 8, 5.4, 4.5, 8.4).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_2 = new Bounds(6, 4.3, 8, 6.4, 4.5, 8.4).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_3 = new Bounds(7, 4.3, 8, 7.4, 4.5, 8.4).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(FRAME_BOTTOM, FRAME_TOP, FRAME_BACK, FRAME_LEFT, FRAME_RIGHT, FRAME_BACK_2, FRAME_BACK_3, BUTTON_1, BUTTON_2, BUTTON_3);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    public BlockDigitalClock()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("digital_clock");
        this.setRegistryName("digital_clock");
        this.setLightLevel(0.5F);
        this.setHardness(0.5F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            String info = I18n.format("cfm.digital_clock.info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        }
        else
        {
            tooltip.add(TextFormatting.YELLOW + I18n.format("cfm.info"));
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof IColored)
        {
            IColored colored = (IColored) tileEntity;
            state = state.withProperty(BlockColored.COLOR, colored.getColor());
        }
        return state;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        if(tileEntity instanceof TileEntityDigitalClock)
        {
            TileEntityDigitalClock clock = (TileEntityDigitalClock) tileEntity;
            ItemStack itemstack = new ItemStack(this, 1, clock.getColor().getMetadata());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, tileEntity, stack);
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof IColored)
        {
            return new ItemStack(Item.getItemFromBlock(this), 1, ((IColored) tileEntity).getColor().getMetadata());
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof IColored)
        {
            IColored colored = (IColored) tileEntity;
            colored.setColor(EnumDyeColor.byMetadata(stack.getMetadata()));
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if(!heldItem.isEmpty() && heldItem.getItem() instanceof ItemDye)
            {
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if(tileEntity instanceof TileEntityDigitalClock)
                {
                    TileEntityDigitalClock digitalClock = (TileEntityDigitalClock) tileEntity;
                    digitalClock.setTextColorColor(EnumDyeColor.byDyeDamage(heldItem.getMetadata()));
                    if(!playerIn.capabilities.isCreativeMode)
                    {
                        heldItem.shrink(1);
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, BlockColored.COLOR);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDigitalClock();
    }
}
