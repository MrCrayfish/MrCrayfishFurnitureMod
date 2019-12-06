package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.filters.Filters;
import com.mrcrayfish.furniture.FurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.client.MailBoxEntry;
import com.mrcrayfish.furniture.client.event.CreativeScreenEvents;
import com.mrcrayfish.furniture.client.gui.screen.inventory.CrateScreen;
import com.mrcrayfish.furniture.client.gui.screen.inventory.MailBoxScreen;
import com.mrcrayfish.furniture.client.gui.screen.inventory.PostBoxScreen;
import com.mrcrayfish.furniture.client.renderer.SeatRenderer;
import com.mrcrayfish.furniture.client.renderer.tileentity.DoorMatTileEntityRenderer;
import com.mrcrayfish.furniture.client.renderer.tileentity.GrillTileEntityRenderer;
import com.mrcrayfish.furniture.client.renderer.tileentity.KitchenSinkTileEntityRenderer;
import com.mrcrayfish.furniture.core.ModBlocks;
import com.mrcrayfish.furniture.core.ModContainers;
import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.tileentity.DoorMatTileEntity;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import com.mrcrayfish.furniture.tileentity.KitchenSinkTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void onSetupClient()
    {
        super.onSetupClient();
        ClientRegistry.bindTileEntitySpecialRenderer(GrillTileEntity.class, new GrillTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DoorMatTileEntity.class, new DoorMatTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(KitchenSinkTileEntity.class, new KitchenSinkTileEntityRenderer());
        RenderingRegistry.registerEntityRenderingHandler(SeatEntity.class, SeatRenderer::new);
        ScreenManager.registerFactory(ModContainers.CRATE, CrateScreen::new);
        ScreenManager.registerFactory(ModContainers.POST_BOX, PostBoxScreen::new);
        ScreenManager.registerFactory(ModContainers.MAIL_BOX, MailBoxScreen::new);
        this.registerColors();

        if(!ModList.get().isLoaded("filters"))
        {
            MinecraftForge.EVENT_BUS.register(new CreativeScreenEvents());
        }
        else
        {
            Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "general"), new ItemStack(ModBlocks.CHAIR_OAK));
            Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "storage"), new ItemStack(ModBlocks.CABINET_OAK));
            Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "bedroom"), new ItemStack(ModBlocks.DESK_OAK));
            Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "outdoors"), new ItemStack(ModBlocks.MAIL_BOX_OAK));
            Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "kitchen"), new ItemStack(ModBlocks.KITCHEN_COUNTER_CYAN));
            Filters.get().register(FurnitureMod.GROUP, new ResourceLocation(Reference.MOD_ID, "items"), new ItemStack(ModItems.SPATULA));
        }
    }

    private void registerColors()
    {
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
                ModBlocks.PICKET_GATE_BLACK,
                ModBlocks.POST_BOX
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
                ModBlocks.PICKET_GATE_BLACK,
                ModBlocks.POST_BOX
        );

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> i == 1 ? 0xBBBBBB : 0,
                ModBlocks.CRATE_STRIPPED_OAK,
                ModBlocks.CRATE_STRIPPED_SPRUCE,
                ModBlocks.CRATE_STRIPPED_BIRCH,
                ModBlocks.CRATE_STRIPPED_JUNGLE,
                ModBlocks.CRATE_STRIPPED_ACACIA,
                ModBlocks.CRATE_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK
        );

        Minecraft.getInstance().getItemColors().register((stack, i) -> i == 1 ? 0xBBBBBB : 0,
                ModBlocks.CRATE_STRIPPED_OAK,
                ModBlocks.CRATE_STRIPPED_SPRUCE,
                ModBlocks.CRATE_STRIPPED_BIRCH,
                ModBlocks.CRATE_STRIPPED_JUNGLE,
                ModBlocks.CRATE_STRIPPED_ACACIA,
                ModBlocks.CRATE_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_OAK,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_COUNTER_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_OAK,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_DRAWER_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_OAK,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_SINK_LIGHT_STRIPPED_DARK_OAK,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_OAK,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_SPRUCE,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_BIRCH,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_JUNGLE,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_ACACIA,
                ModBlocks.KITCHEN_SINK_DARK_STRIPPED_DARK_OAK
        );

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> i == 1 ? 0x999999 : 0,
                ModBlocks.PARK_BENCH_STRIPPED_OAK,
                ModBlocks.PARK_BENCH_STRIPPED_SPRUCE,
                ModBlocks.PARK_BENCH_STRIPPED_BIRCH,
                ModBlocks.PARK_BENCH_STRIPPED_JUNGLE,
                ModBlocks.PARK_BENCH_STRIPPED_ACACIA,
                ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK
        );

        Minecraft.getInstance().getItemColors().register((stack, i) -> i == 1 ? 0x999999 : 0,
                ModBlocks.PARK_BENCH_STRIPPED_OAK,
                ModBlocks.PARK_BENCH_STRIPPED_SPRUCE,
                ModBlocks.PARK_BENCH_STRIPPED_BIRCH,
                ModBlocks.PARK_BENCH_STRIPPED_JUNGLE,
                ModBlocks.PARK_BENCH_STRIPPED_ACACIA,
                ModBlocks.PARK_BENCH_STRIPPED_DARK_OAK
        );

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> FoliageColors.getSpruce(),
                ModBlocks.HEDGE_SPRUCE);

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> FoliageColors.getBirch(),
                ModBlocks.HEDGE_BIRCH);

        Minecraft.getInstance().getBlockColors().register((state, reader, pos, i) -> reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault(),
                ModBlocks.HEDGE_OAK,
                ModBlocks.HEDGE_JUNGLE,
                ModBlocks.HEDGE_ACACIA,
                ModBlocks.HEDGE_DARK_OAK);

        Minecraft.getInstance().getItemColors().register((stack, i) -> {
            BlockState state = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return Minecraft.getInstance().getBlockColors().getColor(state, null, null, i);
        }, ModBlocks.HEDGE_OAK, ModBlocks.HEDGE_SPRUCE, ModBlocks.HEDGE_BIRCH, ModBlocks.HEDGE_JUNGLE, ModBlocks.HEDGE_ACACIA, ModBlocks.HEDGE_DARK_OAK);
    }

    @Override
    public void updateMailBoxes(CompoundNBT compound)
    {
        if(Minecraft.getInstance().currentScreen instanceof PostBoxScreen)
        {
            if(compound.contains("MailBoxes", Constants.NBT.TAG_LIST))
            {
                List<MailBoxEntry> entries = new ArrayList<>();
                ListNBT mailBoxList = compound.getList("MailBoxes", Constants.NBT.TAG_COMPOUND);
                mailBoxList.forEach(nbt -> entries.add(new MailBoxEntry((CompoundNBT) nbt)));
                ((PostBoxScreen) Minecraft.getInstance().currentScreen).updateMailBoxes(entries);
            }
        }
    }

    public boolean useFancyGraphics()
    {
        Minecraft mc = Minecraft.getInstance();
        return mc.gameSettings.fancyGraphics;
    }

    @Override
    public void setGrillFlipping(BlockPos pos, int position)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft.world != null)
        {
            TileEntity tileEntity = minecraft.world.getTileEntity(pos);
            if(tileEntity instanceof GrillTileEntity)
            {
                ((GrillTileEntity) tileEntity).setFlipping(position);
            }
        }
    }
}
