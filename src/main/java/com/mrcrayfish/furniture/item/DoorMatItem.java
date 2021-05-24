package com.mrcrayfish.furniture.item;

import com.mrcrayfish.furniture.client.ClientHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class DoorMatItem extends BlockItem
{
    public DoorMatItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state)
    {
        boolean placedTileEntity = super.onBlockPlaced(pos, worldIn, player, stack, state);
        if(worldIn.isRemote && !placedTileEntity && player != null)
        {
            ClientHandler.showDoorMatScreen(worldIn, pos);
        }
        return placedTileEntity;
    }
}
