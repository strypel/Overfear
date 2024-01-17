package com.strypel.overfear.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlaySoundPlayerPacket {
    private SoundEvent event;
    public PlaySoundPlayerPacket(SoundEvent event) {
        this.event = event;
    }
    public PlaySoundPlayerPacket(FriendlyByteBuf buffer) {
        this.event = buffer.readJsonWithCodec(SoundEvent.CODEC).get();
    }
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeJsonWithCodec(SoundEvent.CODEC, Holder.direct(this.event));
    }

    public boolean handle(CustomPayloadEvent.Context ctx) {
        final AtomicBoolean success = new AtomicBoolean(false);
        ctx.enqueueWork(()->{
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(this.event, 1.0F));
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                    () -> () -> success.set(true));
        });
        ctx.setPacketHandled(true);
        return success.get();
    }
}