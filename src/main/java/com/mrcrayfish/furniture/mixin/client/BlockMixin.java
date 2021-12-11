package com.mrcrayfish.furniture.mixin.client;

import com.mrcrayfish.furniture.block.BlindsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
    @Inject(method = "shouldRenderFace", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/phys/shapes/Shapes;joinIsNotEmpty(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/BooleanOp;)Z"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void shouldRenderSideWithBlinds(BlockState state, BlockGetter getter, BlockPos pos, Direction direction, BlockPos offsetPos, CallbackInfoReturnable<Boolean> cir, BlockState offsetState)
    {
        if(offsetState.getBlock() instanceof BlindsBlock)
        {
            cir.setReturnValue(true);
        }
    }
}
