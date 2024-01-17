package com.strypel.overfear.phase_actions.actions;

import com.strypel.overfear.phase_actions.actions.core.Action;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TestAction extends Action {

    @Override
    public void action(Level level, MinecraftServer server, Player player) {
        player.sendSystemMessage(Component.nullToEmpty("Action is work!"));
    }
}
