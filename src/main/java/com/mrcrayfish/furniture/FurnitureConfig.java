package com.mrcrayfish.furniture;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Author: MrCrayfish
 */
public class FurnitureConfig
{
    public static class Client
    {
        public final ForgeConfigSpec.BooleanValue drawCollisionShapes;

        Client(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Client configuration settings").push("client");
            this.drawCollisionShapes = builder
                    .comment("Draws the collision shape rather than the selection shape when hovering blocks. Used for debugging collisions.")
                    .translation("cfm.configgui.drawCollisionShapes")
                    .define("drawCollisionShapes", false);
            builder.pop();
        }
    }

    static final ForgeConfigSpec clientSpec;
    public static final FurnitureConfig.Client CLIENT;

    static
    {
        final Pair<FurnitureConfig.Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(FurnitureConfig.Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
}
