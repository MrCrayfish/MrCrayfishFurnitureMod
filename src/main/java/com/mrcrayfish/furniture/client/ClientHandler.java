package com.mrcrayfish.furniture.client;

import com.mrcrayfish.furniture.client.event.CreativeScreenEvents;
import com.mrcrayfish.furniture.client.gui.screen.DoorMatScreen;
import com.mrcrayfish.furniture.client.gui.screen.inventory.CrateScreen;
import com.mrcrayfish.furniture.client.gui.screen.inventory.FreezerScreen;
import com.mrcrayfish.furniture.client.gui.screen.inventory.MailBoxScreen;
import com.mrcrayfish.furniture.client.gui.screen.inventory.PostBoxScreen;
import com.mrcrayfish.furniture.client.renderer.SeatRenderer;
import com.mrcrayfish.furniture.client.renderer.tileentity.DoorMatTileEntityRenderer;
import com.mrcrayfish.furniture.client.renderer.tileentity.GrillTileEntityRenderer;
import com.mrcrayfish.furniture.client.renderer.tileentity.KitchenSinkTileEntityRenderer;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.core.ModEntities;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import com.mrcrayfish.furniture.tileentity.TrampolineTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Predicate;

/**
 * Author: MrCrayfish
 */
public class ClientHandler
{
    public static void setup()
    {
        registerTileEntityRenderers();
        registerEntityRenderers();
        registerScreenFactories();
        registerLayers();
        registerColors();

        if(!ModList.get().isLoaded("filters"))
        {
            MinecraftForge.EVENT_BUS.register(new CreativeScreenEvents());
        }
        else
        {
            //Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "general"), new ItemStack(ModBlocks.CHAIR_OAK));
            //Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "storage"), new ItemStack(ModBlocks.CABINET_OAK));
            //Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "bedroom"), new ItemStack(ModBlocks.DESK_OAK));
            //Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "outdoors"), new ItemStack(ModBlocks.MAIL_BOX_OAK));
            //Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "kitchen"), new ItemStack(ModBlocks.KITCHEN_COUNTER_CYAN));
            //Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "items"), new ItemStack(ModItems.SPATULA));
        }
    }

