package com.strypel.overfear.phase_actions.triggers;

import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.triggers.core.IRandomTrigger;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RandomItemTrigger extends PhaseTrigger implements IRandomTrigger {

    public RandomItemTrigger(int id, int phase, Action action) {
        super(id,phase, action);
    }

    @Override
    public boolean сondition(Level level, MinecraftServer server, Player player) {
        return this.randomIsTrue();
    }


    @Override
    public double probability() {
        return 0.00001388888;
    }
}
