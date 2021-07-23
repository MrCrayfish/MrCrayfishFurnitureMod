package com.mrcrayfish.furniture.mixin.client;

/**
 * Author: MrCrayfish
 */
//@Mixin(Block.class)
public class BlockMixin
{
    /*@Inject(method = "shouldSideBeRendered", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/shapes/Shapes;compare(Lnet/minecraft/util/math/shapes/VoxelShape;Lnet/minecraft/util/math/shapes/VoxelShape;Lnet/minecraft/util/math/shapes/BooleanOp;)Z"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void shouldRenderSideWithBlinds(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir, BlockPos offsetPos, BlockState offsetState)
    {
        if(offsetState.getBlock() instanceof BlindsBlock)
        {
            cir.setReturnValue(true);
        }
    }*/
}
