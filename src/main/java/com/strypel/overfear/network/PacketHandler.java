package com.strypel.overfear.network;

import com.strypel.overfear.Overfear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(Overfear.MODID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(PlaySoundPlayerPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(PlaySoundPlayerPacket::encode)
                .decoder(PlaySoundPlayerPacket::new)
                .consumerMainThread(PlaySoundPlayerPacket::handle)
                .add();
        INSTANCE.messageBuilder(SetSpectatorOverlayPlayerPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SetSpectatorOverlayPlayerPacket::encode)
                .decoder(SetSpectatorOverlayPlayerPacket::new)
                .consumerMainThread(SetSpectatorOverlayPlayerPacket::handle)
                .add();
        INSTANCE.messageBuilder(SetPointerSpeedDataGeneratorEntityPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SetPointerSpeedDataGeneratorEntityPacket::encode)
                .decoder(SetPointerSpeedDataGeneratorEntityPacket::new)
                .consumerMainThread(SetPointerSpeedDataGeneratorEntityPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }
}