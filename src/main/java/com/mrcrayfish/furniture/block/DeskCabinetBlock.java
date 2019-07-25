package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.tileentity.CabinetTileEntity;
import com.mrcrayfish.furniture.tileentity.DeskCabinetTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class DeskCabinetBlock extends DeskBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public DeskCabinetBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult)
    {
        if(blockState.get(DIRECTION).getOpposite() == rayTraceResult.getFace())
        {
            if(!world.isRemote())
            {
                TileEntity tileEntity = world.getTileEntity(pos);
                if(tileEntity instanceof DeskCabinetTileEntity)
                {
                    playerEntity.openContainer((INamedContainerProvider) tileEntity);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void tick(BlockState blockState, World world, BlockPos pos, Random random)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof DeskCabinetTileEntity)
        {
            ((DeskCabinetTileEntity) tileEntity).onScheduledTick();
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new DeskCabinetTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(OPEN);
    }
}
