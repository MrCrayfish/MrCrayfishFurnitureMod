package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class CommonProxy
{
    public void onSetupCommon()
    {
        PacketHandler.init();
        DispenserBlock.registerDispenseBehavior(() -> ModItems.SPATULA, (source, stack) ->
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

    public void onSetupClient() {}

    public void updateMailBoxes(CompoundNBT nbt) {}

    public boolean useFancyGraphics()
    {
        return false;
    }

    public void setGrillFlipping(BlockPos pos, int position) {}

    public void showDoorMatScreen(World world, BlockPos pos) {}
}
