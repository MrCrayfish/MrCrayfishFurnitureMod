package com.mrcrayfish.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityChoppingBoard;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChoppingBoard extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BODY = new Bounds(3, 0, 0.5, 13, 1, 15.5).getRotatedBounds(Rotation.COUNTERCLOCKWISE_90);

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(BODY);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(COLLISION_BOXES);

    public BlockChoppingBoard(Material material)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    protected List<AxisAlignedBB> getCollisionBoxes(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, boolean isActualState)
    {
        return COLLISION_BOXES[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityChoppingBoard)
        {
            TileEntityChoppingBoard tileEntityChoppingBoard = (TileEntityChoppingBoard) tileEntity;
            if(!heldItem.isEmpty())
            {
                if(Recipes.getChoppingBoardRecipeFromInput(heldItem) != null)
                {
                    if(tileEntityChoppingBoard.getFood() == null)
                    {
                        tileEntityChoppingBoard.setFood(new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
                        TileEntityUtil.markBlockForUpdate(worldIn, pos);
                        worldIn.updateComparatorOutputLevel(pos, this);
                        heldItem.shrink(1);
                        return true;
                    }
                    else
                    {
                        if(!worldIn.isRemote)
                        {
                            EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityChoppingBoard.getFood());
                            worldIn.spawnEntity(entityFood);
                            tileEntityChoppingBoard.setFood(null);
                            TileEntityUtil.markBlockForUpdate(worldIn, pos);
                        }
                        worldIn.updateComparatorOutputLevel(pos, this);
                        return true;
                    }
                }
                else if(heldItem.getItem() == FurnitureItems.KNIFE && tileEntityChoppingBoard.getFood() != null)
                {
                    if(tileEntityChoppingBoard.chopFood())
                    {
                        heldItem.damageItem(1, playerIn);
                    }
                    return true;
                }
            }
            if(tileEntityChoppingBoard.getFood() != null)
            {
                if(!worldIn.isRemote)
                {
                    EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityChoppingBoard.getFood());
                    worldIn.spawnEntity(entityFood);
                }
                tileEntityChoppingBoard.setFood(null);
                TileEntityUtil.markBlockForUpdate(worldIn, pos);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityChoppingBoard();
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        if(world.getTileEntity(pos) instanceof TileEntityChoppingBoard)
        {
            TileEntityChoppingBoard tecb = (TileEntityChoppingBoard) world.getTileEntity(pos);
            return tecb.getFood() != null ? 1 : 0;
        }
        return 0;
    }
}
