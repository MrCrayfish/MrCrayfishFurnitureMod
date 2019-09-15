package com.mrcrayfish.furniture.common.mail;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Author: MrCrayfish
 */
public class Mail implements INBTSerializable<CompoundNBT>
{
    private String note;
    private ItemStack stack;
    private String sender;

    public Mail(String note, ItemStack stack, String sender)
    {
        this.note = note;
        this.stack = stack;
        this.sender = sender;
    }

    public Mail(CompoundNBT compound)
    {
        this.deserializeNBT(compound);
    }

    public String getNote()
    {
        return this.note;
    }

    public ItemStack getStack()
    {
        return this.stack;
    }

    public String getSender()
    {
        return this.sender;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT compound = new CompoundNBT();
        compound.putString("Note", this.note);
        compound.put("Item", this.stack.write(new CompoundNBT()));
        compound.putString("Sender", this.sender);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound)
    {
        this.note = compound.getString("Note");
        this.stack = ItemStack.read(compound.getCompound("Item"));
        this.sender = compound.getString("Sender");
    }
}
