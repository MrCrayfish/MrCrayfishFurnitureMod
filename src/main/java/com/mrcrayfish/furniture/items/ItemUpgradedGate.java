package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.blocks.BlockFurniture;
import com.mrcrayfish.furniture.blocks.BlockUpgradedGate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class ItemUpgradedGate extends ItemBlock
{
    public ItemUpgradedGate(Block block)
    {
        super(block);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState state = worldIn.getBlockState(pos);
            Block block = state.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
                pos = pos.offset(facing);
            }

            ItemStack stack = player.getHeldItem(hand);
            if (player.canPlayerEdit(pos, facing, stack) && this.block.canPlaceBlockAt(worldIn, pos))
            {
                EnumFacing playerFacing = EnumFacing.fromAngle((double)player.rotationYaw);
                int offsetX = playerFacing.getFrontOffsetX();
                int offsetZ = playerFacing.getFrontOffsetZ();
                boolean side = offsetX < 0 && hitZ < 0.5F || offsetX > 0 && hitZ > 0.5F || offsetZ < 0 && hitX > 0.5F || offsetZ > 0 && hitX < 0.5F;
                this.placeGate(worldIn, pos, playerFacing, this.block, side);
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                stack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }

    private void placeGate(World world, BlockPos pos, EnumFacing facing, Block block, boolean rightSide)
    {
        world.setBlockState(pos, block.getDefaultState().withProperty(BlockFurniture.FACING, facing).withProperty(BlockUpgradedGate.HINGE, rightSide ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(BlockUpgradedGate.OPENED, false), 3);
    }
}
