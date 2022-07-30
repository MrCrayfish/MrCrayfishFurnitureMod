package com.mrcrayfish.furniture.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A wrapper model to create the same behaviour of leaves when switching from fast to fancy graphics
 */
public class FancyModel extends BakedModelWrapper<BakedModel>
{
    public FancyModel(BakedModel model)
    {
        super(model);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType)
    {
        return super.getQuads(state, side, rand, extraData, null);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data)
    {
        return ChunkRenderTypeSet.of(Minecraft.useFancyGraphics() ? RenderType.cutoutMipped() : RenderType.solid());
    }
}
