package dev.cheos.baastimod.block;

import dev.cheos.baastimod.BaastiMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class SimpleBlock extends Block implements BlockItemConvertible {

	public SimpleBlock(String id, AbstractBlock.Properties properties) {
		super(properties);
		setRegistryName(BaastiMod.MODID, id);
	}

	@Override
	public BlockItem asBlockItem() {
		return toNewBlockItem(this);
	}

	@Override
	public Item.Properties getBlockItemProperties() {
		return new Item.Properties().rarity(Rarity.UNCOMMON).tab(ItemGroup.TAB_BUILDING_BLOCKS);
	}
}
