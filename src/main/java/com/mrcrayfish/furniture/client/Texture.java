package com.mrcrayfish.furniture.client;

import at.dhyan.open_imaging.GifDecoder;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: MrCrayfish
 */
public class Texture
{
    protected static final ExecutorService THREAD_SERVICE = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setName("Texture File I/O");
        return thread;
    });

    private ByteBuffer buffer;
    private int textureId = -1;
    protected int width, height;
    protected int counter;
    protected boolean delete;

    public Texture(File file)
    {
        this.load(file);
    }

    public void load(File file)
    {
        THREAD_SERVICE.submit(() ->
        {
            try
            {
                //Loads the image
                FileInputStream inputStream = new FileInputStream(file);
                BufferedImage image = ImageIO.read(inputStream);
                this.width = image.getWidth();
                this.height = image.getHeight();

                //Creates the ByteBuffer
                int[] imageData = new int[this.width * this.height];
                image.getRGB(0, 0, this.width, this.height, imageData, 0, this.width);
                buffer = createBuffer(imageData);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    public void update()
    {
        int textureId = getTextureId();
        if(buffer != null)
        {
            GlStateManager.bindTexture(textureId);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        }
        if(counter++ >= 600)
        {
            delete = true;
            if(GL11.glIsTexture(textureId))
            {
                GlStateManager.deleteTexture(textureId);
            }
        }
    }

    protected ByteBuffer createBuffer(int[] data)
    {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int y = height - 1; y >= 0; y--)
        {
            for (int x = width - 1; x >= 0; x--)
            {
                int color = data[x + y * width];
                buffer.put((byte) ((color >> 16) & 0xff));
                buffer.put((byte) ((color >> 8) & 0xff));
                buffer.put((byte) (color & 0xff));
                buffer.put((byte) ((color >> 24) & 0xff));
            }
        }
        buffer.flip();
        return buffer;
    }

    public void bind()
    {
        counter = 0;
        GlStateManager.bindTexture(getTextureId());
    }

    public int getTextureId()
    {
        if(textureId == -1 || (textureId >= 0 && !GL11.glIsTexture(textureId)))
        {
            textureId = GlStateManager.generateTexture();
        }
        return textureId;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isPendingDeletion()
    {
        return delete;
    }
}
