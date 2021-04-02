package dev.cheos.baastimod.net;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class SUndyingActivatedPacket implements IPacket<ClientNetHandler> {
	private int entityId;
	private ItemStack stack;
	
	public SUndyingActivatedPacket() { }
	
	public SUndyingActivatedPacket(Entity entity, ItemStack stack) {
		this.entityId = entity.getId();
		this.stack    = stack;
	}
	
	@Override
	public void read(PacketBuffer buffer) throws IOException {
		this.entityId = buffer.readInt();
		this.stack    = buffer.readItem();
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.entityId);
		buffer.writeItemStack(this.stack, true);
	}

	@Override
	public void handle(ClientNetHandler handler) {
		handler.handleUndyingActivatedEvent(this);
	}
	
	public Entity getEntity(World world) {
		return world.getEntity(this.entityId);
	}
	
	public ItemStack getItemStack() {
		return this.stack;
	}
}
