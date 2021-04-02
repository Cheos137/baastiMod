package dev.cheos.baastimod.potion;

import dev.cheos.baastimod.BaastiMod;
import dev.cheos.baastimod.effect.CustomEffects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;

public class UndyingPotion extends Potion {
	public UndyingPotion(int duration) {
		super(new EffectInstance(CustomEffects.UNDYING, duration));
		setRegistryName(BaastiMod.MODID, "potion_undying");
	}
}
