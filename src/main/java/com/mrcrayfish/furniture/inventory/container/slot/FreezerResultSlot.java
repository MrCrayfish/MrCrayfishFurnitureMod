package com.mrcrayfish.furniture.inventory.container.slot;

import com.mrcrayfish.furniture.event.ItemSolidifyEvent;
import com.mrcrayfish.furniture.tileentity.FreezerBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * Author: MrCrayfish
 */
public class FreezerResultSlot extends Slot
{
    private final Player player;
    private int removeCount;

    public FreezerResultSlot(Player player, Container container, int index, int x, int y)
    {
        super(container, index, x, y);
        this.player = player;
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return false;
    }

    @Override
    public ItemStack remove(int amount)
    {
        if(this.hasItem())
        {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }
        return super.remove(amount);
    }

    @Override
    public void onTake(Player thePlayer, ItemStack stack)
    {
        this.checkTakeAchievements(stack);
        super.onTake(thePlayer, stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount)
    {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack)
    {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if(!this.player.level.isClientSide && this.container instanceof FreezerBlockEntity)
        {
            ((FreezerBlockEntity) this.container).spawnExperience(this.player);
        }
        this.removeCount = 0;
        MinecraftForge.EVENT_BUS.post(new ItemSolidifyEvent(this.player, stack));
    }
}
