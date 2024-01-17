package com.strypel.overfear.entity.ai.goal;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.core.ModSounds;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import com.strypel.overfear.event.timeEvents.CircleParticleEvent;
import com.strypel.overfear.event.timeEvents.SoundTimeEvent;
import com.strypel.overfear.event.timeEvents.TimesEvents;
import com.strypel.overfear.network.PacketHandler;
import com.strypel.overfear.network.SetSpectatorOverlayPlayerPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DisappearWhenApproachedGoal extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    protected final PathfinderMob mob;
    protected final TargetingConditions lookAtContext;
    protected final float lookDist;
    @Nullable
    protected Player player;
    protected boolean flag = false;
    private int tick = 0;

    public DisappearWhenApproachedGoal(PathfinderMob mob,float lookDist) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.lookDist = lookDist;
        this.lookAtContext = TargetingConditions.forNonCombat().range((double)lookDist).selector((p_25531_) -> {
            return EntitySelector.notRiding(mob).test(p_25531_);
        });
    }

    public boolean canUse() {
        this.player = this.mob.level().getNearestPlayer(this.lookAtContext
                , this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if(this.mob instanceof SpectatorEntity entity){
            return !entity.isAttack() && this.player != null && !player.isCreative() && !player.isSpectator() && mob != null && mob.isAlive();
        }
        return this.player != null && !player.isCreative() && !player.isSpectator() && mob != null && mob.isAlive();
    }


    public boolean canContinueToUse() {
        return mob != null && mob.isAlive();
    }
    

    public void start() {

    }

    public void stop() {
        this.player = null;
        this.flag = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.mob.level().isClientSide) {
            if (mob instanceof SpectatorEntity entity) {
                player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(phase -> {
                    if(!flag){
                        if (phase.getIntPhase() >= 4) {
                            Vec3 vec3 = player.getViewVector(1.0F).normalize();
                            entity.teleportTo(player.getX() + vec3.x, player.getY(), player.getZ() + vec3.z);
                            player.hurt(entity.damageSources().magic(), (float) (player.getMaxHealth() * 0.9));
                        } else if (phase.getIntPhase() < 4) {
                            phase.addPhase(0.5);
                            entity.disintegrate();
                        }
                        TimesEvents.registerEvent(new SoundTimeEvent(this.mob.getOnPos().getCenter(), ModSounds.SPECTATOR_1.get(), 1.0F, 1));
                        PacketHandler.sendToPlayer(new SetSpectatorOverlayPlayerPacket(), this.mob.level().getServer().getPlayerList().getPlayer(player.getUUID()));
                        entity.triggerAnim("controller","attack");
                        this.flag = true;
                    } else if (this.flag) {
                        this.tick++;
                        if (tick >= 10) {
                            phase.addPhase(0.5);
                            entity.disintegrate();
                            this.tick = 0;
                        }
                    }
                });
            } else {
                for (int i = 0; i < 6; i++) {
                    TimesEvents.registerEvent(new CircleParticleEvent(new Vec3(this.mob.getX(), this.mob.getY() + (0.3 * i), this.mob.getZ()), 0.3, ParticleTypes.SMOKE, 20));
                }
                this.mob.discard();
            }
        }
    }
}
