package com.strypel.overfear.capabilities.phase;

import net.minecraft.nbt.CompoundTag;

public class PlayerPhase {
    private double phase;
    private final double MIN_PHASE = 0;
    private final double MAX_PHASE = 5;

    public double getPhase() {
        return phase;
    }
    public int getIntPhase() {
        return ((int) Math.floor(phase));
    }

    public void addPhase(double add) {
        this.phase = Math.min(phase + add, MAX_PHASE);
    }

    public void subPhase(double sub) {
        this.phase = Math.max(phase - sub, MIN_PHASE);
    }

    public void copyFrom(PlayerPhase source) {
        this.phase = source.phase;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putDouble("phase", phase);
    }

    public void loadNBTData(CompoundTag nbt) {
        phase = nbt.getDouble("phase");
    }
    public double getMax(){
        return this.MAX_PHASE;
    }
    public double getMin(){
        return this.MIN_PHASE;
    }
}