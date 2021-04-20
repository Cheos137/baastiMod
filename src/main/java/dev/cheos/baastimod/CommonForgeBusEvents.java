package dev.cheos.baastimod;

import java.util.Map.Entry;
import java.util.Random;

import dev.cheos.baastimod.effect.CustomEffects;
import dev.cheos.baastimod.enchantment.CustomEnchantments;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.ITag;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = BaastiMod.MODID)
public class CommonForgeBusEvents {
	
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
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onJump(LivingJumpEvent event) {
		/// ROCKETBOOST enchantment
		rocketboost: {
			LivingEntity entity = event.getEntityLiving();
			ITag<Block> tag = Tags.Blocks.ROCKETBOOST_BASE.get();
			int level;
			
			if (entity.isShiftKeyDown())
				break rocketboost;
			if (!entity.getItemBySlot(EquipmentSlotType.CHEST).canElytraFly(entity))
				break rocketboost;
			if (!entity.level.getBlockState(entity.blockPosition().below()).is(tag))
				break rocketboost;
			if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FROST_WALKER, entity.getItemBySlot(EquipmentSlotType.FEET)) > 0)
				break rocketboost;
			if ((level = EnchantmentHelper.getItemEnchantmentLevel(CustomEnchantments.ROCKETBOOST, entity.getItemBySlot(EquipmentSlotType.CHEST))) == 0) {
				if ((level = EnchantmentHelper.getItemEnchantmentLevel(CustomEnchantments.ROCKETBOOST, entity.getItemBySlot(EquipmentSlotType.FEET))) == 0)
					break rocketboost;
				else entity.getItemBySlot(EquipmentSlotType.FEET)
						.hurtAndBreak(30 + entity.getRandom().nextInt(31), entity, le -> le.broadcastBreakEvent(EquipmentSlotType.FEET));
			}
			
			entity.move(MoverType.SELF, new Vector3d(0, 1, 0));
			
			if (entity instanceof PlayerEntity)
				((PlayerEntity) entity).tryToStartFallFlying();
			
			entity.moveRelative(Math.max(1, 2 * level - 2), new Vector3d(0, level * 50, 0));
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, ParticleTypes.FLAME, 10);
				Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, ParticleTypes.SMOKE, 30);
				Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, ParticleTypes.ASH, 80);
			});
			entity.level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, entity.getSoundSource(), 1.0F, 1.0F, false);
		}
		/// end section
	}
}
