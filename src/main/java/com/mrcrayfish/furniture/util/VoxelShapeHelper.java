package com.mrcrayfish.furniture.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

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

    public static VoxelShape setMaxHeight(VoxelShape source, double height)
    {
        AtomicReference<VoxelShape> result = new AtomicReference<>(VoxelShapes.empty());
        source.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) ->
        {
            VoxelShape shape = VoxelShapes.create(minX, minY, minZ, maxX, height, maxZ);
            result.set(VoxelShapes.combine(result.get(), shape, IBooleanFunction.OR));
        });
        return result.get().simplify();
    }

    public static VoxelShape limitHorizontal(VoxelShape source)
    {
        AtomicReference<VoxelShape> result = new AtomicReference<>(VoxelShapes.empty());
        source.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) ->
        {
            VoxelShape shape = VoxelShapes.create(limit(minX), minY, limit(minZ), limit(maxX), maxY, limit(maxZ));
            result.set(VoxelShapes.combine(result.get(), shape, IBooleanFunction.OR));
        });
        return result.get().simplify();
    }

    public static VoxelShape[] getRotatedShapes(VoxelShape source)
    {
        VoxelShape shapeNorth = rotate(source, Direction.NORTH);
        VoxelShape shapeEast = rotate(source, Direction.EAST);
        VoxelShape shapeSouth = rotate(source, Direction.SOUTH);
        VoxelShape shapeWest = rotate(source, Direction.WEST);
        return new VoxelShape[] { shapeSouth, shapeWest, shapeNorth, shapeEast };
    }

    public static VoxelShape rotate(VoxelShape source, Direction direction)
    {
        double[] adjustedValues = adjustValues(direction, source.getStart(Direction.Axis.X), source.getStart(Direction.Axis.Z), source.getEnd(Direction.Axis.X), source.getEnd(Direction.Axis.Z));
        return VoxelShapes.create(adjustedValues[0], source.getStart(Direction.Axis.Y), adjustedValues[1], adjustedValues[2], source.getEnd(Direction.Axis.Y), adjustedValues[3]);
    }

    private static double[] adjustValues(Direction direction, double var1, double var2, double var3, double var4)
    {
        switch(direction)
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

    private static double limit(double value)
    {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
