package com.strypel.overfear.event;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.core.ModEntities;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Overfear.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
        event.put(ModEntities.SPECTATOR.get(), SpectatorEntity.setAttributes());
    }
}
