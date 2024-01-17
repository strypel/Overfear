package com.strypel.overfear.menu;

import com.strypel.overfear.block.entity.DataGeneratorEntity;
import com.strypel.overfear.core.ModBlocks;
import com.strypel.overfear.core.ModItems;
import com.strypel.overfear.core.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class DataGeneratorMenu extends AbstractContainerMenu {
    private final DataGeneratorEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final ContainerData data;
    public DataGeneratorMenu(int containerId, Inventory playerInv, FriendlyByteBuf buf){
        this(containerId,playerInv, playerInv.player.level().getBlockEntity(buf.readBlockPos()),new SimpleContainerData(5));
    }
    public DataGeneratorMenu(int containerId, Inventory playerInv, BlockEntity blockEntity, ContainerData data){
        super(ModMenus.DATA_GENERATOR_MENU.get(),containerId);
        if(blockEntity instanceof DataGeneratorEntity be){
            this.blockEntity = be;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into DataGenerator!".formatted(blockEntity.getClass().getCanonicalName()));
        }
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(),blockEntity.getBlockPos());
        this.data = data;
        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
        createBlockEntityInventory(this.blockEntity);
        addDataSlots(data);
    }

    private void createBlockEntityInventory(DataGeneratorEntity blockEntity) {
        blockEntity.getOptional().ifPresent(inventory -> {
            //for (int row = 0; row < 3; row++) {
            //    for (int i = 0; i < 3; i++) {
            //        addSlot(new SlotItemHandler(inventory,i + (row * 3),30 + (i * 18),17 + (row * 18)));
            //    }
            //}
            addSlot(new SlotItemHandler(inventory,0,139,94){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(ModItems.TAPE.get());
                }
            });
        });
    }
    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int i = 0; i < 9; i++) {
                addSlot(new Slot(playerInv,9 + i + (row * 9),8 + (i * 18),120 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInv,i,8 + (i * 18),178));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        Slot fromSlot = getSlot(p_38942_);
        ItemStack fromStack = fromSlot.getItem();

        if(fromStack.getCount() <= 0){
            fromSlot.set(ItemStack.EMPTY);
        }
        if(!fromSlot.hasItem()){
            return ItemStack.EMPTY;
        }
        ItemStack copyFromStack = fromStack.copy();

        if(p_38942_ < 36){
            if(!moveItemStackTo(fromStack,36,47,false))
                return ItemStack.EMPTY;
        } else if (p_38942_ < 47){
            if(!moveItemStackTo(fromStack,0,36,false))
                return ItemStack.EMPTY;
        } else {
            System.err.println("Invalid slot index: " + p_38942_);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(p_38941_,fromStack);

        return copyFromStack;
    }
    public int getProgress(){
        return data.get(0);
    }
    public int getPointerSpeed(){
        return data.get(1);
    }
    public int getCur_s(){
        return data.get(3);
    }
    public int getReq_s(){
        return data.get(4);
    }
    public boolean tapeSlotIsFull(){
        return data.get(2) > 0;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return stillValid(this.levelAccess,p_38874_, ModBlocks.DATA_GENERATOR.get());
    }

    public DataGeneratorEntity getBlockEntity() {
        return blockEntity;
    }
}
