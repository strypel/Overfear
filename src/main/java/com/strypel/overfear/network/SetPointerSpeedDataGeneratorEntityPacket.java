package com.strypel.overfear.network;

import com.strypel.overfear.block.entity.DataGeneratorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SetPointerSpeedDataGeneratorEntityPacket {
    private BlockPos pos;
    private int speed;
    public SetPointerSpeedDataGeneratorEntityPacket(BlockPos pos, int speed) {
        this.pos = pos;
        this.speed = speed;
    }
    public SetPointerSpeedDataGeneratorEntityPacket(FriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.speed = buffer.readInt();
    }
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeInt(this.speed);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if(player == null)
            return;
        ServerLevel level = player.serverLevel();
        BlockEntity be = level.getBlockEntity(this.pos);
        if(be instanceof DataGeneratorEntity dbe){
            dbe.pointer_speed = this.speed;
            dbe.sendUpdate();
        }
    }
}