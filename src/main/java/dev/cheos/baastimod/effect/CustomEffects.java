package dev.cheos.baastimod.effect;

import net.minecraft.potion.Effect;
import net.minecraftforge.registries.IForgeRegistry;

public class CustomEffects {
	public static final Effect UNDYING = new UndyingEffect();
	
	public static void registerTo(IForgeRegistry<Effect> registry) {
		registry.register(UNDYING);
	}
}
