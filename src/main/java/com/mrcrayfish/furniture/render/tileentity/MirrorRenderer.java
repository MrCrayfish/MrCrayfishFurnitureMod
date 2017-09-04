package com.mrcrayfish.furniture.render.tileentity;

import com.mrcrayfish.furniture.MirrorRenderGlobal;
import com.mrcrayfish.furniture.entity.EntityMirror;
import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import com.mrcrayfish.furniture.proxy.ClientProxy;
import com.mrcrayfish.furniture.tileentity.TileEntityMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MirrorRenderer extends TileEntitySpecialRenderer<TileEntityMirror>
{
	private static Minecraft mc = Minecraft.getMinecraft();
	public static RenderGlobal mirrorGlobalRenderer = new MirrorRenderGlobal(mc);
	private int quality = ConfigurationHandler.mirrorQuality;
	private long renderEndNanoTime;
	
	private static Map<EntityMirror, Integer> registerMirrors = new ConcurrentHashMap<EntityMirror, Integer>();
	private static List<Integer> pendingRemoval = Collections.synchronizedList(new ArrayList<Integer>());

	public static void removeRegisteredMirror(Entity entity)
	{
		pendingRemoval.add(registerMirrors.get(entity));
		registerMirrors.remove(entity);
	}

	public static void clearRegisteredMirrors()
	{
		registerMirrors.clear();
	}

	@Override
	public void render(TileEntityMirror mirror, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if(!ConfigurationHandler.mirrorEnabled)
			return;
		
		if(TileEntityRendererDispatcher.instance.entity instanceof EntityMirror)
			return;
		
		if (!registerMirrors.containsKey(mirror.getMirror()))
		{
			int newTextureId = GL11.glGenTextures();
			GlStateManager.bindTexture(newTextureId);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, quality, quality, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, BufferUtils.createByteBuffer(3 * quality * quality));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			registerMirrors.put(mirror.getMirror(), newTextureId);
			return;
		}
		
		mirror.getMirror().rendering = true;

		EnumFacing facing = EnumFacing.getHorizontal(mirror.getBlockMetadata());
		GlStateManager.pushMatrix();
		{
			GlStateManager.enableBlend();
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);

			GlStateManager.disableLighting();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.bindTexture(registerMirrors.get(mirror.getMirror()).intValue());

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			GlStateManager.translate(x + 0.5, y, z + 0.5);
			GlStateManager.rotate(-90F * facing.getHorizontalIndex() + 180F, 0, 1, 0);
			GlStateManager.translate(-0.5F, 0, -0.43F);

			GlStateManager.enableRescaleNormal();

			// Render
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glTexCoord2d(1, 0);
				GL11.glVertex3d(0.0625, 0.0625, 0);
				GL11.glTexCoord2d(0, 0);
				GL11.glVertex3d(0.9375, 0.0625, 0);
				GL11.glTexCoord2d(0, 1);
				GL11.glVertex3d(0.9375, 0.9375, 0);
				GL11.glTexCoord2d(1, 1);
				GL11.glVertex3d(0.0625, 0.9375, 0);
			}
			GL11.glEnd();

			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
		}
		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public void onTick(TickEvent.RenderTickEvent event)
	{		
		if (event.phase.equals(TickEvent.Phase.END))
			return;
		
		if(!ConfigurationHandler.mirrorEnabled)
			return;

		if (!pendingRemoval.isEmpty())
		{
			for (Integer integer : pendingRemoval)
			{
				GlStateManager.deleteTexture(integer.intValue());
			}
			pendingRemoval.clear();
		}

		if (mc.inGameHasFocus)
		{
			for (EntityMirror entity : registerMirrors.keySet())
			{
				if (entity == null)
				{
					registerMirrors.remove(entity);
					continue;
				}
				
				if(!entity.rendering)
					continue;
				
				if(!mc.player.canEntityBeSeen(entity))
					continue;
				
				if (entity.getDistanceToEntity(mc.player) < 5)
				{
					GameSettings settings = mc.gameSettings;
					RenderGlobal renderBackup = mc.renderGlobal;
					Entity entityBackup = mc.getRenderViewEntity();
					int thirdPersonBackup = settings.thirdPersonView;
					boolean hideGuiBackup = settings.hideGUI;
					int mipmapBackup = settings.mipmapLevels;
					float fovBackup = settings.fovSetting;
					int widthBackup = mc.displayWidth;
					int heightBackup = mc.displayHeight;

					mc.renderGlobal = mirrorGlobalRenderer;
					mc.setRenderViewEntity(entity);
					settings.fovSetting = ConfigurationHandler.mirrorFov;
					settings.thirdPersonView = 0;
					settings.hideGUI = true;
					settings.mipmapLevels = 3;
					mc.displayWidth = quality;
					mc.displayHeight = quality;

					ClientProxy.rendering = true;
					ClientProxy.renderEntity = mc.player;

					int fps = Math.max(30, settings.limitFramerate);
					EntityRenderer entityRenderer = mc.entityRenderer;
					entityRenderer.renderWorld(event.renderTickTime, renderEndNanoTime + (1000000000 / fps));

					GlStateManager.bindTexture(registerMirrors.get(entity).intValue());
					GL11.glCopyTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 0, 0, quality, quality, 0);
					
					renderEndNanoTime = System.nanoTime();

					ClientProxy.renderEntity = null;
					ClientProxy.rendering = false;

					mc.renderGlobal = renderBackup;
					mc.setRenderViewEntity(entityBackup);
					settings.fovSetting = fovBackup;
					settings.thirdPersonView = thirdPersonBackup;
					settings.hideGUI = hideGuiBackup;
					settings.mipmapLevels = mipmapBackup;
					mc.displayWidth = widthBackup;
					mc.displayHeight = heightBackup;
				}
				
				entity.rendering = false;
			}
		}
	}
}
