package com.strypel.overfear.core;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.items.ActivityAnalyzerItem;
import com.strypel.overfear.items.TapeItem;
import com.strypel.overfear.items.DataGeneratorItem;
import com.strypel.overfear.misc.OverFearCreativeTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Overfear.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Overfear.MODID);
    private static final Set<RegistryObject<? extends Item>> OVERFEAR_TAB_ITEMS = new HashSet<>();
    public static final ResourceKey<CreativeModeTab> OVERFEAR_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(Overfear.MODID, "default"));
    private static final Map<ResourceKey<CreativeModeTab>, Set<RegistryObject<? extends Item>>> CREATIVE_TAB_ITEMS = new HashMap<>();
    public static final RegistryObject<CreativeModeTab> OVERFEAR_TAB = CREATIVE_TABS.register(OVERFEAR_TAB_KEY.location().getPath(), () -> OverFearCreativeTab.builder(OVERFEAR_TAB_ITEMS.stream().map(RegistryObject::get).collect(Collectors.toSet())).build());
    public static final RegistryObject<Item> FOIL = register("foil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM = register("aluminum",() -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> ALUMINUM_INGOT = register("aluminum_ingot",() -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> TAPE = register("tape",() -> new TapeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ACTIVITY_ANALYZER = register("activity_analyzer",() -> new ActivityAnalyzerItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DATA_GENERATOR = register("data_generator",() -> new DataGeneratorItem(ModBlocks.DATA_GENERATOR.get(),new Item.Properties()));
    static <I extends Item> RegistryObject<I> register(final String name, ResourceKey<CreativeModeTab> tab, final Supplier<? extends I> sup) {
        RegistryObject<I> item = ITEMS.register(name, sup);
        if (tab == OVERFEAR_TAB_KEY) {
            OVERFEAR_TAB_ITEMS.add(item);
        } else {
            CREATIVE_TAB_ITEMS.computeIfAbsent(tab, (a) -> new HashSet<>()).add(item);
        }
        return item;
    }
    static <I extends Item> RegistryObject<I> register(final String name, final Supplier<? extends I> sup) {
        return register(name, OVERFEAR_TAB_KEY, sup);
    }
    public static void register(IEventBus eventBus){
        CREATIVE_TABS.register(eventBus);
        ITEMS.register(eventBus);
    }



}
