package com.strypel.overfear.phase_actions.actions;

import com.strypel.overfear.capabilities.phase.PlayerPhase;
import com.strypel.overfear.core.ModSounds;
import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.actions.core.IAffectingPhaseAction;
import com.strypel.overfear.phase_actions.triggers.core.PhantomSound;
import com.strypel.overfear.utils.AreaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class RandomLiteSoundAction extends Action implements IAffectingPhaseAction {
    @Override
    public void action(Level level, MinecraftServer server, Player player) {
        List<PhantomSound> RANDOM_SOUNDS = List.of(
                new PhantomSound(SoundEvents.GRAVEL_BREAK,0.8,0.3F),
                new PhantomSound(SoundEvents.STONE_PLACE,0.9,0.3F),
                new PhantomSound(SoundEvents.WOOD_BREAK,0.8,0.8F),
                new PhantomSound(ModSounds.GRAVEL_RUN_1.get(),0.9,0.8F),
                new PhantomSound(ModSounds.SPECTATOR_1.get(),0.4,0.8F),
                new PhantomSound(ModSounds.WOOD_PLACE.get(),0.9,0.4F)
                //new PhantomSound(ModSounds.COUGH.get(),1,0.5F)
        );
        SoundEvent soundEvent = RANDOM_SOUNDS.get(new Random().nextInt((RANDOM_SOUNDS.size() - 1) - 0 + 1) + 0).getSoundRandom();
        if(soundEvent != null){
            level.playSound((Player) null, AreaUtils.LevelAreaRandomPoint(new BlockPos((int) (player.getX() - 10), (int) (player.getY() - 20), (int) (player.getZ() - 10)),
                    new BlockPos((int) (player.getX() + 10), (int) (player.getY() + 20), (int) (player.getZ() + 10)), level), soundEvent, SoundSource.AMBIENT, 1.0F, 1.0F);
        } else {
            while (soundEvent == null){
                soundEvent = RANDOM_SOUNDS.get(new Random().nextInt((RANDOM_SOUNDS.size() - 1) - 0 + 1) + 0).getSoundRandom();
                if(soundEvent != null){
                    level.playSound((Player) null, AreaUtils.LevelAreaRandomPoint(new BlockPos((int) (player.getX() - 10), (int) (player.getY() - 20), (int) (player.getZ() - 10)),
                            new BlockPos((int) (player.getX() + 10), (int) (player.getY() + 20), (int) (player.getZ() + 10)), level), soundEvent, SoundSource.AMBIENT, 1.0F, 1.0F);
                }
            }
        }
    }

    @Override
    public PlayerPhase newPhase(PlayerPhase phase) {
        PlayerPhase phaseToReturn = new PlayerPhase();
        phaseToReturn.copyFrom(phase);
        phaseToReturn.addPhase(0.05);
        return phaseToReturn;
    }
}
