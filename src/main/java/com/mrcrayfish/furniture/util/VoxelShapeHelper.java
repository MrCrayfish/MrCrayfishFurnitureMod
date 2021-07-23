package com.mrcrayfish.furniture.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

public class VoxelShapeHelper
{
    public static VoxelShape combineAll(Collection<VoxelShape> shapes)
    {
        VoxelShape result = Shapes.empty();
        for(VoxelShape shape : shapes)
        {
            result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    public static VoxelShape setMaxHeight(VoxelShape source, double height)
    {
        AtomicReference<VoxelShape> result = new AtomicReference<>(Shapes.empty());
        source.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
        {
            VoxelShape shape = Shapes.box(minX, minY, minZ, maxX, height, maxZ);
            result.set(Shapes.joinUnoptimized(result.get(), shape, BooleanOp.OR));
        });
        return result.get().optimize();
    }

    public static VoxelShape limitHorizontal(VoxelShape source)
    {
        AtomicReference<VoxelShape> result = new AtomicReference<>(Shapes.empty());
        source.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
        {
            VoxelShape shape = Shapes.box(limit(minX), minY, limit(minZ), limit(maxX), maxY, limit(maxZ));
            result.set(Shapes.joinUnoptimized(result.get(), shape, BooleanOp.OR));
        });
        return result.get().optimize();
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
        double[] adjustedValues = adjustValues(direction, source.min(Direction.Axis.X), source.min(Direction.Axis.Z), source.max(Direction.Axis.X), source.max(Direction.Axis.Z));
        return Shapes.box(adjustedValues[0], source.min(Direction.Axis.Y), adjustedValues[1], adjustedValues[2], source.max(Direction.Axis.Y), adjustedValues[3]);
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
