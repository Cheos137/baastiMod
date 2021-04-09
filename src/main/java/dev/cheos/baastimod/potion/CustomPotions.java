package dev.cheos.baastimod.potion;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class CustomPotions {
	public static final Potion UNDYING = new UndyingPotion(2400);
	
	public static void registerTo(IForgeRegistry<Potion> registry) {
		registry.register(UNDYING);
		BrewingRecipeRegistry.addRecipe(
				Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.REGENERATION)),
				Ingredient.of(Items.TOTEM_OF_UNDYING),
				PotionUtils.setPotion(new ItemStack(Items.POTION), CustomPotions.UNDYING));
	}
}
