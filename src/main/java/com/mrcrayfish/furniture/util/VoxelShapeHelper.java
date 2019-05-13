package com.mrcrayfish.furniture.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Collection;

public class VoxelShapeHelper
{
    public static VoxelShape combineAll(Collection<VoxelShape> shapes)
    {
        VoxelShape result = VoxelShapes.empty();
        for(VoxelShape shape : shapes)
        {
            result = VoxelShapes.combine(result, shape, IBooleanFunction.OR);
        }
        return result.simplify();
    }

    public static VoxelShape[] getRotatedVoxelShapes(VoxelShape source)
    {
        VoxelShape shapeNorth = rotateShape(source, EnumFacing.NORTH);
        VoxelShape shapeEast = rotateShape(source, EnumFacing.EAST);
        VoxelShape shapeSouth = rotateShape(source, EnumFacing.SOUTH);
        VoxelShape shapeWest = rotateShape(source, EnumFacing.WEST);
        return new VoxelShape[] { shapeSouth, shapeWest, shapeNorth, shapeEast };
    }

    private static VoxelShape rotateShape(VoxelShape source, EnumFacing facing)
    {
        double[] adjustedValues = adjustValues(facing, source.getStart(EnumFacing.Axis.X), source.getStart(EnumFacing.Axis.Z), source.getEnd(EnumFacing.Axis.X), source.getEnd(EnumFacing.Axis.Z));
        return VoxelShapes.create(adjustedValues[0], source.getStart(EnumFacing.Axis.Y), adjustedValues[1], adjustedValues[2], source.getEnd(EnumFacing.Axis.Y), adjustedValues[3]);
    }

    private static double[] adjustValues(EnumFacing facing, double var1, double var2, double var3, double var4)
    {
        switch(facing)
        {
            case WEST:
                double var_temp_1 = var1;
                var1 = 1.0F - var3;
                double var_temp_2 = var2;
                var2 = 1.0F - var4;
                var3 = 1.0F - var_temp_1;
                var4 = 1.0F - var_temp_2;
                break;
            case NORTH:
                double var_temp_3 = var1;
                var1 = var2;
                var2 = 1.0F - var3;
                var3 = var4;
                var4 = 1.0F - var_temp_3;
                break;
            case SOUTH:
                double var_temp_4 = var1;
                var1 = 1.0F - var4;
                double var_temp_5 = var2;
                var2 = var_temp_4;
                double var_temp_6 = var3;
                var3 = 1.0F - var_temp_5;
                var4 = var_temp_6;
                break;
            default:
                break;
        }
        return new double[]{var1, var2, var3, var4};
    }
}
