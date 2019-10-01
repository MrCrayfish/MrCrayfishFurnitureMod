package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.tileentity.TrampolineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class TrampolineBlock extends FurnitureWaterloggedBlock
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public TrampolineBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(entityIn instanceof LivingEntity)
        {
            float strength = 1.0F;
            float maxHeight = 0;
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TrampolineTileEntity)
            {
                TrampolineTileEntity trampoline = (TrampolineTileEntity) tileEntity;
                strength += trampoline.getCount() / 100F;
                maxHeight = trampoline.getCount() * 0.5F;
            }

            float height = entityIn.fallDistance * strength;
            if(height > 0 && !entityIn.isSneaking())
            {
                if(height > maxHeight - 0.5F) height = maxHeight - 0.5F;
                entityIn.setMotion(entityIn.getMotion().mul(1.0, 0.0, 1.0));
                entityIn.addVelocity(0, Math.sqrt(0.22 * (height + 0.5F)), 0);
                if(worldIn.isRemote)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, entityIn.posX, entityIn.posY, entityIn.posZ, 0.0, 0.0, 0.0);
                    }
                }
                else
                {
                    worldIn.playSound(null, pos, ModSounds.BLOCK_TRAMPOLINE_BOUNCE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.2F + 0.9F);
                }
            }
            entityIn.fallDistance = 0;
        }
    }

    @Override
    public void onLanded(IBlockReader worldIn, Entity entityIn) {}

    @Override
    public boolean addLandingEffects(BlockState state1, ServerWorld worldserver, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles)
    {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TrampolineTileEntity)
            {
                ((TrampolineTileEntity) tileEntity).updateCount();
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getTrampolineState(super.getStateForPlacement(context), context.getWorld(), context.getPos());
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos)
    {
        return this.getTrampolineState(state, world, pos);
    }

    private BlockState getTrampolineState(BlockState state, IWorld world, BlockPos pos)
    {
        boolean north = world.getBlockState(pos.north()).getBlock() == this;
        boolean east = world.getBlockState(pos.east()).getBlock() == this;
        boolean south = world.getBlockState(pos.south()).getBlock() == this;
        boolean west = world.getBlockState(pos.west()).getBlock() == this;
        return state.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
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
        return new TrampolineTileEntity();
    }
}
