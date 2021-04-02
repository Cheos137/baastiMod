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
	
	public static void init() {
		register(SUndyingActivatedPacket.class, SUndyingActivatedPacket::new);
	}
	
	public static <T extends INetHandler> void sendToClient(IPacket<T> packet, ServerPlayerEntity to) {
		channel.send(PacketDistributor.PLAYER.with(() -> to), packet);
	}
	
	public static <T extends INetHandler> void sendToClient(IPacket<T> packet, Entity tracking) {
		channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> tracking), packet);
	}
	
	public static <T extends INetHandler> void sendToServer(IPacket<T> packet) {
		channel.sendToServer(packet);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends INetHandler, PACKET extends IPacket<T>> void register(Class<PACKET> clazz, Supplier<IPacket<T>> provider) {
		channel.registerMessage(packetID++,
				clazz,
				BaastiModPacketHandler::encode,
				buf -> (PACKET) decode(buf, provider),
				BaastiModPacketHandler::handle);
	}
	
	private static <T extends INetHandler> void encode(IPacket<T> packet, PacketBuffer buffer) {
		try {
			packet.write(buffer);
		} catch (IOException e) {
			LOGGER.error("Exception encoding packet " + packet.getClass().getSimpleName(), e);
		}
	}
	
	private static <T extends INetHandler> IPacket<T> decode(PacketBuffer buffer, Supplier<IPacket<T>> provider) {
		try {
			IPacket<T> packet = provider.get();
			packet.read(buffer);
			return packet;
		} catch(IOException e) {
			LOGGER.error("Exception decoding packet", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends INetHandler> void handle(IPacket<T> packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			switch (ctx.get().getDirection()) {
				case PLAY_TO_CLIENT:
					packet.handle((T) ClientNetHandler.instance());
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
