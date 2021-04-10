package dev.cheos.baastimod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.cheos.baastimod.block.CustomBlocks;
import dev.cheos.baastimod.block.tileentity.CustomTileEntityTypes;
import dev.cheos.baastimod.effect.CustomEffects;
import dev.cheos.baastimod.enchantment.CustomEnchantments;
import dev.cheos.baastimod.particle.CustomParticleTypes;
import dev.cheos.baastimod.potion.CustomPotions;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = BaastiMod.MODID)
public class RegistryEvents {
	public static final Logger LOGGER = LogManager.getLogger();
	
	@SubscribeEvent
	public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
		LOGGER.info("Registering blocks...");
		CustomBlocks.registerTo(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> event) {
		LOGGER.info("Registering items...");
		CustomBlocks.registerToAsBlockItem(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void onEnchantmentRegistry(RegistryEvent.Register<Enchantment> event) {
		LOGGER.info("Registering enchantments...");
		CustomEnchantments.registerTo(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void onEffectRegistry(RegistryEvent.Register<Effect> event) {
		LOGGER.info("Registering effects...");
		CustomEffects.registerTo(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void onPotionRegistry(RegistryEvent.Register<Potion> event) {
		LOGGER.info("Registering potions...");
		CustomPotions.registerTo(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void onTileEntityTypeRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
		LOGGER.info("Registering tileentity types...");
		CustomTileEntityTypes.registerTo(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void onParticleTypeRegistry(RegistryEvent.Register<ParticleType<?>> event) {
		LOGGER.info("Registering tileentity types...");
		CustomParticleTypes.registerTo(event.getRegistry());
	}
}
