package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.blocks.BlockCeilingLight;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ItemLightSwitch extends ItemBlock
{
    public ItemLightSwitch(Block block)
    {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            String info = I18n.format("cfm.light_switch.how_to");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        }
        else
        {
            String info = I18n.format("cfm.light_switch.desc");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
            tooltip.add(TextFormatting.YELLOW + I18n.format("cfm.how_to"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        if(player.isSneaking())
        {
            heldItem.setTagCompound(null);
            return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
        }
        return new ActionResult<>(EnumActionResult.PASS, heldItem);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        if(!player.isSneaking())
        {
            IBlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof BlockCeilingLight)
            {
                this.addLight(heldItem, pos);
                return EnumActionResult.SUCCESS;
            }
        }

        if(this.block.canPlaceBlockOnSide(world, pos.offset(side), side))
        {
            NBTTagList lights = getLights(heldItem);
            if(lights != null)
            {
                for(int i = 0; i < lights.tagCount(); i++)
                {
                    NBTBase nbtBase = lights.get(i);
                    BlockPos lightPos = BlockPos.fromLong(((NBTTagLong) nbtBase).getLong());
                    BlockPos placedPos = pos.offset(side);
                    double distance = Math.sqrt(lightPos.distanceSqToCenter(placedPos.getX() + 0.5, placedPos.getY() + 0.5, placedPos.getZ() + 0.5));
                    if(distance > 16)
                    {
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }

        return EnumActionResult.PASS;
    }

    private void addLight(ItemStack stack, BlockPos pos)
    {
        NBTTagCompound tagCompound = createTag(stack);
        if(!tagCompound.hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND))
        {
            tagCompound.setTag("BlockEntityTag", new NBTTagCompound());
        }

        NBTTagCompound entityTagCompound = tagCompound.getCompoundTag("BlockEntityTag");
        if(!entityTagCompound.hasKey("lights", Constants.NBT.TAG_LIST))
        {
            entityTagCompound.setTag("lights", new NBTTagList());
        }

        NBTTagList tagList = (NBTTagList) entityTagCompound.getTag("lights");
        if(!containsLight(tagList, pos))
        {
            tagList.appendTag(new NBTTagLong(pos.toLong()));
        }
    }

    private boolean containsLight(NBTTagList tagList, BlockPos pos)
    {
        for(int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagLong tagLong = (NBTTagLong) tagList.get(i);
            if(tagLong.getLong() == pos.toLong())
            {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private NBTTagList getLights(ItemStack stack)
    {
        NBTTagCompound tagCompound = createTag(stack);
        if(tagCompound.hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND))
        {
            NBTTagCompound entityTagCompound = tagCompound.getCompoundTag("BlockEntityTag");
            if(entityTagCompound.hasKey("lights", Constants.NBT.TAG_LIST))
            {
                return (NBTTagList) entityTagCompound.getTag("lights");
            }
        }
        return null;
    }

    private NBTTagCompound createTag(ItemStack stack)
    {
        if(!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }
}
