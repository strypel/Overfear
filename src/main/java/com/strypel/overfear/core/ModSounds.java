package com.strypel.overfear.core;

import com.strypel.overfear.Overfear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Overfear.MODID);

    public static final RegistryObject<SoundEvent> SPECTATOR_SOUND_IDLE = registerSoundEvent("spectator_sound_idle");
    public static final RegistryObject<SoundEvent> COUGH = registerSoundEvent("cough_1");
    public static final RegistryObject<SoundEvent> GRAVEL_RUN_1 = registerSoundEvent("gravel_run_1");
    public static final RegistryObject<SoundEvent> SPECTATOR_1 = registerSoundEvent("spectator_1");
    public static final RegistryObject<SoundEvent> WOOD_PLACE = registerSoundEvent("wood_place_1");
    public static final RegistryObject<SoundEvent> ACTIVITY_ANALYZER_CHECK = registerSoundEvent("activity_analyzer_check");
    public static final RegistryObject<SoundEvent> ACTIVITY_ANALYZER_WARNING = registerSoundEvent("activity_analyzer_warning");
    public static final RegistryObject<SoundEvent> ACTIVITY_ANALYZER_CLACK = registerSoundEvent("activity_analyzer_clack");
    public static final RegistryObject<SoundEvent> FIRE_IN_THE_HOLE = registerSoundEvent("fire_in_the_hole");
    public static final RegistryObject<SoundEvent> GLITCH_01 = registerSoundEvent("glitch_01");
    public static final RegistryObject<SoundEvent> SCREAMER_01 = registerSoundEvent("screamer_1");
    /* public static final RegistryObject<SoundEvent> COOL_SOUND = registerSoundEvent("cool_sound");

    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_BREAK = registerSoundEvent("animated_block_break");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_WALK = registerSoundEvent("animated_block_walk");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_PLACE = registerSoundEvent("animated_block_place");
    public static final RegistryObject<SoundEvent> ANIMATED_BLOCK_HIT = registerSoundEvent("animated_block_hit");

    public static final RegistryObject<SoundEvent> MUSIC_BOX = registerSoundEvent("music_box");

    public static final ForgeSoundType ANIMATED_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.ANIMATED_BLOCK_BREAK, ModSounds.ANIMATED_BLOCK_WALK, ModSounds.ANIMATED_BLOCK_PLACE,
            ModSounds.ANIMATED_BLOCK_HIT, ModSounds.ANIMATED_BLOCK_WALK);
    */
    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(Overfear.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}