package dev.cheos.baastimod.particle;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.registries.IForgeRegistry;

public class CustomParticleTypes {
	public static final BasicParticleType BETTER_TOTEM = new BasicParticleType(false);

	public static void registerTo(IForgeRegistry<ParticleType<?>> registry) {
		BETTER_TOTEM.setRegistryName(BaastiMod.MODID, "better_totem");
		registry.register(BETTER_TOTEM);
	}
	
	@SuppressWarnings("resource")
	public static void registerFactories() {
		Minecraft.getInstance().particleEngine.register(CustomParticleTypes.BETTER_TOTEM, BetterTotemParticleFactory::new);
	}
}
