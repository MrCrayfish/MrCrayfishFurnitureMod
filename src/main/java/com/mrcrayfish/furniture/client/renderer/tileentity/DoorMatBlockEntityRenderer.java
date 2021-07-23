package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrcrayfish.furniture.block.DoorMatBlock;
import com.mrcrayfish.furniture.tileentity.DoorMatBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class DoorMatBlockEntityRenderer implements BlockEntityRenderer<DoorMatBlockEntity>
{
    private final Font font;

    public DoorMatBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.font = context.getFont();
    }

    @Override
    public void render(DoorMatBlockEntity tileEntity, float partialTicks, PoseStack poseStack, MultiBufferSource source, int light, int overlay)
    {
        if(tileEntity.getMessage() != null)
        {
            BlockState state = tileEntity.getBlockState();
            if(state.getBlock() instanceof DoorMatBlock)
            {
                poseStack.pushPose(); //Push

                poseStack.translate(0.5, 0.0626, 0.5);

                int rotation = state.getValue(DoorMatBlock.DIRECTION).get2DDataValue();
                poseStack.mulPose(Vector3f.YP.rotationDegrees(-90F * rotation + 180F));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(-90F));

                poseStack.scale(1.0F, -1.0F, -1.0F);
                poseStack.scale(0.0125F, 0.0125F, 0.0125F);

                List<FormattedCharSequence> lines = this.font.split(FormattedText.of(tileEntity.getMessage()), 60);
                poseStack.translate(0.0, -(lines.size() * this.font.lineHeight - 1.0) / 2.0, 0);

                for(int j = 0; j < lines.size(); j++)
                {
                    poseStack.pushPose();
                    poseStack.translate(-this.font.width(lines.get(j)) / 2.0, (j * this.font.lineHeight), 0.0);
                    this.font.drawInBatch(lines.get(j), 0, 0, overlay, false, poseStack.last().pose(), source, false, 0, light);
                    poseStack.popPose();
                }
                poseStack.popPose(); //Pop
            }
        }
    }
}
