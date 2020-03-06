package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.BedsideCabinetBlock;
import com.mrcrayfish.furniture.core.ModSounds;
import com.mrcrayfish.furniture.core.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class BedsideCabinetTileEntity extends BasicLootTileEntity
{
    private int playerCount;

    public BedsideCabinetTileEntity()
    {
        super(ModTileEntities.BEDSIDE_CABINET);
    }

    /**
     * Alternative Constructor useful when reusing this class.
     * Needed because registering tile entities requires valid blocks.
     * @see TileEntityType
     * @param tileEntityType : The TileEntityType you use instead.
     */
    public BedsideCabinetTileEntity(TileEntityType<?> tileEntityType) { super(tileEntityType); }


    @Override
    public int getSizeInventory()
    {
        return 9;
    }

    @Override
    protected ITextComponent getDefaultName()
    {
        return new TranslationTextComponent("container.cfm.bedside_cabinet");
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
            boolean open = blockState.get(BedsideCabinetBlock.OPEN);
            if(!open)
            {
                this.playDoorSound(blockState, ModSounds.BLOCK_BEDSIDE_CABINET_OPEN);
                this.setDoorState(blockState, true);
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
            this.playerCount = ChestTileEntity.func_213976_a(world, this, x, y, z); //Gets a count of players around using this inventory
            if(this.playerCount > 0)
            {
                this.scheduleTick();
            }
            else
            {
                BlockState blockState = this.getBlockState();
                if(!(blockState.getBlock() instanceof BedsideCabinetBlock))
                {
                    this.remove();
                    return;
                }

                boolean open = blockState.get(BedsideCabinetBlock.OPEN);
                if(open)
                {
                    this.playDoorSound(blockState, ModSounds.BLOCK_BEDSIDE_CABINET_CLOSE); //TODO change
                    this.setDoorState(blockState, false);
                }
            }
        }
    }

    private void playDoorSound(BlockState blockState, SoundEvent soundEvent)
    {
        Vec3i directionVec = blockState.get(BedsideCabinetBlock.DIRECTION).getDirectionVec();
        double x = this.pos.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.pos.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.pos.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        World world = this.getWorld();
        if(world != null)
        {
            world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

    private void setDoorState(BlockState blockState, boolean open)
    {
        World world = this.getWorld();
        if(world != null)
        {
            world.setBlockState(this.getPos(), blockState.with(BedsideCabinetBlock.OPEN, open), 3);
        }
    }
}
