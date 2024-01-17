package com.strypel.overfear.phase_actions.actions;

import com.strypel.overfear.core.ModEntities;
import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.utils.AreaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpawnSpectatorAction extends Action {
    private static Logger LOGGER = LogManager.getLogger();
    @Override
    public void action(Level level, MinecraftServer server, Player player) {
        ServerLevel serverLevel = server.getLevel(level.dimension());
        Entity entity = ModEntities.SPECTATOR.get().spawn(serverLevel, AreaUtils.LevelAreaRandomPoint(new BlockPos((int) (player.getX() - 15), (int) (player.getY() - 10),(int) (player.getZ() - 15)),
                new BlockPos((int) (player.getX() + 15), (int) (player.getY()+10),(int) (player.getZ() + 15)),level), MobSpawnType.EVENT);
        LOGGER.info("Entity created: " + entity.getName().getString());
    }
}
