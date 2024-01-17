package com.strypel.overfear.network;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.client.gui.overlays.animated.GlitchOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

import java.util.concurrent.atomic.AtomicBoolean;

public class SetSpectatorOverlayPlayerPacket {
    public SetSpectatorOverlayPlayerPacket() {

    }
    public SetSpectatorOverlayPlayerPacket(FriendlyByteBuf buffer) {

    }
    public void encode(FriendlyByteBuf buffer) {

    }

    public boolean handle(CustomPayloadEvent.Context ctx) {
        final AtomicBoolean success = new AtomicBoolean(false);
        ctx.enqueueWork(()->{
            Overfear.getOverlayHandler().setOverlay(new GlitchOverlay(Component.nullToEmpty("glitch_01"),4));
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                    () -> () -> success.set(true));
        });
        ctx.setPacketHandled(true);
        return success.get();
    }
}