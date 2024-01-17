package com.strypel.overfear.misc;

import com.strypel.overfear.core.ModBlocks;
import com.strypel.overfear.core.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Set;

public class OverFearCreativeTab {
    private static ItemStack item = ModItems.FOIL.get().getDefaultInstance();
    public static CreativeModeTab.Builder builder(Set<ItemLike> allItems) {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.overfear"))
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                .icon(() -> item)
                .displayItems(new OverfearDisplayItemGenerator(allItems));
    }
    public static class OverfearDisplayItemGenerator extends ModDisplayItemGenerator {

        public OverfearDisplayItemGenerator(Set<ItemLike> allItems) {
            super(allItems);
        }

        @Override
        protected void addItemsToOutput() {
            addBlocks();
            addItems();
        }
        private void addItems() {
            addItem(ModItems.ALUMINUM);
            addItem(ModItems.ALUMINUM_INGOT);
            addItem(ModItems.FOIL);
        }

        private void addBlocks() {
            //addBlock(ModBlocks.ANALOG_CRAFTING_TABLE);
            addBlock(ModBlocks.ALUMINUM_ORE);

        }
    }
}
