package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.entity.SeatEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class ToiletBlock extends FurnitureHorizontalWaterloggedBlock {
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ToiletBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states) {
        final VoxelShape[] LEFT_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(11.2, 6.4, 8.16, 12.8, 18.4, 9.76), Direction.EAST));
        final VoxelShape[] RIGHT_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(11.2, 6.4, 6.24, 12.8, 18.4, 7.84), Direction.EAST));
        final VoxelShape[] TANK_TOP = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(9.6, 16.0, 1.6, 16.0, 17.6, 14.4), Direction.EAST));
        final VoxelShape[] TANK = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(10.4, 4.8, 2.4, 16.0, 16.0, 13.6), Direction.EAST));
        final VoxelShape[] RIGHT_BOWL_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 4.8, 2.4, 10.4, 9.6, 4.0), Direction.EAST));
        final VoxelShape[] FRONT_BOWL_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 4.8, 4.0, 3.2, 9.6, 12.0), Direction.EAST));
        final VoxelShape[] LEFT_BOWL_SIDE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(1.6, 4.8, 12.0, 10.4, 9.6, 13.6), Direction.EAST));
        final VoxelShape[] RIGHT_BOWL_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(6.4, 4.8, 4.0, 11.2, 5.6, 6.4), Direction.EAST));
        final VoxelShape[] LEFT_BOWL_BOTTOM = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(6.4, 4.8, 9.6, 11.2, 5.6, 12.0), Direction.EAST));
        final VoxelShape[] BOWL = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.2, 4.8, 4.0, 11.2, 6.4, 12.0), Direction.EAST));
        final VoxelShape[] WATER = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(6.4, 6.4, 6.4, 11.2, 6.416, 9.6), Direction.EAST));
        final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(3.2, 0.0, 4.0, 14.4, 4.8, 12.0), Direction.EAST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states) {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(LEFT_TOP[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_TOP[direction.getHorizontalIndex()]);
            shapes.add(TANK_TOP[direction.getHorizontalIndex()]);
            shapes.add(TANK[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_BOWL_SIDE[direction.getHorizontalIndex()]);
            shapes.add(FRONT_BOWL_SIDE[direction.getHorizontalIndex()]);
            shapes.add(LEFT_BOWL_SIDE[direction.getHorizontalIndex()]);
            shapes.add(RIGHT_BOWL_BOTTOM[direction.getHorizontalIndex()]);
            shapes.add(LEFT_BOWL_BOTTOM[direction.getHorizontalIndex()]);
            shapes.add(BOWL[direction.getHorizontalIndex()]);
            shapes.add(WATER[direction.getHorizontalIndex()]);
            shapes.add(BASE[direction.getHorizontalIndex()]);
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES.get(state);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if (playerEntity.isSneaking())
        {
            world.playSound(playerEntity, pos, ModSounds.BLOCK_TOILET_FLUSH.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        else
        {
            return SeatEntity.create(world, pos, 0.4, playerEntity);
        }
        return ActionResultType.PASS;
    }

}
