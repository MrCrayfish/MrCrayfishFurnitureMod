package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.entity.SeatEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ToiletBlock extends FurnitureHorizontalWaterloggedBlock {


    public ToiletBlock(Properties properties) {

        super(properties);

    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        return SeatEntity.create(world, pos, 0.4, playerEntity);
    }


}
