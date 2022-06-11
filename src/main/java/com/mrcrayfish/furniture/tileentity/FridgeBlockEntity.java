package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.block.FridgeBlock;
import com.mrcrayfish.furniture.core.ModBlockEntities;
import com.mrcrayfish.furniture.core.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Author: MrCrayfish
 */
public class FridgeBlockEntity extends BasicLootBlockEntity
{
    protected FridgeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public FridgeBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FRIDGE.get(), pos, state);
    }

    @Override
    public int getContainerSize()
    {
        return 27;
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.translatable("container.cfm.fridge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new ChestMenu(MenuType.GENERIC_9x3, windowId, playerInventory, this, 3);
    }

    @Override
    public void onOpen(Level level, BlockPos pos, BlockState state)
    {
        this.playDoorSound(state, ModSounds.BLOCK_FRIDGE_OPEN.get());
        this.setDoorState(state, true);
    }

    @Override
    public void onClose(Level level, BlockPos pos, BlockState state)
    {
        this.playDoorSound(state, ModSounds.BLOCK_FRIDGE_CLOSE.get());
        this.setDoorState(state, false);
    }

    private void playDoorSound(BlockState state, SoundEvent event)
    {
        Vec3i directionVec = state.getValue(FridgeBlock.DIRECTION).getOpposite().getNormal();
        double x = this.worldPosition.getX() + 0.5D + directionVec.getX() / 2.0D;
        double y = this.worldPosition.getY() + 0.5D + directionVec.getY() / 2.0D;
        double z = this.worldPosition.getZ() + 0.5D + directionVec.getZ() / 2.0D;
        Level level = this.getLevel();
        if(level != null)
        {
            level.playSound(null, x, y, z, event, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    private void setDoorState(BlockState state, boolean open)
    {
        Level level = this.getLevel();
        if(level != null)
        {
            level.setBlock(this.getBlockPos(), state.setValue(FridgeBlock.OPEN, open), 3);
        }
    }
}
