package dev.cheos.baastimod.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.network.play.ClientPlayNetHandler;

@Deprecated
@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlayNetHandler {
//	@Shadow
//	private Minecraft minecraft;
//	@Shadow
//	private ClientWorld level;
//
//
//	@Inject(at = @At("HEAD"), method = "handleEntityEvent(Lnet/minecraft/network/play/server/SEntityStatusPacket)", cancellable = true)
//	private void handleEntityEvent(SEntityStatusPacket packet, CallbackInfo callback) {
//		PacketThreadUtil.ensureRunningOnSameThread(packet, this, this.minecraft);
//		Entity e0 = packet.getEntity(this.level);
//		if (e0 != null) {
//
//			callback.cancel();
//		}
//
//	}
}
