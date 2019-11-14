package com.mrcrayfish.furniture.block;

import com.mrcrayfish.furniture.core.ModItems;
import com.mrcrayfish.furniture.item.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.tileentity.GrillTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GrillBlock extends FurnitureWaterloggedBlock
{
    public GrillBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public float func_220080_a(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 1.0F;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if(!worldIn.isRemote && hit.getFace() == Direction.UP)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof GrillTileEntity)
            {
                GrillTileEntity grillTileEntity = (GrillTileEntity) tileEntity;
                ItemStack stack = player.getHeldItem(handIn);
                if(stack.getItem() == ModItems.SPATULA)
                {
                    grillTileEntity.flipItem(this.getPosition(hit, pos));
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
                        if(grillTileEntity.addItem(stack, this.getPosition(hit, pos), recipe.getCookTime(), recipe.getExperience(), (byte) player.getHorizontalFacing().getHorizontalIndex()))
                        {
                            if(!player.abilities.isCreativeMode)
                            {
                                stack.shrink(1);
                            }
                        }
                    }
                }
            }
        }
        return true;
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
}
