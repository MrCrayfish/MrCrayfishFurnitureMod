package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityComputer;
import com.mrcrayfish.furniture.util.Bounds;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class BlockComputer extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(4.5, 0, 2, 14.5, 4.8, 14).getRotatedBounds();
    private static final AxisAlignedBB[] KEYBOARD_BASE = new Bounds(1, 0, 1, 3.8, 1, 12).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_FRONT_BOTTOM = new Bounds(4, 6.4, 2, 8, 7.4, 14).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_FRONT_TOP = new Bounds(4, 15, 2, 8, 16, 14).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_FRONT_LEFT = new Bounds(4, 7.4008, 2, 8, 15, 3).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_FRONT_RIGHT = new Bounds(4, 7.4, 13, 8, 15, 14).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_BODY = new Bounds(6.4, 7.4, 3, 14, 15, 13).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_SCREEN = new Bounds(4.8, 7.4, 3, 6.4, 15, 13).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_CONNECTION = new Bounds(7.4, 4.8, 6.4, 10.6, 7.4, 9.6).getRotatedBounds();
    private static final AxisAlignedBB[] MONITER_BASE = new Bounds(6.6, 4.8, 5.1, 11.4, 5.6, 10.9).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_1 = new Bounds(4, 3, 3, 5, 4, 4).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_2 = new Bounds(4, 3, 4.5, 5, 4, 5.5).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_3 = new Bounds(4, 3, 6, 5, 4, 7).getRotatedBounds();
    private static final AxisAlignedBB[] CD_DRIVE = new Bounds(4.4, 3, 8, 5.4, 4, 13).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_1 = new Bounds(1.4, 0.2, 1.8, 2.2, 1.2, 2.6).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_2 = new Bounds(2.6, 0.2, 1.4, 3.4, 1.2, 2.2).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_3 = new Bounds(2.6, 0.2, 2.6, 3.4, 1.2, 3.4).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_4 = new Bounds(1.4, 0.2, 3, 2.2, 1.2, 3.8).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_5 = new Bounds(2.6, 0.2, 3.8, 3.4, 1.2, 4.6).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_6 = new Bounds(1.4, 0.2, 4.2, 2.2, 1.2, 5).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_7 = new Bounds(1.4, 0.2, 5.4, 2.2, 1.2, 6.2).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_8 = new Bounds(2.6, 0.2, 5, 3.4, 1.2, 5.8).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_9 = new Bounds(1.4, 0.2, 6.6, 2.2, 1.2, 7.4).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_10 = new Bounds(1.4, 0.2, 7.8, 2.2, 1.2, 8.6).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_11 = new Bounds(2.6, 0.2, 6.2, 3.4, 1.2, 7).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_12 = new Bounds(2.6, 0.2, 7.4, 3.4, 1.2, 8.2).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_13 = new Bounds(2.6, 0.2, 8.6, 3.4, 1.2, 9.4).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_14 = new Bounds(1.4, 0.2, 9, 2.2, 1.2, 9.8).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_15 = new Bounds(2.6, 0.2, 9.8, 3.4, 1.2, 11.6).getRotatedBounds();
    private static final AxisAlignedBB[] KEY_16 = new Bounds(1.4, 0.2, 10.2, 2.2, 1.2, 11).getRotatedBounds();
    private static final AxisAlignedBB[] MOUSE_BASE = new Bounds(0.5, 0, 13, 4, 1, 15).getRotatedBounds();
    private static final AxisAlignedBB[] MOUSE_LEFT = new Bounds(2.6, 0.1, 13.1, 3.8, 1.1, 13.9).getRotatedBounds();
    private static final AxisAlignedBB[] MOUSE_RIGHT = new Bounds(2.6, 0.1, 14.1, 3.8, 1.1, 14.9).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY, KEYBOARD_BASE, MONITER_FRONT_BOTTOM, MONITER_FRONT_TOP, MONITER_FRONT_LEFT, MONITER_FRONT_RIGHT, MONITER_BODY, MONITER_SCREEN, MONITER_CONNECTION, MONITER_BASE, BUTTON_1, BUTTON_2, BUTTON_3, CD_DRIVE, KEY_1, KEY_2, KEY_3, KEY_4, KEY_5, KEY_6, KEY_7, KEY_8, KEY_9, KEY_10, KEY_11, KEY_12, KEY_13, KEY_14, KEY_15, KEY_16, MOUSE_BASE, MOUSE_LEFT, MOUSE_RIGHT);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY, MONITER_FRONT_TOP));

    public BlockComputer(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.ANVIL);
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_APPLIANCE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile_entity = worldIn.getTileEntity(pos);

        if(tile_entity instanceof TileEntityComputer)
        {
            TileEntityComputer computer = (TileEntityComputer) tile_entity;
            if(!computer.isTrading())
            {
                computer.setTrading(true);
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            else
            {
                if(!worldIn.isRemote)
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.computer"));
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityComputer();
    }
}
