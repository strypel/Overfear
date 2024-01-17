package com.strypel.overfear.recipe;

import com.strypel.overfear.core.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class AnalogCraftingTableRecipe implements Recipe<SimpleContainer> {
    protected final Ingredient inputItems;
    protected final ItemStack output;

    public AnalogCraftingTableRecipe(Ingredient inputItems, ItemStack output) {
        this.inputItems = inputItems;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if(level.isClientSide()){
            return false;
        }
        return inputItems.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_, RegistryAccess p_267165_) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ANALOG_CRAFTING_TABLE_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements  RecipeType<AnalogCraftingTableRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "analog_crafting";
    }

}
