package com.mrcrayfish.furniture.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.common.mail.Mail;
import com.mrcrayfish.furniture.common.mail.PostOffice;
import com.mrcrayfish.furniture.tileentity.MailBoxTileEntity;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class MailBoxBlock extends FurnitureHorizontalWaterloggedBlock
{
    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public MailBoxBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));
        SHAPES = this.generateShapes(this.getStateContainer().getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        final VoxelShape[] POST = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(6.5, 0, 6.5, 9.5, 13, 9.5), Direction.SOUTH));
        final VoxelShape[] BOX = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Block.makeCuboidShape(4, 13, 2, 12, 22, 14), Direction.SOUTH));

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for(BlockState state : states)
        {
            Direction direction = state.get(DIRECTION);
            List<VoxelShape> shapes = new ArrayList<>();
            shapes.add(POST[direction.getHorizontalIndex()]);
            shapes.add(BOX[direction.getHorizontalIndex()]);
            builder.put(state, VoxelShapeHelper.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return SHAPES.get(state);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        if(entity instanceof ServerPlayerEntity)
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof MailBoxTileEntity)
            {
                MailBoxTileEntity mailBox = (MailBoxTileEntity) tileEntity;
                mailBox.setId(UUID.randomUUID());
                mailBox.setOwner((ServerPlayerEntity) entity);
                mailBox.setMailBoxName("Mail Box");
                PostOffice.registerMailBox((ServerPlayerEntity) entity, mailBox.getId(), "Mail Box", pos);
                TileEntityUtil.sendUpdatePacket(tileEntity);
            }
        }
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(!world.isRemote)
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof MailBoxTileEntity)
            {
                MailBoxTileEntity mailBox = (MailBoxTileEntity) tileEntity;
                if(mailBox.getId() != null && mailBox.getOwnerId() != null)
                {
                    /* Drops all items that were queue to be inserted into mail box */
                    Supplier<Mail> supplier = PostOffice.getMailForPlayerMailBox(mailBox.getOwnerId(), mailBox.getId());
                    while(true)
                    {
                        Mail mail = supplier.get();
                        if(mail == null) break;
                        InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), mail.getStack());
                    }

                    /* Unregisters the mail box */
                    PostOffice.unregisterMailBox(mailBox.getOwnerId(), mailBox.getId());
                }
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult result)
    {
        if(!world.isRemote())
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof MailBoxTileEntity)
            {
                MailBoxTileEntity mailBox = (MailBoxTileEntity) tileEntity;
                if(playerEntity.getUniqueID().equals(mailBox.getOwnerId()))
                {
                    if(!playerEntity.getName().getString().equals(mailBox.getOwnerName()))
                    {
                        mailBox.setOwnerName(playerEntity.getName().getString());
                    }
                }
                TileEntityUtil.sendUpdatePacket(tileEntity);
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new MailBoxTileEntity();
    }
}
