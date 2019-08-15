package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.block.UpgradedFenceBlock;
import com.mrcrayfish.furniture.block.UpgradedGateBlock;
import com.mrcrayfish.furniture.client.renderer.SeatRenderer;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.BlockNames;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Author: MrCrayfish
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void onSetupClient()
    {
        super.onSetupClient();
        RenderingRegistry.registerEntityRenderingHandler(SeatEntity.class, SeatRenderer::new);

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> i == 1 ? 0xCCCCCC : 0,
                ModBlocks.PICKET_FENCE_WHITE,
                ModBlocks.PICKET_FENCE_ORANGE,
                ModBlocks.PICKET_FENCE_MAGENTA,
                ModBlocks.PICKET_FENCE_LIGHT_BLUE,
                ModBlocks.PICKET_FENCE_YELLOW,
                ModBlocks.PICKET_FENCE_LIME,
                ModBlocks.PICKET_FENCE_PINK,
                ModBlocks.PICKET_FENCE_GRAY,
                ModBlocks.PICKET_FENCE_LIGHT_GRAY,
                ModBlocks.PICKET_FENCE_CYAN,
                ModBlocks.PICKET_FENCE_PURPLE,
                ModBlocks.PICKET_FENCE_BLUE,
                ModBlocks.PICKET_FENCE_BROWN,
                ModBlocks.PICKET_FENCE_GREEN,
                ModBlocks.PICKET_FENCE_RED,
                ModBlocks.PICKET_FENCE_BLACK,
                ModBlocks.PICKET_GATE_WHITE,
                ModBlocks.PICKET_GATE_ORANGE,
                ModBlocks.PICKET_GATE_MAGENTA,
                ModBlocks.PICKET_GATE_LIGHT_BLUE,
                ModBlocks.PICKET_GATE_YELLOW,
                ModBlocks.PICKET_GATE_LIME,
                ModBlocks.PICKET_GATE_PINK,
                ModBlocks.PICKET_GATE_GRAY,
                ModBlocks.PICKET_GATE_LIGHT_GRAY,
                ModBlocks.PICKET_GATE_CYAN,
                ModBlocks.PICKET_GATE_PURPLE,
                ModBlocks.PICKET_GATE_BLUE,
                ModBlocks.PICKET_GATE_BROWN,
                ModBlocks.PICKET_GATE_GREEN,
                ModBlocks.PICKET_GATE_RED,
                ModBlocks.PICKET_GATE_BLACK
        );

        Minecraft.getInstance().getItemColors().register((stack, i) -> i == 1 ? 0xCCCCCC : 0,
                ModBlocks.PICKET_FENCE_WHITE,
                ModBlocks.PICKET_FENCE_ORANGE,
                ModBlocks.PICKET_FENCE_MAGENTA,
                ModBlocks.PICKET_FENCE_LIGHT_BLUE,
                ModBlocks.PICKET_FENCE_YELLOW,
                ModBlocks.PICKET_FENCE_LIME,
                ModBlocks.PICKET_FENCE_PINK,
                ModBlocks.PICKET_FENCE_GRAY,
                ModBlocks.PICKET_FENCE_LIGHT_GRAY,
                ModBlocks.PICKET_FENCE_CYAN,
                ModBlocks.PICKET_FENCE_PURPLE,
                ModBlocks.PICKET_FENCE_BLUE,
                ModBlocks.PICKET_FENCE_BROWN,
                ModBlocks.PICKET_FENCE_GREEN,
                ModBlocks.PICKET_FENCE_RED,
                ModBlocks.PICKET_FENCE_BLACK,
                ModBlocks.PICKET_GATE_WHITE,
                ModBlocks.PICKET_GATE_ORANGE,
                ModBlocks.PICKET_GATE_MAGENTA,
                ModBlocks.PICKET_GATE_LIGHT_BLUE,
                ModBlocks.PICKET_GATE_YELLOW,
                ModBlocks.PICKET_GATE_LIME,
                ModBlocks.PICKET_GATE_PINK,
                ModBlocks.PICKET_GATE_GRAY,
                ModBlocks.PICKET_GATE_LIGHT_GRAY,
                ModBlocks.PICKET_GATE_CYAN,
                ModBlocks.PICKET_GATE_PURPLE,
                ModBlocks.PICKET_GATE_BLUE,
                ModBlocks.PICKET_GATE_BROWN,
                ModBlocks.PICKET_GATE_GREEN,
                ModBlocks.PICKET_GATE_RED,
                ModBlocks.PICKET_GATE_BLACK
        );
    }
}
