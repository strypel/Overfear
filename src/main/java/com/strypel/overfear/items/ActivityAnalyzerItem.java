package com.strypel.overfear.items;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.core.ModSounds;
import com.strypel.overfear.event.timeEvents.SoundTimeEvent;
import com.strypel.overfear.event.timeEvents.TimesEvents;
import com.strypel.overfear.items.client.ActivityAnalyzerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class ActivityAnalyzerItem extends Item implements GeoItem {
    private static final RawAnimation LEVEL_0 = RawAnimation.begin().thenPlay("animation.model.level_0");
    private static final RawAnimation LEVEL_1 = RawAnimation.begin().thenPlay("animation.model.level_1");
    private static final RawAnimation LEVEL_2 = RawAnimation.begin().thenPlay("animation.model.level_2");
    private static final RawAnimation LEVEL_3 = RawAnimation.begin().thenPlay("animation.model.level_3");
    private static final RawAnimation LEVEL_4 = RawAnimation.begin().thenPlay("animation.model.level_4");
    private static final RawAnimation LEVEL_5 = RawAnimation.begin().thenPlay("animation.model.level_5");
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public ActivityAnalyzerItem(Properties properties) {
        super(properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    private PlayState predicate(AnimationState animationState) {
        //animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "analyzer_controller", 0, state -> PlayState.STOP)
                .triggerableAnim("level_0", LEVEL_0)
                .triggerableAnim("level_1", LEVEL_1)
                .triggerableAnim("level_2", LEVEL_2)
                .triggerableAnim("level_3", LEVEL_3)
                .triggerableAnim("level_4", LEVEL_4)
                .triggerableAnim("level_5", LEVEL_5)
                .setSoundKeyframeHandler(state -> {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.ACTIVITY_ANALYZER_CHECK.get(), 1.0F));
                }));
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel){
            TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,6));
            TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,7));
            TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,8));
            TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,9));
            TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CHECK.get(),1,10));
            player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(phase -> {
                switch (phase.getIntPhase()){
                    case 0:{triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "analyzer_controller", "level_0");
                        break;
                    }
                    case 1:{triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "analyzer_controller", "level_1");
                        break;
                    }
                    case 2:{triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "analyzer_controller", "level_2");
                        break;
                    }
                    case 3:{triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "analyzer_controller", "level_3");
                        TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_WARNING.get(),1,30));
                        break;
                    }
                    case 4:{triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "analyzer_controller", "level_4");
                        TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_WARNING.get(),1,30));
                        break;
                    }
                    case 5:{triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "analyzer_controller", "level_5");
                        TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_WARNING.get(),1,30));
                        break;
                    }

                }
                TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,43));
                TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,44));
                TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,45));
                TimesEvents.registerEvent(new SoundTimeEvent(player.getUUID(),ModSounds.ACTIVITY_ANALYZER_CLACK.get(),1,46));
                player.getCooldowns().addCooldown(player.getItemInHand(hand).getItem(),50);
            });
            return InteractionResultHolder.consume(player.getItemInHand(hand));
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ActivityAnalyzerRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new ActivityAnalyzerRenderer();
                }

                return this.renderer;
            }
        });
    }
}
