package com.mrcrayfish.furniture.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityGrill;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.ParticleHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrill extends BlockFurnitureTile
{
    private static final AxisAlignedBB[] BASE = new Bounds(1, 12, 2, 15, 13, 14).getRotatedBounds();
    private static final AxisAlignedBB[] LEFT = new Bounds(1, 13, 2, 2, 16, 14).getRotatedBounds();
    private static final AxisAlignedBB[] RIGHT = new Bounds(14, 13, 2, 15, 16, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BACK = new Bounds(2, 13, 2, 14, 16, 3).getRotatedBounds();
    private static final AxisAlignedBB[] FRONT = new Bounds(2, 13, 13, 14, 16, 14).getRotatedBounds();
    private static final AxisAlignedBB[] BARS = new Bounds(2, 14.5, 3, 14, 15.5, 13).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_LEFT_CONN = new Bounds(0.5, 14, 5, 1, 15, 6).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_LEFT_CONN_2 = new Bounds(0.5, 14, 10, 1, 15, 11).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_LEFT = new Bounds(0, 14, 5, 0.5, 15, 11).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_RIGHT_CONN = new Bounds(15, 14, 5, 15.5, 15, 6).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_RIGHT_CONN_2 = new Bounds(15, 14, 10, 15.5, 15, 11).getRotatedBounds();
    private static final AxisAlignedBB[] HANDLE_RIGHT = new Bounds(15.5, 14, 5, 16, 15, 11).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_BACK_LEFT = new Bounds(1.5, 0, 2.5, 3, 12, 4).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_BACK_RIGHT = new Bounds(13, 0, 2.5, 14.5, 12, 4).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_FRONT_RIGHT = new Bounds(13, 0, 12, 14.5, 12, 13.5).getRotatedBounds();
    private static final AxisAlignedBB[] LEG_FRONT_LEFT = new Bounds(1.5, 0, 12, 3, 12, 13.5).getRotatedBounds();
    private static final AxisAlignedBB[] MESH_HOLDER = new Bounds(2, 5, 3, 14, 5, 13).getRotatedBounds();

    private static final List<AxisAlignedBB>[] COLLISION_BOXES = Bounds.getRotatedBoundLists(BASE, LEFT, RIGHT, BACK, FRONT, BARS, HANDLE_LEFT_CONN, HANDLE_LEFT_CONN_2, HANDLE_LEFT, HANDLE_RIGHT_CONN, HANDLE_RIGHT_CONN_2, HANDLE_RIGHT, LEG_BACK_LEFT, LEG_BACK_RIGHT, LEG_FRONT_RIGHT, LEG_FRONT_LEFT, MESH_HOLDER);
    private static final AxisAlignedBB[] BOUNDING_BOX = Bounds.getBoundingBoxes(Bounds.getRotatedBoundLists(LEG_FRONT_LEFT, LEFT, RIGHT));

    public BlockGrill(Material material)
    {
        super(material);
        this.setHardness(1.5F);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
    {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityGrill)
        {
            TileEntityGrill tileEntityGrill = (TileEntityGrill) tileEntity;
            if(tileEntityGrill.isFireStarted())
            {
                if(RANDOM.nextInt(2) == 0)
                {
                    double posX = 0.2D + 0.6D * RANDOM.nextDouble();
                    double posZ = 0.2D + 0.6D * RANDOM.nextDouble();
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + posX, pos.getY() + 1.0D, pos.getZ() + posZ, 0.0D, 0.0D, 0.0D);
                }
                if(tileEntityGrill.leftCooked && tileEntityGrill.getItem(0) != null && RecipeAPI.getGrillRecipeFromInput(tileEntityGrill.getItem(0)) != null)
                {
                    int meta = getMetaFromState(state);
                    float[] leftFixed = ParticleHelper.fixRotation(meta, 0.5F, 0.25F);
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + leftFixed[0], pos.getY() + 1.0D, pos.getZ() + leftFixed[1], 0.0D, 0.0D, 0.0D);
                }
                if(tileEntityGrill.rightCooked && tileEntityGrill.getItem(1) != null && RecipeAPI.getGrillRecipeFromInput(tileEntityGrill.getItem(1)) != null)
                {
                    int meta = getMetaFromState(state);
                    float[] rightFixed = ParticleHelper.fixRotation(meta, 0.5F, 0.75F);
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rightFixed[0], pos.getY() + 1.0D, pos.getZ() + rightFixed[1], 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityGrill)
            {
                TileEntityGrill tileEntityGrill = (TileEntityGrill) tileEntity;
                if(!heldItem.isEmpty())
                {
                    if(heldItem.getItem() == Items.COAL && heldItem.getItemDamage() == 1)
                    {
                        if(tileEntityGrill.addCoal())
                        {
                            heldItem.shrink(1);
                        }
                    }
                    else if(heldItem.getItem() == Items.FLINT_AND_STEEL)
                    {
                        tileEntityGrill.startFire();
                    }
                    else if(heldItem.getItem() == FurnitureItems.SPATULA && facing == EnumFacing.UP)
                    {
                        EnumFacing side = state.getValue(FACING);
                        tileEntityGrill.flipFood(getClickedSide(side, hitX, hitZ));
                    }
                    else if(RecipeAPI.getGrillRecipeFromInput(heldItem) != null && facing == EnumFacing.UP)
                    {
                        EnumFacing side = state.getValue(FACING);
                        if(tileEntityGrill.addFood(getClickedSide(side, hitX, hitZ), heldItem))
                        {
                            heldItem.shrink(1);
                        }
                    }
                    else
                    {
                        EnumFacing side = state.getValue(FACING);
                        tileEntityGrill.removeFood(getClickedSide(side, hitX, hitZ));
                    }
                }
                else
                {
                    EnumFacing side = state.getValue(FACING);
                    tileEntityGrill.removeFood(getClickedSide(side, hitX, hitZ));
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityGrill();
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public ClickedSide getClickedSide(EnumFacing facing, float hitX, float hitZ)
    {
        switch(facing)
        {
            case NORTH:
                if(hitX <= 0.5) return ClickedSide.LEFT;
                return ClickedSide.RIGHT;
            case EAST:
                if(hitZ <= 0.5) return ClickedSide.LEFT;
                return ClickedSide.RIGHT;
            case SOUTH:
                if(hitX <= 0.5) return ClickedSide.RIGHT;
                return ClickedSide.LEFT;
            case WEST:
                if(hitZ <= 0.5) return ClickedSide.RIGHT;
                return ClickedSide.LEFT;
            default:
                return ClickedSide.UNKNOWN;
        }
    }

    public enum ClickedSide
    {
        LEFT(0),
        RIGHT(1),
        UNKNOWN(2);

        public int id;

        ClickedSide(int id)
        {
            this.id = id;
        }
    }
}
