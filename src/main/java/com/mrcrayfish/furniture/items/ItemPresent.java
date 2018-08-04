/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.blocks.BlockPresent;
import com.mrcrayfish.furniture.gui.inventory.InventoryPresent;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPresent extends ItemBlock implements IMail, SubItems
{
    public ItemPresent(Block block)
    {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

            if(nbttagstring != null)
            {
                tooltip.add(TextFormatting.GRAY + I18n.format("cfm.mail_signed.info", nbttagstring.getString()));
            }
            else
            {
                tooltip.add(TextFormatting.GRAY + I18n.format("cfm.present_unsigned.info"));
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(worldIn.isSideSolid(pos, EnumFacing.UP))
        {
            if(stack.hasTagCompound())
            {
                NBTTagCompound nbttagcompound = stack.getTagCompound();
                NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

                if(nbttagstring != null)
                {
                    NBTTagList itemList = (NBTTagList) NBTHelper.getCompoundTag(stack, "Present").getTag("Items");
                    if(itemList.tagCount() > 0)
                    {
                        IBlockState state = FurnitureBlocks.PRESENT.getDefaultState().withProperty(BlockPresent.COLOUR, EnumDyeColor.byMetadata(stack.getItemDamage()));

                        worldIn.setBlockState(pos.up(), state, 2);
                        worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (state.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, state.getBlock().getSoundType().getPitch() * 0.8F, false);


                        TileEntityPresent tep = new TileEntityPresent();
                        tep.setOwner(player.getName());

                        for(int i = 0; i < itemList.tagCount(); i++)
                        {
                            tep.setInventorySlotContents(i, new ItemStack(itemList.getCompoundTagAt(i)));
                        }

                        worldIn.setTileEntity(pos.up(), tep);

                        stack.shrink(1);
                        return EnumActionResult.SUCCESS;
                    }
                    else
                    {
                        if(worldIn.isRemote)
                        {
                            player.sendMessage(new TextComponentTranslation("cfm.message.present_place"));
                        }
                    }
                }
                else
                {
                    if(worldIn.isRemote)
                    {
                        player.sendMessage(new TextComponentTranslation("cfm.message.present_sign"));
                    }
                }
            }
            else
            {
                if(worldIn.isRemote)
                {
                    player.sendMessage(new TextComponentTranslation("cfm.message.present_sign"));
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        if(!worldIn.isRemote)
        {
            if(stack.hasTagCompound())
            {
                NBTTagCompound nbttagcompound = stack.getTagCompound();
                NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

                if(nbttagstring == null)
                {
                    playerIn.openGui(MrCrayfishFurnitureMod.instance, 9, worldIn, 0, 0, 0);
                }
                else if(nbttagstring.getString().equals(""))
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.present_wrap"));
                }
            }
            else
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 9, worldIn, 0, 0, 0);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public static IInventory getInv(EntityPlayer player, EnumHand activeHand)
    {
        ItemStack present = player.getHeldItemMainhand();
        InventoryPresent invPresent = null;
        if(present != null && present.getItem() instanceof ItemPresent)
        {
            invPresent = new InventoryPresent(player, present);
        }
        return invPresent;
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + "_" + EnumDyeColor.values()[stack.getItemDamage()].getName();
    }

    @Override
    public NonNullList<ResourceLocation> getModels()
    {
        NonNullList<ResourceLocation> modelLocations = NonNullList.create();
        for(EnumDyeColor color : EnumDyeColor.values())
        {
            modelLocations.add(new ResourceLocation(Reference.MOD_ID, getUnlocalizedName().substring(5) + "_" + color.getName()));
        }
        return modelLocations;
    }
}
