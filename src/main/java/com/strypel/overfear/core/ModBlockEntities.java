package com.strypel.overfear.core;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.block.entity.AnalogCraftingTableEntity;
import com.strypel.overfear.block.entity.DataGeneratorEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Overfear.MODID);
    //public static final RegistryObject<BlockEntityType<AnalogCraftingTableEntity>> ANALOG_CRAFTING_TABLE =
    //        BLOCK_ENTITIES.register("analog_crafting_table",() ->
    //            BlockEntityType.Builder.of(AnalogCraftingTableEntity::new,ModBlocks.ANALOG_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<DataGeneratorEntity>> DATA_GENERATOR =
            BLOCK_ENTITIES.register("data_generator",() -> BlockEntityType.Builder.of(DataGeneratorEntity::new,ModBlocks.DATA_GENERATOR.get()).build(null));


    public static void register(IEventBus bus){
        BLOCK_ENTITIES.register(bus);
    }
}
