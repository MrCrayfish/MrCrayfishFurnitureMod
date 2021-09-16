package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.ShowerHeadBlock;
import com.mrcrayfish.furniture.core.ModParticles;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.Random;

public class ShowerHeadTileEntity extends TileEntity implements ITickableTileEntity
{
    private Random random = new Random();
    private int timer = 20;

    public ShowerHeadTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public ShowerHeadTileEntity()
    {
        this(ModTileEntities.SHOWER_HEAD.get());
    }

    if()

    @Override
    public void tick()
    {
        if(this.world.isRemote)
        {
            double posX = pos.getX() + 0.35D + (random.nextDouble() / 3);
            double posZ = pos.getZ() + 0.35D + (random.nextDouble() / 3);
            world.addParticle(ModParticles.SHOWER_PARTICLE.get(), posX, pos.getY(), posZ, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            if(timer % 5 == 0)
            {
                List<PlayerEntity> playerEntityList = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos.getX(), pos.getY() - 1, pos.getZ(), pos.getX() + 1.0D, pos.getY() - 1 + 1.0D, pos.getZ() + 1.0D));
                for(PlayerEntity player: playerEntityList)
                {
                    player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));

                    for(int i = 0; i < 4; i++)
                    {
                        ItemStack itemstack = player.inventory.armorInventory.get(i);
                        if(itemstack != ItemStack.EMPTY)
                        {
                            if(itemstack.getItem() instanceof ArmorItem)
                            {
                                ArmorItem armor = (ArmorItem) itemstack.getItem();
                                if(armor.getArmorMaterial() == ArmorMaterial.LEATHER)
                                {
                                   // player.inventory.armorInventory.set(i, new ItemStack(itemstack.getItem(), 1, itemstack.getDamage()));
                                }
                            }
                        }
                    }

                    if(player.isBurning())
                    {
                        player.extinguish();
                    }
                }
            }

            if(timer >= 20)
            {
                world.playSound(null, pos, ModSounds.BLOCK_SHOWER_RUNNING.get(), SoundCategory.BLOCKS, 0.75F, 1.0F);
                timer = 0;
            }
            timer++;
        }
    }

    @Override
    public BlockState getBlockState() {
        world.getBlockState(ShowerHeadBlock.ACTIVATED);
        return super.getBlockState();
    }
}