    private static void registerTileEntityRenderers()
    {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.GRILL.get(), GrillTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.DOOR_MAT.get(), DoorMatTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.KITCHEN_SINK.get(), KitchenSinkTileEntityRenderer::new);
    }

    private static void registerEntityRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SEAT.get(), SeatRenderer::new);

    }

    private static void registerScreenFactories()
    {
        ScreenManager.registerFactory(ModContainers.CRATE.get(), CrateScreen::new);
        ScreenManager.registerFactory(ModContainers.POST_BOX.get(), PostBoxScreen::new);
        ScreenManager.registerFactory(ModContainers.MAIL_BOX.get(), MailBoxScreen::new);
        ScreenManager.registerFactory(ModContainers.FREEZER.get(), FreezerScreen::new);
    }

    private static void registerLayers()
    {
        Predicate<RenderType> leavesPredicate = renderType -> useFancyGraphics() ? renderType == RenderType.getCutoutMipped() : renderType == RenderType.getSolid();
        RenderTypeLookup.setRenderLayer(ModBlocks.HEDGE_OAK.get(), leavesPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.HEDGE_SPRUCE.get(), leavesPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.HEDGE_BIRCH.get(), leavesPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.HEDGE_JUNGLE.get(), leavesPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.HEDGE_ACACIA.get(), leavesPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.HEDGE_DARK_OAK.get(), leavesPredicate);

        Predicate<RenderType> cutoutPredicate = renderType -> renderType == RenderType.getCutout();
        RenderTypeLookup.setRenderLayer(ModBlocks.TRAMPOLINE.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_WHITE.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_ORANGE.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_MAGENTA.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_LIGHT_BLUE.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_YELLOW.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_LIME.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_PINK.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_GRAY.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_LIGHT_GRAY.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_CYAN.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_PURPLE.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_BLUE.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_BROWN.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_GREEN.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_RED.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRILL_BLACK.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.POST_BOX.get(), cutoutPredicate);

        RenderTypeLookup.setRenderLayer(ModBlocks.MICROWAVE_DARK.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.MICROWAVE_LIGHT.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.OVEN_DARK.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.OVEN_LIGHT.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.SHOWER_DARK.get(), cutoutPredicate);
        RenderTypeLookup.setRenderLayer(ModBlocks.SHOWER_LIGHT.get(), cutoutPredicate);
    }

    private static void registerColors()
    {
        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> i == 1 ? 0xFFCCCCCC : 0xFFFFFFFF,
                ModBlocks.PICKET_FENCE_WHITE.get(),
                ModBlocks.PICKET_FENCE_ORANGE.get(),
                ModBlocks.PICKET_FENCE_MAGENTA.get(),
                ModBlocks.PICKET_FENCE_LIGHT_BLUE.get(),
                ModBlocks.PICKET_FENCE_YELLOW.get(),
                ModBlocks.PICKET_FENCE_LIME.get(),
                ModBlocks.PICKET_FENCE_PINK.get(),
                ModBlocks.PICKET_FENCE_GRAY.get(),
                ModBlocks.PICKET_FENCE_LIGHT_GRAY.get(),
                ModBlocks.PICKET_FENCE_CYAN.get(),
                ModBlocks.PICKET_FENCE_PURPLE.get(),
                ModBlocks.PICKET_FENCE_BLUE.get(),
                ModBlocks.PICKET_FENCE_BROWN.get(),
                ModBlocks.PICKET_FENCE_GREEN.get(),
                ModBlocks.PICKET_FENCE_RED.get(),
                ModBlocks.PICKET_FENCE_BLACK.get(),
                ModBlocks.PICKET_GATE_WHITE.get(),
                ModBlocks.PICKET_GATE_ORANGE.get(),
                ModBlocks.PICKET_GATE_MAGENTA.get(),
                ModBlocks.PICKET_GATE_LIGHT_BLUE.get(),
                ModBlocks.PICKET_GATE_YELLOW.get(),
                ModBlocks.PICKET_GATE_LIME.get(),
                ModBlocks.PICKET_GATE_PINK.get(),
                ModBlocks.PICKET_GATE_GRAY.get(),
                ModBlocks.PICKET_GATE_LIGHT_GRAY.get(),
                ModBlocks.PICKET_GATE_CYAN.get(),
                ModBlocks.PICKET_GATE_PURPLE.get(),
                ModBlocks.PICKET_GATE_BLUE.get(),
                ModBlocks.PICKET_GATE_BROWN.get(),
                ModBlocks.PICKET_GATE_GREEN.get(),
                ModBlocks.PICKET_GATE_RED.get(),
                ModBlocks.PICKET_GATE_BLACK.get(),
                ModBlocks.POST_BOX.get()
        );

        Minecraft.getInstance().getItemColors().register((stack, i) -> i == 1 ? 0xCCCCCC : 0xFFFFFFFF,
                ModBlocks.PICKET_FENCE_WHITE.get(),
                ModBlocks.PICKET_FENCE_ORANGE.get(),
                ModBlocks.PICKET_FENCE_MAGENTA.get(),
                ModBlocks.PICKET_FENCE_LIGHT_BLUE.get(),
                ModBlocks.PICKET_FENCE_YELLOW.get(),
                ModBlocks.PICKET_FENCE_LIME.get(),
                ModBlocks.PICKET_FENCE_PINK.get(),
                ModBlocks.PICKET_FENCE_GRAY.get(),
                ModBlocks.PICKET_FENCE_LIGHT_GRAY.get(),
                ModBlocks.PICKET_FENCE_CYAN.get(),
                ModBlocks.PICKET_FENCE_PURPLE.get(),
                ModBlocks.PICKET_FENCE_BLUE.get(),
                ModBlocks.PICKET_FENCE_BROWN.get(),
                ModBlocks.PICKET_FENCE_GREEN.get(),
                ModBlocks.PICKET_FENCE_RED.get(),
                ModBlocks.PICKET_FENCE_BLACK.get(),
                ModBlocks.PICKET_GATE_WHITE.get(),
                ModBlocks.PICKET_GATE_ORANGE.get(),
                ModBlocks.PICKET_GATE_MAGENTA.get(),
                ModBlocks.PICKET_GATE_LIGHT_BLUE.get(),
                ModBlocks.PICKET_GATE_YELLOW.get(),
                ModBlocks.PICKET_GATE_LIME.get(),
                ModBlocks.PICKET_GATE_PINK.get(),
                ModBlocks.PICKET_GATE_GRAY.get(),
                ModBlocks.PICKET_GATE_LIGHT_GRAY.get(),
                ModBlocks.PICKET_GATE_CYAN.get(),
                ModBlocks.PICKET_GATE_PURPLE.get(),
                ModBlocks.PICKET_GATE_BLUE.get(),
                ModBlocks.PICKET_GATE_BROWN.get(),
                ModBlocks.PICKET_GATE_GREEN.get(),
                ModBlocks.PICKET_GATE_RED.get(),
                ModBlocks.PICKET_GATE_BLACK.get(),
                ModBlocks.POST_BOX.get()
        );

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> i == 1 ? 0xBBBBBB : 0xFFFFFFFF,
                ModBlocks.CRATE_STRIPPED_OAK.get(),
                ModBlocks.CRATE_STRIPPED_SPRUCE.get(),
                ModBlocks.CRATE_STRIPPED_BIRCH.get(),
                ModBlocks.CRATE_STRIPPED_JUNGLE.get(),
                ModBlocks.CRATE_STRIPPED_ACACIA.get(),
                ModBlocks.CRATE_STRIPPED_DARK_OAK.get(),
                ModBlocks.CRATE_STRIPPED_CRIMSON.get(),
                ModBlocks.CRATE_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_WARPED.get()
        );

        Minecraft.getInstance().getItemColors().register((stack, i) -> i == 1 ? 0xBBBBBB : 0xFFFFFFFF,
                ModBlocks.CRATE_STRIPPED_OAK.get(),
                ModBlocks.CRATE_STRIPPED_SPRUCE.get(),
                ModBlocks.CRATE_STRIPPED_BIRCH.get(),
                ModBlocks.CRATE_STRIPPED_JUNGLE.get(),
                ModBlocks.CRATE_STRIPPED_ACACIA.get(),
                ModBlocks.CRATE_STRIPPED_DARK_OAK.get(),
                ModBlocks.CRATE_STRIPPED_CRIMSON.get(),
                ModBlocks.CRATE_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_COUNTER_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_DRAWER_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_WARPED.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_CRIMSON.get(),
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_WARPED.get()
        );

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> i == 1 ? 0x999999 : 0xFFFFFFFF,
                ModBlocks.PARK_BENCH_STRIPPED_OAK.get(),
                ModBlocks.PARK_BENCH_STRIPPED_SPRUCE.get(),
                ModBlocks.PARK_BENCH_STRIPPED_BIRCH.get(),
                ModBlocks.PARK_BENCH_STRIPPED_JUNGLE.get(),
                ModBlocks.PARK_BENCH_STRIPPED_ACACIA.get(),
                ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK.get(),
                ModBlocks.PARK_BENCH_STRIPPED_CRIMSON.get(),
                ModBlocks.PARK_BENCH_STRIPPED_WARPED.get()
        );

        Minecraft.getInstance().getItemColors().register((stack, i) -> i == 1 ? 0x999999 : 0xFFFFFFFF,
                ModBlocks.PARK_BENCH_STRIPPED_OAK.get(),
                ModBlocks.PARK_BENCH_STRIPPED_SPRUCE.get(),
                ModBlocks.PARK_BENCH_STRIPPED_BIRCH.get(),
                ModBlocks.PARK_BENCH_STRIPPED_JUNGLE.get(),
                ModBlocks.PARK_BENCH_STRIPPED_ACACIA.get(),
                ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK.get(),
                ModBlocks.PARK_BENCH_STRIPPED_CRIMSON.get(),
                ModBlocks.PARK_BENCH_STRIPPED_WARPED.get()
        );

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> i == 1 ? 0xCCCCCC : 0xFFFFFFFF,
                ModBlocks.FRIDGE_LIGHT.get(),
                ModBlocks.FREEZER_LIGHT.get(),
                ModBlocks.FRIDGE_DARK.get(),
                ModBlocks.FREEZER_DARK.get()
        );

        Minecraft.getInstance().getItemColors().register((stack, i) -> i == 1 ? 0xCCCCCC : 0xFFFFFFFF,
                ModBlocks.FRIDGE_LIGHT.get(),
                ModBlocks.FREEZER_LIGHT.get(),
                ModBlocks.FRIDGE_DARK.get(),
                ModBlocks.FREEZER_DARK.get()
        );

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> FoliageColors.getSpruce(),
                ModBlocks.HEDGE_SPRUCE.get());

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> FoliageColors.getBirch(),
                ModBlocks.HEDGE_BIRCH.get());

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault(),
                ModBlocks.HEDGE_OAK.get(),
                ModBlocks.HEDGE_JUNGLE.get(),
                ModBlocks.HEDGE_ACACIA.get(),
                ModBlocks.HEDGE_DARK_OAK.get());

        Minecraft.getInstance().getItemColors().register((stack, i) -> {
            BlockState state = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return Minecraft.getInstance().getBlockColors().getColor(state, null, null, i);
        }, ModBlocks.HEDGE_OAK.get(), ModBlocks.HEDGE_SPRUCE.get(), ModBlocks.HEDGE_BIRCH.get(), ModBlocks.HEDGE_JUNGLE.get(), ModBlocks.HEDGE_ACACIA.get(), ModBlocks.HEDGE_DARK_OAK.get());

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> {
            if(reader != null && pos != null)
            {
                TileEntity tileEntity = reader.getTileEntity(pos);
                if(tileEntity instanceof TrampolineTileEntity)
                {
                    return ((TrampolineTileEntity) tileEntity).getColour().getColorValue();
                }
            }
            return 0xFFFFFFFF;
        }, ModBlocks.TRAMPOLINE.get());

        Minecraft.getInstance().getItemColors().register((stack, i) -> {
            CompoundNBT tag = stack.getTag();
            if(tag != null)
            {
                CompoundNBT blockEntityTag = tag.getCompound("BlockEntityTag");
                if(blockEntityTag.contains("Color", Constants.NBT.TAG_INT))
                {
                    return DyeColor.byId(blockEntityTag.getInt("Color")).getColorValue();
                }
            }
            return 0xFFFFFFFF;
        }, ModBlocks.TRAMPOLINE.get());
    }

    private static boolean useFancyGraphics()
    {
        Minecraft mc = Minecraft.getInstance();
        return mc.gameSettings.graphicFanciness.func_238162_a_() > 0;
    }

    public static void showDoorMatScreen(World world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof DoorMatTileEntity)
        {
            Minecraft.getInstance().displayGuiScreen(new DoorMatScreen((DoorMatTileEntity) tileEntity));
        }
    }
}
