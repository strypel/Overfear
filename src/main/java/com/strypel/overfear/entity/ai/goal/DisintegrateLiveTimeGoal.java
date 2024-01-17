package com.strypel.overfear.entity.ai.goal;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import com.strypel.overfear.event.timeEvents.CircleParticleEvent;
import com.strypel.overfear.event.timeEvents.TimesEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DisintegrateLiveTimeGoal extends Goal {
    protected final PathfinderMob mob;
    protected final TargetingConditions lookAtContext;
    protected final float lookDist;
    @Nullable
    protected Player player;
    protected int ticks = 0;
    protected int liveTime;

    public DisintegrateLiveTimeGoal(PathfinderMob mob,int liveTime, float lookDist) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.liveTime = liveTime;
        this.lookDist = lookDist;
        this.lookAtContext = TargetingConditions.forNonCombat().range((double)lookDist).selector((p_25531_) -> {
            return EntitySelector.notRiding(mob).test(p_25531_);
        });
    }

    public boolean canUse() {
        this.player = this.mob.level().getNearestPlayer(this.lookAtContext
                , this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        return mob != null && mob.isAlive() && this.player != null;
    }


    public boolean canContinueToUse() {
        return mob != null && mob.isAlive();
    }


    public void start() {

    }

    public void stop() {
        this.player = null;
    }

    @Override
    public void tick() {
        super.tick();
        if (!mob.level().isClientSide) {
            if(this.ticks < this.liveTime){
                this.ticks++;
            } else {
                if(mob instanceof SpectatorEntity entity){
                    player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(phase -> {
                        phase.addPhase(0.5);
                    });
                    entity.disintegrate();
                } else {
                    for (int i = 0; i < 6; i++) {
                        TimesEvents.registerEvent(new CircleParticleEvent(new Vec3(this.mob.getX(),this.mob.getY() + (0.3 * i),this.mob.getZ()),0.3, ParticleTypes.SMOKE,20));
                    }
                    this.mob.discard();
                }
            }
        }
    }
}
