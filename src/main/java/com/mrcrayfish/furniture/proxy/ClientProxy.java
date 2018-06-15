/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.proxy;

import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.handler.ClientEvents;
import com.mrcrayfish.furniture.handler.GuiDrawHandler;
import com.mrcrayfish.furniture.handler.InputHandler;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.render.tileentity.*;
import com.mrcrayfish.furniture.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;

public class ClientProxy extends CommonProxy
{
    public static boolean rendering = false;
    public static Entity renderEntity = null;
    public static Entity backupEntity = null;

    @Override
    public void init()
    {
        registerColorHandlers();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCookieJar.class, new CookieRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new PlateRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToaster.class, new ToastRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChoppingBoard.class, new ChoppingBoardRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlender.class, new BlenderRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicrowave.class, new MicrowaveRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWashingMachine.class, new WashingMachineRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCup.class, new CupRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTree.class, new TreeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMirror.class, new MirrorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new OvenRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrill.class, new GrillRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEsky.class, new EskyRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDoorMat.class, new DoorMatRenderer());
    }

    public void registerColorHandlers()
    {
        FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((stack, tintIndex) ->
        {
            if(tintIndex == 1)
            {
                if(stack.hasTagCompound())
                {
                    if(stack.getTagCompound().hasKey("Colour"))
                    {
                        int[] colour = stack.getTagCompound().getIntArray("Colour");
                        Color color = new Color(colour[0], colour[1], colour[2]);
                        return color.getRGB();
                    }
                }
            }
            return 16777215;
        }, FurnitureItems.itemDrink);
        IItemColor hedgeItemColor = (stack, tintIndex) ->
        {
            IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
            return Minecraft.getMinecraft().getBlockColors().colorMultiplier(iblockstate, null, null, tintIndex);
        };
        IBlockColor hedgeBlockColorOld = (state, worldIn, pos, tintIndex) ->
        {
            Block block = state.getBlock();
            if(block == FurnitureBlocks.hedge_spruce)
            {
                return ColorizerFoliage.getFoliageColorPine();
            }
            else if(block == FurnitureBlocks.hedge_birch)
            {
                return ColorizerFoliage.getFoliageColorBirch();
            }
            else
            {
                return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
            }
        };
        IBlockColor hedgeBlockColorNew = (state, worldIn, pos, tintIndex) ->
        {
            return worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
        };
        registerColorHandlerForBlock(FurnitureBlocks.hedge_oak, hedgeBlockColorOld, hedgeItemColor);
        registerColorHandlerForBlock(FurnitureBlocks.hedge_spruce, hedgeBlockColorOld, hedgeItemColor);
        registerColorHandlerForBlock(FurnitureBlocks.hedge_birch, hedgeBlockColorOld, hedgeItemColor);
        registerColorHandlerForBlock(FurnitureBlocks.hedge_jungle, hedgeBlockColorOld, hedgeItemColor);
        registerColorHandlerForBlock(FurnitureBlocks.hedge_acacia, hedgeBlockColorNew, hedgeItemColor);
        registerColorHandlerForBlock(FurnitureBlocks.hedge_dark_oak, hedgeBlockColorNew, hedgeItemColor);
        IItemColor christmasItemColor = (stack, tintIndex) -> ColorizerFoliage.getFoliageColorPine();
        IBlockColor christmasBlockColor = (state, worldIn, pos, tintIndex) -> ColorizerFoliage.getFoliageColorPine();
        registerColorHandlerForBlock(FurnitureBlocks.tree_bottom, christmasBlockColor, christmasItemColor);
        registerColorHandlerForBlock(FurnitureBlocks.tree_top, christmasBlockColor, christmasItemColor);
        registerColorHandlerForBlock(FurnitureBlocks.wreath, christmasBlockColor, christmasItemColor);
    }

    public void registerColorHandlerForBlock(Block block, IBlockColor blockColor, IItemColor itemColor)
    {
        FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(itemColor, Item.getItemFromBlock(block));
        FMLClientHandler.instance().getClient().getBlockColors().registerBlockColorHandler(blockColor, block);
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public boolean isSinglePlayer()
    {
        return Minecraft.getMinecraft().isSingleplayer();
    }

    @Override
    public boolean isDedicatedServer()
    {
        return false;
    }

    @Override
    public void preInit()
    {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new InputHandler());
        MinecraftForge.EVENT_BUS.register(new GuiDrawHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    @SubscribeEvent
    public void onClientWorldLoad(WorldEvent.Load event)
    {
        if(event.getWorld() instanceof WorldClient)
        {
            MirrorRenderer.mirrorGlobalRenderer.setWorldAndLoadRenderers((WorldClient) event.getWorld());
        }
    }

    @SubscribeEvent
    public void onClientWorldUnload(WorldEvent.Unload event)
    {
        if(event.getWorld() instanceof WorldClient)
        {
            MirrorRenderer.clearRegisteredMirrors();
        }
    }


    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre event)
    {
        if(!rendering) return;

        if(event.getEntityPlayer() == renderEntity)
        {
            this.backupEntity = Minecraft.getMinecraft().getRenderManager().renderViewEntity;
            Minecraft.getMinecraft().getRenderManager().renderViewEntity = renderEntity;
        }
    }

    @SubscribeEvent
    public void onPostPlayerRender(RenderPlayerEvent.Post event)
    {
        if(!rendering) return;

        if(event.getEntityPlayer() == renderEntity)
        {
            Minecraft.getMinecraft().getRenderManager().renderViewEntity = backupEntity;
            renderEntity = null;
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Side.CLIENT)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event)
        {
            FurnitureItems.registerRenders();
            FurnitureBlocks.registerRenders();
        }
    }
}
