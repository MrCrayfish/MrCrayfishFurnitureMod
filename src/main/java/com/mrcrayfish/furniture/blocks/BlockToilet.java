package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.handler.InputHandler;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.CollisionHelper;
import com.mrcrayfish.furniture.util.SeatUtil;
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

public class BlockToilet extends BlockFurniture
{
    private static final AxisAlignedBB[] BOUNDING_BOX = new Bounds(1.6, 0, 2.4, 16, 16, 13.6).getRotatedBounds();
    private static final AxisAlignedBB[] COLLISION_BOX_TANK = new Bounds(10.4, 8, 0, 16, 17.6, 16).getRotatedBounds();
    private static final AxisAlignedBB COLLISION_BOX_SEAT = new Bounds(0, 0, 0, 16, 8, 16).toAABB();

    public BlockToilet(Material material)
    {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
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
            List<EntityItem> items = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D).grow(1D));
            for(EntityItem item : items)
            {
                item.setDead();
            }
            worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, FurnitureSounds.flush, SoundCategory.BLOCKS, 0.75F, 1.0F, false);
        }
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        if(!(entityIn instanceof EntitySeat))
        {
            EnumFacing facing = state.getValue(FACING);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_TANK[facing.getHorizontalIndex()]);
            addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX_SEAT);
        }
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
