package com.strypel.overfear.block.entity;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.block.entity.util.TickableBlockEntity;
import com.strypel.overfear.core.ModItems;
import com.strypel.overfear.menu.AnalogCraftingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnalogCraftingTableEntity extends BlockEntity implements TickableBlockEntity, MenuProvider {
    private static final Component TITLE = Component.translatable("block.overfear.analog_crafting_table");
    int ticks;
    int data;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int p_58431_) {
            switch (p_58431_) {
                case 0:
                    return AnalogCraftingTableEntity.this.ticks;
                case 1:
                    return AnalogCraftingTableEntity.this.data;
                default:
                    return 0;
            }
        }

        public void set(int p_58433_, int p_58434_) {
            switch (p_58433_) {
                case 0:
                    AnalogCraftingTableEntity.this.ticks = p_58434_;
                    break;
                case 1:
                    AnalogCraftingTableEntity.this.data = p_58434_;
                    break;
            }

        }

        public int getCount() {
            return 2;
        }
    };
    private final ItemStackHandler inventory = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            AnalogCraftingTableEntity.this.setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.inventory);
    public AnalogCraftingTableEntity(BlockPos pos, BlockState state) {
        super(null/*ModBlockEntities.ANALOG_CRAFTING_TABLE.get()*/, pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag overfeardata = nbt.getCompound(Overfear.MODID);
        this.inventory.deserializeNBT(overfeardata.getCompound("inventory"));
        this.ticks = overfeardata.getInt("ticks");
        this.ticks = overfeardata.getInt("data");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        var overfeardata = new CompoundTag();
        overfeardata.put("inventory",this.inventory.serializeNBT());
        tag.put(Overfear.MODID,overfeardata);
        tag.putInt("ticks",this.ticks);
        tag.putInt("data",this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.optional.cast();
        } else {
            return super.getCapability(cap);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optional.invalidate();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }
    public ItemStack getSlotInInventory(int slot){
        return inventory.getStackInSlot(slot);
    }
    public void setSlotInInventory(int slot,ItemStack itemStack){
        this.inventory.setStackInSlot(slot,itemStack);
    }
    public LazyOptional<ItemStackHandler> getOptional() {
        return optional;
    }

    public int getTicks() {
        return ticks;
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide())
            return;

        if(this.inventory.getStackInSlot(10).is(ModItems.TAPE.get())){
            int i = this.inventory.getStackInSlot(10).getCount();
            this.data = i;
            setChanged();
        } else {
            this.data = 0;
            setChanged();
        }
        //
        if(this.inventory.getStackInSlot(0).is(ModItems.ALUMINUM_INGOT.get()) && (this.data * 100 / 64) >= 6){
                if(ticks++ >= 100){
                    this.inventory.extractItem(0,1,false);
                    this.inventory.insertItem(9,new ItemStack(ModItems.FOIL.get(),4),false);
                    ticks = 0;
                }
                setChanged();
        } else {
            this.ticks = 0;
            setChanged();
        }
    }
    //private void craftItem(){
    //    AnalogCraftingTableRecipe recipe = getCurrentRecipe();
    //    ItemStack result = recipe.getResultItem(null);
        //
    //    this.inventory.extractItem(0, 1, false);
    //
    //    this.inventory.setStackInSlot(9, new ItemStack(result.getItem(),
    //            this.inventory.getStackInSlot(9).getCount() + result.getCount()));
    //}
    //private boolean hasRecipe(){
    //    AnalogCraftingTableRecipe recipe = getCurrentRecipe();
    //    if(recipe == null){
    //        System.out.println("Recipe is null!");
    //        return false;
    //    }
    //    ItemStack result = recipe.getResultItem(getLevel().registryAccess());
    //    return /*canInsertAmountIntoOutputSlot(result.getCount()) && */canInsertItemIntoOutputSlot(result.getItem());
    //}
    //private boolean canInsertItemIntoOutputSlot(Item item) {
    //    return this.inventory.getStackInSlot(9).isEmpty() || this.inventory.getStackInSlot(9).is(item);
    //}
    //private boolean canInsertAmountIntoOutputSlot(int count) {
    //    return this.inventory.getStackInSlot(9).getCount() + count <= this.inventory.getStackInSlot(9).getMaxStackSize();
    //}
    //
    //private AnalogCraftingTableRecipe getCurrentRecipe() {
    //    SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots());
    //    for (int i = 0; i < this.inventory.getSlots(); i++) {
    //        inventory.setItem(i,this.inventory.getStackInSlot(i));
    //    }
    //
    //    return this.level.getRecipeManager().getRecipeFor(AnalogCraftingTableRecipe.Type.INSTANCE,inventory,level).map(recipeHolder -> recipeHolder.value()).orElse(null);
    //}

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new AnalogCraftingTableMenu(p_39954_,p_39955_,this,dataAccess);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }
}
