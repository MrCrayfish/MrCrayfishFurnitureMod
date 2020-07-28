package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.CoolerBlock;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class CoolerTileEntity extends BasicLootTileEntity
{
    private int playerCount;

    public CoolerTileEntity()
    {
        super(ModTileEntities.COOLER);
    }

    @Override
    public int getSizeInventory()
    {
        return 9;
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container.cfm.cooler");
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory)
    {
        return new ChestContainer(ContainerType.GENERIC_9X1, windowId, playerInventory, this, 1);
    }

    @Override
    public void openInventory(PlayerEntity playerEntity)
    {
        if(!playerEntity.isSpectator())
        {
            if(this.playerCount < 0)
            {
                this.playerCount = 0;
            }
            this.playerCount++;

            BlockState blockState = this.getBlockState();
            boolean open = blockState.get(CoolerBlock.OPEN);
            if(!open)
            {
                this.playLidSound(blockState, ModSounds.BLOCK_CABINET_OPEN);
                this.setLidState(blockState, true);
            }

            this.scheduleTick();
        }
    }

    @Override
    public void closeInventory(PlayerEntity playerEntity)
    {
        if(!playerEntity.isSpectator())
        {
            this.playerCount--;
        }
    }

    private void scheduleTick()
    {
        this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
    }

    public void onScheduledTick()
    {
        int x = this.pos.getX();
        int y = this.pos.getY();
        int z = this.pos.getZ();
        World world = this.getWorld();
        if(world != null)
        {
            this.playerCount = ChestTileEntity.calculatePlayersUsing(world, this, x, y, z); //Gets a count of players around using this inventory
            if(this.playerCount > 0)
            {
                this.scheduleTick();
            }
            else
            {
                BlockState blockState = this.getBlockState();
                if(!(blockState.getBlock() instanceof CoolerBlock))
                {
                    this.remove();
                    return;
                }

                boolean open = blockState.get(CoolerBlock.OPEN);
                if(open)
                {
                    this.playLidSound(blockState, ModSounds.BLOCK_CABINET_CLOSE);
                    this.setLidState(blockState, false);
                }
            }
        }
    }

    private void playLidSound(BlockState blockState, SoundEvent soundEvent)
    {
        Vector3i directionVec = blockState.get(CoolerBlock.DIRECTION).getDirectionVec();
        double x = this.pos.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.pos.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.pos.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        World world = this.getWorld();
        if(world != null)
        {
            world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.75F, world.rand.nextFloat() * 0.1F + 0.7F);
        }
    }

    private void setLidState(BlockState blockState, boolean open)
    {
        World world = this.getWorld();
        if(world != null)
        {
            world.setBlockState(this.getPos(), blockState.with(CoolerBlock.OPEN, open), 3);
        }
    }
}
