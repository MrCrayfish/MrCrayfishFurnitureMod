package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.blocks.BlockFirePit;
import com.mrcrayfish.furniture.blocks.BlockFirePitOff;
import com.mrcrayfish.furniture.blocks.BlockNewFirepit;

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
	public ItemLog(Block block, String name)
	{
		super(block);
		this.setMaxStackSize(16);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block instanceof BlockFirePitOff)
		{
			int meta = block.getMetaFromState(state);
			if (meta < 3)
			{
				world.playSound(null, pos, SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.withProperty(BlockFirePit.STAGE, meta + 1));
				stack.shrink(1);
				return EnumActionResult.SUCCESS;
			}
			return EnumActionResult.FAIL;
		} else if (block instanceof BlockNewFirepit)
		{
			int stage = state.getValue(BlockNewFirepit.STAGE);
			if (stage < 3 && block == this.getBlock())
			{
				world.playSound(null, pos, SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.withProperty(BlockFirePit.STAGE, stage + 1));
				stack.shrink(1);
				return EnumActionResult.SUCCESS;
			}
			return EnumActionResult.FAIL;
		}

		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
}
