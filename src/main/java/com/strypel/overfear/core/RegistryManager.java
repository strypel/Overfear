package com.strypel.overfear.core;

import com.strypel.overfear.network.PacketHandler;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTriggers;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

public class RegistryManager {
    public static void setupRegistries(@NotNull IEventBus modbus) {
        ModItems.register(modbus);
        ModBlocks.register(modbus);
        ModBlockEntities.register(modbus);
        ModMenus.register(modbus);
        ModRecipes.register(modbus);
        ModEntities.register(modbus);
        ModSounds.register(modbus);
        PhaseTriggers.register();
        PacketHandler.register();
        ModAttributes.ATTRIBUTES.register(modbus);
    }

}
