package com.strypel.overfear.phase_actions.actions;

import com.strypel.overfear.capabilities.phase.PlayerPhase;
import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.actions.core.IAffectingPhaseAction;
import com.strypel.overfear.utils.AreaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomItemAction extends Action implements IAffectingPhaseAction {
    @Override
    public void action(Level level, MinecraftServer server, Player player) {
        List<Map.Entry<ResourceKey<Item>, Item>> items =
                ForgeRegistries.ITEMS.getEntries().stream().toList();

        Item item = items.get(new Random().nextInt((items.size() - 1) - 0 + 1) + 0).getValue();
        ItemStack itemStack = new ItemStack(item,new Random().nextInt(item.getMaxStackSize() - 1 + 1) + 1);

        BlockPos pos = AreaUtils.LevelAreaRandomPoint(new BlockPos((int) (player.getX() - 10), (int) (player.getY() - 20), (int) (player.getZ() - 10)),
                new BlockPos((int) (player.getX() + 10), (int) (player.getY() + 20), (int) (player.getZ() + 10)), level);
        ItemEntity entity = new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),itemStack);

        level.addFreshEntity(entity);
    }

    @Override
    public PlayerPhase newPhase(PlayerPhase phase) {
        PlayerPhase phaseToReturn = new PlayerPhase();
        phaseToReturn.copyFrom(phase);
        phaseToReturn.addPhase(0.05);
        return phaseToReturn;
    }
}
