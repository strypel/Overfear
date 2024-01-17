package com.strypel.overfear.phase_actions.actions.core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class Action {
    public abstract void action(Level level, MinecraftServer server, Player player);
}
