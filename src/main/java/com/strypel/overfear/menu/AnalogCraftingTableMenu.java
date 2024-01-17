package com.strypel.overfear.menu;

import com.strypel.overfear.block.entity.AnalogCraftingTableEntity;
import com.strypel.overfear.core.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class AnalogCraftingTableMenu extends AbstractContainerMenu{
    private final AnalogCraftingTableEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final ContainerData data;
    public AnalogCraftingTableMenu(int containerId, Inventory playerInv, FriendlyByteBuf buf){
        this(containerId,playerInv, playerInv.player.level().getBlockEntity(buf.readBlockPos()),new SimpleContainerData(2));
    }
    public AnalogCraftingTableMenu(int containerId, Inventory playerInv, BlockEntity blockEntity,ContainerData data){
        super(/*ModMenus.ANALOG_CRAFTING_TABLE_MENU.get()*/null,containerId);
        if(blockEntity instanceof AnalogCraftingTableEntity be){
            this.blockEntity = be;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into AnalogCraftingTable!".formatted(blockEntity.getClass().getCanonicalName()));
        }
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(),blockEntity.getBlockPos());
        this.data = data;
        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
        createBlockEntityInventory(this.blockEntity);
        addDataSlots(data);
    }

    private void createBlockEntityInventory(AnalogCraftingTableEntity blockEntity) {
        blockEntity.getOptional().ifPresent(inventory -> {
            for (int row = 0; row < 3; row++) {
                for (int i = 0; i < 3; i++) {
                    addSlot(new SlotItemHandler(inventory,i + (row * 3),30 + (i * 18),17 + (row * 18)));
                }
            }
            addSlot(new SlotItemHandler(inventory,9,124,22){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return false;
                }
            });
            addSlot(new SlotItemHandler(inventory,10,124,49){
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
                addSlot(new Slot(playerInv,9 + i + (row * 9),8 + (i * 18),84 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInv,i,8 + (i * 18),142));
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
    public int getData(){
        return data.get(1);
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        //return stillValid(this.levelAccess,p_38874_, ModBlocks.ANALOG_CRAFTING_TABLE.get());
        return false;
    }

    public AnalogCraftingTableEntity getBlockEntity() {
        return blockEntity;
    }
}
