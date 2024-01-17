package com.strypel.overfear.phase_actions.triggers.core;

public interface IOnceTrigger {
    default int reactivationTime(){
        return 1000;
    }
    boolean isTimeReactivationType();
}
