package dev.cheos.baastimod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.cheos.baastimod.effect.CustomEffects;
import dev.cheos.baastimod.enchantment.CustomEnchantments;
import dev.cheos.baastimod.net.BaastiModPacketHandler;
import dev.cheos.baastimod.net.ClientNetHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(BaastiMod.MODID)
public class BaastiMod {
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static final String MODID = "baastimod";
	
	public BaastiMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
		
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void common(FMLCommonSetupEvent event)   { BaastiModPacketHandler.init(); }
	public void client(FMLClientSetupEvent event)   { new ClientNetHandler(event.getMinecraftSupplier().get()); }
	@SubscribeEvent
	public void server(FMLServerStartedEvent event) { }
	
	@EventBusSubscriber(bus = Bus.MOD, modid = BaastiMod.MODID)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void onEnchantmentRegistry(RegistryEvent.Register<Enchantment> event) {
        	IForgeRegistry<Enchantment> enchRegistry = event.getRegistry();
			
			LOGGER.debug("Registering 'undying' enchantment");
            enchRegistry.register(CustomEnchantments.UNDYING);
		}
		
		@SubscribeEvent
		public static void onEffectRegistry(RegistryEvent.Register<Effect> event) {
			IForgeRegistry<Effect> effectRegistry = event.getRegistry();
			
			LOGGER.debug("Registering 'undying' effect");
			effectRegistry.register(CustomEffects.UNDYING);
		}
	}
}
