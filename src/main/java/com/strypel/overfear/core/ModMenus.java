package com.strypel.overfear.core;

import com.google.common.eventbus.EventBus;
import com.strypel.overfear.Overfear;
import com.strypel.overfear.menu.AnalogCraftingTableMenu;
import com.strypel.overfear.menu.DataGeneratorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Overfear.MODID);

    //public static final RegistryObject<MenuType<AnalogCraftingTableMenu>> ANALOG_CRAFTING_TABLE_MENU = MENU_TYPES.register("analog_crafting_table_menu",
    //        () -> IForgeMenuType.create(AnalogCraftingTableMenu::new));
    public static final RegistryObject<MenuType<DataGeneratorMenu>> DATA_GENERATOR_MENU = MENU_TYPES.register("data_generator_menu",
            () -> IForgeMenuType.create(DataGeneratorMenu::new));
    public static void register(IEventBus bus) {
        MENU_TYPES.register(bus);
    }

}
