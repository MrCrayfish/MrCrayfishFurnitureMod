package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityMailBox;
import com.mrcrayfish.furniture.util.Bounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockMailBox extends BlockFurnitureTile
{
    private static final AxisAlignedBB BOUNDING_BOX = new Bounds(3, 0, 3, 13, 18.6, 13).toAABB();

    public BlockMailBox(Material material)
    {
        super(material);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(1.0F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) placer;
            if(!world.isRemote)
            {
                player.sendMessage(new TextComponentTranslation("cfm.message.mailbox_ownerrequest"));
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile_entity = worldIn.getTileEntity(pos);
        if(!worldIn.isRemote)
        {
            if(tile_entity instanceof TileEntityMailBox)
            {
                TileEntityMailBox tileEntityMailBox = (TileEntityMailBox) tile_entity;
                if(!tileEntityMailBox.hasOwner())
                {
                    tileEntityMailBox.setOwner(playerIn);
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.mailbox_ownerget", TextFormatting.YELLOW + playerIn.getName()));
                    TileEntityUtil.markBlockForUpdate(worldIn, pos);
                    return true;
                }

                tileEntityMailBox.tryAndUpdateName(playerIn);

                if(tileEntityMailBox.canOpen(playerIn))
                {
                    playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
                else
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.mailbox_belong", TextFormatting.YELLOW + tileEntityMailBox.getOwner()));
                }
            }
        }
        return true;
    }

    @Override
    public float getExplosionResistance(Entity entity)
    {
        if(entity instanceof EntityCreeper || entity instanceof EntityTNTPrimed)
        {
            return 1000;
        }
        return super.getExplosionResistance(entity);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMailBox();
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityMailBox mailbox = (TileEntityMailBox) world.getTileEntity(pos);
        return mailbox.getMailCount() > 0 ? 1 : 0;
    }
}
