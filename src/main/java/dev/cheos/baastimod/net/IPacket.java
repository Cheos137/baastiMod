package dev.cheos.baastimod.net;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;

public interface IPacket<T extends INetHandler> {
	void read(PacketBuffer buffer) throws IOException;
	void write(PacketBuffer buffer) throws IOException;
	void handle(T handler);
	
	default boolean isSkippable() {
		return false;
	}
}
