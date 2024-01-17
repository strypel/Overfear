package com.strypel.overfear.phase_actions.triggers;

import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import com.strypel.overfear.phase_actions.triggers.core.IRandomTrigger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;

public class SpawnSpectatorTrigger extends PhaseTrigger implements IRandomTrigger {
    private static List<ResourceKey<Biome>> SPECTATOR_BIOMES = List.of(
            Biomes.FOREST,
            Biomes.DARK_FOREST,
            Biomes.TAIGA,
            Biomes.SNOWY_TAIGA,
            Biomes.SNOWY_BEACH,
            Biomes.BIRCH_FOREST
            );
    public SpawnSpectatorTrigger(int id, int phase, Action action) {
        super(id, phase, action);
    }

    @Override
    public boolean сondition(Level level, MinecraftServer server, Player player) {
        for (ResourceKey<Biome> biome : SPECTATOR_BIOMES){
            if(level.getBiomeManager().getBiome(player.getOnPos()).is(biome)){
                return this.randomIsTrue();
            }
        }
        return false;
    }


    @Override
    public double probability() {
        return 0.00001588888;
    }
}
