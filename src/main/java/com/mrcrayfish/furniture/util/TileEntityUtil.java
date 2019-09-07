package com.mrcrayfish.furniture.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class TileEntityUtil
{
    /**
     * Sends an update packet to clients tracking a tile entity.
     *
     * @param tileEntity the tile entity to update
     */
    public static void sendUpdatePacket(TileEntity tileEntity)
    {
        BlockPos pos = tileEntity.getPos();
        World world = tileEntity.getWorld();
        if(world instanceof ServerWorld)
        {
            ServerWorld server = (ServerWorld) world;
            SUpdateTileEntityPacket packet = tileEntity.getUpdatePacket();
            if(packet != null)
            {
                Stream<ServerPlayerEntity> players = server.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false);
                players.forEach(player -> player.connection.sendPacket(packet));
            }
        }
    }
}
