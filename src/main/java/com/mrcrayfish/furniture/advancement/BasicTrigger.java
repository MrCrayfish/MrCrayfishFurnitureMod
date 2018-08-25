package com.mrcrayfish.furniture.advancement;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class BasicTrigger implements ICriterionTrigger, IModTrigger
{
    private final ResourceLocation id;
    private final Map<PlayerAdvancements, BasicTrigger.Listeners> listeners = Maps.newHashMap();

    public BasicTrigger(String id)
    {
        super();
        this.id = new ResourceLocation(id);
    }

    public BasicTrigger(ResourceLocation id)
    {
        super();
        this.id = id;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener listener)
    {
        BasicTrigger.Listeners tameanimaltrigger$listeners = this.listeners.computeIfAbsent(playerAdvancementsIn, Listeners::new);
        tameanimaltrigger$listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener listener)
    {
        BasicTrigger.Listeners tameanimaltrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if(tameanimaltrigger$listeners != null)
        {
            tameanimaltrigger$listeners.remove(listener);

            if(tameanimaltrigger$listeners.isEmpty())
            {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn)
    {
        this.listeners.remove(playerAdvancementsIn);
    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     *
     * @param json    the json
     * @param context the context
     * @return the tame bird trigger. instance
     */
    @Override
    public BasicTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        return new BasicTrigger.Instance(this.getId());
    }

    /**
     * Trigger.
     *
     * @param player the player
     */
    public void trigger(EntityPlayerMP player)
    {
        BasicTrigger.Listeners listeners = this.listeners.get(player.getAdvancements());

        if(listeners != null)
        {
            listeners.trigger(player);
        }
    }

    public static class Instance extends AbstractCriterionInstance
    {

        /**
         * Instantiates a new instance.
         */
        public Instance(ResourceLocation parID)
        {
            super(parID);
        }

        /**
         * Test.
         *
         * @return true, if successful
         */
        public boolean test()
        {
            return true;
        }
    }

    static class Listeners
    {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener> listeners = Sets.newHashSet();

        /**
         * Instantiates a new listeners.
         *
         * @param playerAdvancementsIn the player advancements in
         */
        public Listeners(PlayerAdvancements playerAdvancementsIn)
        {
            this.playerAdvancements = playerAdvancementsIn;
        }

        /**
         * Checks if is empty.
         *
         * @return true, if is empty
         */
        public boolean isEmpty()
        {
            return this.listeners.isEmpty();
        }

        /**
         * Adds the.
         *
         * @param listener the listener
         */
        public void add(ICriterionTrigger.Listener listener)
        {
            this.listeners.add(listener);
        }

        /**
         * Removes the.
         *
         * @param listener the listener
         */
        public void remove(ICriterionTrigger.Listener listener)
        {
            this.listeners.remove(listener);
        }

        /**
         * Trigger.
         *
         * @param player the player
         */
        public void trigger(EntityPlayerMP player)
        {
            List<ICriterionTrigger.Listener> list = null;

            for(ICriterionTrigger.Listener listener : this.listeners)
            {
                if(((BasicTrigger.Instance) listener.getCriterionInstance()).test())
                {
                    if(list == null)
                    {
                        list = Lists.newArrayList();
                    }

                    list.add(listener);
                }
            }

            if(list != null)
            {
                for(ICriterionTrigger.Listener listener1 : list)
                {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}