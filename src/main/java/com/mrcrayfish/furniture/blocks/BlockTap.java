package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

public class BlockTap extends BlockFurniture
{
    private static final AxisAlignedBB[] PIPE_CAUDAL_MEDIAL = new Bounds(7, 0, 7, 9, 12.8, 9).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_CAUDAL = new Bounds(7.5, 14.4, 7.5, 8.5, 15.2, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_SAGITTAL = new Bounds(6.22, 15.2, 7.5, 9.78, 16, 8.5).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_CORONAL = new Bounds(7.5, 15.2, 6.22, 8.5, 16, 9.78).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_VENTRAL = new Bounds(2, 12.8, 7, 9, 14.4, 9).getRotatedBounds();
    private static final AxisAlignedBB[] PIPE_CAUDAL_VENTRAL = new Bounds(2, 11.2, 7, 4, 12.8, 9).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, PIPE_CAUDAL_MEDIAL, PIPE_VENTRAL, PIPE_CAUDAL_VENTRAL);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_HANDLE = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, HANDLE_CAUDAL, HANDLE_SAGITTAL, HANDLE_CORONAL);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_HANDLE);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES_BODY);

    public BlockTap(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
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
        if(!worldIn.isRemote)
        {
            EnumFacing side = state.getValue(FACING);
            if(hasWaterSource(worldIn, pos))
            {
                if(worldIn.isAirBlock(pos.offset(side.getOpposite())))
                {
                    worldIn.setBlockState(pos.offset(side.getOpposite()), Blocks.WATER.getDefaultState());
                    worldIn.setBlockToAir(pos.down(2));
                }
                else
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.tap_blocked"));
                }
            }
            else
            {
                playerIn.sendMessage(new TextComponentTranslation("cfm.message.tap_nowater"));
            }
        }
        return true;
    }

    public boolean hasWaterSource(World world, BlockPos pos)
    {
        if(world.getBlockState(pos.down(2)).getBlock() == Blocks.WATER)
        {
            if(world.getBlockState(pos.down(2)).getValue(BlockLiquid.LEVEL) == 0)
            {
                return true;
            }
        }
        return false;
    }
}
