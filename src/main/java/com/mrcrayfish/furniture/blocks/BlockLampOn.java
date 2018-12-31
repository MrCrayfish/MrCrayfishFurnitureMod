package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLampOn extends BlockLamp
{
    public BlockLampOn(Material material)
    {
        super(material, true);
        this.setLightLevel(1.0F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!heldItem.isEmpty())
        {
            if(heldItem.getItem() instanceof ItemDye)
            {
                worldIn.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()));
                if(!playerIn.capabilities.isCreativeMode)
                {
                    heldItem.shrink(1);
                }
                return true;
            }
        }

        worldIn.setBlockState(pos, FurnitureBlocks.LAMP_OFF.getDefaultState().withProperty(COLOUR, state.getValue(COLOUR)), 3);
        return true;
    }
}
