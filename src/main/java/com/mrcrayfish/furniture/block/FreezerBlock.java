package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.tileentity.BasicLootBlockEntity;
import com.mrcrayfish.furniture.tileentity.FreezerBlockEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class FreezerBlock extends FurnitureHorizontalBlock implements EntityBlock
{
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();
    private final Supplier<RegistryObject<Block>> fridge;

    public FreezerBlock(Properties properties, Supplier<RegistryObject<Block>> fridge)
    {
        super(properties);
        this.fridge = fridge;
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(OPEN, false));
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 -> {
            Direction direction = state1.getValue(DIRECTION);
            boolean open = state1.getValue(OPEN);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(Block.box(0, 16, 0, 16, 32, 16));
            if(open)
            {
                final VoxelShape[] BASE = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(0, 1, 0, 16, 16, 13), Direction.SOUTH));
                final VoxelShape[] DOOR = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.box(13, 1, 13, 16, 16, 29), Direction.SOUTH));
                shapes.add(BASE[direction.get2DDataValue()]);
                shapes.add(DOOR[direction.get2DDataValue()]);
            }
            else
            {
                shapes.add(Block.box(0, 1, 0, 16, 16, 16));
            }
            return VoxelShapeHelper.combineAll(shapes);
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return context == CollisionContext.empty() ? Shapes.block() : this.getShape(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(state.getValue(DIRECTION).getOpposite() == result.getDirection())
        {
            if(!level.isClientSide())
            {
                if(level.getBlockEntity(pos) instanceof FreezerBlockEntity blockEntity)
                {
                    NetworkHooks.openGui((ServerPlayer) player, blockEntity, pos);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if(level.getBlockEntity(pos) instanceof BasicLootBlockEntity blockEntity)
        {
            blockEntity.updateOpenerCount();
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos)
    {
        return reader.getBlockState(pos.above()).isAir();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        level.setBlockAndUpdate(pos.above(), this.fridge.get().get().defaultBlockState().setValue(DIRECTION, placer.getDirection()));
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        BlockState upState = level.getBlockState(pos.above());
        if(upState.getBlock() instanceof FridgeBlock)
        {
            level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(player, 2001, pos.above(), Block.getId(upState));
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FreezerBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createMailBoxTicker(level, type, ModBlockEntities.FREEZER.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createMailBoxTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends FreezerBlockEntity> freezerBlockEntity)
    {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, freezerBlockEntity, FreezerBlockEntity::serverTick);
    }

    @Override
    public Item asItem()
    {
        return this.fridge.get().get().asItem();
    }
}
