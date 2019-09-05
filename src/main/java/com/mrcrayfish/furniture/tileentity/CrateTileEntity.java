package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.CabinetBlock;
import com.mrcrayfish.furniture.block.CrateBlock;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class CrateTileEntity extends BasicLootTileEntity
{
    private int playerCount;

    public CrateTileEntity()
    {
        super(ModTileEntities.CRATE);
    }

    @Override
    public int getSizeInventory()
    {
        return 27;
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container.cfm.crate");
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory)
    {
        return new CrateContainer(windowId, playerInventory, this);
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
            boolean open = blockState.get(CrateBlock.OPEN);
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
            this.updatePlayerCount(world, this, x, y, z);
            if(this.playerCount > 0)
            {
                this.scheduleTick();
            }
            else
            {
                BlockState blockState = this.getBlockState();
                if(!(blockState.getBlock() instanceof CrateBlock))
                {
                    this.remove();
                    return;
                }

                boolean open = blockState.get(CrateBlock.OPEN);
                if(open)
                {
                    this.playLidSound(blockState, ModSounds.BLOCK_CABINET_CLOSE);
                    this.setLidState(blockState, false);
                }
            }
        }
    }

    private void updatePlayerCount(World world, IInventory inventory, int x, int y, int z)
    {
        this.playerCount = 0;
        for(PlayerEntity playerentity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double) ((float) x - 5.0F), (double) ((float) y - 5.0F), (double) ((float) z - 5.0F), (double) ((float) (x + 1) + 5.0F), (double) ((float) (y + 1) + 5.0F), (double) ((float) (z + 1) + 5.0F))))
        {
            if(playerentity.openContainer instanceof CrateContainer)
            {
                IInventory crateInventory = ((CrateContainer) playerentity.openContainer).getCrateInventory();
                if(inventory == crateInventory)
                {
                    this.playerCount++;
                }
            }
        }
    }

    private void playLidSound(BlockState blockState, SoundEvent soundEvent)
    {
        Vec3i directionVec = blockState.get(CabinetBlock.DIRECTION).getDirectionVec();
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
            world.setBlockState(this.getPos(), blockState.with(CrateBlock.OPEN, open), 3);
        }
    }
}
