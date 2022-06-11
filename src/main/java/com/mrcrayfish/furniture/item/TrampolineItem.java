package com.mrcrayfish.furniture.item;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.TrampolineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class TrampolineItem extends BlockItem
{
    public TrampolineItem(Block blockIn, Item.Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state)
    {
        super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        CompoundTag blockEntityTag = stack.getTagElement("BlockEntityTag");
        if(blockEntityTag != null)
        {
            DyeColor color = DyeColor.byId(blockEntityTag.getInt("Color"));
            if(level.getBlockEntity(pos) instanceof TrampolineBlockEntity blockEntity)
            {
                blockEntity.setColour(color);
            }
        }
        return true;
    }

    @Override
    public String getDescriptionId(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();
        if(tag != null)
        {
            CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");
            DyeColor color = DyeColor.byId(blockEntityTag.getInt("Color"));
            return String.format("block.%s.%s_trampoline", Reference.MOD_ID, color.getName());
        }
        return super.getDescriptionId(stack);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items)
    {
        if(this.allowedIn(tab))
        {
            for(DyeColor color : DyeColor.values())
            {
                ItemStack stack = new ItemStack(this);
                CompoundTag tag = new CompoundTag();
                CompoundTag blockEntityTag = new CompoundTag();
                blockEntityTag.putInt("Color", color.getId());
                tag.put("BlockEntityTag", blockEntityTag);
                stack.setTag(tag);
                items.add(stack);
            }
        }
    }
}
