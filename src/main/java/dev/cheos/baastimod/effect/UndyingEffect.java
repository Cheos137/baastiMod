package dev.cheos.baastimod.effect;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class UndyingEffect extends Effect {
	public UndyingEffect() {
		super(EffectType.BENEFICIAL, 0xE8C61E);
		setRegistryName(new ResourceLocation(BaastiMod.MODID, "undying"));
	}
	
	@Override
	public boolean shouldRenderHUD(EffectInstance effect) {
		return true;
	}
}
