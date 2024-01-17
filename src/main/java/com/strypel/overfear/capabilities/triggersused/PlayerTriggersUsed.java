package com.strypel.overfear.capabilities.triggersused;

import com.mojang.serialization.Codec;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import net.minecraft.nbt.CompoundTag;

import java.util.*;

public class PlayerTriggersUsed {
    private Map<Integer,Integer> triggers_used = new HashMap<>();

    public Map<Integer,Integer> getTriggers() {
        return triggers_used;
    }

    public void addPhase(PhaseTrigger trigger,int time) {
        this.triggers_used.put(trigger.getId(),time);
    }

    public void subPhase(PhaseTrigger trigger) {
        //for (int i = 0; i < triggers_used.size(); i++) {
        //    if(triggers_used.get(i) == trigger.getId()){
        //        triggers_used.remove(i);
        //    }
        //}
        triggers_used.remove(trigger.getId());
    }

    public void copyFrom(PlayerTriggersUsed source) {
        this.triggers_used = source.triggers_used;
    }

    public void saveNBTData(CompoundTag tag) {
        int[] keys = new int[this.triggers_used.size()];
        for (int i = 0; i < keys.length; i++) {
            if(this.triggers_used.containsKey(i)){
                keys[i] = i;
            }
        }
        int[] values = new int[this.triggers_used.size()];
        for (int i = 0; i < values.length; i++) {
            if(this.triggers_used.get(i) != null){
                values[i] = this.triggers_used.get(i);
            }
        }
        tag.putIntArray("triggers_used_key",keys);
        tag.putIntArray("triggers_used_time",values);
    }

    public void loadNBTData(CompoundTag tag) {
        int[] b = tag.getIntArray("triggers_used_key");
        int[] a = tag.getIntArray("triggers_used_time");
        for (int i = 0; i < b.length; i++) {
            this.triggers_used.put(b[i],a[i]);
        }
    }
}
