package com.strypel.overfear.capabilities.phase;

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

public class PlayerPhaseProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerPhase> PLAYER_PHASE = CapabilityManager.get(new CapabilityToken<PlayerPhase>() { });

    private PlayerPhase phase = null;
    private final LazyOptional<PlayerPhase> optional = LazyOptional.of(this::createPlayerThirst);

    private PlayerPhase createPlayerThirst() {
        if(this.phase == null) {
            this.phase = new PlayerPhase();
        }

        return this.phase;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_PHASE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerThirst().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerThirst().loadNBTData(nbt);
    }
}