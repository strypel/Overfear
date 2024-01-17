package com.strypel.overfear.entity.ai.goal;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.core.ModSounds;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import com.strypel.overfear.event.timeEvents.SoundTimeEvent;
import com.strypel.overfear.event.timeEvents.TimesEvents;
import com.strypel.overfear.network.PacketHandler;
import com.strypel.overfear.network.SetSpectatorOverlayPlayerPacket;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SpectatorScreamerWhenLookForPlayerGoal extends Goal {
    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    protected final PathfinderMob mob;
    protected final TargetingConditions lookAtContext;
    protected final float maxLookDist;
    protected final float minLookDist;
    @Nullable
    protected Player player;
    protected boolean flag = false;
    protected boolean look;
    protected float tick = 0;

    public SpectatorScreamerWhenLookForPlayerGoal(PathfinderMob mob, float minLookDist, float maxLookDist, boolean look) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.maxLookDist = maxLookDist;
        this.minLookDist = minLookDist;
        this.look = look;
        this.lookAtContext = TargetingConditions.forNonCombat().range((double)maxLookDist).selector((p_25531_) -> {
            return EntitySelector.notRiding(mob).test(p_25531_);
        });
    }
    public SpectatorScreamerWhenLookForPlayerGoal(PathfinderMob mob,float minLookDist, float maxLookDist) {
        this(mob,minLookDist,maxLookDist,false);
    }

    public boolean canUse() {
        this.player = this.mob.level().getNearestPlayer(this.lookAtContext
                , this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if(player != null){
            return this.mob.distanceToSqr(this.player) >= this.minLookDist && mob != null && mob.isAlive() && this.player != null;
        } else {
            return true;
        }
    }


    public boolean canContinueToUse() {
        return this.mob.distanceToSqr(this.player) >= this.minLookDist && mob != null && mob.isAlive();
    }
    

    public void start() {

    }

    public void stop() {
        this.player = null;
        this.flag = false;
        this.tick = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.mob.level().isClientSide) {
            if (this.mob instanceof SpectatorEntity entity) {
            if(player != null){
                    if (this.look) {
                        if (this.player.isAlive()) {
                            this.mob.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ());
                        }
                    }
                    if (!player.isCreative() && !player.isSpectator()) {
                        player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(phase -> {
                            if (phase.getIntPhase() >= 4) {
                                entity.setAngry(true);
                                Vec3 vec3 = player.getViewVector(1.0F).normalize();
                                if (entity.isLookingAtMe((Player) player) && !this.flag) {
                                    entity.setAttack(true);
                                    entity.teleportTo(player.getX() + vec3.x, player.getY(), player.getZ() + vec3.z);
                                    player.hurt(entity.damageSources().magic(), (float) (player.getMaxHealth() * 0.9));
                                    TimesEvents.registerEvent(new SoundTimeEvent(this.mob.getOnPos().getCenter(), ModSounds.SPECTATOR_1.get(), 1.0F, 1));
                                    PacketHandler.sendToPlayer(new SetSpectatorOverlayPlayerPacket(), this.mob.level().getServer().getPlayerList().getPlayer(player.getUUID()));
                                    entity.triggerAnim("controller","attack");
                                    this.flag = true;
                                } else if (this.flag) {
                                    this.tick++;
                                    if (tick >= 10) {
                                        phase.addPhase(0.5);
                                        entity.disintegrate();
                                        entity.setAttack(false);
                                        this.tick = 0;
                                    }
                                }
                            } else {
                                entity.setAngry(false);
                                if (entity.isLookingAtMe((Player) player) && !this.flag) {
                                    phase.addPhase(0.5);
                                    TimesEvents.registerEvent(new SoundTimeEvent(this.mob.getOnPos().getCenter(), ModSounds.SPECTATOR_1.get(), 1.0F, 1));
                                    PacketHandler.sendToPlayer(new SetSpectatorOverlayPlayerPacket(), this.mob.level().getServer().getPlayerList().getPlayer(player.getUUID()));
                                    this.flag = true;
                                    entity.disintegrate();
                                }
                            }
                        });
                    } else {
                        entity.setAngry(false);
                    }
                } else {
                entity.disintegrate();
            }
            }
        }
    }
}
