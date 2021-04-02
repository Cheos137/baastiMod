package dev.cheos.baastimod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
			if (entity.hasEffect(CustomEffects.UNDYING)) {
				
				EffectInstance effect = entity.getEffect(CustomEffects.UNDYING);
				try {
					Method tickDownDuration = EffectInstance.class.getDeclaredMethod("tickDownDuration", new Class[] { });
					for (int i = 0; i < 40; i++) {
						tickDownDuration.invoke(effect);
						if (effect.getDuration() <= 0)
							break;
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				
				MiscImpl.doUndyingFor(entity, null);
				event.setCanceled(true);
				break undying;
			}
			
			Entry<EquipmentSlotType, ItemStack> armorItem = EnchantmentHelper.getRandomItemWith(
					CustomEnchantments.UNDYING,
					entity,
					stack -> stack.getItem() instanceof ArmorItem);
			
			if (armorItem != null) {
				Random random = entity.getRandom();
				armorItem.getValue().hurtAndBreak(50 + random.nextInt(31), entity, le ->
					le.broadcastBreakEvent(armorItem.getKey()));
				MiscImpl.doUndyingFor(entity, armorItem.getValue());
				event.setCanceled(true);
			}
		}
		/// end section
	}
}
