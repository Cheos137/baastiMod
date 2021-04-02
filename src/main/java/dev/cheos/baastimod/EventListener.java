package dev.cheos.baastimod;

import java.util.Map.Entry;
import java.util.Random;

import dev.cheos.baastimod.effect.CustomEffects;
import dev.cheos.baastimod.enchantment.CustomEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
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
		LivingEntity entity = event.getEntityLiving();
		
		/// UNDYING enchantment / effect impl
		undying: {
			Random random = entity.getRandom();
			
			if (entity.hasEffect(CustomEffects.UNDYING)) {
				EffectInstance effect = entity.getEffect(CustomEffects.UNDYING);
				int duration  = effect.getDuration(); // TICKS
				int amplifier = effect.getAmplifier();
				
				duration = Math.max(0, duration - 20 * (50 + random.nextInt(31)));
				
				MiscImpl.doUndyingFor(entity, null);
				if (duration > 0)
					entity.addEffect(new EffectInstance(CustomEffects.UNDYING, duration, amplifier));
				event.setCanceled(true);
				break undying;
			}
			
			Entry<EquipmentSlotType, ItemStack> armorItem = EnchantmentHelper.getRandomItemWith(
					CustomEnchantments.UNDYING,
					entity,
					stack -> stack.getItem() instanceof ArmorItem);
			
			if (armorItem != null) {
				ItemStack copy = armorItem.getValue().copy();
				
				armorItem.getValue().hurtAndBreak(50 + random.nextInt(31), entity, le ->
					le.broadcastBreakEvent(armorItem.getKey()));
				MiscImpl.doUndyingFor(entity, copy);
				event.setCanceled(true);
			}
		}
		/// end section
	}
}
