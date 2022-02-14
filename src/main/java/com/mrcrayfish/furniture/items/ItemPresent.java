package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.blocks.BlockPresent;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
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

public class ItemPresent extends ItemBlock implements IItemInventory, SubItems, IAuthored
{
    public ItemPresent(Block block)
    {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public Item getSignedItem()
    {
        return Item.getItemFromBlock(FurnitureBlocks.PRESENT);
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
        if (facing != EnumFacing.UP || !worldIn.isSideSolid(pos, facing) || !worldIn.mayPlace(FurnitureBlocks.PRESENT, pos.offset(facing), false, facing, player)) {
            return EnumActionResult.PASS;
        }
        if (!stack.hasTagCompound()) {
            if(worldIn.isRemote) {
                player.sendMessage(new TextComponentTranslation("cfm.message.present_sign"));
            }
            return EnumActionResult.PASS;
        }
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        NBTTagString nbttagstring = (NBTTagString) nbttagcompound.getTag("Author");

        if (nbttagstring == null) {
            if(worldIn.isRemote) {
                player.sendMessage(new TextComponentTranslation("cfm.message.present_sign"));
            }
            return EnumActionResult.PASS;
        }
        NBTTagList itemList = (NBTTagList) NBTHelper.getCompoundTag(stack, "Present").getTag("Items");
        if (itemList.tagCount() <= 0) {
            if(worldIn.isRemote) {
                player.sendMessage(new TextComponentTranslation("cfm.message.present_place"));
            }
            return EnumActionResult.PASS;
        }
        IBlockState state = FurnitureBlocks.PRESENT.getDefaultState().withProperty(BlockPresent.COLOUR, EnumDyeColor.byMetadata(stack.getItemDamage()));

        worldIn.setBlockState(pos.up(), state, 2);
        worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (state.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, state.getBlock().getSoundType().getPitch() * 0.8F, false);


        TileEntityPresent tep = new TileEntityPresent();
        tep.setOwner(player.getName());

        for(int i = 0; i < itemList.tagCount(); i++) {
            tep.setInventorySlotContents(i, new ItemStack(itemList.getCompoundTagAt(i)));
        }

        worldIn.setTileEntity(pos.up(), tep);

        stack.shrink(1);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND)
        {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if(tagCompound != null)
            {
                String author = tagCompound.getString("Author");
                if(author.isEmpty())
                {
                    playerIn.openGui(MrCrayfishFurnitureMod.instance, 9, worldIn, 0, 0, 0);
                }
                else
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.present_wrap"));
                }
            }
            else
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 9, worldIn, 0, 0, 0);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
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
