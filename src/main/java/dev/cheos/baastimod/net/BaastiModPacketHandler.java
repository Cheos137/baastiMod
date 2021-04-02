package dev.cheos.baastimod.net;

import java.io.IOException;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BaastiModPacketHandler {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String PROTOCOL_VERSION = "1";
	private static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(BaastiMod.MODID, "main"),
			() -> PROTOCOL_VERSION,
			BaastiModPacketHandler::checkVersionClient,
			BaastiModPacketHandler::checkVersionServer);
	private static int packetID = 0;
	
	@SuppressWarnings("unchecked")
	public static void init() {
		register((Class<? extends IPacket<INetHandler>>) SUndyingActivatedPacket.class, SUndyingActivatedPacket::new);
	}
	
	public static void sendToClient(IPacket<? extends INetHandler> packet, ServerPlayerEntity to) {
		channel.send(PacketDistributor.PLAYER.with(() -> to), packet);
	}
	
	public static void sendToClient(IPacket<? extends INetHandler> packet, Entity tracking) {
		channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> tracking), packet);
	}
	
	public static void sendToServer(IPacket<? extends INetHandler> packet) {
		channel.sendToServer(packet);
	}
	
	@SuppressWarnings("unchecked")
	private static <MSG extends IPacket<INetHandler>> void register(Class<MSG> clazz, Supplier<IPacket<? extends INetHandler>> provider) {
		channel.registerMessage(packetID++,
				clazz,
				BaastiModPacketHandler::encode,
				buf -> (MSG) decode(buf, provider),
				BaastiModPacketHandler::handle);
	}
	
	private static void encode(IPacket<INetHandler> packet, PacketBuffer buffer) {
		try {
			packet.write(buffer);
		} catch (IOException e) {
			LOGGER.error("Exception encoding packet " + packet.getClass().getSimpleName(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static IPacket<INetHandler> decode(PacketBuffer buffer, Supplier<IPacket<? extends INetHandler>> provider) {
		try {
			IPacket<INetHandler> packet = (IPacket<INetHandler>) provider.get();
			packet.read(buffer);
			return packet;
		} catch(IOException e) {
			LOGGER.error("Exception decoding packet", e);
			return null;
		}
	}
	
	private static void handle(IPacket<INetHandler> packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			switch (ctx.get().getDirection()) {
				case PLAY_TO_CLIENT:
					packet.handle(ClientNetHandler.instance());
					break;
				case PLAY_TO_SERVER:
					break;
				default:
					break;
			}
		});
		ctx.get().setPacketHandled(true);
	}
	
	private static boolean checkVersionClient(String version) {
		return NetworkRegistry.ABSENT.equals(version)
				|| NetworkRegistry.ACCEPTVANILLA.equals(version)
				|| PROTOCOL_VERSION.equals(version);
	}
	
	private static boolean checkVersionServer(String version) {
		return PROTOCOL_VERSION.equals(version);
	}
}
