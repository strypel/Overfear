package com.strypel.overfear.block;

import com.strypel.overfear.block.entity.AnalogCraftingTableEntity;
import com.strypel.overfear.block.entity.util.TickableBlockEntity;
import com.strypel.overfear.core.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class AnalogCraftingTableBlock extends BaseEntityBlock implements IDirectionalBlock{
    public AnalogCraftingTableBlock(Properties p) {
        super(p);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level pLevel, BlockPos pPos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if(!(be instanceof AnalogCraftingTableEntity blockEntity))
            return InteractionResult.PASS;
        if(pLevel.isClientSide)
            return InteractionResult.SUCCESS;

        if(player instanceof ServerPlayer sPlayer){
            sPlayer.openMenu(blockEntity,pPos);
        }
        return InteractionResult.SUCCESS;
    }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55125_) {
       p_55125_.add(FACING);
   }
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext p_55087_) {
       return this.defaultBlockState().setValue(FACING, p_55087_.getHorizontalDirection());
   }
   @Override
   public RenderShape getRenderShape(BlockState p_48727_) {
       return RenderShape.MODEL;
   }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;/*ModBlockEntities.ANALOG_CRAFTING_TABLE.get().create(p_153215_,p_153216_);*/
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return TickableBlockEntity.getTickerHelper(p_153212_);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state_2, boolean p_60519_) {
        if(!level.isClientSide){
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof AnalogCraftingTableEntity){
                ItemStackHandler inventory = ((AnalogCraftingTableEntity) be).getInventory();
                for (int i = 0; i < inventory.getSlots(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    var itemEntity = new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),stack);
                    level.addFreshEntity(itemEntity);
                }
            }
        }
        super.onRemove(state, level, pos, state_2, p_60519_);
    }
}


