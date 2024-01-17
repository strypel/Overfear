package com.strypel.overfear.phase_actions.actions;

import com.strypel.overfear.capabilities.phase.PlayerPhase;
import com.strypel.overfear.phase_actions.actions.core.Action;
import com.strypel.overfear.phase_actions.actions.core.IAffectingPhaseAction;
import com.strypel.overfear.utils.AreaUtils;
import com.strypel.overfear.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.List;
import java.util.Map;

public class SpawnBlockNearbyAction extends Action implements IAffectingPhaseAction {
    @Override
    public void action(Level level, MinecraftServer server, Player player) {
        Block toSpawn = getRandomBlock();
        BlockPos pos = AreaUtils.LevelAreaRandomPoint(new BlockPos((int) (player.getX() - 15), (int) (player.getY() - 20), (int) (player.getZ() - 15)),
                new BlockPos((int) (player.getX() + 15), (int) (player.getY() + 20), (int) (player.getZ() + 15)), level);

        level.setBlock(pos,toSpawn.defaultBlockState(),2);
        level.playSound((Player) null,pos,toSpawn.getSoundType(toSpawn.defaultBlockState()).getPlaceSound(), SoundSource.BLOCKS);

        if(toSpawn instanceof JukeboxBlock){
            BlockEntity placedBlock = level.getBlockEntity(pos);
            if(placedBlock instanceof JukeboxBlockEntity entity) {
                entity.setRecordWithoutPlaying(new ItemStack(getRandomRecord()));
                entity.startPlaying();
            }
        }
        if(toSpawn instanceof SignBlock){
            BlockEntity placedBlock = level.getBlockEntity(pos);
            if(placedBlock instanceof SignBlockEntity entity) {
                String rnd = getRandomText().getString();
                String[] text = divideString(rnd, (int) Math.ceil((double) rnd.length() / 15));
                for (int i = 0; i < text.length; i++) {
                    if(i < 4){
                        entity.setText(entity.getFrontText().setMessage(i,Component.nullToEmpty(text[i])),true);
                    }
                }
            }
        }
    }
    private static Component getRandomText() {
        List<Component> texts = List.of(
                Component.translatable("sign.random.text_1"),
                Component.translatable("sign.random.text_2"),
                Component.translatable("sign.random.text_3"),
                Component.translatable("sign.random.text_4"),
                Component.translatable("sign.random.text_6")
        );
        Component text = texts.get((int) RandomUtils.rnd(0,texts.size()));
        return text;
    }
    private static RecordItem getRandomRecord() {
        List<Map.Entry<ResourceKey<Item>, Item>> items =
        ForgeRegistries.ITEMS.getEntries().stream().filter(entry ->
                entry.getValue().getDefaultInstance().getItem() instanceof RecordItem
        ).toList();
        RecordItem record = (RecordItem) items.get((int) RandomUtils.rnd(0,items.size())).getValue();
        return record;
    }

    private static Block getRandomBlock() {
        List<Block> blocks = List.of(
                Blocks.OAK_WOOD,
                Blocks.GRASS,
                Blocks.LAVA,
                Blocks.FIRE,
                Blocks.REDSTONE_TORCH,
                Blocks.CAKE,
                Blocks.JUKEBOX,
                Blocks.WATER,
                Blocks.OAK_SIGN,
                Blocks.CRAFTING_TABLE
        );
        Block toReturn = blocks.get((int) RandomUtils.rnd(0,blocks.size()));
        return toReturn;
    }

    public static String[] divideString(String inputString, int chunkSize) {
        int inputLength = inputString.length();
        int substringLength = (int) Math.ceil((double) inputLength / chunkSize);
        String[] dividedStrings = new String[chunkSize];
        int startIndex = 0;

        for (int i = 0; i < chunkSize; i++) {
            if (startIndex + substringLength <= inputLength) {
                dividedStrings[i] = inputString.substring(startIndex, startIndex + substringLength);
                startIndex += substringLength;
            } else {
                dividedStrings[i] = inputString.substring(startIndex);
                break;
            }
        }

        return dividedStrings;
    }


    @Override
    public PlayerPhase newPhase(PlayerPhase phase) {
        PlayerPhase phaseToReturn = new PlayerPhase();
        phaseToReturn.copyFrom(phase);
        phaseToReturn.addPhase(0.05);
        return phaseToReturn;
    }

}
