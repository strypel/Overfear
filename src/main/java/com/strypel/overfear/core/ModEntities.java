package com.strypel.overfear.core;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Overfear.MODID);
    public static final RegistryObject<EntityType<SpectatorEntity>> SPECTATOR =
            ENTITY_TYPES.register("spectator",
                    () -> EntityType.Builder.of(SpectatorEntity::new, MobCategory.CREATURE)
                            .sized(1.0f,1.75f)
                            .build(new ResourceLocation(Overfear.MODID,"spectator").toString()));
    public static void register(IEventBus bus){
        ENTITY_TYPES.register(bus);
    }
}
