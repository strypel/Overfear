package com.strypel.overfear.block.entity;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.block.entity.util.TickableBlockEntity;
import com.strypel.overfear.core.ModBlockEntities;
import com.strypel.overfear.core.ModItems;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import com.strypel.overfear.items.tapeInfo.TapeInfos;
import com.strypel.overfear.menu.DataGeneratorMenu;
import com.strypel.overfear.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DataGeneratorEntity extends BlockEntity implements MenuProvider,GeoBlockEntity, TickableBlockEntity {
    private static final RawAnimation ON_USE = RawAnimation.begin().thenPlay("animation.model.onuse").thenLoop("animation.model.onuse_loop");
    private static final RawAnimation ON_OFF = RawAnimation.begin().thenPlay("animation.model.onoff");
    private AnimatableInstanceCache cash = GeckoLibUtil.createInstanceCache(this);
    int ticks = 0;
    public int pointer_speed = 0;
    int tapeSlot = 0;
    int current_s = 0;
    float f_current_s = 0F;
    int required_s = 20;
    boolean required_flag = false;

    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int p_58431_) {
            switch (p_58431_) {
                case 0:
                    return DataGeneratorEntity.this.ticks;
                case 1:
                    return DataGeneratorEntity.this.pointer_speed;
                case 2:
                    return DataGeneratorEntity.this.tapeSlot;
                case 3:
                    return DataGeneratorEntity.this.current_s;
                case 4:
                    return DataGeneratorEntity.this.required_s;
                default:
                    return 0;
            }
        }

        public void set(int p_58433_, int p_58434_) {
            switch (p_58433_) {
                case 0:
                    DataGeneratorEntity.this.ticks = p_58434_;
                    break;
                case 1:
                    DataGeneratorEntity.this.pointer_speed = p_58434_;
                case 2:
                    DataGeneratorEntity.this.tapeSlot = p_58434_;
                    break;
                case 3:
                    DataGeneratorEntity.this.current_s = p_58434_;
                    break;
                case 4:
                    DataGeneratorEntity.this.required_s = p_58434_;
                    break;
            }

        }

        public int getCount() {
            return 5;
        }
    };
    private final ItemStackHandler inventory = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            DataGeneratorEntity.this.setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.inventory);

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag overfeardata = nbt.getCompound(Overfear.MODID);
        if(overfeardata.isEmpty())
            return;
        if (overfeardata.contains("inventory", Tag.TAG_COMPOUND)) {
            this.inventory.deserializeNBT(overfeardata.getCompound("inventory"));
        }
        if (overfeardata.contains("ticks", Tag.TAG_INT)) {
            this.ticks = overfeardata.getInt("ticks");
        }
        if (overfeardata.contains("data", Tag.TAG_INT)) {
            this.pointer_speed = overfeardata.getInt("data");
        }
        if (overfeardata.contains("tapeSlot", Tag.TAG_INT)) {
            this.tapeSlot = overfeardata.getInt("tapeSlot");
        }
        if (overfeardata.contains("current_s", Tag.TAG_INT)) {
            this.current_s = overfeardata.getInt("current_s");
        }
        if (overfeardata.contains("f_current_s", Tag.TAG_FLOAT)) {
            this.f_current_s = overfeardata.getFloat("f_current_s");
        }
        if (overfeardata.contains("required_s", Tag.TAG_INT)) {
            this.required_s = overfeardata.getInt("required_s");
        }
        this.required_flag = overfeardata.getBoolean("required_f");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        var overfeardata = new CompoundTag();
        overfeardata.put("inventory",this.inventory.serializeNBT());
        overfeardata.putInt("ticks",this.ticks);
        overfeardata.putInt("data",this.pointer_speed);
        overfeardata.putInt("tapeSlot",this.tapeSlot);
        overfeardata.putInt("current_s",this.current_s);
        overfeardata.putFloat("f_current_s",this.f_current_s);
        overfeardata.putInt("required_s",this.required_s);
        overfeardata.putBoolean("required_f",this.required_flag);
        tag.put(Overfear.MODID,overfeardata);
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
    public DataGeneratorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DATA_GENERATOR.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "data_generator_controller", 0, this::predicate)
                .triggerableAnim("onuse", ON_USE)
                .triggerableAnim("onoff", ON_OFF)
                .setSoundKeyframeHandler(state -> {
                }));
    }
    private PlayState predicate(AnimationState<DataGeneratorEntity> tAnimationState) {
        boolean flag = false;
        if(this.tapeSlot > 0){
            ItemStack stack = this.getSlotInInventory(0);
            if(stack.is(ModItems.TAPE.get())){
                CompoundTag tag = stack.getTag();
                if(tag != null && tag.contains("data")){
                    float data = tag.getFloat("data");
                    if(data < 100){
                        flag = true;
                    }
                }
            }
        }
        if(flag){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.active_loop", Animation.LoopType.LOOP));
        } else {
            this.triggerAnim("data_generator_controller","onoff");
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cash;
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Data generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new DataGeneratorMenu(p_39954_,p_39955_,this,this.dataAccess);
    }
    public float processingEfficiency(){
        return (float) (100 - Math.abs(this.current_s - this.required_s)) / 100;
    }
    public float scannedPercentage(){
        ItemStack stack = this.getSlotInInventory(0);
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.contains("data")){
            float data = tag.getFloat("data");
            return data;
        }
        return 0;
    }
    //public List<Component> getTextOnTape(){
    //    ItemStack stack = this.getSlotInInventory(0);
    //    CompoundTag tag = stack.getTag();
    //    if(tag != null && tag.contains("info")){
    //        String tapeInfoId = tag.getString("info");
    //        List<Component> info = TapeInfos.getTapeInfoById(tapeInfoId).getText();
    //        return info;
    //    }
    //    return null;
    //}

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide())
            return;
        if(this.pointer_speed >= 0){
            if(this.f_current_s < 100){
                this.f_current_s += (float) this.pointer_speed / 20;
            }
        } else {
            if(this.f_current_s > 0){
                this.f_current_s += (float) this.pointer_speed / 20;
            }
        }
        this.current_s = (int) f_current_s;
        if(this.getSlotInInventory(0).is(ModItems.TAPE.get())){
            this.tapeSlot = 1;
            if(!this.required_flag){
                this.required_s = (int) RandomUtils.rnd(0,100);
                this.required_flag = true;
            }
        } else {
            this.tapeSlot = 0;
            this.required_flag = false;
        }
        if(this.tapeSlot > 0){
            float time = 600 / this.processingEfficiency();
            CompoundTag tag = this.getSlotInInventory(0).getTag();
            if(tag == null){
                tag = new CompoundTag();
            }
            float data = 0;
            if(tag.contains("data")){
                data = tag.getFloat("data");
            }
            if(!tag.contains("info")){
                tag.putString("info", TapeInfos.getRandomTapeInfo().getId());
            }
            tag.putFloat("data",Math.min(data + (100F / time), 100F));
            this.getSlotInInventory(0).setTag(tag);
        }
        if(this.ticks < 6000){
            this.ticks++;
        } else {
            this.required_s += (int) RandomUtils.rnd(-5,5);
            this.ticks = 0;
        }
        sendUpdate();
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    public void sendUpdate() {
        setChanged();

        if(this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }
}
