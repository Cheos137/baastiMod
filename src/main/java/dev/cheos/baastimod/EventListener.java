package dev.cheos.baastimod;

import dev.cheos.baastimod.enchantment.CustomEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * Generic event listener class to implement functionality for various features
 * 
 */
@EventBusSubscriber(modid = BaastiMod.MODID)
public class EventListener {
	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		/// UNDYING enchantment impl
		LivingEntity entity = event.getEntityLiving();
		
		for (ItemStack stack : entity.getArmorSlots())
			if (stack.isEnchanted() && EnchantmentHelper.getEnchantments(stack).containsKey(CustomEnchantments.UNDYING)) {
				entity.setHealth(1.0F);
				entity.removeAllEffects();
				entity.addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
				entity.addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
				entity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
				entity.level.broadcastEntityEvent(entity, (byte)35);
				stack.setDamageValue(Math.min(stack.getDamageValue() + 200, stack.getMaxDamage()));
				event.setCanceled(true);
			}
		/// end section
	}
}
