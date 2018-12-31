package com.mrcrayfish.furniture.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityStereo;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStereo extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(5.5, 0, 0.5, 10.5, 7, 15.5).getRotatedBounds();
    private static final AxisAlignedBB[] SCREEN = new Bounds(5.4, 3, 5.6, 5.5, 6, 10.6).getRotatedBounds();
    private static final AxisAlignedBB[] ANTENNA_BASE = new Bounds(7, 7, 6.2, 9, 7.5, 9.8).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_1 = new Bounds(5.1, 1, 6, 5.5, 2, 7).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_2 = new Bounds(5.1, 1, 7.5, 5.5, 2, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] BUTTON_3 = new Bounds(5.1, 1, 9, 5.5, 2, 10).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BODY);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_PROTRUSIONS = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, SCREEN, ANTENNA_BASE, BUTTON_1, BUTTON_2, BUTTON_3);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_PROTRUSIONS);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES_BODY);

    public static ArrayList<ItemRecord> records = new ArrayList<>();

    public BlockStereo(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.METAL);

        for(Item item : Item.REGISTRY)
        {
            if(item instanceof ItemRecord)
            {
                records.add((ItemRecord) item);
            }
        }
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile_entity = worldIn.getTileEntity(pos);
        if(tile_entity instanceof TileEntityStereo)
        {
            TileEntityStereo tileEntityStereo = (TileEntityStereo) tile_entity;
            if(!playerIn.isSneaking())
            {
                if(tileEntityStereo.count == 13)
                {
                    tileEntityStereo.count = records.size();
                }
                else
                {
                    tileEntityStereo.count++;
                }
                if(tileEntityStereo.count == records.size())
                {
                    tileEntityStereo.count = 0;
                }
                worldIn.playEvent(1010, pos, Item.getIdFromItem(records.get(tileEntityStereo.count)));
                Triggers.trigger(Triggers.STEREO_ACTIVATED, playerIn);
            }
            else
            {
                if(tileEntityStereo.count != 13)
                {
                    tileEntityStereo.count = 13;
                    if(!worldIn.isRemote)
                    {
                        playerIn.sendMessage(new TextComponentTranslation("cfm.message.stereo1"));
                    }
                    this.ejectRecord(worldIn, pos);
                }
                else
                {
                    if(!worldIn.isRemote)
                        playerIn.sendMessage(new TextComponentTranslation("cfm.message.stereo2"));
                }
            }
            if(!worldIn.isRemote)
            {
                TileEntityUtil.markBlockForUpdate(worldIn, pos);
            }
        }
        return true;

    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        this.ejectRecord(worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityStereo();
    }

    public void ejectRecord(World worldIn, BlockPos pos)
    {
        worldIn.playEvent(1010, pos, 0);
        worldIn.playRecord(pos, null);
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityStereo stereo = (TileEntityStereo) world.getTileEntity(pos);
        return stereo.count;
    }
}
