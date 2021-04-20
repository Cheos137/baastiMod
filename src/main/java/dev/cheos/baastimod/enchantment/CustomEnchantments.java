package dev.cheos.baastimod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.registries.IForgeRegistry;

public class CustomEnchantments {
	public static final Enchantment UNDYING = new UndyingEnchantment();
	public static final Enchantment ROCKETBOOST = new RocketboostEnchantment();
	
	public static void registerTo(IForgeRegistry<Enchantment> registry) {
		registry.registerAll(UNDYING, ROCKETBOOST);
	}
}
