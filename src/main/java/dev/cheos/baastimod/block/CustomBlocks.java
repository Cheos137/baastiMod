package dev.cheos.baastimod.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.IForgeRegistry;

public class CustomBlocks {
	public static final Block UNDYING_CORE = new UndyingCoreBlock();
	public static final Block GILDED_EMERALD = new SimpleBlock("gilded_emerald",
			AbstractBlock.Properties.of(Material.METAL, MaterialColor.EMERALD)
			.strength(1.5F, 6.0F)
			.requiresCorrectToolForDrops()
			.harvestTool(ToolType.PICKAXE)
			.harvestLevel(2));
	
	public static void registerTo(IForgeRegistry<Block> registry) {
		registry.registerAll(UNDYING_CORE,
				GILDED_EMERALD);
	}
	
	public static void registerToAsBlockItem(IForgeRegistry<Item> registry) {
		registry.registerAll(((BlockItemConvertible) UNDYING_CORE).asBlockItem(),
				((BlockItemConvertible) GILDED_EMERALD).asBlockItem());
	}
}
