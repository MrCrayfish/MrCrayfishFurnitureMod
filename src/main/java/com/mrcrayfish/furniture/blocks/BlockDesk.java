package com.mrcrayfish.furniture.blocks;

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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
public class BlockDesk extends BlockFurniture implements IDesk
{
    public static final PropertyEnum<DeskType> TYPE = PropertyEnum.create("type", DeskType.class);

    public static final AxisAlignedBB[] TOP = new Bounds(0, 14.5, 0, 16, 16, 16).getRotatedBounds();
    private static final AxisAlignedBB[] BACK = new Bounds(2, 2, 1.5, 14, 14.5, 2.5).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(TOP, BACK);

    private static final AxisAlignedBB[] BACK_COVER_LEFT = new Bounds(0, 2, 1.5, 2, 14.5, 2.5).getRotatedBounds();
    private static final AxisAlignedBB[] BACK_COVER_RIGHT = new Bounds(14, 2, 1.5, 16, 14.5, 2.5).getRotatedBounds();
    public static final List<AxisAlignedBB>[] COLLISION_BOXES_BACK_COVER_LEFT = Bounds.getRotatedBoundLists(BACK_COVER_LEFT);
    public static final List<AxisAlignedBB>[] COLLISION_BOXES_BACK_COVER_RIGHT = Bounds.getRotatedBoundLists(BACK_COVER_RIGHT);

    private static final AxisAlignedBB[] LEFT = new Bounds(0.5, 0, 1, 2, 14.5, 15).getRotatedBounds();
    public static final List<AxisAlignedBB>[] COLLISION_BOXES_SIDE_LEFT = Bounds.getRotatedBoundLists(LEFT);
    public static final List<AxisAlignedBB>[] COLLISION_BOXES_SIDE_RIGHT = Bounds.transformBoxListsHorizontal(EnumFacing.Axis.Z, 13.5, COLLISION_BOXES_SIDE_LEFT);

    private static final AxisAlignedBB[] LEG_RIGHT = new Bounds(13.5, 0, 1, 15, 14.5, 2.5).getRotatedBounds();
    private static final AxisAlignedBB[] CORNER_RIGHT = new Bounds(13.5, 2, 2.5, 14.5, 14.5, 16).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_SIDE_CORNER_RIGHT = Bounds.getRotatedBoundLists(LEG_RIGHT, CORNER_RIGHT);

    private static final AxisAlignedBB[] LEG_LEFT = new Bounds(1, 0, 1, 2.5, 14.5, 2.5).getRotatedBounds();
    private static final AxisAlignedBB[] CORNER_LEFT = new Bounds(1.5, 2, 2.5, 2.5, 14.5, 16).getRotatedBounds();
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_SIDE_CORNER_LEFT = Bounds.getRotatedBoundLists(LEG_LEFT, CORNER_LEFT);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_NONE = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_SIDE_LEFT, COLLISION_BOXES_SIDE_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BOTH = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_BACK_COVER_LEFT, COLLISION_BOXES_BACK_COVER_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_RIGHT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_SIDE_RIGHT, COLLISION_BOXES_BACK_COVER_LEFT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_LEFT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_SIDE_LEFT, COLLISION_BOXES_BACK_COVER_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_CORNER_RIGHT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_SIDE_CORNER_RIGHT, COLLISION_BOXES_BACK_COVER_LEFT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_CORNER_LEFT = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_SIDE_CORNER_LEFT, COLLISION_BOXES_BACK_COVER_RIGHT);

    private static final AxisAlignedBB[] BOUNDING_BOX_BOTH = Bounds.getBoundingBoxes(COLLISION_BOXES_BOTH);
    private static final AxisAlignedBB[] BOUNDING_BOX_NONE = Bounds.getBoundingBoxes(COLLISION_BOXES_NONE);

    public BlockDesk(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, DeskType.NONE));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        int i = state.getValue(FACING).getHorizontalIndex();
        return state.getValue(TYPE) == DeskType.BOTH ? BOUNDING_BOX_BOTH[i] : BOUNDING_BOX_NONE[i];
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
            case CORNER_LEFT: return COLLISION_BOXES_CORNER_LEFT[i];
            case CORNER_RIGHT: return COLLISION_BOXES_CORNER_RIGHT[i];
            case BOTH: return COLLISION_BOXES_BOTH[i];
            default: return COLLISION_BOXES_NONE[i];
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            String info = I18n.format("cfm.desk.info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        }
        else
        {
            tooltip.add(TextFormatting.YELLOW + I18n.format("cfm.info"));
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if(StateHelper.getBlock(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) instanceof IDesk)
        {
            if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.RIGHT)
            {
                return state.withProperty(TYPE, DeskType.CORNER_RIGHT);
            }
            else if(StateHelper.getRotation(world, pos, state.getValue(FACING), StateHelper.Direction.DOWN) == StateHelper.Direction.LEFT)
            {
                return state.withProperty(TYPE, DeskType.CORNER_LEFT);
            }
        }

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
            return state.withProperty(TYPE, DeskType.LEFT);
        }
        else if(!left && right)
        {
            return state.withProperty(TYPE, DeskType.RIGHT);
        }
        else if(!left && !right)
        {
            return state.withProperty(TYPE, DeskType.NONE);
        }
        return state.withProperty(TYPE, DeskType.BOTH);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    public enum DeskType implements IStringSerializable
    {
        NONE,
        LEFT,
        RIGHT,
        BOTH,
        CORNER_LEFT,
        CORNER_RIGHT;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
