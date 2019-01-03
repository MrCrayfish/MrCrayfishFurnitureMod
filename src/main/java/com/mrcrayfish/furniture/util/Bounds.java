package com.mrcrayfish.furniture.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * Author: MrCrayfish
 */
public class Bounds
{
    public double minX, minY, minZ;
    public double maxX, maxY, maxZ;

    public Bounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        this(minX, minY, minZ, maxX, maxY, maxZ, true);
    }

    public Bounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean scale)
    {
        double scaleFactor = scale ? 0.0625 : 1;
        this.minX = minX * scaleFactor;
        this.minY = minY * scaleFactor;
        this.minZ = minZ * scaleFactor;
        this.maxX = maxX * scaleFactor;
        this.maxY = maxY * scaleFactor;
        this.maxZ = maxZ * scaleFactor;
    }

    public Bounds rotateX()
    {
        return rotateX(Rotation.CLOCKWISE_90);
    }

    public Bounds rotateY()
    {
        return rotateY(Rotation.CLOCKWISE_90);
    }

    public Bounds rotateZ()
    {
        return rotateZ(Rotation.CLOCKWISE_90);
    }

    public Bounds rotateX(Rotation rotation)
    {
        double[] fixedBounds = CollisionHelper.fixRotation(rotation, minY, minZ, maxY, maxZ);
        return new Bounds(minX, fixedBounds[0], fixedBounds[1], maxX, fixedBounds[2], fixedBounds[3], false);
    }

    public Bounds rotateY(Rotation rotation)
    {
        double[] fixedBounds = CollisionHelper.fixRotation(rotation, minX, minZ, maxX, maxZ);
        return new Bounds(fixedBounds[0], minY, fixedBounds[1], fixedBounds[2], maxY, fixedBounds[3], false);
    }

    public Bounds rotateZ(Rotation rotation)
    {
        double[] fixedBounds = CollisionHelper.fixRotation(rotation, minX, minY, maxX, maxY);
        return new Bounds(fixedBounds[0], fixedBounds[1], minZ, fixedBounds[2], fixedBounds[3], maxZ, false);
    }

    public AxisAlignedBB toAABB()
    {
        return CollisionHelper.getBlockBounds(Rotation.NONE, this);
    }

    public AxisAlignedBB toAABB(Rotation rotation)
    {
        return CollisionHelper.getBlockBounds(rotation, this);
    }

    public void union(AxisAlignedBB box)
    {
        minX = Math.min(minX, box.minX);
        minY = Math.min(minY, box.minY);
        minZ = Math.min(minZ, box.minZ);
        maxX = Math.max(maxX, box.maxX);
        maxY = Math.max(maxY, box.maxY);
        maxZ = Math.max(maxZ, box.maxZ);
    }

    public AxisAlignedBB getRotation(Rotation rotation)
    {
        return CollisionHelper.getBlockBounds(rotation, this);
    }

    public AxisAlignedBB[] getRotatedBounds()
    {
        return getRotatedBounds(Rotation.NONE);
    }

    public AxisAlignedBB[] getRotatedBounds(Rotation rotation)
    {
        AxisAlignedBB boundsNorth = CollisionHelper.getBlockBounds(rotation, this);
        AxisAlignedBB boundsEast = CollisionHelper.getBlockBounds(rotation = rotation.add(Rotation.CLOCKWISE_90), this);
        AxisAlignedBB boundsSouth = CollisionHelper.getBlockBounds(rotation = rotation.add(Rotation.CLOCKWISE_90), this);
        AxisAlignedBB boundsWest = CollisionHelper.getBlockBounds(rotation.add(Rotation.CLOCKWISE_90), this);
        return new AxisAlignedBB[] { boundsSouth, boundsWest, boundsNorth, boundsEast };
    }
  
    public static List<AxisAlignedBB>[] getRotatedBoundLists(AxisAlignedBB[]... bounds)
    {
        return getRotatedBoundLists(Rotation.NONE, bounds);
    }

    public static List<AxisAlignedBB>[] getRotatedBoundLists(Rotation rotation, AxisAlignedBB[]... bounds)
    {
        ArrayList<AxisAlignedBB[]> boundLists = Lists.newArrayList(bounds);
        return IntStream.range(0, 4)
                .mapToObj(i -> boundLists.stream().map(boxesFacing -> boxesFacing[(i + rotation.ordinal()) % 4]).collect(Collectors.toList()))
                .toArray(ArrayList[]::new);
    }

    public static AxisAlignedBB[] getBoundingBoxes(List<AxisAlignedBB>[]... boxListsArray)
    {
        List<AxisAlignedBB>[] boxLists = combineBoxLists(boxListsArray);
        AxisAlignedBB[] boundingBoxes = new AxisAlignedBB[4];
        for (int i = 0; i < 4; i++)
            boundingBoxes[i] = getBoundingBox(boxLists[i]);

        return boundingBoxes;
    }

    public static List<AxisAlignedBB>[] combineBoxLists(List<AxisAlignedBB>[]... boxListsArray)
    {
        ArrayList<List<AxisAlignedBB>[]> boxListsList = Lists.newArrayList(boxListsArray);
        List<AxisAlignedBB>[] boxLists = new ArrayList[4];
        for (int i = 0; i < 4; i++)
        {
            List<AxisAlignedBB> list = new ArrayList<>();
            for (List<AxisAlignedBB>[] boxList : boxListsList)
                list.addAll(boxList[i]);

            boxLists[i] = list;
        }
        return boxLists;
    }

    public static List<AxisAlignedBB>[] transformBoxListsVertical(double translation, List<AxisAlignedBB>... boxListsArray)
    {
        return Arrays.stream(boxListsArray).map(bounds -> bounds.stream().map(box -> box.offset(0, translation, 0)).collect(Collectors.toList())).toArray(List[]::new);
    }

    public static ArrayList[] transformBoxListsHorizontal(EnumFacing.Axis axis, double translation, List<AxisAlignedBB>... boxLists)
    {
        int rotation = axis == EnumFacing.Axis.X ? 0 : 1;
        return IntStream.range(0, boxLists.length)
                .mapToObj(i -> boxLists[i].stream().map(box -> box.offset(EnumFacing.HORIZONTALS[(i + rotation) % boxLists.length].getFrontOffsetX() * translation * 0.0625, 0,
                        EnumFacing.HORIZONTALS[(i + rotation) % boxLists.length].getFrontOffsetZ() * translation * 0.0625))
                        .collect(Collectors.toList())).toArray(ArrayList[]::new);
    }

    public static AxisAlignedBB getBoundingBox(List<AxisAlignedBB>... boxLists)
    {
        Bounds boundingBox = new Bounds(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
        Arrays.stream(boxLists).forEach(boxList -> boxList.forEach(box -> boundingBox.union(box)));
        return boundingBox.toAABB();
    }

    public static AxisAlignedBB getBoundingBox(AxisAlignedBB... boxLists)
    {
        Bounds boundingBox = new Bounds(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
        Arrays.stream(boxLists).forEach(box -> boundingBox.union(box));
        return boundingBox.toAABB();
    }
}
