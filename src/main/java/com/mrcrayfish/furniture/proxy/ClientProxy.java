package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.client.renderer.SeatRenderer;
import com.mrcrayfish.furniture.entity.SeatEntity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Author: MrCrayfish
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void setup()
    {
        RenderingRegistry.registerEntityRenderingHandler(SeatEntity.class, SeatRenderer::new);
    }
}
