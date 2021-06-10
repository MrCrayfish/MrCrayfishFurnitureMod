package com.mrcrayfish.furniture.mixin.client;

import com.mrcrayfish.furniture.block.BlindsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Author: MrCrayfish
 */
@Mixin(Block.class)
public class BlockMixin
{
    @Inject(method = "shouldSideBeRendered", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/shapes/VoxelShapes;compare(Lnet/minecraft/util/math/shapes/VoxelShape;Lnet/minecraft/util/math/shapes/VoxelShape;Lnet/minecraft/util/math/shapes/IBooleanFunction;)Z"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void shouldRenderSideWithBlinds(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir, BlockPos offsetPos, BlockState offsetState)
    {
        if(offsetState.getBlock() instanceof BlindsBlock)
        {
            cir.setReturnValue(true);
        }
    }
}
