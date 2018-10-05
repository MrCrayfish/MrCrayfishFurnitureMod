package com.mrcrayfish.furniture.client;

import at.dhyan.open_imaging.GifDecoder;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Author: MrCrayfish
 */
public class AnimatedTexture extends Texture
{
    private List<ByteBuffer> framesTextureData;
    private int frameCounter;

    public AnimatedTexture(File file)
    {
        super(file);
    }

    @Override
    public void load(File file)
    {
        framesTextureData = Lists.newArrayList();
        THREAD_SERVICE.submit(() ->
        {
            try
            {
                FileInputStream inputStream = new FileInputStream(file);
                GifDecoder.GifImage decoder = GifDecoder.read(inputStream);
                this.width = decoder.getWidth();
                this.height = decoder.getHeight();

                int frameCount = decoder.getFrameCount();
                for(int i = 0; i < frameCount; i++)
                {
                    BufferedImage image = parseFrame(decoder.getFrame(i));
                    int[] imageData = new int[this.width * this.height];
                    image.getRGB(0, 0, this.width, this.height, imageData, 0, this.width);
                    framesTextureData.add(createBuffer(imageData));
                }
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
        if(framesTextureData.size() > 0)
        {
            if(++frameCounter >= framesTextureData.size())
            {
                frameCounter = 0;
            }
            ByteBuffer buffer = framesTextureData.get(frameCounter);
            GlStateManager.bindTexture(getTextureId());
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        }
        if(counter++ >= 600)
        {
            delete = true;
            GlStateManager.deleteTexture(getTextureId());
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
