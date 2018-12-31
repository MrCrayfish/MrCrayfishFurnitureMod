package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.handler.InputHandler;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SeatUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;

import javax.annotation.Nullable;

public class BlockToilet extends BlockFurniture
{
    private static final AxisAlignedBB[] BASE = new Bounds(3.2, 0, 4, 14.4, 6.4, 12).getRotatedBounds();
    private static final AxisAlignedBB[] TANK = new Bounds(10.4, 4.8, 2.4, 16, 16, 13.6).getRotatedBounds();
    private static final AxisAlignedBB[] TANK_LID = new Bounds(9.6, 16, 1.6, 16, 17.6, 14.4).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_LEFT = new Bounds(1.6, 4.8, 2.4, 10.4, 9.6, 4).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_FRONT = new Bounds(1.6, 4.8, 4, 3.2, 9.6, 12).getRotatedBounds();
    private static final AxisAlignedBB[] BOWL_RIGHT = new Bounds(1.6, 4.8, 12, 10.4, 9.6, 13.6).getRotatedBounds();
    private static final AxisAlignedBB[] FLUSH_1 = new Bounds(11.2, 6.4, 6.24, 12.8, 18.4, 7.84).getRotatedBounds();
    private static final AxisAlignedBB[] FLUSH_2 = new Bounds(11.2, 6.4, 8.16, 12.8, 18.4, 9.76).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES_BODY = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, BASE, TANK, TANK_LID, BOWL_LEFT, BOWL_FRONT, BOWL_RIGHT);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES_PROTRUSIONS = Bounds.getRotatedBoundLists(Rotation.COUNTERCLOCKWISE_90, FLUSH_1, FLUSH_2);
    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.combineBoxLists(COLLISION_BOXES_BODY, COLLISION_BOXES_PROTRUSIONS);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES_BODY);

    public BlockToilet(Material material)
    {
        super(material);
        this.setHardness(1.0F);
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
        return entity instanceof EntitySeat ? EMPTY : COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!playerIn.isSneaking())
        {
            if(SeatUtil.sitOnBlockWithRotationOffset(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0.4D, getMetaFromState(state), 0.1D))
            {
                if(worldIn.isRemote)
                {
                    String key = new TextComponentTranslation("cfm.message.toilet", Keyboard.getKeyName(InputHandler.KEY_FART.getKeyCode())).getUnformattedComponentText();
                    playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + key));
                }
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }
        else
        {
            List<EntityItem> items = worldIn.getEntitiesWithinAABB(EntityItem.class, FULL_BLOCK_AABB.offset(pos).grow(1));
            for(EntityItem item : items)
            {
                item.setDead();
            }
            worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.flush, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
        }
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return SeatUtil.isSomeoneSitting(worldIn, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
    }
}
