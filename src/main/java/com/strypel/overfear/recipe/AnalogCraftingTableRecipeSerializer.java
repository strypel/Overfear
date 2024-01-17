package com.strypel.overfear.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public class AnalogCraftingTableRecipeSerializer implements RecipeSerializer<AnalogCraftingTableRecipe> {
    private final AnalogCraftingTableRecipeSerializer.IFactory<AnalogCraftingTableRecipe> factory;
    private Codec<AnalogCraftingTableRecipe> codec;
    public AnalogCraftingTableRecipeSerializer(IFactory<AnalogCraftingTableRecipe> factory){
        this.factory = factory;
        codec = RecordCodecBuilder.create((builder) -> builder
                .group(
                        Ingredient.CODEC_NONEMPTY
                                .fieldOf("ingredient")
                                .forGetter((recipe) -> recipe.inputItems),
                        BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem)
                                .fieldOf("result")
                                .forGetter((recipe) -> recipe.output)
                ).apply(builder, factory::create));
    }
    @Override
    public Codec<AnalogCraftingTableRecipe> codec() {
        return codec;
    }

    @Override
    public @Nullable AnalogCraftingTableRecipe fromNetwork(FriendlyByteBuf buffer) {
        return factory.create(Ingredient.fromNetwork(buffer), buffer.readItem());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, AnalogCraftingTableRecipe recipe) {
        recipe.inputItems.toNetwork(buffer);
        buffer.writeItem(recipe.output);
    }
    public interface IFactory<T extends AnalogCraftingTableRecipe> {
        T create(Ingredient input, ItemStack output);
    }
}
