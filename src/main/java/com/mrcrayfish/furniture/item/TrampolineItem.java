package com.mrcrayfish.furniture.item;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.tileentity.TrampolineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class TrampolineItem extends BlockItem
{
    public TrampolineItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state)
    {
        super.onBlockPlaced(pos, worldIn, player, stack, state);
        CompoundNBT blockEntityTag = stack.getChildTag("BlockEntityTag");
        if(blockEntityTag != null)
        {
            DyeColor color = DyeColor.byId(blockEntityTag.getInt("Color"));
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TrampolineTileEntity)
            {
                ((TrampolineTileEntity) tileEntity).setColour(color);
            }
        }
        return true;
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        CompoundNBT tag = stack.getTag();
        if(tag != null)
        {
            CompoundNBT blockEntityTag = tag.getCompound("BlockEntityTag");
            DyeColor color = DyeColor.byId(blockEntityTag.getInt("Color"));
            return String.format("block.%s.%s_trampoline", Reference.MOD_ID, color.getTranslationKey());
        }
        return super.getTranslationKey(stack);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
    {
        if(this.isInGroup(group))
        {
            for(DyeColor color : DyeColor.values())
            {
                ItemStack stack = new ItemStack(this);
                CompoundNBT tag = new CompoundNBT();
                CompoundNBT blockEntityTag = new CompoundNBT();
                blockEntityTag.putInt("Color", color.getId());
                tag.put("BlockEntityTag", blockEntityTag);
                stack.setTag(tag);
                items.add(stack);
            }
        }
    }
}
