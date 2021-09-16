package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.core.ModParticles;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.tileentity.ShowerHeadTileEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowerHeadBlock extends FurnitureHorizontalWaterloggedBlock
{
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public ShowerHeadBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(ACTIVATED, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] TOP_PIPE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(7.2, 5.6, 7.2, 16.0, 7.2, 8.8), Direction.EAST));
        final VoxelShape[] CENTER_PIPE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(7.2, 4.0, 7.2, 8.8, 5.6, 8.8), Direction.EAST));
        final VoxelShape[] MAIN_WATER_OUTPUT = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(5.6, 2.4, 5.6, 10.4, 4.0, 10.4), Direction.EAST));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state: states) {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(TOP_PIPE[direction.getHorizontalIndex()]);
            shapes.add(CENTER_PIPE[direction.getHorizontalIndex()]);
            shapes.add(MAIN_WATER_OUTPUT[direction.getHorizontalIndex()]);
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES.get(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(ACTIVATED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.SHOWER_HEAD.get().create();
    }

    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(state.get(ACTIVATED)) {
            setUnactivated(state, world, pos);
        } else {
            Random random = new Random();
            setActivated(state, world, pos);
        }
        return ActionResultType.SUCCESS;
    }

    public BlockState setActivated(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(ACTIVATED, Boolean.valueOf(true)), 2);
        return state;
    }

    public BlockState setUnactivated(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(ACTIVATED, Boolean.valueOf(false)), 2);
        return state;
    }

}
