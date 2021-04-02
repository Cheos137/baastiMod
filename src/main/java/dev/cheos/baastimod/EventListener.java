package dev.cheos.baastimod;

import java.util.Map.Entry;
import java.util.Random;

import dev.cheos.baastimod.enchantment.CustomEnchantments;
import dev.cheos.baastimod.net.BaastiModPacketHandler;
import dev.cheos.baastimod.net.SUndyingActivatedPacket;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
		Entry<EquipmentSlotType, ItemStack> armorItem = EnchantmentHelper.getRandomItemWith(
				CustomEnchantments.UNDYING,
				entity,
				stack -> stack.getItem() instanceof ArmorItem);
		
		if (armorItem != null) {
			Random random = entity.getRandom();
			armorItem.getValue().hurtAndBreak(50 + random.nextInt(31), entity, le ->
				le.broadcastBreakEvent(armorItem.getKey()));
			
			entity.setHealth(1.0F);
			entity.removeAllEffects();
			entity.addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
			entity.addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
			entity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
//			entity.level.broadcastEntityEvent(entity, (byte)35); // LEGACY CODE
			BaastiModPacketHandler.sendToClient(new SUndyingActivatedPacket(entity, new ItemStack(Items.TOTEM_OF_UNDYING)), entity);
			event.setCanceled(true);
		}
		/// end section
	}
}
