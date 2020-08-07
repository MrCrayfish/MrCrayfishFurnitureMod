package com.mrcrayfish.furniture.client;

import net.buttology.lwjgl.dds.DDSFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: MrCrayfish
 */
public class Texture
{
    protected static final int GL_COMPRESSED_RGBA_S3TC_DXT1_EXT = 0x83f1;
    protected static final int GL_COMPRESSED_RGBA_S3TC_DXT3_EXT = 0x83f2;
    protected static final int GL_COMPRESSED_RGBA_S3TC_DXT5_EXT = 0x83f3;
    protected static final ExecutorService THREAD_SERVICE = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setName("Texture File I/O");
        return thread;
    });

    protected int textureId = -1;
    protected int width, height;
    protected boolean flipped = false;
    protected boolean delete = false;

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
                byte[] magic = new byte[4];
                inputStream.read(magic);
                inputStream.close();

                if (DDSFile.isDDSFile(magic))
                {
                    DDSFile image = new DDSFile(file);
                    this.width = image.getWidth();
                    this.height = image.getHeight();
                    this.flipped = true;

                    //Create and upload the buffer
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        textureId = GlStateManager.generateTexture();
                        GlStateManager.bindTexture(textureId);
                        for (int level = 0; level < image.getMipMapCount(); level++)
                            GL13.glCompressedTexImage2D(GL11.GL_TEXTURE_2D, level, image.getFormat(), width >> level, height >> level, 0, image.getBuffer(level));
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, image.getMipMapCount() - 1);
                    });
                }
                else
                {
                    BufferedImage image = ImageIO.read(file);
                    this.width = image.getWidth();
                    this.height = image.getHeight();
                    IntBuffer buffer = createBuffer(image);

                    //Create and upload the buffer
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        textureId = GlStateManager.generateTexture();
                        GlStateManager.bindTexture(textureId);
                        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, buffer);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 2);
                        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
                    });
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    public void update()
    {
        if (delete)
        {
            GlStateManager.deleteTexture(textureId);
            textureId = -1;
        }
    }

    public void delete()
    {
        delete = true;
    }

    static protected IntBuffer createBuffer(BufferedImage image)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(image.getWidth() * image.getHeight());
        for (int y = image.getHeight() - 1; y >= 0; y--)
        {
            for (int x = image.getWidth() - 1; x >= 0; x--)
            {
                buffer.put(image.getRGB(x, y));
            }
        }
        buffer.flip();
        return buffer;
    }

    public boolean bind()
    {
        if(textureId == -1 || !GL11.glIsTexture(textureId))
            return false;

        GlStateManager.bindTexture(getTextureId());
        return true;
    }

    public int getTextureId()
    {
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

    public boolean isFlipped()
    {
        return flipped;
    }

    public boolean isPendingDeletion()
    {
        return delete;
    }
}
