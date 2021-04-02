package dev.cheos.baastimod;

import dev.cheos.baastimod.net.BaastiModPacketHandler;
import dev.cheos.baastimod.net.SUndyingActivatedPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class MiscImpl {
	public static void doUndyingFor(LivingEntity entity, ItemStack stack) {
		entity.setHealth(1.0F);
		entity.removeAllEffects();
		entity.addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
		entity.addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
		entity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
//		entity.level.broadcastEntityEvent(entity, (byte)35); // LEGACY CODE
		BaastiModPacketHandler.sendToClient(new SUndyingActivatedPacket(entity, stack != null ? stack : new ItemStack(Items.AIR)), entity);
	}
}
