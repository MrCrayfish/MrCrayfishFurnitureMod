package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.common.mail.MailBox;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.inventory.container.PostBoxMenu;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class PostBoxBlock extends FurnitureHorizontalWaterloggedBlock
{
    private static final TranslatableComponent TITLE = new TranslatableComponent("container.cfm.post_box");

    public final VoxelShape SHAPE;

    public PostBoxBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
        SHAPE = this.generateShape();
    }

    private VoxelShape generateShape()
    {
        List<VoxelShape> shapes = new ArrayList<>();
        shapes.add(Block.box(1, 0, 1, 3, 6, 3));
        shapes.add(Block.box(13, 0, 1, 15, 6, 3));
        shapes.add(Block.box(13, 0, 13, 15, 6, 15));
        shapes.add(Block.box(1, 0, 13, 3, 6, 15));
        shapes.add(Block.box(1, 6, 1, 15, 23, 15));
        return VoxelShapeHelper.combineAll(shapes);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(player instanceof ServerPlayer serverPlayer)
        {
            NetworkHooks.openGui(serverPlayer, this.getMenuProvider(state, level, pos), buffer -> {
                // Send the mailbox data when the player opens the post box
                List<MailBox> mailBoxes = PostOffice.getMailBoxes(serverPlayer);
                CompoundTag compound = new CompoundTag();
                ListTag mailBoxList = new ListTag();
                mailBoxes.forEach(mailBox -> mailBoxList.add(mailBox.serializeDetails()));
                compound.put("MailBoxes", mailBoxList);
                buffer.writeNbt(compound);
            });
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos)
    {
        return new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> {
            return new PostBoxMenu(windowId, playerInventory, ContainerLevelAccess.create(level, pos), ImmutableList.of());
        }, TITLE);
    }
}
