package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHammer extends Item {
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) 
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
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Mailbox unlocked."));
					}
					else if (!tileEntityMailBox.locked)
					{
						tileEntityMailBox.locked = true;
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Mailbox locked."));
					}
					return true;
				}
			}
		}
		return false;
	}
}
