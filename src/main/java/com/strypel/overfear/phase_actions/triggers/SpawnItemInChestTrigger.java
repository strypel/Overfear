package com.strypel.overfear.phase_actions.triggers;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.capabilities.triggersused.PlayerTriggersUsedProvider;
import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.triggers.core.IOnceTrigger;
import com.strypel.overfear.phase_actions.triggers.core.IRandomTrigger;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import com.strypel.overfear.utils.AreaUtils;
import com.strypel.overfear.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SpawnItemInChestTrigger extends PhaseTrigger implements IRandomTrigger, IOnceTrigger {
    public SpawnItemInChestTrigger(int id, int phase, Action action) {
        super(id, phase, action);
    }

    @Override
    public boolean сondition(Level level, MinecraftServer server, Player player) {
        return true;
    }

    @Override
    public boolean isTrue(Level level, MinecraftServer server, Player player) {
        if(this.randomIsTrue()) {
            AtomicBoolean t = new AtomicBoolean(false);
            player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(playerPhase -> {
                player.getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).ifPresent(playerTriggersUsed -> {
                    if (!playerTriggersUsed.getTriggers().containsKey(this.getId()) && Math.floor(playerPhase.getIntPhase()) == this.getPhase()) {
                        BlockPos b = AreaUtils.getNearestBlockFromArea(Blocks.CHEST,new BlockPos((int) (player.getX() - 8), (int) (player.getY() - 5), (int) (player.getZ() - 8)),
                                new BlockPos((int) (player.getX() + 8), (int) (player.getY() + 5), (int) (player.getZ() + 8)), level);
                        if (b != null) {
                            BlockState bst = level.getBlockState(b);
                            if (bst.getBlock() instanceof ChestBlock chest) {

                                if(level.getBlockEntity(b) instanceof ChestBlockEntity chestEntity){
                                    chestEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.EAST).ifPresent(p ->{
                                        short rnd = (short) RandomUtils.rnd(0,p.getSlots());
                                        if (p.getStackInSlot(rnd).isEmpty()){
                                            Item item = getRandomItem();
                                            if(item instanceof ArmorItem armor){
                                                ItemStack stack = new ItemStack(armor,1);
                                                stack.setDamageValue(stack.getMaxDamage() - (int) RandomUtils.rnd(1,3));
                                                p.insertItem(rnd,stack,false);
                                            }if(item instanceof TieredItem tool){
                                                ItemStack stack = new ItemStack(tool,1);
                                                stack.setDamageValue(stack.getMaxDamage() - (int) RandomUtils.rnd(1,3));
                                                p.insertItem(rnd,stack,false);
                                            }
                                            else {
                                                p.insertItem(rnd,new ItemStack(item,(int) RandomUtils.rnd(1,p.getStackInSlot(rnd).getMaxStackSize() * 0.2)),false);
                                            }
                                        }
                                    });
                                }
                                playerTriggersUsed.addPhase(this, (int) level.getDayTime());
                                t.set(true);
                            }
                        }
                    }
                });
            });
            return t.get();
        }
        return false;
    }
    public static Item getRandomItem(){
        int rnd = (int) RandomUtils.rnd(1,4);
        switch (rnd){
            case 1:{
                return getRandomArmor();
            }
            case 2:{
                return getRandomTool();
            }
            case 3:{
                return getRandomTrash();
            }
        }
        return null;
    }
    private static Item getRandomArmor() {
        List<Map.Entry<ResourceKey<Item>, Item>> items = ForgeRegistries.ITEMS.getEntries().stream().filter(resourceKeyItemEntry ->
                resourceKeyItemEntry.getValue().getDefaultInstance().getItem() instanceof ArmorItem
        ).toList();
        Item toReturn = items.get((int) RandomUtils.rnd(0,items.size())).getValue();
        return toReturn;
    }
    private static Item getRandomTool() {
        List<Map.Entry<ResourceKey<Item>, Item>> items = ForgeRegistries.ITEMS.getEntries().stream().filter(resourceKeyItemEntry ->
                resourceKeyItemEntry.getValue().getDefaultInstance().getItem() instanceof TieredItem
        ).toList();
        Item toReturn = items.get((int) RandomUtils.rnd(0,items.size())).getValue();
        return toReturn;
    }
    private static Item getRandomTrash() {
        List<Map.Entry<ResourceKey<Item>, Item>> items = ForgeRegistries.ITEMS.getEntries().stream().toList();
        Item toReturn = items.get((int) RandomUtils.rnd(0,items.size())).getValue();
        return toReturn;
    }

    @Override
    public double probability() {
        return 0.000035888888;
    }

    @Override
    public int reactivationTime() {
        return 5000;
    }

    @Override
    public boolean isTimeReactivationType() {
        return false;
    }
}
