package dev.cheos.baastimod.effect;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class UndyingEffect extends Effect {
	public UndyingEffect(int color) {
		super(EffectType.BENEFICIAL, color);
		setRegistryName(new ResourceLocation(BaastiMod.MODID, "undying"));
	}
}
