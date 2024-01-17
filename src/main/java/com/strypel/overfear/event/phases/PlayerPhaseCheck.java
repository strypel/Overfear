package com.strypel.overfear.event.phases;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.phase_actions.actions.core.Actions;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Overfear.MODID)
public class PlayerPhaseCheck {
    @SubscribeEvent
    public static void playerCheck(TickEvent.LevelTickEvent event){
        if(Minecraft.getInstance().getConnection() != null && event.level.getServer() != null) {
            if(!event.level.isClientSide){
                for (ServerPlayer player : event.level.getServer().getPlayerList().getPlayers()) {
                    Actions.doActionsFromPlayer(player);
                }
            }
        }
    }
}
