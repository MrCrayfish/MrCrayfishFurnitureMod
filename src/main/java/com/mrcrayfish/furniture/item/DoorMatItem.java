package com.mrcrayfish.furniture.item;

import com.mrcrayfish.furniture.client.ClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class DoorMatItem extends BlockItem
{
    public DoorMatItem(Block blockIn, Item.Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state)
    {
        boolean placedBlockEntity = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if(level.isClientSide() && !placedBlockEntity && player != null)
        {
            ClientHandler.showDoorMatScreen(level, pos);
        }
        return placedBlockEntity;
    }
}
