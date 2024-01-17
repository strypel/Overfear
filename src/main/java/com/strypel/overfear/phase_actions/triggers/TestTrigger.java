package com.strypel.overfear.phase_actions.triggers;

import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.triggers.core.IOnceTrigger;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TestTrigger extends PhaseTrigger implements IOnceTrigger{

    public TestTrigger(int id,int phase, Action action) {
        super(id,phase, action);
    }

    @Override
    public boolean сondition(Level level, MinecraftServer server, Player player) {
        return true;
    }

    @Override
    public boolean isTimeReactivationType() {
        return false;
    }
}
