package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityDeskCabinet;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.StateHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

/**
 * Author: MrCrayfish
 */
public class BlockDeskCabinet extends BlockFurnitureTile implements IDesk
{
    public static final PropertyEnum<DeskCabinetType> TYPE = PropertyEnum.create("type", DeskCabinetType.class);

    private static final AxisAlignedBB[] BODY = new Bounds(2, 2, 1.5, 14, 14.5, 14.5).getRotatedBounds();
    private static final AxisAlignedBB[] DRAWER_1 = new Bounds(3, 4, 14.5, 13, 8, 15.5).getRotatedBounds();
    private static final AxisAlignedBB[] DRAWER_2 = new Bounds(3, 9.5, 14.5, 13, 13.5, 15.5).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_1 = new Bounds(6.5, 5.5, 15, 9.5, 6.5, 16).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_2 = new Bounds(6.5, 11, 15, 9.5, 12, 16).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(BlockDesk.TOP, BODY, DRAWER_1, DRAWER_2, HANDLE_1, HANDLE_2);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_NONE = Bounds.combineBoxLists(COLLISION_BOXES_BODY, BlockDesk.COLLISION_BOXES_SIDE_LEFT, BlockDesk.COLLISION_BOXES_SIDE_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTH = Bounds.combineBoxLists(COLLISION_BOXES_BODY, BlockDesk.COLLISION_BOXES_BACK_COVER_LEFT, BlockDesk.COLLISION_BOXES_BACK_COVER_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_RIGHT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, BlockDesk.COLLISION_BOXES_SIDE_RIGHT, BlockDesk.COLLISION_BOXES_BACK_COVER_LEFT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_LEFT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, BlockDesk.COLLISION_BOXES_SIDE_LEFT, BlockDesk.COLLISION_BOXES_BACK_COVER_RIGHT);

    private static final AxisAlignedBB[] BOUNDING_BOX_BOTH = Bounds.getBoundingBoxes(COLLISION_BOXES_BOTH);
    private static final AxisAlignedBB[] BOUNDING_BOX_NONE = Bounds.getBoundingBoxes(COLLISION_BOXES_NONE);

    public BlockDeskCabinet(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, DeskCabinetType.NONE));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(TYPE) == DeskCabinetType.BOTH ? BOUNDING_BOX_BOTH[i] : BOUNDING_BOX_NONE[i];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        if (!isActualState)
            state = getActualState(state, world, pos);

        int i = state.getValue(FACING).getHorizontalIndex();
        switch (state.getValue(TYPE))
        {
            case LEFT: return COLLISION_BOXES_LEFT[i];
            case RIGHT: return COLLISION_BOXES_RIGHT[i];
            case BOTH: return COLLISION_BOXES_BOTH[i];
            default: return COLLISION_BOXES_NONE[i];
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            String info = I18n.format("cfm.desk_cabinet.info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        }
        else
        {
            tooltip.add(TextFormatting.YELLOW + I18n.format("cfm.info"));
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityDeskCabinet)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        boolean left = false;
        boolean right = false;

        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.LEFT) instanceof IDesk)
        {
            left = true;
        }
        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.RIGHT) instanceof IDesk)
        {
            right = true;
        }
        if(left && !right)
        {
            return state.withProperty(TYPE, DeskCabinetType.LEFT);
        }
        else if(!left && right)
        {
            return state.withProperty(TYPE, DeskCabinetType.RIGHT);
        }
        else if(!left && !right)
        {
            return state.withProperty(TYPE, DeskCabinetType.NONE);
        }
        return state.withProperty(TYPE, DeskCabinetType.BOTH);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDeskCabinet();
    }

    public enum DeskCabinetType implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
