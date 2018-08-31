package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.tileentity.TileEntityWashingMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;

public class WashingMachineRenderer extends TileEntitySpecialRenderer<TileEntityWashingMachine>
{
    private EntityItem armour = new EntityItem(Minecraft.getMinecraft().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityWashingMachine machine, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int metadata = machine.getBlockMetadata();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        GL11.glRotatef(metadata * -90F, 0, 1, 0);
        this.armour.hoverStart = 0.0F;

        for(int i = 0; i < 4; i++)
        {
            if(machine.getStackInSlot(i) != null)
            {
                double zOffset = getOffsetZ(metadata, i);
                armour.setItem(machine.getStackInSlot(i));
                GL11.glRotated(machine.progress * 5, 0, 0, 1);
                Minecraft.getMinecraft().getRenderManager().renderEntity(armour, 0.0, -0.35D, zOffset, 0.0F, 0.0F, false);
            }
        }

        GL11.glPopMatrix();
    }

    public double getOffsetZ(int metadata, int slot)
    {
        switch(slot)
        {
            case 0:
                return -0.2;
            case 1:
                return -0.1;
            case 2:
                return 0.0;
            case 3:
                return 0.1;
        }
        return 0;
    }
}
