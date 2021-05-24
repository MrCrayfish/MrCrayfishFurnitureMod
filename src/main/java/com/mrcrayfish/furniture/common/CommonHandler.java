package com.mrcrayfish.furniture.common;

import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

/**
 * Author: MrCrayfish
 */
public class CommonHandler
{
    public static void setup()
    {
        PacketHandler.init();
        DispenserBlock.registerDispenseBehavior(ModItems.SPATULA::get, (source, stack) ->
        {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos pos = source.getBlockPos().offset(direction).down();
            TileEntity tileEntity = source.getWorld().getTileEntity(pos);
            if(tileEntity instanceof GrillTileEntity)
            {
                ((GrillTileEntity) tileEntity).flipItems();
            }
            return stack;
        });
    }
}
