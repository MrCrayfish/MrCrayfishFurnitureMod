package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.ParticleSpawner;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.Random;

public class TileEntityShowerHead extends TileEntity implements ITickable
{
    private Random rand = new Random();
    private int timer = 20;

    @Override
    public void update()
    {
        if(this.world.isRemote)
        {
            double posX = pos.getX() + 0.35D + (rand.nextDouble() / 3);
            double posZ = pos.getZ() + 0.35D + (rand.nextDouble() / 3);
            ParticleSpawner.spawnParticle("shower", posX, pos.getY() + 0.065D, posZ);
        }
        else
        {
            if(timer % 5 == 0)
            {
                List<EntityPlayer> listPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, Block.FULL_BLOCK_AABB.offset(pos.down()));
                for(EntityPlayer player : listPlayers)
                {
                    player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));

                    for(int i = 0; i < 4; i++)
                    {
                        ItemStack itemstack = player.inventory.armorInventory.get(i);
                        if(itemstack != ItemStack.EMPTY)
                        {
                            if(itemstack.getItem() instanceof ItemArmor)
                            {
                                ItemArmor armour = (ItemArmor) itemstack.getItem();
                                if(armour.getArmorMaterial() == ArmorMaterial.LEATHER)
                                {
                                    player.inventory.armorInventory.set(i, new ItemStack(itemstack.getItem(), 1, itemstack.getItemDamage()));
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
                world.playSound(null, pos, FurnitureSounds.shower, SoundCategory.BLOCKS, 0.75F, 1.0F);
                timer = 0;
            }
            timer++;
        }
    }
}
