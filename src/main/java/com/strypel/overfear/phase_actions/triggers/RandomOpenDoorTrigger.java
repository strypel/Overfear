package com.strypel.overfear.phase_actions.triggers;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.capabilities.triggersused.PlayerTriggersUsedProvider;
import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.triggers.core.IOnceTrigger;
import com.strypel.overfear.phase_actions.triggers.core.IRandomTrigger;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import com.strypel.overfear.utils.AreaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicBoolean;

public class RandomOpenDoorTrigger extends PhaseTrigger implements IRandomTrigger, IOnceTrigger {
    public RandomOpenDoorTrigger(int id, int phase, Action action) {
        super(id, phase, action);
    }

    @Override
    public boolean сondition(Level level, MinecraftServer server, Player player) {
        return true;
    }

    @Override
    public boolean isTrue(Level level, MinecraftServer server, Player player) {
        if(this.randomIsTrue()) {
            AtomicBoolean t = new AtomicBoolean(false);
            player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(playerPhase -> {
                player.getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).ifPresent(playerTriggersUsed -> {
                    if (!playerTriggersUsed.getTriggers().containsKey(this.getId()) && Math.floor(playerPhase.getIntPhase()) == this.getPhase()) {
                        BlockPos b = AreaUtils.getNearestDoorFromArea(new BlockPos((int) (player.getX() - 8), (int) (player.getY() - 5), (int) (player.getZ() - 8)),
                                new BlockPos((int) (player.getX() + 8), (int) (player.getY() + 5), (int) (player.getZ() + 8)), level);
                        if (b != null) {
                            BlockState bst = level.getBlockState(b);
                            if (bst.getBlock() instanceof DoorBlock door) {
                                door.setOpen((Player) null, level, bst, b, !door.isOpen(bst));
                                playerTriggersUsed.addPhase(this, (int) level.getDayTime());
                                t.set(true);
                            }
                        }
                    }
                });
            });
            return t.get();
        }
        return false;
    }

    @Override
    public double probability() {
        return 0.000003588888;
    }

    @Override
    public int reactivationTime() {
        return 5000;
    }

    @Override
    public boolean isTimeReactivationType() {
        return true;
    }
}
