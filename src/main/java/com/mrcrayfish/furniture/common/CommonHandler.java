package com.mrcrayfish.furniture.common;

import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.item.crafting.ModRecipeTypes;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.tileentity.GrillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Author: MrCrayfish
 */
public class CommonHandler
{
    public static void setup()
    {
        ModRecipeTypes.init();
        PacketHandler.init();
        DispenserBlock.registerBehavior(ModItems.SPATULA::get, (source, stack) ->
        {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos pos = source.getPos().relative(direction).below();
            BlockEntity tileEntity = source.getLevel().getBlockEntity(pos);
            if(tileEntity instanceof GrillBlockEntity)
            {
                ((GrillBlockEntity) tileEntity).flipItems();
            }
            return stack;
        });
    }
}
