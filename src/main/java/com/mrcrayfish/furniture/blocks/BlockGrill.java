package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockGrill extends BlockFurnitureTile
{
    public static final AxisAlignedBB NORTH_SOUTH_BOUDING_BOX = new Bounds(0, 0, 1, 16, 16, 15).toAABB();
    public static final AxisAlignedBB EAST_WEST_BOUDING_BOX = new Bounds(1, 0, 0, 15, 16, 16).toAABB();

    public static final AxisAlignedBB[] TOP = new Bounds(2, 12, 1, 14, 16, 15).getRotatedBounds();
    public static final AxisAlignedBB[] GRILL = new Bounds(3, 5, 2, 13, 5, 14).getRotatedBounds();
    public static final AxisAlignedBB[] LEGS = {new AxisAlignedBB(13 * 0.0625, 0, 2.5 * 0.0625, 14.5 * 0.0625, 12 * 0.0625, 4 * 0.0625), new AxisAlignedBB(1.5 * 0.0625, 0, 2.5 * 0.0625, 3 * 0.0625, 12 * 0.0625, 4 * 0.0625), new AxisAlignedBB(14.5 * 0.0625, 0, 12 * 0.0625, 13 * 0.0625, 12 * 0.0625, 13.5 * 0.0625), new AxisAlignedBB(1.5 * 0.0625, 0, 12 * 0.0625, 3 * 0.0625, 12 * 0.0625, 13.5 * 0.0625),new AxisAlignedBB(12 * 0.0625, 0, 1.5 * 0.0625, 13.5 * 0.0625, 12 * 0.0625, 3 * 0.0625), new AxisAlignedBB(2.5 * 0.0625, 0, 1.5 * 0.0625, 4 * 0.0625, 12 * 0.0625, 3 * 0.0625),new AxisAlignedBB(12 * 0.0625, 0, 13 * 0.0625, 13.5 * 0.0625, 12 * 0.0625, 14.5 * 0.0625), new AxisAlignedBB(2.5 * 0.0625, 0, 13 * 0.0625, 4 * 0.0625, 12 * 0.0625, 14.5 * 0.0625), new AxisAlignedBB(13 * 0.0625, 0, 2.5 * 0.0625, 14.5 * 0.0625, 12 * 0.0625, 4 * 0.0625), new AxisAlignedBB(1.5 * 0.0625, 0, 2.5 * 0.0625, 3 * 0.0625, 12 * 0.0625, 4 * 0.0625), new AxisAlignedBB(14.5 * 0.0625, 0, 12 * 0.0625, 13 * 0.0625, 12 * 0.0625, 13.5 * 0.0625), new AxisAlignedBB(1.5 * 0.0625, 0, 12 * 0.0625, 3 * 0.0625, 12 * 0.0625, 13.5 * 0.0625), new AxisAlignedBB(12 * 0.0625, 0, 1.5 * 0.0625, 13.5 * 0.0625, 12 * 0.0625, 3 * 0.0625), new AxisAlignedBB(2.5 * 0.0625, 0, 1.5 * 0.0625, 4 * 0.0625, 12 * 0.0625, 3 * 0.0625), new AxisAlignedBB(12 * 0.0625, 0, 13 * 0.0625, 13.5 * 0.0625, 12 * 0.0625, 14.5 * 0.0625), new AxisAlignedBB(2.5 * 0.0625, 0, 13 * 0.0625, 4 * 0.0625, 12 * 0.0625, 14.5 * 0.0625)};

    public BlockGrill(Material material)
    {
        super(material);
        this.setHardness(1.5F);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return facing.getHorizontalIndex() % 2 == 0 ? NORTH_SOUTH_BOUDING_BOX : EAST_WEST_BOUDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState)
    {
        List<AxisAlignedBB> list = getCollisionBoxList(this.getActualState(state, worldIn, pos));
        for(AxisAlignedBB box : list)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
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

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);

        list.add(TOP[facing.getHorizontalIndex()]);
        list.add(GRILL[facing.getHorizontalIndex()]);
        list.addAll(Arrays.asList(LEGS).subList(facing.getHorizontalIndex() * 4, 4 + facing.getHorizontalIndex() * 4));

        return list;
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for(AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for(RayTraceResult raytraceresult : list)
        {
            if(raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if(d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }
}
