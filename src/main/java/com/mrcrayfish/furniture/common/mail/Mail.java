package com.mrcrayfish.furniture.common.mail;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Author: MrCrayfish
 */
public class Mail implements INBTSerializable<CompoundTag>
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

    public Mail(CompoundTag compound)
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
    public CompoundTag serializeNBT()
    {
        CompoundTag compound = new CompoundTag();
        compound.putString("Note", this.note);
        compound.put("Item", this.stack.save(new CompoundTag()));
        compound.putString("Sender", this.sender);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound)
    {
        this.note = compound.getString("Note");
        this.stack = ItemStack.of(compound.getCompound("Item"));
        this.sender = compound.getString("Sender");
    }
}
