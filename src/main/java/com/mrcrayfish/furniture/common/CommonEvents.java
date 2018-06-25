package com.mrcrayfish.furniture.common;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class CommonEvents
{
    public static final DataParameter<Boolean> PUSHING_CART = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.BOOLEAN);
    public static final DataParameter<NBTTagCompound> HELD_VEHICLE = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.COMPOUND_TAG);

    private static final List<String> IGNORE_ITEMS;

    static
    {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("cup");
        builder.add("grand_chair_top");
        builder.add("fridge");
        builder.add("divingboard_plank");
        IGNORE_ITEMS = builder.build();
    }

    @SubscribeEvent
    public void onMissingItem(RegistryEvent.MissingMappings<Item> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<Item> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_ITEMS.contains(missing.key.getResourcePath()))
            {
                missing.ignore();
            }
        }
    }
}