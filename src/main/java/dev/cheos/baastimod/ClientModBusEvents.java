package dev.cheos.baastimod;

import dev.cheos.baastimod.particle.CustomParticleTypes;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = BaastiMod.MODID, value = Dist.CLIENT)
public class ClientModBusEvents {
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			event.addSprite(new ResourceLocation(BaastiMod.MODID, "entity/undying_core_totem"));
			event.addSprite(new ResourceLocation(BaastiMod.MODID, "entity/undying_core_box"));
		}
	}
	
	@SubscribeEvent
	public static void onParticleFactoryRegistry(ParticleFactoryRegisterEvent event) {
		CustomParticleTypes.registerFactories();
	}
}
