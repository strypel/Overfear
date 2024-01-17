package com.strypel.overfear.phase_actions.actions;

import com.strypel.overfear.capabilities.phase.PlayerPhase;
import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.actions.core.IAffectingPhaseAction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AddPhaseAction extends Action implements IAffectingPhaseAction {
    @Override
    public void action(Level level, MinecraftServer server, Player player) {
        //player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(phase -> {
        //    player.sendSystemMessage(Component.nullToEmpty("Phase is " + phase.getPhase()));
        //});
    }

    @Override
    public PlayerPhase newPhase(PlayerPhase phase) {
        PlayerPhase phaseToReturn = new PlayerPhase();
        phaseToReturn.copyFrom(phase);
        phaseToReturn.addPhase(0.05);
        return phaseToReturn;
    }
}
