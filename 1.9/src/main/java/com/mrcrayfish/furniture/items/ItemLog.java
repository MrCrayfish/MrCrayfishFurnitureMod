package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.blocks.BlockFirePit;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLog extends ItemBlockSpecial 
{
	public ItemLog(Block block) 
	{
		super(block);
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		Block block = worldIn.getBlockState(pos).getBlock();
		if(block instanceof BlockFirePit)
		{
			int meta = block.getMetaFromState(state);
			if(meta < 3)
			{
				worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F, true);
				worldIn.setBlockState(pos, state.withProperty(BlockFirePit.STAGE, meta + 1));
				stack.stackSize--;
				return EnumActionResult.SUCCESS;
			}
			return EnumActionResult.FAIL;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
