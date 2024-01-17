package com.strypel.overfear.core;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.recipe.AnalogCraftingTableRecipe;
import com.strypel.overfear.recipe.AnalogCraftingTableRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Overfear.MODID);
    public static final RegistryObject<RecipeSerializer<AnalogCraftingTableRecipe>> ANALOG_CRAFTING_TABLE_RECIPE = RECIPE_SERIALIZER_REGISTER.register("analog_crafting",() -> new AnalogCraftingTableRecipeSerializer(AnalogCraftingTableRecipe::new));
    public static void register(IEventBus bus){
        RECIPE_SERIALIZER_REGISTER.register(bus);
    }

}
