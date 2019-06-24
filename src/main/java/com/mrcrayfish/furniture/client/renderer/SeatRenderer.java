package com.mrcrayfish.furniture.client.renderer;

import com.mrcrayfish.furniture.entity.SeatEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class SeatRenderer extends EntityRenderer<SeatEntity>
{
    public SeatRenderer(EntityRendererManager manager)
    {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(SeatEntity seatEntity)
    {
        return null;
    }

    @Override
    public void doRender(SeatEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {}
}
