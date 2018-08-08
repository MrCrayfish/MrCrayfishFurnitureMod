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

/**
 * Author: MrCrayfish
 */
public class AnimatedTexture
{
    private List<ByteBuffer> framesTextureData = Lists.newArrayList();
    private int frameCounter;
    private int textureId = -1;
    private int width, height;

    public AnimatedTexture(File file)
    {
        this.load(file);
    }

    public void load(File file)
    {
        framesTextureData.clear();
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
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        if(framesTextureData.size() > 0)
        {
            if(++frameCounter >= framesTextureData.size())
            {
                frameCounter = 0;
            }
            ByteBuffer buffer = framesTextureData.get(frameCounter);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureId());
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
        }
    }

    private ByteBuffer createBuffer(int[] data)
    {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 3);
        for (int y = height - 1; y >= 0; y--)
        {
            for (int x = width - 1; x >= 0; x--)
            {
                int color = data[x + y * width];
                buffer.put((byte) ((color >> 16) & 0xff));
                buffer.put((byte) ((color >> 8) & 0xff));
                buffer.put((byte) (color & 0xff));
            }
        }
        buffer.flip();
        return buffer;
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

    public void bind()
    {
        if(textureId != -1)
        {
            GlStateManager.bindTexture(textureId);
        }
    }

    public int getTextureId()
    {
        if(textureId == -1 || !GL11.glIsTexture(textureId))
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

    public static void main(String[] args)
    {
        AnimatedTexture animatedTexture = new AnimatedTexture(new File("C:\\Users\\Casey\\Desktop\\alien.gif"));
    }
}
