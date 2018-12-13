package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

public class ItemCup extends Item
{
    private boolean hasLiquid = false;

    public ItemCup(boolean hasLiquid)
    {
        this.hasLiquid = hasLiquid;
        if(hasLiquid)
        {
            setMaxStackSize(1);
        }
        else
        {
            setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            if(nbt.hasKey("Name", 8))
            {
                return nbt.getString("Name");
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack cup, World world, EntityLivingBase entity)
    {
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            if(hasLiquid)
            {
                int heal = cup.getTagCompound().getInteger("HealAmount");
                player.getFoodStats().addStats(heal, 0.5F);
                return new ItemStack(FurnitureItems.CUP);
            }
        }
        return cup;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack cup)
    {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(hasLiquid)
        {
            if(playerIn.getFoodStats().needFood())
            {
                playerIn.setActiveHand(hand);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if(!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = player.getHeldItem(hand);

        if(!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(FurnitureBlocks.CUP, pos, false, facing, null))
        {
            int i = this.getMetadata(itemstack.getMetadata());
            IBlockState iblockstate1 = FurnitureBlocks.CUP.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

            if(placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
            {
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromCompound(ItemStack cup)
    {
        if(cup.hasTagCompound())
        {
            if(cup.getTagCompound().hasKey("Colour"))
            {
                int[] colour = cup.getTagCompound().getIntArray("Colour");
                Color color = new Color(colour[0], colour[1], colour[2]);
                return color.getRGB();
            }
        }
        return 16777215;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if(!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() == FurnitureBlocks.CUP)
        {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            FurnitureBlocks.CUP.onBlockPlacedBy(world, pos, state, player, stack);
        }

        return true;
    }
}