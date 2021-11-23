package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.tileentity.KitchenSinkTileEntity;
import com.mrcrayfish.furniture.tileentity.SinkTileEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class SinkBlock extends FurnitureHorizontalBlock
{

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public SinkBlock(Properties properties)
    {
        super(properties);
    }

    private VoxelShape getShape(BlockState state) {

        if(SHAPES.containsKey(state)) {
            return SHAPES.get(state);
        }

        List<VoxelShape> shapes = new ArrayList<>();
        Direction direction = state.get(DIRECTION);

        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(8.0, 0.0, 3.2, 16.0, 11.0, 12.8), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 11.0, 0.0, 16.0, 12.0, 16.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(12.0, 12.0, 0.0, 16.0, 16.0, 16.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 12.0, 0.0, 12.0, 16.0, 2.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 12.0, 14.0, 12.0, 16.0, 16.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(0.0, 12.0, 2.0, 2.0, 16.0, 14.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.0, 16.0, 7.0, 15.0, 20.0, 9.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(9.0, 20.0, 7.0, 15.0, 22.0, 9.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.0, 16.0, 2.0, 15.0, 18.0, 4.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(13.0, 16.0, 12.0, 15.0, 18.0, 14.0), Direction.EAST))[direction.getHorizontalIndex()]);
        shapes.add(VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(9.0, 19.68, 7.0, 11.0, 20.0, 9.0), Direction.EAST))[direction.getHorizontalIndex()]);

        VoxelShape shape = VoxelShapeHelper.combineAll(shapes);
        SHAPES.put(state, shape);
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return this.getShape(state);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(!world.isRemote())
        {
            ItemStack heldItem = playerEntity.getHeldItem(hand);
            if(heldItem.getItem() == Items.GLASS_BOTTLE)
            {
                IFluidHandler handler = FluidUtil.getFluidHandler(world, pos, null).orElse(null);
                if(handler.getFluidInTank(0).getAmount() > 0 && !world.isRemote())
                {
                    if(!playerEntity.abilities.isCreativeMode)
                    {
                        ItemStack waterPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                        heldItem.shrink(1);
                        if(heldItem.isEmpty())
                        {
                            playerEntity.setHeldItem(hand, waterPotion);
                        }
                        else if(!playerEntity.inventory.addItemStackToInventory(waterPotion))
                        {
                            playerEntity.dropItem(waterPotion, false);
                        }
                        else if(playerEntity instanceof ServerPlayerEntity)
                        {
                            ((ServerPlayerEntity) playerEntity).sendContainerToPlayer(playerEntity.container);
                        }
                    }

                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    handler.drain(FluidAttributes.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
                }
                return ActionResultType.func_233537_a_(world.isRemote());
            }

            if(!heldItem.isEmpty() && heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent())
            {
                return FluidUtil.interactWithFluidHandler(playerEntity, hand, world, pos, result.getFace()) ? ActionResultType.SUCCESS : ActionResultType.PASS;
            }

            BlockPos waterPos = pos.down().down();
            if(this.isWaterSource(world, waterPos))
            {
                IFluidHandler handler = FluidUtil.getFluidHandler(world, pos, null).orElse(null);
                if(handler.getFluidInTank(0).getAmount() != handler.getTankCapacity(0))
                {
                    handler.fill(new FluidStack(Fluids.WATER, FluidAttributes.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
                    world.playSound(null, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    Direction direction = state.get(DIRECTION);
                    double posX = pos.getX() + 0.5 + direction.getDirectionVec().getX() * 0.1;
                    double posY = pos.getY() + 1.15;
                    double posZ = pos.getZ() + 0.5 + direction.getDirectionVec().getZ() * 0.1;
                    ((ServerWorld)world).spawnParticle(ParticleTypes.FALLING_WATER, posX, posY, posZ, 10, 0.01, 0.01, 0.01, 0);

                    int adjacentSources = 0;
                    adjacentSources += this.isWaterSource(world, waterPos.north()) ? 1 : 0;
                    adjacentSources += this.isWaterSource(world, waterPos.east()) ? 1 : 0;
                    adjacentSources += this.isWaterSource(world, waterPos.south()) ? 1 : 0;
                    adjacentSources += this.isWaterSource(world, waterPos.west()) ? 1 : 0;
                    if(adjacentSources < 2) //If it has less then two adjacent water sources, it is not infinite and thus it should be consumed
                    {
                        world.setBlockState(waterPos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    private boolean isWaterSource(World world, BlockPos pos)
    {
        return world.getFluidState(pos).getFluid() == Fluids.WATER;
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
        return new SinkTileEntity();
    }
}

