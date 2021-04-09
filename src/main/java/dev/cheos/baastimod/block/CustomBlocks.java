package dev.cheos.baastimod.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class CustomBlocks {
	public static final Block UNDYING_CORE = new UndyingCoreBlock();
	public static final Block ANCIENT_FRAGMENT = new AncientFragmentBlock();
	
	public static void registerTo(IForgeRegistry<Block> registry) {
		registry.register(UNDYING_CORE);
		registry.register(ANCIENT_FRAGMENT);
	}
	
	public static void registerToAsBlockItem(IForgeRegistry<Item> registry) {
		registry.register(((BlockItemConvertible) UNDYING_CORE).asBlockItem());
		registry.register(((BlockItemConvertible) ANCIENT_FRAGMENT).asBlockItem());
	}
}
