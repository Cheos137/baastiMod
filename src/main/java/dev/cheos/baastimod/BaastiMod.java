package dev.cheos.baastimod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.cheos.baastimod.block.tileentity.CustomTileEntityTypes;
import dev.cheos.baastimod.block.tileentity.UndyingCoreTileEntityRenderer;
import dev.cheos.baastimod.net.BaastiModPacketHandler;
import dev.cheos.baastimod.net.ClientNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BaastiMod.MODID)
@EventBusSubscriber(bus = Bus.FORGE, modid = BaastiMod.MODID)
public class BaastiMod {
	private static final Logger LOGGER = LogManager.getLogger();
	private static MinecraftServer server;
	public static final String MODID = "baastimod";
	
	public BaastiMod() {
		LOGGER.info("Ready to take over new worlds? Let's go!");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
		
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void common(FMLCommonSetupEvent event)   { BaastiModPacketHandler.init(); }
	public void client(FMLClientSetupEvent event)   {
		new ClientNetHandler(event.getMinecraftSupplier().get());
		ClientRegistry.bindTileEntityRenderer(CustomTileEntityTypes.UNDYING_CORE, UndyingCoreTileEntityRenderer::new);
	}
	
	@SubscribeEvent
	public void server(FMLServerStartingEvent event) { server = event.getServer(); }
	
	public static MinecraftServer server() {
		return server;
	}
}
