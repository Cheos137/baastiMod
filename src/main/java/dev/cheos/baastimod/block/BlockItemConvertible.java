package dev.cheos.baastimod.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public interface BlockItemConvertible {
	BlockItem asBlockItem();
	Item.Properties getBlockItemProperties();
	
	default BlockItem toNewBlockItem(Block block) {
		BlockItem blockitem = new BlockItem(block, getBlockItemProperties());
		blockitem.setRegistryName(block.getRegistryName());
		return blockitem;
	}
}
