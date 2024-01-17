package com.strypel.overfear.event;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.capabilities.triggersused.PlayerTriggersUsed;
import com.strypel.overfear.capabilities.triggersused.PlayerTriggersUsedProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Mod.EventBusSubscriber(modid = Overfear.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!(event.getObject()).getCapability(PlayerPhaseProvider.PLAYER_PHASE).isPresent()) {
                event.addCapability(new ResourceLocation(Overfear.MODID, "phase"), new PlayerPhaseProvider());
            }
        }
        if(event.getObject() instanceof Player) {
            if(!(event.getObject()).getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).isPresent()) {
                event.addCapability(new ResourceLocation(Overfear.MODID, "triggers_used"), new PlayerTriggersUsedProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerPhaseProvider.class);
        event.register(PlayerTriggersUsedProvider.class);
    }
}
