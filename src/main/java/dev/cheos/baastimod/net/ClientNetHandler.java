package dev.cheos.baastimod.net;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

public class ClientNetHandler implements INetHandler {
	private static ClientNetHandler INSTANCE;
	
	private Minecraft minecraft;
	
	public ClientNetHandler(Minecraft minecraft) {
		this.minecraft = minecraft;
		
		INSTANCE = this;
	}
	
	public static final ClientNetHandler instance() {
		return INSTANCE;
	}
	
	public void handleUndyingActivatedEvent(SUndyingActivatedPacket packet) {
		Entity entity = packet.getEntity(this.minecraft.level);
		ItemStack stack = packet.getItemStack();
		
		if (entity != null && stack != null) {
			this.minecraft.particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
			this.minecraft.level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);
			
			if (entity == this.minecraft.player)
				this.minecraft.gameRenderer.displayItemActivation(stack);
		}
	}
}
