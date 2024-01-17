package com.strypel.overfear.entity.custom;

import com.strypel.overfear.core.ModEntities;
import com.strypel.overfear.entity.ai.goal.DisappearWhenApproachedGoal;
import com.strypel.overfear.entity.ai.goal.DisintegrateLiveTimeGoal;
import com.strypel.overfear.entity.ai.goal.SpectatorScreamerWhenLookForPlayerGoal;
import com.strypel.overfear.event.timeEvents.CircleParticleEvent;
import com.strypel.overfear.event.timeEvents.TimesEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class SpectatorEntity extends Animal implements GeoEntity {
    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenPlay("animation.model.attack");
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(SpectatorEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ATTACK = SynchedEntityData.defineId(SpectatorEntity.class, EntityDataSerializers.BOOLEAN);

    public SpectatorEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f).build();
    }


    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(2,new LookAtPlayerGoal(this,Player.class, 40.0F,1f,false));
        this.goalSelector.addGoal(1,new DisappearWhenApproachedGoal(this,7.25F));
        this.goalSelector.addGoal(2,new SpectatorScreamerWhenLookForPlayerGoal(this, 0F,40.0F,true));
        this.goalSelector.addGoal(3,new DisintegrateLiveTimeGoal(this,200,100F));
        this.goalSelector.addGoal(4, new FloatGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(this.ANGRY,false);
        this.getEntityData().define(this.ATTACK,false);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.SPECTATOR.get().create(serverLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::predicate)
                .triggerableAnim("attack", ATTACK_ANIM));
    }

    private PlayState predicate(AnimationState<SpectatorEntity> tAnimationState) {
        //if(tAnimationState.isMoving()) {
        //    tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.spectator.walk", Animation.LoopType.LOOP));
        //    return PlayState.CONTINUE;
        //}
        if(this.isAngry()){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.angry", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("angry",this.isAngry());
        tag.putBoolean("attack",this.isAttack());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setAngry(tag.getBoolean("angry"));
        this.setAttack(tag.getBoolean("attack"));
    }
    public void setAngry(boolean bool){
        this.getEntityData().set(this.ANGRY,bool);
    }
    public boolean isAngry(){
        return this.getEntityData().get(this.ANGRY);
    }
    public void setAttack(boolean bool){
        this.getEntityData().set(this.ATTACK,bool);
    }
    public boolean isAttack(){
        return this.getEntityData().get(this.ATTACK);
    }

    public boolean isLookingAtMe(Player p_32535_) {
        Vec3 vec3 = p_32535_.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - p_32535_.getX(), this.getEyeY() - p_32535_.getEyeY(), this.getZ() - p_32535_.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0D - 0.025D / d0 ? p_32535_.hasLineOfSight(this) : false;
    }

    public void disintegrate(){
        for (int i = 0; i < 6; i++) {
            TimesEvents.registerEvent(new CircleParticleEvent(new Vec3(this.getX(),this.getY() + (0.3 * i),this.getZ()),0.3, ParticleTypes.SMOKE,20));
        }
        this.discard();
    }
}
