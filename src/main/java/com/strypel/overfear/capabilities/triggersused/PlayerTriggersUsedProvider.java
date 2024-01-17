package com.strypel.overfear.capabilities.triggersused;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerTriggersUsedProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerTriggersUsed> PLAYER_TRIGGER_USED = CapabilityManager.get(new CapabilityToken<PlayerTriggersUsed>() { });

    private PlayerTriggersUsed used = null;
    private final LazyOptional<PlayerTriggersUsed> optional = LazyOptional.of(this::createPlayerTriggerUsed);

    private PlayerTriggersUsed createPlayerTriggerUsed() {
        if(this.used == null) {
            this.used = new PlayerTriggersUsed();
        }

        return this.used;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_TRIGGER_USED) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerTriggerUsed().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerTriggerUsed().loadNBTData(nbt);
    }
}
