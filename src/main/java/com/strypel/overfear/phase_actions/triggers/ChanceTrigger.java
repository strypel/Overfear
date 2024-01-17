package com.strypel.overfear.phase_actions.triggers;

import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.triggers.core.IRandomTrigger;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ChanceTrigger extends PhaseTrigger implements IRandomTrigger {
    private float chance = 0F;

    public ChanceTrigger(int id, int phase, Action action, float chance) {
        super(id,phase, action);
        this.chance = chance;
    }

    @Override
    public boolean сondition(Level level, MinecraftServer server, Player player) {
        return this.randomIsTrue();
    }


    @Override
    public double probability() {
        return chance;
    }
}
