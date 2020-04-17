package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GrillBlock extends FurnitureWaterloggedBlock implements ISidedInventoryProvider
{
    public static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(Arrays.asList(Block.makeCuboidShape(0.0, 11.0, 0.0, 16.0, 16.0, 16.0), Block.makeCuboidShape(1.5, 0.0, 1.5, 14.5, 11.0, 14.5)));

    public GrillBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof GrillTileEntity)
            {
                GrillTileEntity grillTileEntity = (GrillTileEntity) tileEntity;
                InventoryHelper.dropItems(worldIn, pos, grillTileEntity.getGrill());
                InventoryHelper.dropItems(worldIn, pos, grillTileEntity.getFuel());
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(!world.isRemote && result.getFace() == Direction.UP)
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof GrillTileEntity)
            {
                GrillTileEntity grillTileEntity = (GrillTileEntity) tileEntity;
                ItemStack stack = playerEntity.getHeldItem(hand);
                if(stack.getItem() == ModItems.SPATULA)
                {
                    grillTileEntity.flipItem(this.getPosition(result, pos));
                }
                else if(stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL)
                {
                    if(grillTileEntity.addFuel(stack))
                    {
                        stack.shrink(1);
                    }
                }
                else if(!stack.isEmpty())
                {
                    Optional<GrillCookingRecipe> optional = grillTileEntity.findMatchingRecipe(stack);
                    if(optional.isPresent())
                    {
                        GrillCookingRecipe recipe = optional.get();
                        if(grillTileEntity.addItem(stack, this.getPosition(result, pos), recipe.getCookTime(), recipe.getExperience(), (byte) playerEntity.getHorizontalFacing().getHorizontalIndex()))
                        {
                            if(!playerEntity.abilities.isCreativeMode)
                            {
                                stack.shrink(1);
                            }
                        }
                    }
                }
                else
                {
                    grillTileEntity.removeItem(this.getPosition(result, pos));
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    private int getPosition(BlockRayTraceResult hit, BlockPos pos)
    {
        Vec3d hitVec = hit.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
        int position = 0;
        if(hitVec.getX() > 0.5) position += 1;
        if(hitVec.getZ() > 0.5) position += 2;
        return position;
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
        return new GrillTileEntity();
    }

    @Override
    public ISidedInventory createInventory(BlockState state, IWorld world, BlockPos pos)
    {
        if(!world.isRemote())
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof GrillTileEntity)
            {
                return (GrillTileEntity) tileEntity;
            }
        }
        return null;
    }
}
