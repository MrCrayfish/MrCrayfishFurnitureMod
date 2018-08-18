package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BlockLampOff extends BlockLamp
{
    public BlockLampOff()
    {
        super(Material.GLASS, "lamp_off", false);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(worldIn.isBlockPowered(pos))
        {
            worldIn.setBlockState(pos, FurnitureBlocks.LAMP_ON.getDefaultState(), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isBlockPowered(pos) || worldIn.getBlockState(pos.down()).getBlock() == FurnitureBlocks.BEDSIDE_CABINET_OAK)
        {
            worldIn.setBlockState(pos, FurnitureBlocks.LAMP_ON.getDefaultState(), 2);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this, true);
        }
        else if(!worldIn.isRemote)
        {
            playerIn.sendMessage(new TextComponentTranslation("cfm.message.lamp"));
        }
        return true;
    }
}
