package com.mrcrayfish.furniture.util;

public class ParticleHelper
{
    public static float[] fixRotation(int metadata, float var1, float var2)
    {
        switch(metadata)
        {
            case 1:
                var1 = 1.0F - var1;
                var2 = 1.0F - var2;
                break;
            case 2:
                float temp_1 = var1;
                var1 = var2;
                var2 = 1.0F - temp_1;
                break;
            case 0:
                float temp_2 = var2;
                var2 = var1;
                var1 = 1.0F - temp_2;
                break;
        }
        return new float[]{var1, var2};
    }
}
