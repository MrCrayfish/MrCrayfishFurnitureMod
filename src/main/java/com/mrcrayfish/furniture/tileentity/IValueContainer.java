package com.mrcrayfish.furniture.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public interface IValueContainer
{
    List<Entry> getEntries();

    String updateEntries(Map<String, String> entries, EntityPlayer player);

    default boolean requiresTool()
    {
        return true;
    }

    BlockPos getContainerPos();

    class Entry
    {
        private String id;
        private String name;
        private Type type;
        private String value;

        public Entry(String id, String name, Type type, Object value)
        {
            this.id = id;
            this.name = name;
            this.type = type;
            if(value != null)
            {
                this.value = String.valueOf(value);
            }
        }

        public Entry(String id, String name, String value)
        {
            this.id = id;
            this.name = name;
            this.value = value;
        }

        public Entry(String id, String value)
        {
            this.id = id;
            this.value = value;
        }

        public String getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }

        @Nullable
        public Type getType()
        {
            return type;
        }

        public String getValue()
        {
            return value;
        }

        public enum Type
        {
            TEXT_FIELD, TOGGLE;

            @Nullable
            public static Type get(String name)
            {
                for(Type type : Type.values())
                {
                    if(type.name().equals(name))
                    {
                        return type;
                    }
                }
                return null;
            }
        }
    }
}
