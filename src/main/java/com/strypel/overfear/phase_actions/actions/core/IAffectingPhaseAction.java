package com.strypel.overfear.phase_actions.actions.core;

import com.strypel.overfear.capabilities.phase.PlayerPhase;

public interface IAffectingPhaseAction {
    PlayerPhase newPhase(PlayerPhase phase);
}
