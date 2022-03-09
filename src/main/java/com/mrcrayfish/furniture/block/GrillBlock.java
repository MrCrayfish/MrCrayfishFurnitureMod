package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.tileentity.GrillBlockEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GrillBlock extends FurnitureWaterloggedBlock implements EntityBlock
{
    public static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(Arrays.asList(Block.box(0.0, 11.0, 0.0, 16.0, 16.0, 16.0), Block.box(1.5, 0.0, 1.5, 14.5, 11.0, 14.5)));

    public GrillBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    //TODO add tickers for all block entities that need them

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(level.getBlockEntity(pos) instanceof GrillBlockEntity blockEntity)
            {
                Containers.dropContents(level, pos, blockEntity.getGrill());
                Containers.dropContents(level, pos, blockEntity.getFuel());
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide() && result.getDirection() == Direction.UP)
        {
            if(level.getBlockEntity(pos) instanceof GrillBlockEntity blockEntity)
            {
                ItemStack stack = player.getItemInHand(hand);
                if(stack.getItem() == ModItems.SPATULA.get())
                {
                    blockEntity.flipItem(this.getPosition(result, pos));
                }
                else if(stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL)
                {
                    if(blockEntity.addFuel(stack))
                    {
                        stack.shrink(1);
                        level.playSound(null, pos, SoundEvents.ANCIENT_DEBRIS_HIT, SoundSource.BLOCKS, 1.0F, 1.5F);
                    }
                }
                else if(!stack.isEmpty())
                {
                    Optional<GrillCookingRecipe> optional = blockEntity.findMatchingRecipe(stack);
                    if(optional.isPresent())
                    {
                        GrillCookingRecipe recipe = optional.get();
                        if(blockEntity.addItem(stack, this.getPosition(result, pos), recipe.getCookingTime(), recipe.getExperience(), (byte) player.getDirection().get2DDataValue()))
                        {
                            if(!player.getAbilities().instabuild)
                            {
                                stack.shrink(1);
                            }
                        }
                    }
                }
                else
                {
                    blockEntity.removeItem(this.getPosition(result, pos));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private int getPosition(BlockHitResult hit, BlockPos pos)
    {
        Vec3 hitVec = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        int position = 0;
        if(hitVec.x() > 0.5) position += 1;
        if(hitVec.z() > 0.5) position += 2;
        return position;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new GrillBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createMailBoxTicker(level, type, ModBlockEntities.GRILL.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createMailBoxTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends GrillBlockEntity> grillBlockEntity)
    {
        if(level.isClientSide())
        {
            return createTickerHelper(blockEntityType, grillBlockEntity, GrillBlockEntity::clientTick);
        }
        else
        {
            return createTickerHelper(blockEntityType, grillBlockEntity, GrillBlockEntity::serverTick);
        }
    }
}
