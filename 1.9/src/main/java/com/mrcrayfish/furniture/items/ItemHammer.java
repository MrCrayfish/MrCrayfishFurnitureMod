package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemHammer extends Item {
	
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) 
	{
		if(!world.isRemote)
		{
			TileEntity tileEntity = world.getTileEntity(pos);
			if(tileEntity instanceof TileEntityMailBox)
			{
				TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tileEntity;
				if(player.isSneaking())
				{
					if (tileEntityMailBox.locked)
					{
						tileEntityMailBox.locked = false;
						player.addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + "Mailbox unlocked."));
					}
					else if (!tileEntityMailBox.locked)
					{
						tileEntityMailBox.locked = true;
						player.addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + "Mailbox locked."));
					}
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.FAIL;
	}
}
