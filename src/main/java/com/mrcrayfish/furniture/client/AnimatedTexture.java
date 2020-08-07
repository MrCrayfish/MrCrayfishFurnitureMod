package com.mrcrayfish.furniture.client;

import com.madgag.gif.fmsware.GifDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;

/**
 * Author: MrCrayfish
 */
public class AnimatedTexture extends Texture
{
    private int[] framesTextureIds;
    private int frameCounter = 0;

    public AnimatedTexture(File file)
    {
        super(file);
    }

    @Override
    public void load(File file)
    {
        framesTextureIds = new int[0];
        THREAD_SERVICE.submit(() ->
        {
            try
            {
                FileInputStream inputStream = new FileInputStream(file);
                GifDecoder decoder = new GifDecoder();
                decoder.read(inputStream);
                this.width = decoder.getFrameSize().width;
                this.height = decoder.getFrameSize().height;

                int frameCount = decoder.getFrameCount();
                int duration = 0;
                IntBuffer[] framesTextureData = new IntBuffer[frameCount];
                for(int i = 0; i < frameCount; i++)
                {
                    BufferedImage image = decoder.getFrame(i);
                    framesTextureData[i] = createBuffer(image);
                    duration += decoder.getDelay(i); // GIF delay resolution is 1/100th
                }

                int[] ids = new int[duration / 10];
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    for(int i = 0, j = 0; i < frameCount; i++)
                    {
                        int id = GlStateManager.generateTexture();
                        GlStateManager.bindTexture(id);
                        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, framesTextureData[i]);

                        int delay = decoder.getDelay(i) / 10;
                        while (delay-- > 0)
                            ids[j++] = id;
                    }
                    framesTextureIds = ids;
                });
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void update()
    {
        if (framesTextureIds.length > 0)
        {
            if (frameCounter >= framesTextureIds.length)
                frameCounter = 0;

            textureId = framesTextureIds[frameCounter];
            frameCounter += 5; // 20 fps is 5/100th of a second
        }

        if (delete)
        {
            int prevId = -1;
            for (int id : framesTextureIds)
            {
                if (id != prevId)
                    GlStateManager.deleteTexture(id);
            }
            framesTextureIds = new int[0];
            textureId = -1;
        }
    }

    /**
     * Filters a BufferedImage and converts all non-opaque pixels to black.
     *
     * @param bufferedImage the image to filter
     * @return a new Bufferred
     */
    private static BufferedImage parseFrame(BufferedImage bufferedImage)
    {
        ImageFilter filter = new RGBImageFilter()
        {
            @Override
            public int filterRGB(int x, int y, int rgb)
            {
                if(((rgb >> 24) & 0xff) != 255)
                {
                    return 0;
                }
                return rgb;
            }
        };

        ImageProducer producer = new FilteredImageSource(bufferedImage.getSource(), filter);
        Image image = Toolkit.getDefaultToolkit().createImage(producer);

        //Convert Image back to a BufferedImage
        BufferedImage resultBufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resultBufferedImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return resultBufferedImage;
    }
}
