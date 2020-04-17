package com.mrcrayfish.furniture.inventory.container.slot;

import com.mrcrayfish.furniture.event.ItemSolidifyEvent;
import com.mrcrayfish.furniture.tileentity.FreezerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * Author: MrCrayfish
 */
public class FreezerResultSlot extends Slot
{
    private final PlayerEntity player;
    private int removeCount;

    public FreezerResultSlot(PlayerEntity player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount)
    {
        if(this.getHasStack())
        {
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }
        return super.decrStackSize(amount);
    }

    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack)
    {
        this.onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    @Override
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onCrafting(ItemStack stack)
    {
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        if(!this.player.world.isRemote && this.inventory instanceof FreezerTileEntity)
        {
            ((FreezerTileEntity) this.inventory).spawnExperience(this.player);
        }
        this.removeCount = 0;
        MinecraftForge.EVENT_BUS.post(new ItemSolidifyEvent(this.player, stack));
    }
}
